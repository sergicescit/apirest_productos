<template>
  <div class="app">
    <div>
      <router-link to="/crear-usuario"><q-btn push color="primary">Crear usuarios</q-btn></router-link>
    </div>
    <div>
      <router-link to="/buscar-usuarios"><q-btn push color="primary">Buscar usuarios</q-btn></router-link>
    </div>
    <div>
      <router-link to="/"><q-btn color="amber">Inicio</q-btn></router-link>
    </div>
  </div>
</template>

<script setup>

</script>

<style soped>
.app {
  display: flex;
  flex-direction: row;
  gap: 1em;
  padding: 10px;
  margin-bottom: 30px;
}
</style>
