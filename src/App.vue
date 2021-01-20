<template>
  <v-app>
      <nav-bar></nav-bar>
    <router-view/>
  </v-app>
</template>

<script>
import navBar from './components/nav-bar.vue'
export default {
  components: { navBar },
  
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
