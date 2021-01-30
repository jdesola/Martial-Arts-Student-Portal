<template>
  <v-app>
      <drawer></drawer>
    <router-view/>
  </v-app>
</template>

<script>
import Drawer from './components/Drawer.vue'
export default {
  components: { Drawer },
  
}
</script>

<style>
v-app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
}


</style>
