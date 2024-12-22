<template>
  <div class="announcement-card" @click="onClick">
    <h2 class="announcement-title">{{ title }}</h2>
    <p class="announcement-meta">作者：{{ author }} | {{ createdAt }}</p>
  </div>
</template>

<script>
export default {
  props: {
    title: {
      type: String,
      required: true,
    },
    author: {
      type: String,
      required: true,
    },
    createdAt: {
      type: String,
      required: true,
    },
  },
  methods: {
    onClick() {
      this.$emit("viewDetails"); // 点击事件向父组件发送信号
    },
  },
};
</script>

<style scoped>
.announcement-card {
  padding: 15px;
  border: 1px solid #00cccc;
  border-radius: 8px;
  background-color: #1a1a1a;
  cursor: pointer;
  transition: transform 0.3s, box-shadow 0.3s;
}

.announcement-card:hover {
  transform: translateY(-5px);
  box-shadow: 0 5px 15px rgba(0, 255, 255, 0.3);
}

.announcement-title {
  font-size: 18px;
  color: #00ffff;
  margin-bottom: 5px;
}

.announcement-meta {
  font-size: 14px;
  color: #cccccc;
}
</style>
