import { createRouter, createWebHistory } from "vue-router";

// 导入页面组件
import Login from "@/components/Login.vue";
import Register from "@/components/Register.vue";
import RecoverPassword from "@/components/RecoverPassword.vue";
import UserDashboard from "@/views/UserDashboard.vue";
import AnnouncementDetail from "@/views/AnnouncementDetail.vue";
import AdminDashboard from "@/views/AdminDashboard.vue";
import CreateAnnouncement from "@/views/CreateAnnouncement.vue"; // 创建公告页面
import EditAnnouncement from "@/views/EditAnnouncement.vue"; // 编辑公告页面

const routes = [
    // 登录页面
    {
        path: "/",
        name: "Login",
        component: Login,
    },

    // 注册页面
    {
        path: "/register",
        name: "Register",
        component: Register,
    },

    // 找回密码页面
    {
        path: "/recover-password",
        name: "RecoverPassword",
        component: RecoverPassword,
    },

    // 普通用户页面（/user）
    {
        path: "/user",
        name: "UserDashboard",
        component: UserDashboard,
        meta: { requiresAuth: true, role: "user" }, // 普通用户专属
    },

    {
        path: "/user/announcement/:id",
        name: "AnnouncementDetail",
        component: AnnouncementDetail,
        meta: { requiresAuth: true, role: "user" },
    },


    // 管理员公告管理页面
    {
        path: "/admin",
        name: "AdminDashboard",
        component: AdminDashboard,
        meta: { requiresAuth: true, role: "admin" }, // 管理员专属
    },

    // 创建公告页面（管理员专属）
    {
        path: "/admin/create",
        name: "CreateAnnouncement",
        component: CreateAnnouncement,
        meta: { requiresAuth: true, role: "admin" }, // 管理员专属
    },

    // 编辑公告页面（管理员专属）
    {
        path: "/admin/edit/:id",
        name: "EditAnnouncement",
        component: EditAnnouncement,
        meta: { requiresAuth: true, role: "admin" }, // 管理员专属
    },
];

const router = createRouter({
    history: createWebHistory(),
    routes,
});

// // 路由守卫：检查登录和权限
// router.beforeEach((to, from, next) => {
//     const isLoggedIn = localStorage.getItem("token"); // 假设 token 存储在 localStorage
//     const userRole = localStorage.getItem("role"); // 存储用户角色（user/admin）
//
//     // 如果路由需要认证
//     if (to.meta.requiresAuth && !isLoggedIn) {
//         next({ name: "Login" }); // 未登录，跳转到登录页面
//     } else if (to.meta.role && to.meta.role !== userRole) {
//         // 检查角色是否匹配
//         if (userRole === "admin") {
//             next({ name: "AdminDashboard" }); // 如果管理员无权限访问普通页面，跳转到管理员页面
//         } else {
//             next({ name: "UserDashboard" }); // 如果普通用户无权限访问管理员页面，跳转到用户页面
//         }
//     } else {
//         next(); // 放行
//     }
// });

export default router;
