<template>
  <div>
    <b-card no-body class="findPassword">
      <b-row no-gutters>
        <b-col md="6">
          <b-card-img
            :src="require('@/assets/img/passwordImage.jpg')"
            alt="Image"
            class="rounded-0"
            style="height: auto"
          ></b-card-img>
        </b-col>
        <b-col md="6">
          <b-card-body title="비밀번호 찾기">
            <b-form @submit.stop.prevent>
              <b-form-input
                type="text"
                id="id"
                name="id"
                v-model="id"
                placeholder="아이디"
                required
              />
              <b-form-input
                type="text"
                id="name"
                name="name"
                v-model="name"
                placeholder="이름"
                required
              />
              <b-form-input
                type="text"
                id="tel"
                name="tel"
                v-model="tel"
                placeholder="전화번호"
                required
              />
              <b-form-input
                type="password"
                id="newPw"
                name="newPw"
                v-model="newPw"
                placeholder="새로운 비밀번호"
                required
              />
              <b-button variant="outline-secondary" @click="checkValue" style="width: 90%"
                >비밀번호 변경</b-button
              >
            </b-form>
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
      name: "",
      tel: "",
      newPw: "",
    };
  },
  methods: {
    checkValue() {
      let err = true;
      let msg = "";
      !this.id && ((msg = "아이디를 입력해주세요."), (err = false), this.$refs.id.focus());
      err && !this.name && ((msg = "이름을 입력해주세요"), (err = false), this.$refs.name.focus());
      err &&
        !this.tel &&
        ((msg = "전화번호를 입력해주세요"), (err = false), this.$refs.tel.focus());
      err &&
        !this.newPw &&
        ((msg = "변경할 비밀번호를 입력해주세요"), (err = false), this.$refs.newPw.focus());

      if (!err) alert(msg);
      else this.modifyPassword();
    },

    modifyPassword() {
      http
        .post(
          "/users/auth/forget-password",
          {
            id: this.id,
            name: this.name,
            tel: this.tel,
            newPw: this.newPw,
          },
          {}
        )
        .then(({ data }) => {
          // 서버에서 정상적인 값이 넘어 왔을경우 실행.
          let msg = "비밀번호 변경에 실패했습니다.";
          if (data != null) {
            msg = "비밀번호가 변경 되었습니다.";
          }
          alert(msg);
          this.$router.push({ name: "login" });
        });
    },
  },
};
</script>
