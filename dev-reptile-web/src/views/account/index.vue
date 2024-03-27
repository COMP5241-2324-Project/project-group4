<script setup>
import { ref, onMounted } from 'vue'
import '@/style.css'

const avatar = ref({
  squareUrl: 'https://cube.elemecdn.com/9/c2/f0ee8a3c7c9638a54940382568c9dpng.png'
})
const basicInformation = ref([
  {
    desc: 'User Name',
    input: ''
  },
  {
    desc: 'Full Name',
    input: ''
  },
  {
    desc: 'Email Address',
    input: ''
  },
])

const additionalInformation = ref([
  {
    desc: 'Gender',
    input: ''
  },
  {
    desc: 'Additional Information',
    input: ''
  },
  {
    desc: 'Website',
    input: ''
  },
])

const contactInformation = ref([
  {
    desc: 'Phone Number',
    input: ''
  },
  {
    desc: 'Address',
    input: ''
  }
])

</script>

<template>
  <page-container title="">

  </page-container>
  <div class="center acccount-avatar" style="margin-bottom: 20px;">
    <el-avatar shape="square" :size="120" :src="avatar.squareUrl" />
  </div>
  <el-card style="margin-bottom: 20px;">
    <template #header>
      <div class="card-header">
        <span>Basic Information</span>
      </div>
    </template>
    <div class="space-between pd-bottom-20">
      <div v-for="(item, index) in basicInformation" :key="index" style="width: 32%;">
        <div class="mr-bottom-10">{{ item.desc }}</div>
        <el-input v-model="item.input" style="width: 100%" placeholder="Please input" />
      </div>
    </div>
  </el-card>
  <el-card style="margin-bottom: 20px;">
    <template #header>
      <div class="card-header">
        <span>Addtional Information</span>
      </div>
    </template>
    <div class="space-between pd-bottom-20">
      <div v-for="(item, index) in additionalInformation" :key="index" style="width: 32%;">
        <div class="mr-bottom-10">{{ item.desc }}</div>
        <el-input v-model="item.input" style="width: 100%" placeholder="Please input" />
      </div>
    </div>
  </el-card>
  <el-card>
    <template #header>
      <div class="card-header">
        <span>Contact Information</span>
      </div>
    </template>
    <div class="space-between pd-bottom-20">
      <div v-for="(item, index) in contactInformation" :key="index" style="width: 48%;">
        <div class="mr-bottom-10">{{ item.desc }}</div>
        <el-input v-model="item.input" style="width: 100%" placeholder="Please input" />
      </div>
    </div>
  </el-card>

</template>

<style lang="scss" scoped>
.page-container {
  height: 0;
  min-height: 0;
  border: 0;
}

:deep() {

  .el-card__header {
    font-weight: 700;
  }
}
</style>
