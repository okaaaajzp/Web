<template>
    <div class="grid-container">
      <el-row gutter="20">
        <el-col v-for="item in gridData" :key="item.id" :span="8">
          <el-card @click="handleClick(item.id)" class="clickable-card">
            <h3>{{ item.title }}</h3>
            <p>{{ item.description }}</p>
          </el-card>
        </el-col>
      </el-row>
    </div>
  </template>
  
  <script>
  export default {
    data() {
      return {
        gridData: [
  { "id": 1, "title": "卡片 1", "description": "描述 1" },
  { "id": 2, "title": "卡片 2", "description": "描述 2" },
  { "id": 3, "title": "卡片 3", "description": "描述 3" },
  { "id": 4, "title": "卡片 4", "description": "描述 4" },
  { "id": 5, "title": "卡片 5", "description": "描述 5" },
  { "id": 6, "title": "卡片 6", "description": "描述 6" }
] // 存储从后端获取的数据
      };
    },
    mounted() {
      this.fetchData();
    },
    methods: {
      async fetchData() {
        try {
          const response = await fetch('你的后端API地址'); // 替换为你的API地址
          this.gridData = await response.json();
        } catch (error) {
          console.error('获取数据失败', error);
        }
      },
      handleClick(id) {
        console.log(`点击了卡片 ID: ${id}`);
        // 这里可以进行跳转或其他操作
      }
    }
  };
  </script>
  
  <style>
  .grid-container {
    padding: 20px;
  }
  .clickable-card {
    cursor: pointer;
    transition: transform 0.2s;
  }
  .clickable-card:hover {
    transform: scale(1.05);
  }
  </style>
  