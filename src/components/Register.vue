<template>
  <div class="register-container">
    <h1>注册</h1>
    <form @submit.prevent="handleRegister">
      <!-- 输入用户名 -->
      <input
          v-model="username"
          type="text"
          placeholder="请输入用户名"
          class="form-input"
      />
      <!-- 输入密码 -->
      <input
          v-model="password"
          type="password"
          placeholder="请输入密码"
          class="form-input"
      />
      <!-- 注册按钮 -->
      <button type="submit" class="btn-primary">注册</button>
    </form>

    <!-- 提示信息 -->
    <p v-if="message" class="message">{{ message }}</p>

    <!-- 返回登录按钮 -->
    <p class="link-group">
      <router-link to="/" class="link">返回登录</router-link>
    </p>
  </div>
</template>

<script>
import axios from "axios";

export default {
  data() {
    return {
      username: "",
      password: "",
      message: "",
    };
  },
  methods: {
    async handleRegister() {
      // 校验用户名和密码是否为空
      if (!this.username.trim() || !this.password.trim()) {
        this.message = "用户名或密码不能为空！";
        return;
      }

      try {
        const response = await axios.post(
            "http://localhost:8080/BulletinSystem/api/register",
            {
              username: this.username.trim(),
              password: this.password.trim(),
            },
            {
              headers: {
                "Content-Type": "application/json",
              },
            }
        );

        if (response.data.success) {
          this.message = "注册成功！";
        } else {
          this.message = response.data.message || "注册失败，请重试！";
        }
      } catch (error) {
        console.error("请求失败：", error);
        this.message = "服务器连接失败，请稍后再试！";
      }
    },
  },
};
</script>

<style scoped>
/* 容器样式 */
.register-container {
  max-width: 350px; /* 调整容器宽度，更紧凑 */
  margin: 100px auto;
  padding: 20px;
  background-color: #1a1a1a;
  border-radius: 10px;
  box-shadow: 0 0 15px rgba(0, 255, 255, 0.5);
  color: white;
  text-align: center;
  font-family: Arial, sans-serif;
}

/* 标题样式 */
h1 {
  font-size: 24px;
  margin-bottom: 20px;
  color: #00ffff;
}

/* 输入框样式 */
.form-input {
  width: 90%; /* 调整宽度为容器的 90% */
  padding: 8px; /* 减小输入框的内边距 */
  margin: 10px 0;
  border: 1px solid #ccc;
  border-radius: 5px;
  font-size: 14px; /* 缩小字体大小 */
}

/* 按钮样式 */
.btn-primary {
  width: 90%; /* 调整按钮宽度为 90% */
  padding: 10px;
  margin-top: 20px;
  background-color: #00ffff;
  border: none;
  border-radius: 5px;
  color: black;
  font-size: 16px;
  cursor: pointer;
  transition: background-color 0.3s ease;
}

.btn-primary:hover {
  background-color: #00cccc;
}

/* 提示信息样式 */
.message {
  margin-top: 10px;
  color: #ff4d4d;
  font-size: 14px;
}

/* 链接样式 */
.link-group {
  margin-top: 20px;
}

.link {
  color: #00ffff;
  text-decoration: none;
  transition: color 0.3s ease;
}

.link:hover {
  color: #00cccc;
  text-decoration: underline;
}
</style>
