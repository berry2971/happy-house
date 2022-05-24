<template>
  <div>
    <b-card no-body class="login">
      <b-row no-gutters>
        <b-col md="6">
          <b-card-img
            :src="require('@/assets/img/loginImage.jpg')"
            alt="Image"
            class="rounded-0"
            style="height: 300px"
          ></b-card-img>
        </b-col>
        <b-col md="6">
          <b-card-body title="로그인">
            <b-form @submit.stop.prevent>
              <b-form-input type="text" id="id" name="id" v-model="id" placeholder="아이디" />
              <b-form-input type="password" id="pw" name="pw" v-model="pw" placeholder="비밀번호" />
              <b-button variant="outline-secondary" @click="checkValue" style="width: 90%"
                >로그인</b-button
              >
            </b-form>
            <div>
              <router-link to="/user/join">회원가입</router-link>
              <router-link to="/board">비밀번호 찾기</router-link>
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
      !this.id && ((msg = "아이디를 입력해주세요."), (err = false), this.$refs.id.focus());
      err && !this.pw && ((msg = "비밀번호를 입력해주세요"), (err = false), this.$refs.pw.focus());

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
          // 서버에서 정상적인 값이 넘어 왔을경우 실행.
          let msg = "로그인 문제가 발생했습니다.";
          if (data != null) {
            sessionStorage.clear("vuex");
            sessionStorage.clear("token");

            this.$store.dispatch("setToken", data);
            msg = "로그인이 완료되었습니다.";
          }
          alert(msg);
          location.href = "/";
          //this.$router.push({ name: "home" });
        });
    },
  },
};
</script>
