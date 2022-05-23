<template>
  <div>
    <b-card no-body class="overflow-hidden join">
      <b-card-body title="회원가입" style="padding: 10%">
        <b-form class="join-form" @submit.stop.prevent>
          <label for="id">아이디</label>
          <b-form-input
            type="text"
            id="id"
            name="id"
            v-model="id"
            :state="idValidation"
            placeholder="아이디"
          />
          <b-form-invalid-feedback :state="idValidation">
            아이디를 5글자 이상 13글자 이하로 반드시 입력해주세요.
          </b-form-invalid-feedback>
          <label for="password">비밀번호</label>
          <b-form-input
            type="password"
            id="pw"
            name="pw"
            v-model="pw"
            :state="pwValidation"
            placeholder="비밀번호"
          />
          <b-form-invalid-feedback :state="pwValidation">
            비밀번호는 8글자 이상 20글자 이하로 반드시 입력해주세요.
          </b-form-invalid-feedback>
          <label for="name">이름</label>
          <b-form-input
            type="text"
            id="name"
            name="name"
            v-model="name"
            :state="nameValidation"
            placeholder="이름"
          />
          <b-form-invalid-feedback :state="nameValidation">
            이름은 2글자 이상 반드시 입력해주세요.
          </b-form-invalid-feedback>
          <label for="tel">전화번호</label>
          <b-form-input
            type="text"
            maxlength="11"
            id="tel"
            name="tel"
            v-model="tel"
            :state="telValidation"
            placeholder="010 1234 5678"
          />
          <b-form-invalid-feedback :state="telValidation">
            전화번호는 반드시 11글자로 입력해주세요.
          </b-form-invalid-feedback>
          <label for="bmk_addr_lv1">관심지역(광역시도)</label>
          <b-form-input
            type="text"
            id="bmk_addr_lv1"
            name="bmk_addr_lv1"
            v-model="bmk_addr_lv1"
            placeholder="광역시도"
          />
          <label for="bmk_addr_lv2">관심지역(시군구)</label>
          <b-form-input
            type="text"
            id="bmk_addr_lv2"
            name="bmk_addr_lv2"
            v-model="bmk_addr_lv2"
            placeholder="시군구"
          />
          <label for="bmk_addr_lv3">관심지역(읍면동)</label>
          <b-form-input
            type="text"
            id="bmk_addr_lv3"
            name="bmk_addr_lv3"
            v-model="bmk_addr_lv3"
            placeholder="읍면동"
          />
          <label for="bmk_addr_lv4">관심지역(리)</label>
          <b-form-input
            type="text"
            id="bmk_addr_lv4"
            name="bmk_addr_lv4"
            v-model="bmk_addr_lv4"
            placeholder="리(선택)"
          />
          <b-button
            @click="checkValue"
            variant="outline-secondary"
            style="width: 90%"
            >회원가입</b-button
          >
        </b-form>
      </b-card-body>
    </b-card>
  </div>
</template>

<script>
import http from "@/api/http";

export default {
  name: "UserJoin",
  data() {
    return {
      id: "",
      pw: "",
      name: "",
      tel: "",
      bmk_addr_lv1: "",
      bmk_addr_lv2: "",
      bmk_addr_lv3: "",
      bmk_addr_lv4: "",
      role: "0",
    };
  },
  methods: {
    checkValue() {
      let err = true;
      let msg = "";
      !this.id && ((msg = "아이디를 입력해주세요"), (err = false));
      err && !this.pw && ((msg = "비밀번호를 입력해주세요"), (err = false));
      err && !this.name && ((msg = "이름을 입력해주세요"), (err = false));
      err && !this.tel && ((msg = "전화번호를 입력해주세요"), (err = false));

      if (!err) alert(msg);
      else this.joinUser();
    },
    joinUser() {
      http
        .post("/users/auth", {
          id: this.id,
          pw: this.pw,
          name: this.name,
          tel: this.tel,
          bmk_addr_lv1: this.bmk_addr_lv1,
          bmk_addr_lv2: this.bmk_addr_lv2,
          bmk_addr_lv3: this.bmk_addr_lv3,
          bmk_addr_lv4: this.bmk_addr_lv4,
          role: "0",
        })
        .then(({ data }) => {
          let msg = "회원가입시 문제가 발생했습니다.";
          if (data != null) {
            msg = "회원가입을 축하합니다.";
          }
          alert(data);
          this.moveLogin();
        });
    },
    moveLogin() {
      this.$router.push({ name: "login" });
    },
  },
  computed: {
    idValidation() {
      return this.id.length > 4 && this.id.length < 14;
    },
    pwValidation() {
      return this.pw.length > 7 && this.pw.length < 21;
    },
    nameValidation() {
      return this.name.length > 1;
    },
    telValidation() {
      return this.tel.length == 11;
    },
  },
};
</script>
