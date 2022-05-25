<template>
  <div class="header">
    <div class="header-right">
      <template v-if="id">
        <a @click="logout()" class="userInfo">로그아웃</a>
        <a @click="moveModify(id)" class="userInfo">정보관리</a>
        <span class="userInfo">{{ this.id }}님 안녕하세요</span>
      </template>
      <template v-else>
        <router-link to="/user/login">로그인</router-link>
        <router-link to="/user/join">회원가입</router-link>
      </template>
    </div>

    <div class="menu">
      <router-link to="/"><img src="@/assets/img/logo.png" class="happy_logo" /></router-link>
      <router-link to="/board">공지사항</router-link>
      <router-link to="/board/community">커뮤니티</router-link>
      <router-link to="/board">관심지역</router-link>
    </div>
  </div>
</template>

<script>
import http from "@/api/http";
const store = "store";

export default {
  name: "HeaderNav",
  data() {
    return {
      id: "",
    };
  },
  created() {
    if (this.$store.getters.isLogin) {
      http
        .get("/users/login/current", {
          headers: {
            Authorization: `Bearer ` + sessionStorage.getItem("token"),
          },
        })
        .then(({ data }) => {
          this.id = data.id;
        });
    }
  },
  methods: {
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
          sessionStorage.clear("vuex");
          sessionStorage.clear("token");
          alert("로그아웃 되었습니다.");
          location.href = "/";
        });
    },
    moveModify(id) {
      this.$router.push({ name: "UserModify", params: { id: id } });
    },
  },
};
</script>
