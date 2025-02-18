import { createRouter, createWebHistory } from 'vue-router'
import MainLayout from '../components/MainLayout.vue'
import MainView from '../components/MainView.vue'
import ContenidoResultados from '../components/ContenidoResultados.vue'

const routes = [
  {
    path: '/',
    component: MainLayout,
    children: [
      { path: '', component: MainView },
      { path: '/lista-usuarios', component: ContenidoResultados },
      { path: '/buscar-usuarios', component: MainView },
      { path: '/contenido-resultados', component: ContenidoResultados },
    ],
  },
]

const router = createRouter({
  history: createWebHistory(),
  routes,
})

export default router
