import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import './registerServiceWorker'
import vuetify from './plugins/vuetify';
import '@babel/polyfill'
import 'roboto-fontface/css/roboto/roboto-fontface.css'
import '@mdi/font/css/materialdesignicons.css'

Vue.config.productionTip = false
Vue.prototype.$hostname = (Vue.config.productionTip) ? 'https://www.your-api.com' : 'http://192.168.1.133:8080/api'


new Vue({
  router,
  store,
  vuetify,
  render: h => h(App)
}).$mount('#app')
