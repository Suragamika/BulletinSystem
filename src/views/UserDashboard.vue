<template>
  <div class="dashboard">
    <header class="header">
      <h1>公告列表</h1>
      <button @click="logout" class="btn-secondary">退出登录</button>
    </header>

    <div class="announcement-container">
      <div v-if="announcements.length === 0" class="empty-message">暂无公告</div>
      <div v-for="announcement in announcements" :key="announcement.id" class="announcement-card">
        <h2>{{ announcement.title }}</h2>
        <p>发布时间: {{ formatDate(announcement.createdAt) }}</p>
        <button @click="viewAnnouncement(announcement.id)" class="btn-primary">查看详情</button>
      </div>
    </div>
  </div>
</template>

<script>
import axios from "axios";

export default {
  name: "UserDashboard",
  data() {
    return {
      announcements: [],
    };
  },
  methods: {
    async fetchAnnouncements() {
      try {
        const response = await axios.get("http://localhost:8080/BulletinSystem/api/announcements");
        this.announcements = response.data;
      } catch (error) {
        console.error("公告加载失败:", error);
      }
    },
    formatDate(date) {
      return new Date(date).toLocaleString();
    },
    viewAnnouncement(id) {
      this.$router.push(`/user/announcement/${id}`);
    },
    logout() {
      localStorage.clear();
      this.$router.push("/");
    },
  },
  mounted() {
    this.fetchAnnouncements();
  },
};
</script>

<style scoped>
.dashboard {
  background: linear-gradient(135deg, #1a1a1a, #333333);
  color: white;
  min-height: 100vh;
  padding: 20px;
}
.header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  background: #222;
  padding: 20px;
  box-shadow: 0 2px 5px rgba(0, 0, 0, 0.3);
}
.announcement-container {
  margin-top: 20px;
}
.announcement-card {
  background: rgba(0, 0, 0, 0.8);
  color: #00ffff;
  border: 1px solid #00ffff;
  border-radius: 8px;
  padding: 20px;
  margin-bottom: 20px;
  box-shadow: 0 0 15px rgba(0, 255, 255, 0.5);
}
.btn-primary {
  background: linear-gradient(45deg, #ff00ff, #00ffff);
  padding: 10px 20px;
  border: none;
  color: white;
  border-radius: 5px;
  cursor: pointer;
}
.btn-primary:hover {
  box-shadow: 0 0 15px rgba(255, 0, 255, 0.7);
}
.btn-secondary {
  background: #444;
  color: white;
  padding: 10px 20px;
  border: none;
  border-radius: 5px;
  cursor: pointer;
}
.btn-secondary:hover {
  background: #555;
}
.empty-message {
  text-align: center;
  color: #aaa;
  margin-top: 20px;
}
</style>
