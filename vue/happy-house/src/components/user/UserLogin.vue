<template>
  <div>
    <b-card no-body class="login">
      <b-row no-gutters>
        <b-col md="6">
          <b-card-img
            :src="require('@/assets/img/loginImage.jpg')"
            alt="Image"
            class="rounded-0"
          ></b-card-img>
        </b-col>
        <b-col md="6">
          <b-card-body title="로그인">
            <b-form @submit.stop.prevent>
              <b-form-input type="text" id="id" name="id" v-model="id" placeholder="아이디" />
              <b-form-input type="password" id="pw" name="pw" v-model="pw" placeholder="비밀번호" />
              <b-button variant="outline-secondary" @click="checkValue()" style="width: 90%"
                >로그인</b-button
              >
            </b-form>
            <div>
              <router-link to="/user/join">회원가입</router-link>
              <router-link to="/user/forget-password">비밀번호 찾기</router-link>
            </div>
          </b-card-body>
        </b-col>
      </b-row>
    </b-card>
  </div>
</template>

<script>
import http from "@/api/http";
const store = "store";

export default {
  name: "UserLogin",
  data() {
    return {
      id: "",
      pw: "",
    };
  },
  methods: {
    checkValue() {
      let err = true;
      let msg = "";
      !this.id && ((msg = "아이디를 입력해주세요."), (err = false));
      err && !this.pw && ((msg = "비밀번호를 입력해주세요"), (err = false));

      if (!err) alert(msg);
      else this.userAuth();
    },

    userAuth() {
      http
        .post("/users/auth/login", {
          id: this.id,
          pw: this.pw,
        })
        .then(({ data }) => {
          if (data != null) {
            sessionStorage.clear("vuex");
            sessionStorage.clear("token");

            this.$store.dispatch("setToken", data);
            alert("로그인이 완료되었습니다.");
          } else {
            alert("로그인 문제가 발생했습니다.");
          }
          location.href = "/";
        })
        .catch((error) => {
          if (error.response.status == 500) {
            alert("아이디와 비밀번호를 다시 확인 부탁드립니다.");
          } else if (error.response.status == 401) {
            alert("비밀번호를 다시 확인 부탁드립니다.");
          }
        });
    },
  },
};
</script>
