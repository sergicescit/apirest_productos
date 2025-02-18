<template>
  <q-layout view="hHh lpR lfr">
    <q-header reveal class="bg-primary text-white">
      <q-toolbar>
        <q-btn dense flat round icon="menu" @click="drawer = !drawer" />

        <q-toolbar-title> Gestión - Usuarios </q-toolbar-title>

        <q-select v-model="empresaSelect" :options="optionsEmpresa" label="Opciones de Empresa" dense>
          <template v-slot:append>
            <q-icon name="work" class="text-white"/>
            <label class="q-pa-md text-white">Empresa </label>
            <q-icon name="expand_more" class="text-white" />
          </template>
        </q-select>

        <q-select v-model="usuarioSelect" :options="optionsUsuario" label="Opciones de Usuario" dense>
          <template v-slot:append>
              <q-avatar>
                  <img src="https://cdn.quasar.dev/logo-v2/svg/logo.svg" />
                </q-avatar>
                <label class="q-pa-md text-white">Usuario </label>
                <q-icon name="expand_more" class="text-white" />
          </template>
        </q-select>

      </q-toolbar>
    </q-header>

    <q-drawer
      v-model="drawer"
      show-if-above
      :mini="miniState"
      @mouseenter="miniState = false"
      @mouseleave="miniState = true"
      :width="200"
      :breakpoint="500"
      bordered
      :class="$q.dark.isActive ? 'bg-grey-9' : 'bg-grey-3'"
    >
      <q-scroll-area class="fit" :horizontal-thumb-style="{ opacity: 0 }">
        <q-list padding>
          <q-item clickable v-ripple @click="modalCrearUsuario = true">
            <q-item-section avatar>
              <q-icon name="account_circle" />
            </q-item-section>

            <q-item-section> Crear usuario </q-item-section>
          </q-item>

          <q-item active clickable v-ripple>
            <q-item-section avatar>
              <q-icon name="settings" />
            </q-item-section>

            <q-item-section> Configuración </q-item-section>
          </q-item>

          <q-item clickable v-ripple>
            <q-item-section avatar>
              <q-icon name="date_range" />
            </q-item-section>

            <q-item-section> Calendario </q-item-section>
          </q-item>

          <q-separator />

          <q-item clickable v-ripple @click="listarUsuarios">
            <q-item-section avatar>
              <q-icon name="arrow_circle_left" />
            </q-item-section>

            <q-item-section> Lista usuarios </q-item-section>
          </q-item>
        </q-list>
      </q-scroll-area>
    </q-drawer>

    <q-page-container>
      <router-view />
    </q-page-container>

    <!-- MODAL CREAR NUEVO USUARIO -->
    <q-dialog v-model="modalCrearUsuario">
      <q-card>
        <q-card-section>
          <div class="text-h6">Crear usuario</div>
        </q-card-section>
        <q-card-section>
          <q-input v-model="nuevoUsuario.nombre" label="Nombre" />
          <q-input v-model="nuevoUsuario.email" label="Email" />
          <q-input v-model="nuevoUsuario.password" label="Contraseña" />
        </q-card-section>
        <q-card-actions align="right">
          <q-btn label="Cancelar" flat @click="modalCrearUsuario = false" />
          <q-btn label="Guardar" color="primary" @click="guardarUsuario" />
        </q-card-actions>
      </q-card>
    </q-dialog>
  </q-layout>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import { crearUsuario } from '../services/usuarioService';
import { useUsuarioStore } from '../stores/usuarioStore';

const modalCrearUsuario = ref(false)
const drawer = ref(false)
const miniState = ref(true)
const usuarioSelect = ref(null)
const empresaSelect = ref(null)
const optionsUsuario = ['Perfil', 'Herramientas', 'Logout']
const optionsEmpresa = ['Usuarios', 'Administración']
const usuarioStore = useUsuarioStore();

const nuevoUsuario = ref({
  nombre: '',
  email: '',
  password: '',
})

const guardarUsuario = async () => {
  try {
    await crearUsuario(nuevoUsuario.value)
    nuevoUsuario.value = { nombre: '', email: '', password: '' }
    modalCrearUsuario.value = false
    usuarioStore.getUsuarios();

  } catch (error) {
    console.error('Error al crear nuevo usuario: ', error)
    throw error
  }
}

const listarUsuarios = async () => {
  try {
    await usuarioStore.getUsuarios();
  } catch (error) {
    console.error('Error al cargar lista de usuarios: ', error);
  }
}
onMounted( async () => {
  await usuarioStore.getUsuarios();
});
</script>

<style scoped>
</style>
