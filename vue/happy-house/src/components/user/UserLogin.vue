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
              <b-form-input type="text" id="id" name="id" v-model="user.id" placeholder="아이디" />
              <b-form-input
                type="password"
                id="pw"
                name="pw"
                v-model="user.pw"
                placeholder="비밀번호"
              />
              <b-button variant="outline-secondary" @click="confirm" style="width: 90%"
                >로그인</b-button
              >
            </b-form>
            <div>
              <router-link :to="{ name: 'join' }">회원가입</router-link>
              <router-link :to="{ name: 'userFindPassword' }">비밀번호 찾기</router-link>
            </div>
          </b-card-body>
        </b-col>
      </b-row>
    </b-card>
  </div>
</template>

<script>
import { mapState, mapActions } from "vuex";
const userStore = "userStore";

export default {
  name: "UserLogin",
  data() {
    return {
      user: {
        id: null,
        pw: null,
      },
    };
  },
  computed: {
    ...mapState(userStore, ["isLogin", "isLoginError"]),
  },
  methods: {
    ...mapActions(userStore, ["userConfirm", "getUserInfo"]),
    async confirm() {
      await this.userConfirm(this.user);
      let token = sessionStorage.getItem("token");
      if (this.isLogin) {
        await this.getUserInfo(token);
        this.$router.push({ name: "home" });
      }
    },
  },
};
</script>
