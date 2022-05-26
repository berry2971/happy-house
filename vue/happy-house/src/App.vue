<template>
  <div id="app">
    <header-nav></header-nav>
    <router-view />
  </div>
</template>

<script>
import HeaderNav from "@/components/HeaderNav.vue";

export default {
  name: "App",
  components: { HeaderNav },
};
</script>

<style>
@import url('https://cdn.jsdelivr.net/gh/orioncactus/pretendard/dist/web/variable/pretendardvariable.css');

* {
  font-family: Pretendard, -apple-system, BlinkMacSystemFont, system-ui, Roboto,
  "Helvetica Neue", "Segoe UI", "Apple SD Gothic Neo", "Noto Sans KR",
  "Malgun Gothic", sans-serif;
}

a {
  text-decoration: none;
  color: #787878;
}
input,
textarea,
.view {
  width: 100%;
  padding: 12px 20px;
  margin: 8px 0;
  display: inline-block;
  border: 1px solid #ccc;
  border-radius: 4px;
  box-sizing: border-box;
  color: #787878;
  font-size: medium;
}
label {
  display: inline-block;
  width: 80px;
}

.header-right a {
  float: right;
  color: white;
  text-decoration: none;
  font-size: small;
  padding: 5px;
}

.header-right a:hover {
  color: gray;
  text-decoration: none;
}

.userInfo {
  color: white;
  float: right;
  text-decoration: none;
  font-size: small;
  padding: 5px;
}

.happy_logo {
  width: 250px;
}
.header img {
  vertical-align: middle;
}

.menu a {
  color: white;
  text-decoration: none;
  font-size: large;
  padding: 10px;
  box-shadow: inset 0 0 0 0 #ff5f00;
  /*margin: 0 -.25rem;*/
  /*padding: 0 .25rem;*/
  transition: color .3s ease-in-out, box-shadow .3s ease-in-out;
  border-radius: 5px;
}

.menu-router a:hover {
  /*color: white;*/
  font-weight: 900;
  text-decoration: none;
  box-shadow: inset 100px 0 0 0 #ffffff;
  color: #ff5f00;
}

.logo {
  display: inline-block;
  font-size: 30pt;
  font-weight: bold;
}
.underline {
  display: inline-block;
  background: linear-gradient(180deg, rgba(255, 255, 255, 0) 70%, cyan 30%);
}

.indexDropdown {
  font-weight: 500;
}

.indexDropdown option {
  font-weight: 500;
  padding: 3px 0px 3px 0px;
}
</style>
