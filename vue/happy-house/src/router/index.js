import Vue from "vue";
import VueRouter from "vue-router";
import HomeView from "@/views/HomeView.vue";
import store from "@/store/index.js";
Vue.use(VueRouter);

const onlyAuthUser = async (to, from, next) => {
  // console.log(store);
  const checkUserInfo = store.getters["userStore/checkUserInfo"];
  const getUserInfo = store._actions["userStore/getUserInfo"];
  let token = sessionStorage.getItem("token");
  if (checkUserInfo == null && token) {
    await getUserInfo(token);
  }
  if (checkUserInfo === null) {
    alert("로그인이 필요한 페이지입니다..");
    next({ name: "login" });
    // router.push({ name: "signIn" });
  } else {
    // console.log("로그인 했다.");
    next();
  }
};

const routes = [
  {
    path: "/",
    name: "home",
    component: HomeView,
  },
  {
    path: "/apt/detail",
    name: "apt-detail",
    component: () => import("@/components/map/AptDetail.vue"),
  },
  {
    path: "/board/community",
    name: "board",
    component: () => import("@/views/BoardView.vue"),
    beforeEnter: onlyAuthUser,
    redirect: "/board/community",
    children: [
      {
        path: "/",
        name: "list",
        component: () => import("@/components/board/BoardList.vue"),
      },
      {
        path: "write",
        name: "write",
        component: () => import("@/components/board/BoardWrite.vue"),
      },
      {
        path: "/:id",
        name: "detail",
        component: () => import("@/components/board/BoardDetail.vue"),
      },
      {
        path: "edit/:id",
        name: "modify",
        component: () => import("@/components/board/BoardModify.vue"),
      },
    ],
  },
  {
    path: "/board/notice",
    name: "notice",
    component: () => import("@/views/NoticeView.vue"),
    redirect: "/board/notice",
    children: [
      {
        path: "/",
        name: "noticeList",
        component: () => import("@/components/notice/NoticeList.vue"),
      },
      {
        path: "write",
        name: "noticeWrite",
        component: () => import("@/components/notice/NoticeWrite.vue"),
      },
      {
        path: "/:id",
        name: "noticeDetail",
        component: () => import("@/components/notice/NoticeDetail.vue"),
      },
      {
        path: "edit/:id",
        name: "noticeModify",
        component: () => import("@/components/notice/NoticeModify.vue"),
      },
    ],
  },
  {
    path: "/user",
    name: "user",
    component: () => import("@/views/UserView.vue"),
    children: [
      {
        path: "login",
        name: "login",
        component: () => import("@/components/user/UserLogin.vue"),
      },
      {
        path: "join",
        name: "join",
        component: () => import("@/components/user/UserJoin.vue"),
      },
      {
        path: "modify/:id",
        name: "UserModify",
        beforeEnter: onlyAuthUser,
        component: () => import("@/components/user/UserModify.vue"),
      },
    ],
  },
  {
    path: "/user/forget-password",
    name: "userFindPassword",
    component: () => import("@/components/user/UserFindPassword.vue"),
  },
];

const router = new VueRouter({
  mode: "history",
  base: process.env.BASE_URL,
  routes,
});

export default router;
