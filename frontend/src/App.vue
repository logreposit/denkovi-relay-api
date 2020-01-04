<template>
  <v-app>
    <v-app-bar app>
      <v-toolbar-title class="headline text-uppercase">
        <span>Denkovi Relay API </span>
        <span class="font-weight-light">INTERFACE</span>
      </v-toolbar-title>
      <v-spacer></v-spacer>
    </v-app-bar>
    <v-content>
      <RelayOverview/>
    </v-content>
  </v-app>
</template>

<script>
import RelayOverview from './components/RelayOverview';

export default {
  name: 'App',
  components: {
    RelayOverview
  },
  data: () => ({
  }),
};
</script>
