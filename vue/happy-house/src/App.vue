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
button,
.btn {
  width: 15%;
  background-color: #d0d3d0;
  color: rgb(80, 82, 79);
  padding: 14px 10px;
  margin: 8px 15px;
  border: 1px solid #787878;
  border-radius: 4px;
  font-size: small;
  cursor: pointer;
}
.regist {
  padding: 10px;
}
.regist_form {
  border-radius: 5px;
  background-color: #f2f2f2;
  padding: 20px;
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

.happy_logo {
  width: 250px;
}
.header {
  text-align: center;
  width: 100%;
  height: 20%;
  background-color: #000000;
}
.header img {
  vertical-align: middle;
}

.menu a {
  color: white;
  text-decoration: none;
  font-size: large;
  padding: 10px;
}

.menu a:hover {
  color: gray;
  text-decoration: none;
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
#book-list {
  border-collapse: collapse;
  width: 100%;
}

#book-list thead {
  background-color: #ccc;
  font-weight: bold;
}

#book-list td,
#book-list th {
  text-align: center;
  border-bottom: 1px solid #ddd;
  height: 50px;
}
</style>
