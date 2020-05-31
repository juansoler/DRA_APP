<template>
<v-container>
  <v-card
    class="mx-auto"
    max-width="500"
     v-if="questionIndex < preguntas.length"
          v-bind:key="questionIndex"
  >
  <v-card-title>{{"Tema " + this.temaSelected.tema +": "+ this.temaSelected.nombreTema}}</v-card-title>
      <v-progress-linear
          v-if="true"
          :value="(questionIndex / preguntas.length) * 100"
                max="100"
          color="green"
          absolute
          height="7"
        ></v-progress-linear>
     <v-spacer></v-spacer>
     <v-card-subtitle><h2>{{ preguntas[questionIndex].pregunta }}</h2></v-card-subtitle>
     <v-spacer></v-spacer>

    <v-list>
      <v-list-item-group>
        <v-list-item         
        >
         
          <v-list-item-content>
            <v-list-item-title               @click="selectOption(0)"
>{{ 0 | charIndex }}. {{ preguntas[questionIndex].respuestaA }}
</v-list-item-title>
          </v-list-item-content>
        </v-list-item>
        <v-list-item         
        >
         
          <v-list-item-content>               

            <v-list-item-title @click="selectOption(1)">{{ 1 | charIndex }}. {{ preguntas[questionIndex].respuestaB }}
</v-list-item-title>
          </v-list-item-content>
        </v-list-item>
        <v-list-item         
        >
         
          <v-list-item-content               @click="selectOption(2)"
>
            <v-list-item-title>{{ 2 | charIndex }}. {{ preguntas[questionIndex].respuestaC }}
</v-list-item-title>
          </v-list-item-content>
        </v-list-item>
        <v-list-item         
        >
         
          <v-list-item-content               @click="selectOption(3)"
>
            <v-list-item-title>{{ 3 | charIndex }}. {{ preguntas[questionIndex].respuestaD }}
</v-list-item-title>
          </v-list-item-content>
        </v-list-item>
      </v-list-item-group>
    </v-list>
<v-card-actions >
      <v-btn
        :disabled='atrasDisabled'
        color="orange"
        
                        v-on:click="prev()"

      >
        Atrás
      </v-btn>
        <v-spacer></v-spacer>

      <v-btn
        :color="this.userResponses[questionIndex] == null ? 'blue' : 'green'"
        
        align-end
                        v-on:click="next()"
                :disabled="questionIndex >= preguntas.length"

      >
        {{ userResponses[questionIndex] == null ? "Saltar" : "Siguiente" }}
      </v-btn>
    </v-card-actions>
    
    <v-card-title class="justify-center">Tiempo restante: {{timerCount}}</v-card-title>
  </v-card>
   <v-card
    class="mx-auto"
    max-width="500"
     v-if="questionIndex >= preguntas.length"
          v-bind:key="questionIndex"
  >

    <v-card-title>               Lo has hecho {{ score() > 7 ? "muy bien!" : "normal, tienes que estudiar más." }} 
</v-card-title>
  <v-spacer></v-spacer>

<v-card-subtitle><h2>            Puntuación total: {{ score() }} / {{ preguntas.length }}</h2>
</v-card-subtitle>
   <v-card-actions class="justify-center">
  <v-btn @click="restart()">
    Reiniciar test
  </v-btn>
</v-card-actions>
   </v-card>
   </v-container>
   
</template>

<script>
import { mapState, mapActions, mapGetters } from "vuex";
import jsPDF from "jspdf";
import { firebase } from "@/firebase";

import "jspdf-autotable";
import { async } from "q";


export default {
  data: vm => ({
    user: "",
    timerCount: 5,
    userResponses: null,
    questionIndex: 0,
    isActive: false,

    texto: "",
    menu1: false,
    menu2: false,
    items: ["A", "B", "C", "D"],
    rules: {
      required: value => !!value || "Required.",

      email: value => {
        const pattern = /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
        return pattern.test(value) || "Invalid e-mail.";
      }
    },

    dialog: false,
    dialogEdit: false,

    editedIndex: -1,
    nuevaPregunta: {
      tema: "",
      numeroPregunta: Number,
      numeroTema: Number,
      pregunta: "",
      respuestaA: "",
      respuestaB: "",
      respuestaC: "",
      respuestaD: "",
      respuestaCorrecta: ""
    },
    preguntaEdited: {
      tema: "",
      numeroPregunta: Number,
      numeroTema: Number,
      pregunta: "",
      respuestaA: "",
      respuestaB: "",
      respuestaC: "",
      respuestaD: "",
      respuestaCorrecta: ""
    },
    editedItem: {
      pregunta: "",
      respuestaA: "",
      respuestaB: "",
      respuestaC: "",
      respuestaD: "",
      respuestaCorrecta: ""
    },
    defaultItem: {
      tema: "",
      numeroPregunta: Number,
      numeroTema: Number,
      pregunta: "",
      respuestaA: "",
      respuestaB: "",
      respuestaC: "",
      respuestaD: "",
      respuestaCorrecta: ""
    },
    atrasDisabled: false
  }),

  filters: {
    charIndex: function(i) {
      return String.fromCharCode(97 + i);
    }
  },
  created() {
     console.log("PARAMS")
    console.log(this.temaSelected)
    this.loadPreguntasPorTema(this.temaSelected.id);
    this.userResponses = Array(this.preguntas.length).fill(null)
  },
  computed: {
    ...mapState(["preguntas", "exist"]),
    // ...mapGetters(["arrayFiltradoPreguntas"]),
     isDisabled: function(){
        return !this.atrasDisabled;
    }
  },
  props: {
    temaSelected: {
      type: Object,
      default: () => {}
    }
  },
  watch: {

            timerCount: {
                handler(value) {
                    
                    if (value > 0) {
                        setTimeout(() => {
                            this.timerCount--;
                        }, 1000);
                    }else{
                      /* this.next()
                      this.atrasDisabled = true */
                      this.questionIndex = this.preguntas.length
                    }

                },
                immediate: true // This ensures the watcher is triggered upon creation
            }

        },
  methods: {
    ...mapActions(["loadPreguntasPorTema"]),
    restart: function() {
      this.questionIndex = 0;
      this.userResponses = Array(this.preguntas.length).fill(null);
      this.timerCount = 30
    },
    selectOption: function(index) {
      // conjunto, key, value
      if (this.userResponses[this.questionIndex] == null ){
      this.$set(this.userResponses, this.questionIndex, index);

      }else {
              this.$set(this.userResponses, this.questionIndex, null);

      }

      /* if (index == 0){
         this.$set(this.userResponses, this.preguntas.respuestaA, index);

         }
         if (index == 1){
         this.$set(this.userResponses, this.preguntas.respuestaB, index);

         }
         if (index == 2){
         this.$set(this.userResponses, this.preguntas.respuestaC, index);

         }
         if (index == 0){
         this.$set(this.userResponses, this.preguntas.respuestaD, index);

         }
         */

      //console.log(this.userResponses);
    },
    next: function() {
      if (this.questionIndex < this.preguntas.length) {
          this.questionIndex++;

      } 
    },

    prev: function() {
      if (this.preguntas.length > 0) this.questionIndex--;
    },
    // Return "true" count in userResponses
    score: function() {
      var score = 0;
      for (let i = 0; i < this.userResponses.length; i++) {
       
        var respuestaElegida = "error";
        console.log("this.userResponses[i]")
        console.log(this.userResponses[i])
        switch (this.userResponses[i]) {
          case 0:
            respuestaElegida = "A";
            break;
          case 1:
            respuestaElegida = "B";
            break;
          case 2:
            respuestaElegida = "C";
            break;
          case 3:
            respuestaElegida = "D";
            break;
          default:
            break;
        }
         console.log("this.preguntas[i].respuestaCorrecta")
         console.log(this.preguntas[i].respuestaCorrecta)
         console.log("this.respuestaElegida")
         console.log(respuestaElegida)
        if (
          this.preguntas[i].respuestaCorrecta == respuestaElegida
          // esto esta mal....tengo que cambiar la A,B,C,D por el index

          // this.preguntas[i].responses[this.userResponses[i]].correct
        ) {
          score = score + 1;
        }
      }
      return score;

      //return this.userResponses.filter(function(val) { return val }).length;
    }
  }
};
</script>
