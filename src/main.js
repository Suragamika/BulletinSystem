import { createApp } from 'vue';
import App from './App.vue';
import router from './router';

const app = createApp(App);

// 使用路由
app.use(router);

// 挂载到页面
app.mount('#app');
