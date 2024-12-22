<template>
  <div class="login-container">
    <h1>登录</h1>
    <form @submit.prevent="handleLogin">
      <!-- 用户名输入框 -->
      <input
          v-model="username"
          type="text"
          placeholder="请输入用户名"
          class="form-input"
      />
      <!-- 密码输入框 -->
      <input
          v-model="password"
          type="password"
          placeholder="请输入密码"
          class="form-input"
      />
      <!-- 登录按钮 -->
      <button type="submit" class="btn-primary">登录</button>
    </form>

    <!-- 提示信息 -->
    <p v-if="message" class="message">{{ message }}</p>

    <!-- 注册和找回密码的按钮 -->
    <div class="link-group">
      <router-link to="/register" class="link">注册账号</router-link>
      <span class="spacer">|</span>
      <router-link to="/recover-password" class="link">找回密码</router-link>
    </div>
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
    async handleLogin() {
      if (!this.username.trim() || !this.password.trim()) {
        this.message = "用户名或密码不能为空！";
        return;
      }

      try {
        const response = await axios.post(
            "http://localhost:8080/BulletinSystem/api/login",
            {
              username: this.username.trim(),
              password: this.password.trim(),
            },
            {
              headers: {
                "Content-Type": "application/json",
              }
            }
        );

        if (response.data.success) {
          this.message = "登录成功！";

          // 根据角色跳转
          if (response.data.role === "admin") {
            this.$router.push("/admin"); // 管理员页面
          } else {
            this.$router.push("/user"); // 普通用户页面
          }
        } else {
          this.message = response.data.message || "登录失败，请重试！";
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
/* 背景渐变 */
.login-container {
  max-width: 400px;
  margin: 100px auto;
  padding: 20px;
  border-radius: 12px;
  background: rgba(20, 20, 20, 0.9);
  box-shadow: 0 0 20px rgba(0, 255, 255, 0.3), 0 0 40px rgba(255, 0, 255, 0.3);
  text-align: center;
  font-family: 'Courier New', Courier, monospace;
}

h1 {
  color: #00ffff;
  font-size: 24px;
  margin-bottom: 20px;
  text-shadow: 0 0 10px #00ffff, 0 0 20px #00ffff;
}

.form-input {
  width: 90%;
  padding: 12px;
  margin: 10px 0;
  border: 2px solid #333;
  border-radius: 8px;
  background-color: #1a1a1a;
  color: #fff;
  font-size: 16px;
  box-shadow: inset 0 0 5px rgba(0, 255, 255, 0.5);
  transition: border-color 0.3s ease, box-shadow 0.3s ease;
}

.form-input:focus {
  outline: none;
  border-color: #00ffff;
  box-shadow: 0 0 10px #00ffff, 0 0 20px #00ffff;
}

.btn-primary {
  width: 100%;
  padding: 12px;
  margin-top: 20px;
  background: linear-gradient(45deg, #ff00ff, #00ffff);
  color: white;
  font-size: 16px;
  border: none;
  border-radius: 8px;
  cursor: pointer;
  box-shadow: 0 0 15px rgba(0, 255, 255, 0.5), 0 0 30px rgba(255, 0, 255, 0.5);
  transition: all 0.3s ease;
}

.btn-primary:hover {
  transform: scale(1.05);
  box-shadow: 0 0 20px rgba(0, 255, 255, 0.8), 0 0 40px rgba(255, 0, 255, 0.8);
}

.error-message {
  color: #ff4d4d;
  margin-top: 10px;
}

.link-group {
  margin-top: 20px;
}

.link {
  color: #00ffff;
  text-decoration: none;
  transition: color 0.3s ease;
}

.link:hover {
  color: #ff00ff;
  text-decoration: underline;
}
</style>
