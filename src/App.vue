<template>
  <div id="app">
    <!-- 路由页面内容 -->
    <router-view></router-view>
  </div>
</template>

<script>
export default {
  name: 'App',
};
</script>

<style>
/* 全局样式 */
#app {
  text-align: center;
  color: white;
  background: linear-gradient(135deg, #1a1a1a, #333333);
  min-height: 100vh;
  display: flex;
  flex-direction: column;
}

.header {
  padding: 20px 0;
  background: #222;
  color: #00ffff;
  box-shadow: 0 2px 5px rgba(0, 0, 0, 0.3);
}

.footer {
  margin-top: auto;
  padding: 10px 0;
  background: #222;
  color: #aaa;
  font-size: 14px;
}
</style>
