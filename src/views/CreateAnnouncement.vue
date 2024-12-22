<template>
  <div class="create-announcement">
    <h1>创建公告</h1>
    <form @submit.prevent="handleCreate" class="announcement-form">
      <div class="form-group">
        <label for="title">标题</label>
        <input
            id="title"
            v-model="title"
            type="text"
            placeholder="请输入公告标题"
            class="form-input"
        />
      </div>
      <div class="form-group">
        <label for="content">内容</label>
        <textarea
            id="content"
            v-model="content"
            placeholder="请输入公告内容"
            class="form-textarea"
        ></textarea>
      </div>
      <div class="form-buttons">
        <button type="submit" class="btn-submit">发布公告</button>
        <button type="button" class="btn-cancel" @click="handleCancel">取消发布</button>
      </div>
    </form>
  </div>
</template>

<script>
import axios from "axios";

export default {
  name: "CreateAnnouncement",
  data() {
    return {
      title: "",
      content: "",
    };
  },
  methods: {
    async handleCreate() {
      if (!this.title || !this.content) {
        alert("标题和内容不能为空！");
        return;
      }

      try {
        await axios.post("http://localhost:8080/BulletinSystem/api/announcements/create", {
          title: this.title,
          content: this.content,
        });
        alert("公告创建成功！");
        this.$router.push("/admin"); // 跳转回管理员首页
      } catch (error) {
        console.error("公告创建失败：", error);
        alert("创建失败，请稍后重试！");
      }
    },
    handleCancel() {
      this.$router.push("/admin"); // 取消发布，返回管理员首页
    },
  },
};
</script>

<style scoped>
/* 霓虹风格样式 */
.create-announcement {
  max-width: 80%;
  margin: 50px auto;
  padding: 40px;
  background: linear-gradient(135deg, #1a1a1a, #333333);
  box-shadow: 0px 4px 20px rgba(0, 0, 0, 0.6), 0px 0px 20px #00ffcc;
  border-radius: 10px;
  text-align: center;
  color: #00ffcc;
  animation: fadeIn 1s ease-in-out;
}

@keyframes fadeIn {
  from {
    opacity: 0;
    transform: scale(0.9);
  }
  to {
    opacity: 1;
    transform: scale(1);
  }
}

.create-announcement h1 {
  margin-bottom: 30px;
  font-size: 3rem;
  color: #00ffcc;
  text-shadow: 0 0 5px #00ffcc, 0 0 10px #00ffcc;
}

.announcement-form .form-group {
  margin-bottom: 25px;
  text-align: left;
}

.announcement-form label {
  font-size: 1.5rem;
  color: #00ffcc;
  display: block;
  margin-bottom: 10px;
  text-shadow: 0 0 5px #00ffcc;
}

.announcement-form .form-input,
.announcement-form .form-textarea {
  width: 100%;
  padding: 15px;
  border: none;
  border-radius: 10px;
  background: #222;
  color: #00ffcc;
  box-shadow: inset 0 0 5px #00ffcc;
  font-size: 1.2rem;
}

.announcement-form .form-input:focus,
.announcement-form .form-textarea:focus {
  outline: none;
  box-shadow: 0 0 10px #00ffcc, 0 0 20px #00ffcc;
}

.announcement-form .form-textarea {
  height: 200px;
  resize: none;
}

.announcement-form .form-buttons {
  display: flex;
  justify-content: space-around;
  margin-top: 20px;
}

.btn-submit,
.btn-cancel {
  font-size: 1.5rem;
  font-weight: bold;
  padding: 10px 30px;
  border: none;
  border-radius: 10px;
  cursor: pointer;
  transition: transform 0.2s, box-shadow 0.2s;
}

.btn-submit {
  background: #00ffcc;
  color: #1a1a1a;
  box-shadow: 0 4px 10px rgba(0, 255, 204, 0.6), 0 0 10px #00ffcc;
}

.btn-submit:hover {
  transform: translateY(-3px);
  box-shadow: 0 6px 15px rgba(0, 255, 204, 0.9), 0 0 15px #00ffcc;
}

.btn-submit:active {
  transform: translateY(0);
  box-shadow: 0 4px 10px rgba(0, 255, 204, 0.6), 0 0 10px #00ffcc;
}

.btn-cancel {
  background: #ff5555;
  color: #fff;
  box-shadow: 0 4px 10px rgba(255, 85, 85, 0.6), 0 0 10px #ff5555;
}

.btn-cancel:hover {
  transform: translateY(-3px);
  box-shadow: 0 6px 15px rgba(255, 85, 85, 0.9), 0 0 15px #ff5555;
}

.btn-cancel:active {
  transform: translateY(0);
  box-shadow: 0 4px 10px rgba(255, 85, 85, 0.6), 0 0 10px #ff5555;
}
</style>
