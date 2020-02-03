import Vue from 'vue'
import App from './App.vue'
import vuetify from './plugins/vuetify';
import axios from 'axios'

Vue.config.productionTip = false;

//axios.defaults.baseURL='/v1/api/relays';
axios.defaults.baseURL='http://192.168.8.106:8080/v1/api';

new Vue({
  vuetify,
  render: h => h(App)
}).$mount('#app');
