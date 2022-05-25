import Vue from "vue";
import Vuex from "vuex";
import createPersistedState from "vuex-persistedstate";

Vue.use(Vuex);
const store = new Vuex.Store({
  namespaced: true,
  state: {
    token: null,
  },
  getters: {
    isLogin(state) {
      return sessionStorage.getItem("token") == null ? false : true;
    },
  },
  mutations: {
    setToken(state, _token) {
      sessionStorage.setItem("token", _token);
    },
  },
  actions: {
    setToken: ({ commit }, _token) => {
      commit("setToken", _token);
    },
  },
  modules: {},
  plugins: [
    createPersistedState({
      storage: sessionStorage,
    }),
  ],
});

export default store;
