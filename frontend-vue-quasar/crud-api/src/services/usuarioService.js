import { apiCliente } from './api';

export const crearUsuario = async (usuario) => {
  try {
    const response = await apiCliente.post('/usuarios/crear', usuario);
    return response.data;

  } catch (error) {
    console.error('Error al crear usuario: ', error);
    throw error;
  }
}

export const actualizarUsuario = async (id, usuario) => {
  const usuarioActualizado = {
    idUser: Number(id),
    nombre: usuario.nombre,
    email: usuario.email,
    password: usuario.password
  };
  
  try  {
    const response = await apiCliente.put(`/usuarios/actualizar/${id}`, usuarioActualizado);
    return response.data;
  } catch (error) {
    console.error('Error al actualizar usuario: ', error);
    throw error;
  }
}

export const eliminarUsuario = async (id) => {

  try {
    const response = await apiCliente.delete(`/usuarios/eliminar/${id}`);
    return response.data;
  } catch (error) {
    console.error('Error al eliminar usuario', error);
    throw error;
  }
}

export const getUsuarios = async () => {
  try {
    const response = await apiCliente.get('/usuarios/listar');
    return response.data;
  } catch (error) {
    console.error('Error al obtener los usuarios: ', error);
    throw error;
  }
};
export const getUsuarioById = async (id) => {
  try {
    const response = await apiCliente.get(`/usuarios/id/${id}`);
    return response.data;
  } catch (error) {
    if (error.response && error.response.status === 404) {
      console.warn(`Usuario con id ${id} no encontrado`);
      return null;
    } else {
      console.error("Error desconocido: ", error.message);
      throw new error(error.message);
    }
  }
};

export const getUsuarioByNombre = async (nombre) => {
    try{ 
        const response = await apiCliente.get(`/usuarios/nombre/${nombre}`);
        return response.data;
    }catch (error) {
      if (error.response && error.response.status === 404) {
        console.error(`Usuario con nombre ${nombre} no encontrado `);
        return null;
      } else {
        console.error("Error desconocido: ", error.message);
        throw new error(error.message);
      }
    }
};

export const getUsuarioByEmail = async (email) => {
    try {
        const response = await apiCliente.get(`/usuarios/email/${email}`)
        return response.data;
    } catch (error) {
      if (error.response && error.response.status === 404) {
        console.error(`Usuario con email ${email} no encontrado`)
        return null;
      } else {
        console.error(`Error al buscar usuario por email: `, error);
        throw error;
      }
    }
};


