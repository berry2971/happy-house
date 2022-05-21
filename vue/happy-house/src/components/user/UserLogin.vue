<template>
  <div class="login">
    <b-card no-body class="overflow-hidden" style="max-width: 540px">
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
            <form>
              <input type="text" placeholder="아이디" />
              <input type="text" placeholder="비밀번호" />
              <b-button variant="outline-secondary" style="width: 90%"
                >로그인</b-button
              >
            </form>
            <div>
              <router-link to="/board">회원가입</router-link>
              <router-link to="/board">비밀번호 찾기</router-link>
            </div>
          </b-card-body>
        </b-col>
      </b-row>
    </b-card>
  </div>
</template>
