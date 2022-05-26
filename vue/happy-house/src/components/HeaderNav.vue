<template>
  <div class="header">
    <div class="header-right">
      <template v-if="this.currentUser">
        <a @click="logout()" class="userInfo">로그아웃</a>
        <router-link :to="{ name: 'UserModify', params: { id: currentUser.id } }"
          >정보관리</router-link
        >
        <span class="userInfo">{{ currentUser.id }}님 안녕하세요</span>
      </template>
      <template v-else>
        <router-link :to="{ name: 'login' }">로그인</router-link>
        <router-link :to="{ name: 'join' }">회원가입</router-link>
      </template>
    </div>

    <div class="menu" style="">
      <router-link :to="{ name: 'home' }"
        ><img
          src="@/assets/img/logo_white.png"
          style="height: 10%; width: 10%"
          class="happy_logo"
      /></router-link>
      <span class="menu-router"
        ><router-link :to="{ name: 'notice' }">공지사항</router-link></span
      >
      <span class="menu-router"
        ><router-link :to="{ name: 'list' }">커뮤니티</router-link></span
      >
      <span class="menu-router"
        ><router-link :to="{ name: 'bookmark-home' }"
          >관심지역</router-link
        ></span
      >
    </div>
  </div>
</template>

<script>
import http from "@/api/http";
import { mapState, mapMutations } from "vuex";
const userStore = "userStore";

export default {
  name: "HeaderNav",
  data() {
    return {
    };
  },
  computed: {
    ...mapState(userStore, ["currentUser", "userInfo"]),
  },
  created() {
    http
      .get("http://localhost:8090/users/login/current", {
        headers: {
          Authorization: `Bearer ` + sessionStorage.getItem("token"),
        },
      })
      .then((resp) => { // if logined
        this.SET_CURRENT_USER(resp.data);
      })
      .catch((err) => { // if NOT logined
        this.SET_CURRENT_USER(null);
      });
  },
  methods: {
    ...mapMutations(userStore, ["SET_IS_LOGIN", "SET_USER_INFO", "SET_CURRENT_USER"]),
    logout() {
      http
        .post(
          "/users/logout",
          {},
          {
            headers: {
              Authorization: `Bearer ` + sessionStorage.getItem("token"),
            },
          }
        )
        .then(({ data }) => {
          this.SET_IS_LOGIN(false);
          this.SET_USER_INFO(null);
          sessionStorage.removeItem("vuex");
          sessionStorage.removeItem("token");
          //if (this.$route.path != "/")
          this.$router.push({ name: "home" });
          //this.currentUser = null;
          this.SET_CURRENT_USER(null);
        });
    },
  },
  watch: {
  },
};
</script>
