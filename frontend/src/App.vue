<template>
  <v-app>
    <v-app-bar app>
      <v-app-bar-nav-icon @click="drawer = !drawer"></v-app-bar-nav-icon>
      <v-toolbar-title class="mx-2">Academia</v-toolbar-title>
      <v-spacer></v-spacer>
      <v-btn class="success mx-2">Ingreso</v-btn>
      <v-btn class="error mx-2">Salir</v-btn>
    </v-app-bar>

    <v-navigation-drawer app v-model="drawer" temporary>
      <v-layout mt-4 column align-center>
        <v-flex>
          <v-avatar>
            <img src="https://randomuser.me/api/portraits/med/men/65.jpg" />
          </v-avatar>
        </v-flex>
        <v-flex>
          <p mt-3 headline>Usuario...</p>
        </v-flex>
      </v-layout>
      <v-layout column>
        <v-flex class="mx-5">
          <v-btn color="success" class="mt-5" block :to="{ name: 'home'}">Home</v-btn>
          <v-btn color="success" class="mt-5" block :to="{ name: 'temas'}">Temas</v-btn>
          <v-btn color="success" class="mt-5" block :to="{ name: 'preguntas'}">Preguntas</v-btn>

        </v-flex>
      </v-layout>
    </v-navigation-drawer>

    <v-content>
      <router-view />
    </v-content>
  </v-app>
</template>

<script>
export default {
  name: "App",
  data: () => ({
    //
    drawer: true
  })
};
</script>
