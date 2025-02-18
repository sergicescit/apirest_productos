//import './assets/main.css'

import { createApp } from 'vue';
import { createPinia } from 'pinia';
import { Quasar } from 'quasar';
import quasarIconSet from 'quasar/icon-set/fontawesome-v5.js';
import '@quasar/extras/material-icons/material-icons.css'
import 'quasar/dist/quasar.css';


import App from './App.vue'
import router from './router'

const app = createApp(App)

app.use(createPinia())
app.use(router)
app.use(Quasar, {
    iconSet: quasarIconSet,
})
app.mount('#app')
