<template>
  <div ref="content">
    <v-data-table
      :headers="headers"
      :items="arrayFiltradoPreguntas"
      sort-by="numeroPregunta"
      class="elevation-1"
    >
      <template v-slot:top>
        <v-layout row wrap>
          <v-flex md-6>
            <v-form @submit.prevent="buscador(texto)" class="mx-10">
              <v-text-field
                type="text"
                placeholder="Buscar..."
                v-model="texto"
                v-on:keyup="buscador(texto)"
              />
            </v-form>
          </v-flex>

          <v-flex mr-7 text-right>
            <v-btn
              @click="generarPDF"
              color="secondary"
              dark
              class="mb-2 mt-5 justify-end"
              >Imprimir listado</v-btn
            >
          </v-flex>
        </v-layout>

        <v-toolbar flat color="white">
          <v-toolbar-title>Preguntas </v-toolbar-title>

          <v-divider class="mx-4" inset vertical></v-divider>

          <div class="flex-grow-1"></div>
          <v-dialog v-model="dialog" max-width="500px">
            <template v-slot:activator="{ on }">
              <v-btn
                color="primary"
                @click="nuevaEntrada"
                dark
                class="mb-2"
                v-on="on"
                >Ingresar preguntas</v-btn
              >
            </template>

            <v-card>
              <v-card-title>
                <span class="headline">{{ formTitle }}</span>
              </v-card-title>

              <v-card-text v-cloak @drop.prevent="addFile" @dragover.prevent>
                <v-container fill-height>
                  <v-row align="center" justify="center">
                    <v-col cols="12" sm="12" md="12">
                      <v-text-field
                        ref="inputPregunta"
                        v-model="nuevaPregunta.pregunta"
                        required
                        label="Pregunta"
                      ></v-text-field>
                    </v-col>
                     <v-col cols="6" sm="6">
                      <v-row>
                        <v-select
                          v-model="nuevaPregunta.respuestaCorrecta"
                          required
                          :items="items"
                          label="Respuesta Correcta"
                        ></v-select>
                        <!-- v-text-field
                          @keyup.enter.native="agregarPreguntaSinSalir"
                          v-model="nuevaPregunta.respuestaCorrecta"
                          required
                          label="Respuesta Correcta"
                        ></v-text-field -->
                      </v-row>
                    </v-col>
                    <v-col cols="12" sm="12" md="12">
                      <v-text-field
                        v-model="nuevaPregunta.respuestaA"
                        required
                        label="Respuesta A"
                      ></v-text-field>
                      <v-text-field
                        v-model="nuevaPregunta.respuestaB"
                        required
                        label="Respuesta B"
                      ></v-text-field>
                      <v-text-field
                        v-model="nuevaPregunta.respuestaC"
                        required
                        label="Respuesta C"
                      ></v-text-field>
                      <v-text-field
                                                @keyup.enter.native="agregarPreguntaSinSalir"

                        v-model="nuevaPregunta.respuestaD"
                        required
                        label="Respuesta D"
                      ></v-text-field>
                    </v-col>
                   

                    <!--
                     <v-menu
                      ref="menu1"
                      v-model="menu1"
                      :close-on-content-click="false"
                      transition="scale-transition"
                      offset-y
                      full-width
                      max-width="290px"
                      min-width="290px"
                    >
                      <template v-slot:activator="{ on }">
                        <v-text-field
                          v-model="dateFormatted"
                          label="Fecha de entrada"
                          hint="DD/MM/YYYY format"
                          persistent-hint
                          prepend-icon="mdi-calendar"
                          @blur="date = parseDate(dateFormatted)"
                          v-on="on"
                        ></v-text-field>
                      </template>
                      <v-date-picker :max="maxDate" v-model="date" no-title @input="menu1 = false"></v-date-picker>
                    </v-menu>
                    

                    <v-col cols="12" sm="12" md="12">
                      <div>
                        <v-card-text>Arrastra aqui para subir un archivo</v-card-text>

                        <ul>
                          <li v-for="(file,index) in files" :key="index">
                            {{ file.name }} ({{ file.size }} kb)
                            <button
                              @click="removeFile(file)"
                              title="Remove"
                            >X</button>
                          </li>
                        </ul>

                        <button @click="upload">Upload</button>
                      </div>
                    </v-col>
                    -->
                  </v-row>
                </v-container>
              </v-card-text>

              <v-card-actions>
                <div class="flex-grow-1"></div>
                <v-btn color="blue darken-1" text @click="close">Cancel</v-btn>
                <v-btn color="blue darken-1" text @click="agregarEntrada"
                  >Guardar y cerrar</v-btn
                >
              </v-card-actions>
            </v-card>
          </v-dialog>

          <v-dialog v-model="dialogEdit" max-width="500px">
            <v-card>
              <v-card-title>
                <span class="headline">{{ formTitle }}</span>
              </v-card-title>

              <v-card-text v-cloak @drop.prevent="addFile" @dragover.prevent>
                <v-container fill-height>
                  <v-row align="center" justify="center">
                    <v-col cols="12" sm="12" md="12">
                      <v-text-field
                        v-model="preguntaEdited.pregunta"
                        required
                        label="Pregunta"
                      ></v-text-field>
                    </v-col>
                    <v-col cols="12" sm="12" md="12">
                      <v-text-field
                        v-model="preguntaEdited.respuestaA"
                        required
                        label="Respuesta A"
                      ></v-text-field>
                      <v-text-field
                        v-model="preguntaEdited.respuestaB"
                        required
                        label="Respuesta B"
                      ></v-text-field>
                      <v-text-field
                        v-model="preguntaEdited.respuestaC"
                        required
                        label="Respuesta C"
                      ></v-text-field>
                      <v-text-field
                        v-model="preguntaEdited.respuestaD"
                        required
                        label="Respuesta D"
                      ></v-text-field>
                    </v-col>
                    <v-col cols="6" sm="6">
                      <v-row>
                        <v-select
                          v-model="preguntaEdited.respuestaCorrecta"
                          required
                          :items="items"
                          label="Respuesta Correcta"
                        ></v-select>
                       
                      </v-row>
                    </v-col>

                    <!--
                     <v-menu
                      ref="menu1"
                      v-model="menu1"
                      :close-on-content-click="false"
                      transition="scale-transition"
                      offset-y
                      full-width
                      max-width="290px"
                      min-width="290px"
                    >
                      <template v-slot:activator="{ on }">
                        <v-text-field
                          v-model="dateFormatted"
                          label="Fecha de entrada"
                          hint="DD/MM/YYYY format"
                          persistent-hint
                          prepend-icon="mdi-calendar"
                          @blur="date = parseDate(dateFormatted)"
                          v-on="on"
                        ></v-text-field>
                      </template>
                      <v-date-picker :max="maxDate" v-model="date" no-title @input="menu1 = false"></v-date-picker>
                    </v-menu>
                    

                    <v-col cols="12" sm="12" md="12">
                      <div>
                        <v-card-text>Arrastra aqui para subir un archivo</v-card-text>

                        <ul>
                          <li v-for="(file,index) in files" :key="index">
                            {{ file.name }} ({{ file.size }} kb)
                            <button
                              @click="removeFile(file)"
                              title="Remove"
                            >X</button>
                          </li>
                        </ul>

                        <button @click="upload">Upload</button>
                      </div>
                    </v-col>
                    -->
                  </v-row>
                </v-container>
              </v-card-text>

              <v-card-actions>
                <div class="flex-grow-1"></div>
                <v-btn color="blue darken-1" text @click="closeEdit">Cancel</v-btn>
                <v-btn color="blue darken-1" text @click="saveEditItem"
                  >Guardar</v-btn
                >
              </v-card-actions>
            </v-card>
          </v-dialog>
        </v-toolbar>
      </template>
      <template v-slot:item.action="{ item }">
        <v-icon small class="mr-2" @click="editItem(item)">mdi-pencil</v-icon>
        <v-icon small @click="deleteItem(item)">mdi-delete</v-icon>
      </template>
      <template v-slot:no-data>
        <v-btn color="primary" @click="initialize">Reset</v-btn>
      </template>
    </v-data-table>

    <!-- <v-container v-if="false" grid-list-xl>
      <v-layout row wrap>
        <v-flex md-6>
          <v-form>
            <v-text-field required label="Nombre"></v-text-field>
            <v-text-field
              required
              :rules="dniRules"
              :counter="9"
              label="DNI"
            ></v-text-field>
          </v-form>
        </v-flex>

        <v-flex md-6>
          <v-form>
            <v-text-field
              required
              filled
              shaped
              label="Apellidos"
            ></v-text-field>

            <v-text-field
              prepend-icon="mdi-phone"
              label="Telefono"
            ></v-text-field>
            <v-text-field
              :rules="[rules.required, rules.email]"
              prepend-icon="mdi-email"
              label="Email"
            ></v-text-field>
          </v-form>
        </v-flex>
      </v-layout>
      <v-flex md-12>
        <v-btn block color="blue">Agregar</v-btn>
      </v-flex>
    </v-container>
    -->
  </div>
</template>

<script>
import { mapState, mapActions, mapGetters } from "vuex";
import jsPDF from "jspdf";
import { firebase } from "@/firebase";

import "jspdf-autotable";
import { async } from "q";

export default {
  data: vm => ({
    texto: "",
    date: new Date().toISOString().substr(0, 10),
    dateFormatted: vm.formatDate(new Date().toISOString().substr(0, 10)),
    menu1: false,
    menu2: false,
    items: ["A", "B", "C", "D"],
    files: [],
    rules: {
      required: value => !!value || "Required.",

      email: value => {
        const pattern = /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
        return pattern.test(value) || "Invalid e-mail.";
      }
    },
    dniRules: [
      v => !!v || "DNI es necesario",
      v => v.length == 9 || "DNI incorrecto"
    ],
    dialog: false,
    dialogEdit: false,
    headers: [
      {
        text: "Numero Pregunta",
        align: "left",
        sortable: false,
        value: "numeroPregunta"
      },
      { text: "Pregunta", value: "pregunta" },
      { text: "RespuestaA", value: "respuestaA" },
      { text: "RespuestaB", value: "respuestaB" },
      { text: "RespuestaC", value: "respuestaC" },
      { text: "RespuestaD", value: "respuestaD" },

      { text: "Acciones", value: "action", align: "right", sortable: false }
    ],
    desserts: [],
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
      respuestaCorrecta: "",
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
      respuestaCorrecta: "",
    },
    editedItem: {
      pregunta: "",
      respuestaA: "",
      respuestaB: "",
      respuestaC: "",
      respuestaD: "",
      respuestaCorrecta: "",

      asunto: "",
      fecha: "",
      jefe1: "",
      jefe2: "",
      jefe3: "",
      jefe4: "",
      jefe5: "",
      file: ""
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
      respuestaCorrecta: "",
    },
    
    
  }),

  created() {
    this.initialize();
    console.log("PARAMS")
    console.log(this.temaSelected)
    if (this.temaSelected == null){
      this.$router.push({ name: 'home' })
    }
    this.loadPreguntasPorTema(this.temaSelected.id);
    //this.loadEntradas();
    this.prueba();
  },
  props: {
    temaSelected: {
      type: Object,
      default: () => {}
    }
  },
  computed: {
    ...mapState(["entradas", "preguntas", "exist"]),
    ...mapGetters(["arrayFiltradoPreguntas"]),
    

    computedDateFormatted() {
      return this.formatDate(this.date);
    },
    formTitle() {
      return this.editedIndex === -1 ? "Nueva pregunta" : "Editar pregunta";
    },
    maxDate() {
      const today = new Date();
      // const dd = today.getUTCDate();
      return this.formatDate2(today);
    },
   sortedArray: function() {
      function compare(a, b) {
        if (a.numeroPregunta < b.numeroPregunta)
          return -1;
        if (a.numeroPregunta > b.numeroPregunta)
          return 1;
        return 0;
      }

      return this.preguntas.sort(compare);
    }
  },

  watch: {
    date(val) {
      this.dateFormatted = this.formatDate(this.date);
    },
    dialog(val) {
      val || this.close();
    }
  },

  methods: {
    ...mapActions([
      "loadPreguntasPorTema",
      "saveEntrada",
      "editarEntrada",
      "loadEntradas",
      "loadPreguntas",
      "loadComentario",
      "buscador",
      "eliminarCliente",
      "savePregunta",
      "deletePregunta",
      "editPregunta"
    ]),
    addFile(e) {
      let droppedFiles = e.dataTransfer.files;
      if (!droppedFiles) return;
      // this tip, convert FileList to array, credit: https://www.smashingmagazine.com/2018/01/drag-drop-file-uploader-vanilla-js/
      [...droppedFiles].forEach(f => {
        this.files.push(f);
      });
    },
    removeFile(file) {
      this.files = this.files.filter(f => {
        return f != file;
      });
    },
    upload() {
      let formData = new FormData();
      this.files.forEach((f, x) => {
        formData.append("file" + (x + 1), f);
      });
      console.log("prueba1");
      fetch("http://localhost:8000/api/upload", {
        method: "POST",
        headers: {
          Accept: "multipart/form-data",
          name: "file"
        },
        mode: "no-cors",

        body: formData
      })
        .then(res => res.json())
        .then(res => {
          console.log("done uploading", res);
        })
        .catch(e => {
          console.error(JSON.stringify(e.message));
        });
    },
    async agregarPreguntaSinSalir(){
      console.log("agregarPreguntaSinSalir")

      this.nuevaPregunta.tema = this.temaSelected._links.self.href
      if (this.preguntas.length === 0){
        this.nuevaPregunta.numeroPregunta = 1
      }else {
      this.nuevaPregunta.numeroPregunta = this.sortedArray[this.sortedArray.length-1].numeroPregunta+1

      }
      this.nuevaPregunta.numeroTema = this.temaSelected.tema
            console.log(this.nuevaPregunta)

      await this.savePregunta(this.nuevaPregunta)
              console.log("exist " + this.exist)
      this.nuevaPregunta = Object.assign({}, this.defaultItem);
      this.$refs.inputPregunta.focus()
      if (this.exist){
          console.log("dentro if exist")
          this.snackbar = true;
          this.mensaje = "Ese tema ya existe"
          this.$store.commit("setExist", false)
        }else {
        this.snackbar = true;
        this.mensaje = "Tarea Agregada";
        }
    },
    agregarEntrada(entrada) {
      console.log("vista agreagr");
      this.editedItem.fecha = this.dateFormatted;
      if (this.editedIndex > -1) {
        console.log("if arriba");
        this.editarCliente(this.editedItem);
        //   Object.assign(this.desserts[this.editedIndex], this.editedItem);
      } else {
        console.log("if abajo");
        console.log(this.editedItem);
        this.saveEntrada(this.editedItem);
        this.loadEntradas();
      }
      this.close();

      //   this.$router.push({name: 'home'})
    },
    
    agregarComentario(entrada) {
      console.log("vista agreagrComentario");
      this.agregarComentario(this.comentario);
      /*
      if (this.editedIndex > -1) {
        console.log("if arriba");
        
        //   Object.assign(this.desserts[this.editedIndex], this.editedItem);
      } else {
        console.log("if abajo");
        console.log(this.editedItem);
        this.saveEntrada(this.editedItem);
        this.loadEntradas();
      }
      */
      this.closeEdit();

      //   this.$router.push({name: 'home'})
    },

    formatDate(date) {
      if (!date) return null;

      const [year, month, day] = date.split("-");
      return `${day}/${month}/${year}`;
    },
    parseDate(date) {
      if (!date) return null;
      const [day, month, year] = date.split("/");
      return `${year}-${month.padStart(2, "0")}-${day.padStart(2, "0")}`;
    },
    // allowedDates: val => parseInt(val.split('-')[2], 10) % 2 === 0,
    formatDate2(date) {
      let month = `${date.getMonth() + 1}`;
      let day = `${date.getDate()}`;
      const year = date.getFullYear();

      if (month.length < 2) month = `0${month}`;
      if (day.length < 2) day = `0${day}`;

      return [year, month, day].join("-");
    },
    initialize() {
      //console.log("parametros " + this.$route.params.id)
      
    },

    async editItem(item) {
      this.editedIndex = this.preguntas.indexOf(item);
      console.log("index " + this.editedIndex);
      this.preguntaEdited = Object.assign({}, item);
      console.log(this.preguntaEdited);
      this.dialogEdit = true;
    },
    async saveEditItem() {

      this.editPregunta(this.preguntaEdited)
      console.log("pruenba con arrray filtardo")
      console.log(this.arrayFiltradoPreguntas[this.editedIndex])
     //this.preguntas[this.editedIndex] = this.preguntaEdited
            console.log(this.arrayFiltradoPreguntas[this.editedIndex])
      this.preguntas.splice(this.editedIndex, 1, this.preguntaEdited); 
      this.closeEdit();

    },

    deleteItem(item) {
      const index = this.preguntas.indexOf(item);
      confirm("Está seguro que desea eliminar esta pregunta?") &&
        this.preguntas.splice(index, 1);
        this.deletePregunta(item)
    },

    close() {
      this.dialog = false;
      setTimeout(() => {
        this.editedItem = Object.assign({}, this.defaultItem);
        this.editedIndex = -1;
      }, 300);
    },
    nuevaEntrada() {
      console.log("nuevaEntrada");
      setTimeout(() => {
        this.editedItem = Object.assign({}, this.defaultItem);
        this.editedIndex = -1;
      }, 300);
    },

    closeEdit() {
      console.log("closeEdit Pulse")
      this.dialogEdit = false;
      setTimeout(() => {
        this.preguntaEdited = Object.assign({}, this.defaultItem);
        this.editedIndex = -1;
      }, 300);
    },

    /* save() {
      this.editedItem.fechaNacimiento = this.dateFormatted;
      if (this.editedIndex > -1) {
        Object.assign(this.desserts[this.editedIndex], this.editedItem);
      } else {
        this.desserts.push(this.editedItem);
      }
      this.close();
    },*/
    generarPDF() {
      var doc = new jsPDF();

      var columns = [
        { title: "Pregunta", datakey: "pregunta" },
        { title: "apellidos", datakey: "apellidos" },
        { title: "dni", datakey: "dni" },
        { title: "telefono", datakey: "telefono" },
        { title: "email", datakey: "email" },
        { title: "fechaNacimiento", datakey: "fechaNacimiento" }
      ];

      // doc.autoTable(columns, this.clientes)
      // doc.autoTable({
      //     head: [['Nombre', 'apellidos', 'dni', 'telefono', 'email', 'fechanacimiento']],
      //     body: this.arrayFiltrado
      // });
      var pdfInMM=210;  // width of A4 in mm
      var pageCenter=pdfInMM/2;
      var line = 10;
      // var image = "data:image/jpeg;base64,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"
      line = line+20;
      doc.setFontSize(20)
      doc.text(pageCenter-30, line, "DRA 2020 - Juan Soler  ")
            doc.setFontSize(10)
      doc.addImage(image, 'JPEG', 53, 17, 20, 20)
      this.preguntas.forEach(element => {
          console.log(element)
          line = line+10
          doc.line(0,line, 210,line)
          line +=10
          doc.text(50, line, element.pregunta);
          line = line+10
          doc.text(20, line, "A)" + element.respuestaA);
                    line = line+10

          doc.text(20, line, "B)" +element.respuestaB);
          line = line+10

          doc.text(20, line, "C)" +element.respuestaC);
           line = line+10

          doc.text(20, line,"D)" + element.respuestaD);

      });

      //const contentHtml = this.$refs.content.innerHTML;
      //  doc.fromHTML(contentHtml, 15, 15, {
      //  width: 170
      //});
      // doc.fromHTML(document.getElementById("preguntas"), 5, 5, {});
      doc.save("sample-file.pdf");
    },
    prueba() {
      console.log("prueba");

      console.log(this.preguntas);
      console.log(this.entradas);
      const db = firebase.firestore();
      const increment = firebase.firestore.FieldValue.increment(1);

      const statsRef = db.collection("prueba").doc("stats");
      const storyRef = db.collection("prueba").doc(`${Math.random()}`);

      const batch = db.batch();

      batch.set(storyRef, { title: "New Story" });
      batch.set(statsRef, { storyCount: increment }, { merge: true });
      batch.commit();
    }
  }
};
</script>
