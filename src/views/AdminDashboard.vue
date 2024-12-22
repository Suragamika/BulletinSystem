<template>
  <div class="admin-dashboard">
    <h1 class="dashboard-title">公告管理系统</h1>

    <!-- 公告列表 -->
    <div v-if="announcements.length > 0" class="announcement-list">
      <div
          v-for="announcement in announcements"
          :key="announcement.id"
          class="announcement-card"
      >
        <h2 class="announcement-title">{{ announcement.title }}</h2>
        <p class="announcement-content">
          {{ announcement.content }}
        </p>
        <div class="announcement-meta">
          <span class="announcement-date">发布时间: {{ announcement.createdAt }}</span>
        </div>
        <div class="card-buttons">
          <button
              class="btn-edit"
              @click="editAnnouncement(announcement.id)"
          >
            编辑
          </button>
          <button
              class="btn-delete"
              @click="deleteAnnouncement(announcement.id)"
          >
            删除
          </button>
        </div>
      </div>
    </div>

    <!-- 如果没有公告 -->
    <p v-else class="no-announcements">暂无公告</p>

    <!-- 发布新公告按钮 -->
    <button class="btn-new-announcement" @click="createAnnouncement">
      发布新公告
    </button>

    <!-- 退出登录按钮 -->
    <button class="btn-logout" @click="logout">
      退出登录
    </button>
  </div>
</template>

<script>
import axios from "axios";

export default {
  data() {
    return {
      announcements: [],
    };
  },
  methods: {
    async fetchAnnouncements() {
      try {
        const response = await axios.get(
            "http://localhost:8080/BulletinSystem/api/announcements"
        );
        this.announcements = response.data;
      } catch (error) {
        console.error("公告加载失败:", error);
        alert("公告加载失败，请稍后重试！");
      }
    },
    createAnnouncement() {
      this.$router.push("/admin/create");
    },
    editAnnouncement(id) {
      this.$router.push(`/admin/edit/${id}`);
    },
    async deleteAnnouncement(id) {
      if (confirm("确定要删除该公告吗？")) {
        try {
          const response = await axios.delete(
              `http://localhost:8080/BulletinSystem/api/announcements/delete/${id}`
          );
          if (response.status === 200) {
            alert("公告删除成功！");
            this.fetchAnnouncements(); // 删除成功后刷新公告列表
          } else {
            alert("删除失败，公告未找到！");
          }
        } catch (error) {
          console.error("删除公告失败:", error);
          alert("删除失败，请稍后再试！");
        }
      }
    },
    logout() {
      localStorage.removeItem("token");
      localStorage.removeItem("role");
      this.$router.push("/");
    },
  },
  mounted() {
    this.fetchAnnouncements();
  },
};
</script>

<style scoped>
.admin-dashboard {
  padding: 20px;
  background: linear-gradient(135deg, #1a1a1a, #333333);
  color: #fff;
  font-family: "Courier New", Courier, monospace;
  min-height: 100vh;
}

.dashboard-title {
  text-align: center;
  font-size: 32px;
  color: #00ffff;
  margin-bottom: 20px;
  text-shadow: 0 0 15px #00ffff, 0 0 30px #ff00ff;
}

.announcement-list {
  display: flex;
  flex-wrap: wrap;
  gap: 20px;
  justify-content: center;
}

.announcement-card {
  background: rgba(20, 20, 20, 0.9);
  border-radius: 12px;
  padding: 20px;
  width: 300px;
  box-shadow: 0 0 15px rgba(0, 255, 255, 0.5), 0 0 30px rgba(255, 0, 255, 0.5);
  transition: transform 0.3s ease, box-shadow 0.3s ease;
}

.announcement-card:hover {
  transform: scale(1.05);
  box-shadow: 0 0 20px rgba(0, 255, 255, 0.8), 0 0 40px rgba(255, 0, 255, 0.8);
}

.announcement-title {
  font-size: 20px;
  color: #00ffff;
  margin-bottom: 10px;
  text-shadow: 0 0 10px #00ffff, 0 0 20px #ff00ff;
}

.announcement-content {
  font-size: 16px;
  color: #ddd;
  margin-bottom: 10px;
}

.announcement-meta {
  font-size: 14px;
  color: #aaa;
  margin-bottom: 10px;
}

.card-buttons {
  display: flex;
  justify-content: space-between;
}

.btn-edit,
.btn-delete {
  padding: 10px 15px;
  border: none;
  border-radius: 8px;
  cursor: pointer;
  font-size: 14px;
  color: #fff;
  transition: background 0.3s ease, transform 0.2s ease;
}

.btn-edit {
  background: linear-gradient(45deg, #00ffff, #00ff99);
}

.btn-edit:hover {
  background: linear-gradient(45deg, #00ff99, #00ffff);
  transform: scale(1.1);
}

.btn-delete {
  background: linear-gradient(45deg, #ff0066, #ff0000);
}

.btn-delete:hover {
  background: linear-gradient(45deg, #ff0000, #ff0066);
  transform: scale(1.1);
}

.no-announcements {
  text-align: center;
  font-size: 18px;
  color: #aaa;
}

.btn-new-announcement,
.btn-logout {
  display: block;
  margin: 20px auto;
  padding: 12px 20px;
  font-size: 16px;
  color: #fff;
  border: none;
  border-radius: 8px;
  cursor: pointer;
  background: linear-gradient(45deg, #ff00ff, #00ffff);
  box-shadow: 0 0 15px rgba(0, 255, 255, 0.5), 0 0 30px rgba(255, 0, 255, 0.5);
  transition: all 0.3s ease;
}

.btn-new-announcement:hover,
.btn-logout:hover {
  transform: scale(1.1);
  box-shadow: 0 0 20px rgba(0, 255, 255, 0.8), 0 0 40px rgba(255, 0, 255, 0.8);
}
</style>
