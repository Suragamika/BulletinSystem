<template>
  <div class="announcement-detail-container">
    <div v-if="announcement.title">
      <!-- 公告标题 -->
      <h1 class="announcement-title">{{ announcement.title }}</h1>

      <!-- 公告发布时间 -->
      <p class="announcement-date">发布时间：{{ announcement.createdAt }}</p>

      <!-- 公告内容 -->
      <div class="announcement-content">
        {{ announcement.content }}
      </div>

      <!-- 返回公告列表按钮 -->
      <button class="btn-primary" @click="goBack">返回公告列表</button>
    </div>
    <div v-else>
      <!-- 加载中提示 -->
      <p class="loading-message">加载中，请稍候...</p>
    </div>
  </div>
</template>

<script>
import axios from "axios";

export default {
  data() {
    return {
      announcement: {}, // 用于存储公告详情
    };
  },
  methods: {
    async fetchAnnouncement() {
      const id = this.$route.params.id; // 从路由参数获取公告ID
      try {
        const response = await axios.get(
            `http://localhost:8080/BulletinSystem/api/announcements/${id}`
        );
        this.announcement = response.data; // 将响应数据存储到 `announcement`
      } catch (error) {
        console.error("获取公告详情失败：", error);
        this.announcement = {
          title: "公告加载失败",
          content: "无法加载公告详情，请稍后重试。",
        };
      }
    },
    goBack() {
      this.$router.push("/user"); // 返回公告列表页面
    },
  },
  watch: {
    // 监听路由变化，动态加载新的公告详情
    $route(to, from) {
      this.fetchAnnouncement();
    },
  },
  mounted() {
    this.fetchAnnouncement(); // 在组件挂载时获取公告详情
  },
};
</script>

<style scoped>
.announcement-detail-container {
  max-width: 800px;
  margin: 50px auto;
  padding: 20px;
  border-radius: 12px;
  background: rgba(20, 20, 20, 0.9);
  box-shadow: 0 0 20px rgba(0, 255, 255, 0.3), 0 0 40px rgba(255, 0, 255, 0.3);
  text-align: center;
  font-family: 'Courier New', Courier, monospace;
  color: white;
}

.announcement-title {
  font-size: 32px;
  margin-bottom: 10px;
  text-shadow: 0 0 10px #00ffff, 0 0 20px #00ffff;
}

.announcement-date {
  margin-bottom: 20px;
  color: #ccc;
  font-size: 16px;
}

.announcement-content {
  font-size: 18px;
  line-height: 1.6;
  margin-bottom: 30px;
  color: #fff;
  text-align: left;
  white-space: pre-wrap; /* 保留换行 */
}

.loading-message {
  font-size: 20px;
  color: #ccc;
}

.btn-primary {
  padding: 12px 20px;
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
</style>
