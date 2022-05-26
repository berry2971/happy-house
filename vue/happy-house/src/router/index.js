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
import AptDetail from "@/components/map/AptDetail.vue";
import UserModify from "@/components/user/UserModify.vue";
import UserFindPassword from "@/components/user/UserFindPassword.vue";
import NoticeWrite from "@/components/notice/NoticeWrite.vue";
import NoticeList from "@/components/notice/NoticeList.vue";
import NoticeDetail from "@/components/notice/NoticeDetail.vue";
import NoticeModify from "@/components/notice/NoticeModify.vue";

Vue.use(VueRouter);

const routes = [
  {
    path: "/",
    name: "home",
    component: HomeView,
  },
  {
    path: "/apt/detail",
    name: "apt-detail",
    component: AptDetail,
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
    path: "/board/notice",
    name: "notice",
    component: BoardView,
    redirect: "/board/notice",
    children: [
      {
        path: "/",
        name: "list",
        component: NoticeList,
      },
      {
        path: "write",
        name: "noticeWrite",
        component: NoticeWrite,
      },
      {
        path: "/:id",
        name: "noticeDetail",
        component: NoticeDetail,
      },
      {
        path: "edit/:id",
        name: "noticeModify",
        component: NoticeModify,
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
  {
    path: "/user/forget-password",
    name: "userFindPassword",
    component: UserFindPassword,
  },
];

const router = new VueRouter({
  mode: "history",
  base: process.env.BASE_URL,
  routes,
});

export default router;
