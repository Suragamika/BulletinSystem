<template>
  <div class="announcement-list-container">
    <!-- 页头 -->
    <header class="header">
      <h1>班级公告栏</h1>
      <div class="user-info">
        {{ username }} | <router-link to="/" @click="handleLogout">退出登录</router-link>
      </div>
    </header>

    <!-- 公告列表 -->
    <div class="announcement-cards">
      <AnnouncementCard
          v-for="announcement in announcements"
          :key="announcement.id"
          :title="announcement.title"
          :author="announcement.author"
          :createdAt="announcement.created_at"
          @viewDetails="viewAnnouncement(announcement.id)"
      />
    </div>

    <!-- 分页 -->
    <Pagination
        :currentPage="currentPage"
        :totalPages="totalPages"
        @prevPage="prevPage"
        @nextPage="nextPage"
    />
  </div>
</template>

<script>
import AnnouncementCard from "@/components/AnnouncementCard.vue";
import Pagination from "@/components/Pagination.vue";

export default {
  components: {
    AnnouncementCard,
    Pagination,
  },
  data() {
    return {
      username: localStorage.getItem("username") || "未知用户",
      announcements: [],
      currentPage: 1,
      totalPages: 1,
    };
  },
  methods: {
    async fetchAnnouncements() {
      try {
        const response = await axios.get(
            `http://localhost:8080/BulletinSystem/api/announcements?page=${this.currentPage}`
        );
        this.announcements = response.data.announcements;
        this.totalPages = response.data.totalPages;
      } catch (error) {
        console.error("无法加载公告列表：", error);
      }
    },
    viewAnnouncement(id) {
      this.$router.push(`/announcement/${id}`);
    },
    prevPage() {
      if (this.currentPage > 1) {
        this.currentPage--;
        this.fetchAnnouncements();
      }
    },
    nextPage() {
      if (this.currentPage < this.totalPages) {
        this.currentPage++;
        this.fetchAnnouncements();
      }
    },
    handleLogout() {
      localStorage.clear();
      this.$router.push("/");
    },
  },
  created() {
    this.fetchAnnouncements();
  },
};
</script>

<style scoped>
/* 页头样式 */
.header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 10px 20px;
  background-color: #1a1a1a;
  color: #00ffff;
}

/* 公告列表容器 */
.announcement-list-container {
  max-width: 800px;
  margin: 20px auto;
  padding: 10px;
  font-family: Arial, sans-serif;
  color: white;
}

/* 公告卡片样式 */
.announcement-cards {
  display: flex;
  flex-direction: column;
  gap: 15px;
}
</style>
