<template>
  <div class="edit-announcement">
    <h1>编辑公告</h1>
    <form @submit.prevent="handleEdit" class="announcement-form">
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
        <button type="submit" class="btn-submit">保存修改</button>
        <button type="button" class="btn-cancel" @click="handleCancel">取消</button>
      </div>
    </form>
  </div>
</template>

<script>
import axios from "axios";

export default {
  name: "EditAnnouncement",
  data() {
    return {
      title: "",
      content: "",
    };
  },
  mounted() {
    const id = this.$route.params.id; // 从路由参数获取公告 ID
    this.fetchAnnouncement(id);
  },
  methods: {
    async fetchAnnouncement(id) {
      try {
        const response = await axios.get(`http://localhost:8080/BulletinSystem/api/announcements/${id}`);
        this.title = response.data.title;
        this.content = response.data.content;
      } catch (error) {
        console.error("获取公告失败：", error);
        alert("加载公告失败！");
      }
    },
    async handleEdit() {
      const id = this.$route.params.id;
      if (!this.title || !this.content) {
        alert("标题和内容不能为空！");
        return;
      }

      try {
        await axios.put(`http://localhost:8080/BulletinSystem/api/announcements/edit/${id}`, {
          title: this.title,
          content: this.content,
        });
        alert("公告修改成功！");
        this.$router.push("/admin"); // 跳转回管理员首页
      } catch (error) {
        console.error("公告修改失败：", error);
        alert("修改失败，请稍后重试！");
      }
    },
    handleCancel() {
      this.$router.push("/admin"); // 取消修改，返回管理员首页
    },
  },
};
</script>

<style scoped>
/* 蒸汽朋克风格 */
.edit-announcement {
  max-width: 80%;
  margin: 50px auto;
  padding: 40px;
  background: #2c2c2c;
  border: 4px solid #705438;
  border-radius: 15px;
  box-shadow: 0px 5px 15px rgba(0, 0, 0, 0.5);
  color: #d8c3a5;
  font-family: 'Courier New', Courier, monospace;
}

.edit-announcement h1 {
  margin-bottom: 30px;
  font-size: 2.5rem;
  color: #e4b97f;
  text-shadow: 0 0 5px #e4b97f, 0 0 10px #e4b97f;
  text-align: center;
}

.announcement-form .form-group {
  margin-bottom: 20px;
}

.announcement-form label {
  font-size: 1.5rem;
  color: #e4b97f;
  display: block;
  margin-bottom: 10px;
}

.announcement-form .form-input,
.announcement-form .form-textarea {
  width: 100%;
  padding: 15px;
  border: 1px solid #705438;
  border-radius: 5px;
  background: #3b3b3b;
  color: #d8c3a5;
  font-size: 1.2rem;
}

.announcement-form .form-input:focus,
.announcement-form .form-textarea:focus {
  outline: none;
  border-color: #e4b97f;
}

.announcement-form .form-textarea {
  height: 200px;
  resize: none;
}

.announcement-form .form-buttons {
  display: flex;
  justify-content: space-between;
  margin-top: 20px;
}

.btn-submit,
.btn-cancel {
  font-size: 1.5rem;
  padding: 10px 30px;
  border: none;
  border-radius: 10px;
  cursor: pointer;
  transition: transform 0.2s, box-shadow 0.2s;
}

.btn-submit {
  background: #e4b97f;
  color: #2c2c2c;
  box-shadow: 0 4px 10px rgba(228, 185, 127, 0.6);
}

.btn-submit:hover {
  transform: translateY(-3px);
  box-shadow: 0 6px 15px rgba(228, 185, 127, 0.9);
}

.btn-cancel {
  background: #705438;
  color: #d8c3a5;
  box-shadow: 0 4px 10px rgba(112, 84, 56, 0.6);
}

.btn-cancel:hover {
  transform: translateY(-3px);
  box-shadow: 0 6px 15px rgba(112, 84, 56, 0.9);
}
</style>
