import Vue from "vue";
import VueRouter from "vue-router";
import HomeView from "@/views/HomeView.vue";
import BoardView from "@/views/BoardView.vue";
import BoardWrite from "@/components/board/BoardWrite.vue";
import BoardList from "@/components/board/BoardList.vue";
import BoardDetail from "@/components/board/BoardDetail.vue";
import BoardModify from "@/components/board/BoardModify.vue";
import UserView from "@/views/UserView.vue";
import UserLogin from "@/components/user/UserLogin.vue";
import UserJoin from "@/components/user/UserJoin.vue";
import UserModify from "@/components/user/UserModify.vue";

Vue.use(VueRouter);

const routes = [
  {
    path: "/",
    name: "home",
    component: HomeView,
  },
  {
    path: "/board/community",
    name: "board",
    component: BoardView,
    redirect: "/board/community",
    children: [
      {
        path: "/",
        name: "list",
        component: BoardList,
      },
      {
        path: "write",
        name: "write",
        component: BoardWrite,
        title: "글쓰기",
      },
      {
        path: "/:id",
        name: "detail",
        component: BoardDetail,
      },
      {
        path: "edit/:id",
        name: "modify",
        component: BoardModify,
      },
    ],
  },
  {
    path: "/user",
    name: "user",
    component: UserView,
    children: [
      {
        path: "login",
        name: "login",
        component: UserLogin,
      },
      {
        path: "join",
        name: "join",
        component: UserJoin,
      },
      {
        path: "modify/:id",
        name: "UserModify",
        component: UserModify,
      },
    ],
  },
];

const router = new VueRouter({
  mode: "history",
  base: process.env.BASE_URL,
  routes,
});

export default router;
