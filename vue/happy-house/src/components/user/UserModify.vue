<template>
  <div>
    <b-card no-body class="overflow-hidden join">
      <b-card-body title="회원정보관리" style="padding: 10%">
        <b-form class="join-form" @submit.stop.prevent>
          <label for="id">아이디</label>
          <b-form-input type="text" readonly name="id" v-model="id" />
          <label for="password">비밀번호</label>
          <b-form-input type="password" name="pw" v-model="pw" placeholder="비밀번호" />
          <label for="name">이름</label>
          <b-form-input type="text" name="name" v-model="name" />
          <label for="tel">전화번호</label>
          <b-form-input type="text" maxlength="11" name="tel" v-model="tel" />

          <label for="bmk_addr_lv1">관심지역(광역시도)</label>
          <b-form-input type="text" name="bmk_addr_lv1" v-model="bmk_addr_lv1" />
          <label for="bmk_addr_lv2">관심지역(시군구)</label>
          <b-form-input type="text" name="bmk_addr_lv2" v-model="bmk_addr_lv2" />
          <label for="bmk_addr_lv3">관심지역(읍면동)</label>
          <b-form-input type="text" name="bmk_addr_lv3" v-model="bmk_addr_lv3" />
          <label for="bmk_addr_lv4">관심지역(리)</label>
          <b-form-input type="text" name="bmk_addr_lv4" v-model="bmk_addr_lv4" />
          <b-button @click="checkValue" variant="outline-secondary" style="width: 90%"
            >수정</b-button
          >
        </b-form>
      </b-card-body>
    </b-card>
  </div>
</template>

<script>
import http from "@/api/http";

export default {
  name: "UserModify",
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
    };
  },
  created() {
    http
      .get(`/users/${this.$route.params.id}`, {
        headers: {
          Authorization: `Bearer ` + sessionStorage.getItem("token"),
        },
      })
      .then(({ data }) => {
        this.id = data.id;
        this.pw = data.pw;
        this.name = data.name;
        this.tel = data.tel;
        this.bmk_addr_lv1 = data.bmk_addr_lv1;
        this.bmk_addr_lv2 = data.bmk_addr_lv2;
        this.bmk_addr_lv3 = data.bmk_addr_lv3;
        this.bmk_addr_lv4 = data.bmk_addr_lv4;
      });
  },

  methods: {
    checkValue() {
      let err = true;
      let msg = "";
      !this.pw && ((msg = "비밀번호를 입력해주세요"), (err = false));
      err && !this.name && ((msg = "이름을 입력해주세요"), (err = false));
      err && !this.tel && ((msg = "전화번호를 입력해주세요"), (err = false));
      err && !this.bmk_addr_lv1 && ((msg = "광역시도를 입력해주세요"), (err = false));
      err && !this.bmk_addr_lv2 && ((msg = "시군구를 입력해주세요"), (err = false));
      err && !this.bmk_addr_lv3 && ((msg = "읍면동를 입력해주세요"), (err = false));
      err && !this.bmk_addr_lv4 && ((msg = "리, 상세주소를 입력해주세요"), (err = false));
      if (!err) alert(msg);
      else this.User();
    },
    User() {
      http
        .put(
          `/users/${this.$route.params.id}`,
          {
            id: this.id,
            pw: this.pw,
            name: this.name,
            tel: this.tel,
            bmk_addr_lv1: this.bmk_addr_lv1,
            bmk_addr_lv2: this.bmk_addr_lv2,
            bmk_addr_lv3: this.bmk_addr_lv3,
            bmk_addr_lv4: this.bmk_addr_lv4,
          },
          {
            headers: {
              Authorization: `Bearer ` + sessionStorage.getItem("token"),
            },
          }
        )
        .then(({ data }) => {
          let msg = "회원정보 수정시 문제가 발생했습니다.";
          if (data != null) {
            msg = "회원정보 수정을 완료했습니다.";
          }
          alert(data);
          this.$router.push({ name: "home" });
        });
    },
  },
};
</script>
