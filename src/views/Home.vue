<template>
  <v-container grid-list-md>
    <v-layout row wrap>
      <v-flex xs12 sm6>
        <v-card @click="openUrl('https://news.ual.es/rectorado/evaluacion-continua-y-examenes-online-que-no-superen-el-50-por-ciento-de-la-calificacion-final/')">
          <v-img src="../assets/evaluacion.jpg">
            <v-container>
              <v-layout>
               
              </v-layout>
            </v-container>
          </v-img>
          <v-card-title>
            <h2>
              Evaluación continua y exámenes ‘online’ que no superen el 50 por
              ciento de la calificación final
            </h2>
          </v-card-title>
          <v-card-text>
            <p>
              La Universidad de Almería da prioridad a estas medidas que están
              recogidas en la resolución aprobada por el rector, Carmelo
              Rodríguez, para la adaptación a la modalidad no presencial
              conforme al acuerdo adoptado por el Consejero de Economía,
              Conocimiento, Empresas y Universidad y los rectores y rectoras de
              las universidades públicas andaluzas.
            </p>
          </v-card-text>
          <v-card-actions>
            <v-btn color="info">
              Leer más
            </v-btn>
          </v-card-actions>
        </v-card>
      </v-flex>

      <v-flex xs12 sm6>
        <v-card @click="openUrl('https://news.ual.es/sociedad/la-ual-da-apoyo-emocional-a-los-heroes-sanitarios-frente-al-covid-19-a-traves-del-proyecto-cuidados-compasion/')">
          <v-img src="../assets/enfermera.jpg"></v-img>
          <v-card-title>
            <h2>
              La UAL da apoyo emocional a los héroes sanitarios frente al
              COVID-19 a través del proyecto ‘Cuidados Compasión’
            </h2>
          </v-card-title>
          <v-card-text>
            <p>
              Se trata de un entorno web surgido en el seno de la Universidad de
              Almería, uniendo los ámbitos universitario y profesional de parte
              de Andalucía, que ahora suma fuerzas con el Grupo de Intervención
              en Crisis de la Universidad de Alicante y ofrece herramientas que
              ayuden a los que están en ‘primera línea’ del coronavirus a
              “conllevar su trabajo diario”.
            </p>
          </v-card-text>
          <v-card-actions>
            <v-btn @click="dialog = true" color="info">
              Leer más
            </v-btn>
          </v-card-actions>
        </v-card>
      </v-flex>
    </v-layout>

    <v-dialog v-model="dialog" persistent>
      <v-card>
        <v-card-title>
          Dialog
        </v-card-title>
        <v-card-text>
          Lorem ipsum dolor sit amet consectetur adipisicing elit. Qui et
          tenetur officiis commodi optio voluptatibus, saepe facilis, iure error
          molestiae velit dolores mollitia accusantium sed voluptatem sunt
          suscipit. Nam, tenetur!
        </v-card-text>
        <v-divider></v-divider>
        <v-card-actions class="justify-center">
          <v-btn @click="dialog = false">Cerrar</v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
  </v-container>
</template>

<script>
export default {
  components: {},
  data() {
    return {
      dialog: false,
    };
  },
  methods: {
    openUrl(url) {
      window.open(url, "_blank");
    },
  },
};
</script>
