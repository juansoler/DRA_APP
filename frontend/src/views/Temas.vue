<template>
  <v-container grid-list-xl>
    <v-dialog v-model="dialog" max-width="500px">
            <template v-slot:activator="{ on }">
              <v-btn
                color="grey lighten-1"
              
                class="mb-2"
                v-on="on"
                >Crear Tema</v-btn
              >
            </template>

            <v-card>
              <v-card-title>
                <span class="headline"> Nuevo test tema </span>
              </v-card-title>

              <v-card-text>
                <v-container fill-height>
                  <v-row align="center" justify="center">
                    <v-col cols="12" sm="12" md="12">
                      <v-text-field
                        v-model="tema.nombreTema"
                        required
                        label="Título del tema"
                      ></v-text-field>
                    </v-col>
                     <v-col cols="6" sm="6">
                      
                    </v-col>
                    <v-col cols="12" sm="12" md="12">
                      <v-text-field
                        v-model="tema.tema"
                        required
                        label="Número de Tema"
                      ></v-text-field>
                      <v-text-field
                        v-model="tema.descripcion"
                        required
                        label="Descripción"
                      ></v-text-field>
                     
                    </v-col>
                  </v-row>
                </v-container>
              </v-card-text>

              <v-card-actions>
                <div class="flex-grow-1"></div>
                <v-btn color="blue darken-1" text @click="cancelarAgregar">Cancel</v-btn>
                <v-btn color="blue darken-1" text @click="agregarTema"
                  >Guardar y cerrar</v-btn
                >
              </v-card-actions>
            </v-card>
          </v-dialog>
          <v-dialog v-model="dialogEdit" max-width="500px">
            

            <v-card>
              <v-card-title>
                <span class="headline"> Editar tema </span>
              </v-card-title>

              <v-card-text>
                <v-container fill-height>
                  <v-row align="center" justify="center">
                    <v-col cols="12" sm="12" md="12">
                      <v-text-field
                        v-model="tema.nombreTema"
                        required
                        label="Título del tema"
                      ></v-text-field>
                    </v-col>
                     <v-col cols="6" sm="6">
                      
                    </v-col>
                    <v-col cols="12" sm="12" md="12">
                      <v-text-field
                        v-model="tema.tema"
                        required
                        label="Número de Tema"
                      ></v-text-field>
                      <v-text-field
                        v-model="tema.descripcion"
                        required
                        label="Descripción"
                      ></v-text-field>
                     
                    </v-col>
                  </v-row>
                </v-container>
              </v-card-text>

              <v-card-actions>
                <div class="flex-grow-1"></div>
                <v-btn color="blue darken-1" text  @click="cancelarEditar()">Cancelar editar</v-btn>
                <v-btn color="blue darken-1" text @click="editar"
                  >Guardar y cerrar</v-btn
                >
              </v-card-actions>
            </v-card>
          </v-dialog>
    <v-layout column wrap>
      <v-flex md6>
        <v-card class="mb-3" v-for="(item,index) in temas" :key="index">
          <v-card-text>
            <v-chip-group >
 <v-chip label color="blue" text-color="white" class="ml-0 mb-3">
              <v-icon left>mdi-text-subject</v-icon>
              Tema {{item.tema}}: {{item.nombreTema}}
            </v-chip>
            <v-chip  @click="editarTarea(index)" label color="warning" >
              <v-icon>mdi-pencil</v-icon>
              
            </v-chip>
             <v-chip @click="eliminarTema(item)" label color="error" >
              <v-icon>mdi-delete</v-icon>
              
            </v-chip>

            </v-chip-group>
           
            <p>{{item.descripcion}}</p>
            <p>{{item._links.self.href}}</p>

           <!-- <v-btn :to="{ name: 'preguntas', params: { id: item }}" color="success" class="ml-0 mx-2" >Editar Preguntas</v-btn>
            -->
            <v-btn @click="editarPreguntas(item)" color="success" class="ml-0 mx-2" >Editar Preguntas</v-btn>
            <v-btn @click="hacerExamen(item)" color="secondary" class="ml-0 mx-2" >Hacer examen</v-btn>

          </v-card-text>
        </v-card>
      </v-flex>
      <!-- <v-flex md6 v-if="!formAgregar">
        <v-card class="mb-3 pa-5">
          <v-form @submit.prevent="editar">
            <v-text-field v-model="tema.nombreTema" label="Titulo del tema"></v-text-field>
            <v-text-field v-model="tema.tema" label="Numero del tema"></v-text-field>
            <v-textarea v-model="tema.descripcion" label="Descripción del tema"></v-textarea>
            <v-btn type="submit" class="ml-0 mx-2" color="warning">Editar tema</v-btn>
            <v-btn @click="cancelarEditar()" class="ml-0 mx-2" color="error">Cancelar</v-btn>

          </v-form>
        </v-card>
      </v-flex>

      <v-flex md6 v-if="formAgregar">
        <v-card class="mb-3 pa-5">
          <v-form @submit.prevent="agregarTema">
            <v-text-field v-model="tema.nombreTema" label="Título del tema"></v-text-field>
            <v-text-field v-model="tema.tema" label="Numero del tema"></v-text-field>

            <v-textarea v-model="tema.descripcion" label="Descripción del tema"></v-textarea>
            <v-btn type="submit" block color="success">Agregar tema</v-btn>
          </v-form>
        </v-card>
      </v-flex> -->
    </v-layout>
    


    <v-snackbar v-model="snackbar">
      {{ mensaje }}
      <v-btn color="pink" text @click="snackbar = false">Close</v-btn>
    </v-snackbar>
  </v-container>
</template>

<script>
import { mapState, mapActions, mapGetters } from "vuex";
import { async } from "q";

export default {
  data: vm => ({
      temasLocal: [],
      tema: {
      
      },
      temaSelected:{
      type: Object,
      default: () => {}
    },
    dialog: false,
    dialogEdit: false,
      
      snackbar: false,
      mensaje: "",
      formAgregar: true,
      indexTema: ""
    }),
    created() {
      console.log("title " + process.env.VUE_APP_TITLE )
    console.log(this.$hostname)
    this.loadTemas();
 
  },
  computed: {
    ...mapState(["temas", "exist"]),
    ...mapGetters(["arrayFiltradoEntradas"]),
  },
  props:{
    
  },
  watch:{
    dialog(val) {
      val // || this.close();
    }
  },
  methods: {
    ...mapActions([
      "loadTemas",
      "saveTema",
      "editTema",
      "deleteTema"
    ]),
    async agregarTema() {
      if (this.tema.nombreTema === "" || this.tema.descripcion === "") {
        this.snackbar = true;
        this.mensaje = "Los campos están vacios";
      } else {
        this.tema.id = this.tema.tema;
        console.log("tema nuevo");
        console.log(this.tema);
        await this.saveTema(this.tema);
        console.log("exist " + this.exist)
        
        if (this.exist){
          console.log("dentro if exist")
          this.snackbar = true;
          this.mensaje = "Ese tema ya existe"
          this.$store.commit("setExist", false)
        }else {
        this.snackbar = true;
        this.mensaje = "Tema Agregada";
        }

        /* this.listaTareas.push({
          id: Date.now(),
          titulo: this.titulo,
          descripcion: this.descripcion
        });
        */
       this.tema = {
       
      };
        this.dialog = false
       
      }
    },
    close() {
      this.dialog = false;
      setTimeout(() => {
        this.tema = Object.assign({}, {});
      }, 300);
    },
    eliminarTema(tema) {
      /* this.listaTareas = this.listaTareas.filter(e => {
        return e.id != id;
      });
      */
      this.deleteTema(tema);

      
    },
    cancelarEditar(){
      this.formAgregar = !this.formAgregar;
      this.tema = {}
      this.dialogEdit = false
    },
     cancelarAgregar(){
      this.formAgregar = !this.formAgregar;
      this.tema = {}
      this.dialog = false
    },
    editarPreguntas(temaParam){
      // this.$route   ._links.self.href.split('/')[5]
    
      // this.$router.push({ name: 'preguntas', params: { id: '123' } })

      this.temaSelected = temaParam;
      console.log("temaSelected")
      console.log(this.temaSelected) 
      this.$router.push({ name: 'preguntas', params: { temaSelected: this.temaSelected } })

      //this.$router.push({ name: 'preguntas' })

    },
    hacerExamen(temaParam){
      // this.$route   ._links.self.href.split('/')[5]
    
      // this.$router.push({ name: 'preguntas', params: { id: '123' } })

      this.temaSelected = temaParam;
      console.log("hacerExamen ")
      console.log(this.temaSelected) 
      this.$router.push({ name: 'examen', params: { temaSelected: this.temaSelected } })

      //this.$router.push({ name: 'preguntas' })

    },
    editarTarea(index) {
      this.formAgregar = false;
      console.log("elemento a editar")
      console.log(this.temas[index])
      this.tema.nombreTema = this.temas[index].nombreTema;
      this.tema.tema = this.temas[index].tema;
      this.tema.descripcion = this.temas[index].descripcion;
      console.log(this.tema)
      this.indexTema = index;
      this.dialogEdit = true;

    },
    editar() {
      this.temas[this.indexTema].nombreTema = this.tema.nombreTema;
      this.temas[this.indexTema].tema = this.tema.tema;
      this.temas[this.indexTema].descripcion = this.tema.descripcion;
      console.log("edit Tema");
      console.log(this.temas[this.indexTema])
      this.editTema(this.temas[this.indexTema]);
      this.formAgregar = true;
      this.nombreTema = "";
      this.tema = "";
      this.snackbar = true;
      this.mensaje = "Tema editada";
      this.dialogEdit = false
    }
  }
};
</script>