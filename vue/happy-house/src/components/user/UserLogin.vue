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
              <b-form-input
                type="text"
                id="id"
                name="id"
                v-model="id"
                placeholder="아이디"
              />
              <b-form-input
                type="password"
                id="password"
                name="password"
                v-model="password"
                placeholder="비밀번호"
              />
              <b-button variant="outline-secondary" style="width: 90%"
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
