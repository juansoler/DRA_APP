import Vue from "vue";
import Vuex from "vuex";

import { auth, db } from "@/firebase";
import router from "@/router";
import VueAxios from "vue-axios";
import axios from "axios";



Vue.use(Vuex);
Vue.use(VueAxios, axios);



export default new Vuex.Store({
  state: {
    pregunta: {
      numeroPregunta: "",
      pregunta: "",
      respuestaA: "",
      respuestaB: "",
      respuestaC: "",
      respuestaD: "",
      respuestaCorrecta: ""
    },
    preguntas: [],
    tema: {},
    temas: [],
   
   
    
    
    texto: "",
    
    exist: false
  },
  mutations: {
    
    cargarFirebase(state, payload) {
      state.carga = payload;
    },
    
    setPreguntas(state, preguntas) {
      state.preguntas = preguntas;
    },
    setTemas(state, temas) {
      state.temas = temas;
    },
    addTema(state, payload) {
      state.temas.push(payload);
    },
    addPregunta(state, payload) {
      state.preguntas.push(payload);
    },
    setExist(state, payload) {
      state.exist = payload;
    },
    deleteTemaMutate(state, payload) {
      state.temas = state.temas.filter(e => {
        console.log("e.tema " + e.tema);
        console.log("payload " + payload);
        console.log(e.tema == payload);
        return e.tema != payload;
      });
    },
    deletePreguntaMutate(state, payload) {
      state.preguntas = state.preguntas.filter(e => {
        
        return e.id != payload;
      });
    }
  },
  actions: {
    loadPreguntas({ commit }) {
      console.log("dentro laodEntradas");
      console.log(Vue.prototype.$hostname+"/preguntas")
      Vue.axios
        .get(Vue.prototype.$hostname+"/preguntas")
        .then(r => r.data)
        .then(preguntas => {
          console.log("Preguntas");
          console.log(preguntas);
          commit("setPreguntas", preguntas._embedded.preguntas);
        });
    },
    loadTemas({ commit }) {
      console.log("dentro laodTemas");
      console.log(Vue.prototype.$hostname)
      Vue.axios
        .get(Vue.prototype.$hostname+"/temas")
        .then(r => r.data)
        .then(temas => {
          commit("setTemas", temas._embedded.temas);
        });
    },
    loadPreguntasPorTema({ commit }, id) {
      console.log("dentro loadPreguntasPorTemas " + id);
      Vue.axios
        .get(Vue.prototype.$hostname+"/temas/" + id + "/preguntas")
        .then(r => r.data)
        .then(preguntas => {
          commit("setPreguntas", preguntas._embedded.preguntas);
        });
    },
   
    editTema({ commit }, tema) {
      console.log("editTema");
      Vue.axios
        .put(Vue.prototype.$hostname+"/temas/" + tema.id, tema)
        .then(r => {
          console.log(r);
        });
      /* Vue.axios
        .get("http://localhost:8000/api/temas/"+id +"/preguntas/")
        .then(r => r.data)
        .then(entrada => {
          commit("setPreguntas", entrada._embedded.preguntas);
        });

        */
    },
    editPregunta({ commit }, pregunta) {
      console.log("editPregunta");
      console.log(pregunta);

      Vue.axios
        .put(Vue.prototype.$hostname+"/preguntas/" + pregunta.id, pregunta)
        .then(r => {
          console.log(r);
        });
      /* Vue.axios
        .get("http://localhost:8000/api/temas/"+id +"/preguntas/")
        .then(r => r.data)
        .then(entrada => {
          commit("setPreguntas", entrada._embedded.preguntas);
        });

        */
    },
    async saveTema({ commit }, data) {
      console.log("saveTema");

      try {
        await Vue.axios
          .post(Vue.prototype.$hostname+"/temas/", data)
          .then(r => {
            commit("addTema", r.data);
            console.log("Antes de r");
            console.log(r);
          });
      } catch (error) {
        commit("setExist", true);
      }
    },
    async savePregunta({ commit }, data) {
      console.log("savePreguntas");

      try {
        await Vue.axios
          .post(Vue.prototype.$hostname+"/preguntas/", data)
          .then(r => {
            commit("addPregunta", r.data);
            console.log("Antes de r");
            console.log(r);
          });
      } catch (error) {
        commit("setExist", true);
      }
    },
    async deleteTema({ commit }, tema) {
      console.log("deleteTema con id " + tema._links.self.href);
      Vue.axios.delete(tema._links.self.href).then(r => {
        console.log(r);
      });
      commit("deleteTemaMutate", tema.tema);
    },
    async deletePregunta({ commit }, pregunta) {
      console.log("deletePregunta con id " + pregunta._links.self.href);
      Vue.axios.delete(pregunta._links.self.href).then(r => {
        console.log(r);
      });
      commit("deletePreguntaMutate", pregunta.id);
    },
   

    buscador({ commit, state }, payload) {
      state.texto = payload.toLowerCase();
      console.log("prueba buscador : " + state.texto);
    },
    
  },
  getters: {
    

    arrayFiltrado(state) {
      let arrayFiltrado = [];

      for (let cliente of state.clientes) {
        let nombre = cliente.nombre.toLowerCase();
        // console.log(nombre.indexOf(state.texto));
        // console.log("nombre: " + nombre)
        // console.log("state.texto" + state.texto)
        if (nombre.indexOf(state.texto) >= 0) {
          // console.log("Dentro del if");
          arrayFiltrado.push(cliente);
        }
      }
      return arrayFiltrado;
    },
    
    arrayFiltradoPreguntas(state) {
      let arrayFiltrado = [];

      for (let pregunta of state.preguntas) {
        let titulo = pregunta.pregunta.toLowerCase();
        // console.log(nombre.indexOf(state.texto));
        // console.log("nombre: " + nombre)
        // console.log("state.texto" + state.texto)
        if (titulo.indexOf(state.texto) >= 0) {
          // console.log("Dentro del if");
          arrayFiltrado.push(pregunta);
        }
      }
      return arrayFiltrado;
    }
  }
});
