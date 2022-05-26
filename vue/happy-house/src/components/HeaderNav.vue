<template>
  <div class="header">
    <div class="header-right">
      <template v-if="userInfo">
        <a @click="logout()" class="userInfo">로그아웃</a>
        <router-link :to="{ name: 'UserModify', params: { id: userInfo.id } }"
          >정보관리</router-link
        >
        <span class="userInfo">{{ userInfo.id }}님 안녕하세요</span>
      </template>
      <template v-else>
        <router-link :to="{ name: 'login' }">로그인</router-link>
        <router-link :to="{ name: 'join' }">회원가입</router-link>
      </template>
    </div>

    <div class="menu">
      <router-link :to="{ name: 'home' }"
        ><img src="@/assets/img/logo.png" class="happy_logo"
      /></router-link>
      <router-link :to="{ name: 'notice' }">공지사항</router-link>
      <router-link :to="{ name: 'list' }">커뮤니티</router-link>
      <router-link :to="{ name: 'home' }">관심지역</router-link>
    </div>
  </div>
</template>

<script>
import http from "@/api/http";
import { mapState, mapMutations } from "vuex";
const userStore = "userStore";

export default {
  name: "HeaderNav",
  computed: {
    ...mapState(userStore, ["isLogin", "userInfo"]),
  },
  methods: {
    ...mapMutations(userStore, ["SET_IS_LOGIN", "SET_USER_INFO"]),
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
          if (this.$route.path != "/") this.$router.push({ name: "home" });
        });
    },
  },
};
</script>
