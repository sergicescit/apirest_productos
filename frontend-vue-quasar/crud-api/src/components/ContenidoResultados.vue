<template>
  <q-card flat square>
    <q-card-section class="bg-primary text-white flat square">
      <q-toolbar>
        <q-toolbar-title>Usuarios</q-toolbar-title>

        <q-input
          v-model="buscarUsuarios"
          filled
          type="search"
          dark
          dense
          @keydown.enter="buscarUsuario"
        >
          <template v-slot:append>
            <q-icon class="text-white" name="search" @click="buscarUsuario" />
          </template>
        </q-input>

        <q-item clickable v-ripple @click="modalCrearUsuario = true">
          <q-item-section avatar>
            <q-icon name="add" />
          </q-item-section>
        </q-item>
      </q-toolbar>
    </q-card-section>

    <q-card-section v-if="error">
      <q-banner dense class="bg-red text-white">
        <p>{{ error }}</p>
      </q-banner>
    </q-card-section>

    <q-card-section v-else>
      <q-table flat square :rows="usuarios" :columns="columnas" row-key="idUser">
        <template v-slot:body-cell-acciones="props">
          <q-td :props="props">
            <q-btn icon="edit" color="primary" dense flat @click="editarUsuario(props.row)" />
            <q-btn
              icon="delete"
              color="negative"
              dense
              flat
              @click="confirmarEliminar(props.row.idUser)"
            />
          </q-td>
        </template>
      </q-table>
    </q-card-section>

    <!--MODAL EDITAR USUARIOS-->
    <q-dialog v-model="modalEditar">
      <q-card>
        <q-card-section>
          <div class="text-h6">Editar usuario</div>
        </q-card-section>
        <q-card-section>
          <q-input v-model="usuarioActual.nombre" label="Nombre" />
          <q-input v-model="usuarioActual.email" label="Email" />
          <q-input v-model="usuarioActual.password" label="Contraseña" />
        </q-card-section>
        <q-card-actions align="right">
          <q-btn label="Cancelar" flat @click="modalEditar = false" />
          <q-btn label="Guardar" color="primary" @click="guardarEdicion" />
        </q-card-actions>
      </q-card>
    </q-dialog>

    <!-- MODAL CONFIRMAMOS ELIMINAR USUARIO -->
    <q-dialog v-model="modalEliminar">
      <q-card>
        <q-card-section>
          <div class="text-h6" icon="delete">¿Seguro que deseas eliminar el usuario?</div>
        </q-card-section>
        <q-card-actions align="right">
          <q-btn label="No" flat @click="modalEliminar = false" />
          <q-btn label="Si" color="negative" @click="eliminarUsuarioConfirmado" />
        </q-card-actions>
      </q-card>
    </q-dialog>

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
  </q-card>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue'
import { useUsuarioStore } from '../stores/usuarioStore'
import {
  eliminarUsuario,
  actualizarUsuario,
  getUsuarioById,
  getUsuarioByNombre,
  getUsuarioByEmail,
  crearUsuario,
} from '../services/usuarioService'

const usuarioStore = useUsuarioStore()
const usuarios = computed(() => usuarioStore.resultado || [])
const error = computed(() => usuarioStore.error)
const modalEditar = ref(false)
const modalEliminar = ref(false)
const usuarioActual = ref({})
const usuarioAEliminar = ref(null)
const buscarUsuarios = ref('')
const columnas = [
  { name: 'idUser', label: 'ID Usuario', field: 'idUser', align: 'left' },
  { name: 'nombre', label: 'Nombre', field: 'nombre', align: 'left' },
  { name: 'email', label: 'Email', field: 'email', align: 'left' },
  { name: 'password', label: 'Contraseña', field: 'password', align: 'left' },
  { name: 'acciones', label: 'Acciones' },
]
const modalCrearUsuario = ref(false)
const nuevoUsuario = ref({
  nombre: '',
  email: '',
  password: '',
})

const guardarUsuario = async () => {
  try {
    await crearUsuario(nuevoUsuario.value)
    console.log('Usuario creado con éxito: ', nuevoUsuario.value)

    nuevoUsuario.value = { nombre: '', email: '', password: '' }
    modalCrearUsuario.value = false
    usuarioStore.getUsuarios()
  } catch (error) {
    console.error('Error al crear nuevo usuario: ', error)
    throw error
  }
}

const editarUsuario = (usuario) => {
  usuarioActual.value = { ...usuario }
  modalEditar.value = true
}

const guardarEdicion = async () => {
  await actualizarUsuario(usuarioActual.value.idUser, usuarioActual.value)
  modalEditar.value = false
  usuarioStore.getUsuarios()
}

const confirmarEliminar = (id) => {
  usuarioAEliminar.value = id
  modalEliminar.value = true
}

const eliminarUsuarioConfirmado = async () => {
  await eliminarUsuario(usuarioAEliminar.value)
  modalEliminar.value = false
  usuarioStore.getUsuarios()
}

const buscarUsuario = async () => {
  const valorBusqueda = buscarUsuarios.value.trim()

  if (!valorBusqueda) {
    alert('Por favor, ingresa un valor de búsqueda')
    return
  }

  usuarioStore.setResultado([])
  usuarioStore.setError('')

  try {
    let resultado = null;

    if (/^\d+$/.test(valorBusqueda)) {
      // Si es un número ID
      resultado = await getUsuarioById(parseInt(valorBusqueda, 10))
    } else if (/\S+@\S+\.\S+/.test(valorBusqueda)) {
      //Si es un Email
      resultado = await getUsuarioByEmail(encodeURIComponent(valorBusqueda))
    } else if (/^[a-zA-Z\s]+$/.test(valorBusqueda)) {
      //Si es un texto
      resultado = await getUsuarioByNombre(valorBusqueda)
    } else {
      usuarioStore.setError('Formato de búsqueda no válido')
      return;
    }

    if (!resultado || (Array.isArray(resultado)) && resultado.length === 0) {
      usuarioStore.setError('No se encontró al usuario');
      return;
    }

    //usuarioStore.setResultado(resultado);
    usuarioStore.setResultado(Array.isArray(resultado) ? resultado : [resultado]) //Prevenimos recibir un objeto en búsquedas individuales

    buscarUsuarios.value = '' //limpiamos input para ver si soluciona listado usuarios
  } catch (err) {
    console.log('Error al buscar usuario: ', err)
    usuarioStore.setError('Error al buscar usuario: ' + err.message)
  }
}

onMounted(async () => {
  usuarioStore.getUsuarios()
})
</script>

<style scoped></style>
