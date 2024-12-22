<template>
  <div class="recover-container">
    <h1>找回密码</h1>
    <form @submit.prevent="handleRecover">
      <!-- 输入用户名 -->
      <input
          v-model="username"
          type="text"
          placeholder="请输入用户名"
          class="form-input"
      />
      <!-- 设置新密码 -->
      <input
          v-model="newPassword"
          type="password"
          placeholder="设置新密码"
          class="form-input"
      />
      <!-- 确认按钮 -->
      <button type="submit" class="btn-primary">
        <span class="icon">🔒</span> 确认修改密码
      </button>
    </form>
    <!-- 提示信息 -->
    <p v-if="message" :class="{'success-message': success, 'error-message': !success}">
      {{ message }}
    </p>
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
      username: "", // 用户名
      newPassword: "", // 新密码
      message: "", // 提示信息
      success: false, // 成功状态标志
    };
  },
  methods: {
    async handleRecover() {
      // 校验输入
      if (!this.username.trim() || !this.newPassword.trim()) {
        this.message = "用户名和新密码不能为空！";
        this.success = false;
        return;
      }

      try {
        // 使用 Axios 发送 JSON 数据
        const response = await axios.post(
            "http://localhost:8080/BulletinSystem/api/recover-password",
            {
              username: this.username.trim(),
              newPassword: this.newPassword.trim(),
            },
            {
              headers: {
                "Content-Type": "application/json", // 设置请求头为 JSON 格式
              },
            }
        );

        // 根据响应设置提示信息
        if (response.data.success) {
          this.message = "密码修改成功！";
          this.success = true;
        } else {
          this.message = response.data.message || "修改失败，请重试！";
          this.success = false;
        }
      } catch (error) {
        console.error("请求失败：", error);
        this.message = "服务器连接失败，请稍后再试！";
        this.success = false;
      }
    },
  },
};
</script>

<style scoped>
/* 容器样式 */
.recover-container {
  max-width: 500px;
  margin: 100px auto;
  padding: 30px;
  border-radius: 15px;
  background: rgba(20, 20, 20, 0.9);
  box-shadow: 0 0 20px rgba(0, 255, 255, 0.3), 0 0 40px rgba(255, 0, 255, 0.3);
  text-align: center;
  font-family: "Courier New", Courier, monospace;
}

h1 {
  color: #00ffff;
  font-size: 28px;
  margin-bottom: 25px;
  text-shadow: 0 0 10px #00ffff, 0 0 20px #00ffff;
}

/* 输入框样式 */
.form-input {
  width: 95%;
  padding: 15px;
  margin: 15px 0;
  border: 2px solid #333;
  border-radius: 12px;
  background-color: #1a1a1a;
  color: #fff;
  font-size: 18px;
  box-shadow: inset 0 0 5px rgba(0, 255, 255, 0.5);
  transition: border-color 0.3s ease, box-shadow 0.3s ease;
}

.form-input:focus {
  outline: none;
  border-color: #00ffff;
  box-shadow: 0 0 15px #00ffff, 0 0 30px #00ffff;
}

/* 按钮样式 */
.btn-primary {
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 10px; /* 图标与文字的间距 */
  width: 95%;
  padding: 15px;
  margin-top: 20px;
  background: linear-gradient(45deg, #ff00ff, #00ffff);
  color: white;
  font-size: 18px;
  border: none;
  border-radius: 12px;
  cursor: pointer;
  box-shadow: 0 0 15px rgba(0, 255, 255, 0.5), 0 0 30px rgba(255, 0, 255, 0.5);
  transition: all 0.3s ease;
}

.btn-primary:hover {
  transform: scale(1.05); /* 悬停缩放 */
  box-shadow: 0 0 20px rgba(0, 255, 255, 0.8), 0 0 40px rgba(255, 0, 255, 0.8);
}

/* 提示信息样式 */
.success-message {
  color: #00ff00;
  margin-top: 15px;
  font-size: 16px;
}

.error-message {
  color: #ff4d4d;
  margin-top: 15px;
  font-size: 16px;
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
  color: #ff00ff;
  text-decoration: underline;
}
</style>
