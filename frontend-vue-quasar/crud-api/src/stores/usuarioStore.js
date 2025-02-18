import { defineStore } from 'pinia';
import { getUsuarios } from '../services/usuarioService';

export const useUsuarioStore = defineStore('usuario', {
  state: () => ({
    listaUsuarios: [],   //almacenaremos la lista de usuarios para agilizar consultas
    resultado: null,
    error: '',
  }),
  actions: {
    async getUsuarios() {
      try {
        const response = await getUsuarios();
        this.resultado = response;  //actualizamos RESULTADO
        this.listaUsuarios = response;  //Guardamos en la lista para reutilizar
        this.error = '';      //limpiamos error si todo ok
      } catch (err) {
        this.error = 'Error al obtener usuarios';
        this.resultado = [];          //reiniciamos resultado
        this.listaUsuarios = [];      //reiniciamos lista
        console.error(err);
      }
    },
    // setResultado(datos) {
    //   this.resultado = datos;
    // },
    setResultado(datos) {
      this.resultado = Array.isArray(datos) ? datos : [datos];  //Aseguramos que sea un array para evitar error al buscar por id o email
    },
    setError(mensaje) {
      this.error = mensaje;
    },
    setListaUsuarios(usuarios) {
      this.listaUsuarios = usuarios;
    },
    actualizarUsuariosEnLista(usuarioActualizado) {
      const index = this.listaUsuarios.findIndex( u => u.idUser === usuarioActualizado.idUser);
      if (index !== -1){
        this.listaUsuarios[index] = usuarioActualizado;
      }
    },
    eliminarUsuarioDeLista(idUser) {
      this.listaUsuarios = this.listaUsuarios.filter(usuario => usuario.idUser !== idUser);
    },

  },
});
