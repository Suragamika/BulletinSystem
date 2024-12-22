<template>
  <div class="pagination">
    <button @click="prevPage" :disabled="currentPage === 1">上一页</button>
    <span>第 {{ currentPage }} 页</span>
    <button @click="nextPage" :disabled="currentPage === totalPages">下一页</button>
  </div>
</template>

<script>
export default {
  props: {
    currentPage: {
      type: Number,
      required: true,
    },
    totalPages: {
      type: Number,
      required: true,
    },
  },
  methods: {
    prevPage() {
      this.$emit("prevPage"); // 通知父组件切换到上一页
    },
    nextPage() {
      this.$emit("nextPage"); // 通知父组件切换到下一页
    },
  },
};
</script>

<style scoped>
.pagination {
  display: flex;
  justify-content: center;
  align-items: center;
  gap: 10px;
  margin-top: 20px;
}

.pagination button {
  padding: 5px 10px;
  background-color: #00ffff;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  color: black;
  font-size: 14px;
  transition: background-color 0.3s;
}

.pagination button:disabled {
  background-color: #444;
  cursor: not-allowed;
}

.pagination button:hover:not(:disabled) {
  background-color: #00cccc;
}

.pagination span {
  font-size: 14px;
  color: #cccccc;
}
</style>
