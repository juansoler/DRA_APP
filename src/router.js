import Vue from "vue";
import Router from "vue-router";
import Home from "./views/Home.vue";

Vue.use(Router);

export default new Router({
  mode: "history",
  base: process.env.BASE_URL,
  routes: [
    {
      path: "/",
      name: "home",
      component: Home
    },
   
    {
      path: "/temas",
      name: "temas",
      // route level code-splitting
      // this generates a separate chunk (about.[hash].js) for this route
      // which is lazy-loaded when the route is visited.
      component: () =>
        import(/* webpackChunkName: "tareas" */ "./views/Temas.vue")
    },
   
   
   
   
    {
      path: "/preguntas/",
      name: "preguntas",
      props: true,

      // route level code-splitting
      // this generates a separate chunk (about.[hash].js) for this route
      // which is lazy-loaded when the route is visited.
      component: () =>
        import(/* webpackChunkName: "preguntas" */ "./views/Preguntas.vue"),
    },
    {
      path: "/examen/",
      name: "examen",
      props: true,

      // route level code-splitting
      // this generates a separate chunk (about.[hash].js) for this route
      // which is lazy-loaded when the route is visited.
      component: () =>
        import(/* webpackChunkName: "preguntas" */ "./views/Examen.vue"),
    }
  ]
});