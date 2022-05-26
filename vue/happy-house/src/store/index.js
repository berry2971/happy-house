import Vue from "vue";
import Vuex from "vuex";
import createPersistedState from "vuex-persistedstate";

Vue.use(Vuex);

import userStore from "./modules/userStore.js";
import boardStore from "./modules/boardStore.js";

const store = new Vuex.Store({
  modules: {
    userStore,
    boardStore,
  },
  plugins: [
    createPersistedState({
      storage: sessionStorage,
    }),
  ],
});

export default store;
