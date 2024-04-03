<script setup>
import { ref, onBeforeMount } from 'vue'
import { getComments } from '@/api/index'
import { convertTimestamp, objectValuesToArray } from '@/utils/format'

const avatar = ref({
  circleUrl: 'https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png'
})

const list = ref([])

const buildList = array => {
  const len = array.length
  let name, commentsCount, comments
  for (let i = 0; i < len; ++i) {
    name = array[i].name
    commentsCount = array[i].commentsCount
    comments = array[i].comments
    for (let j = 0; j < commentsCount; ++j) {
      list.value.push({
        name,
        message: comments[j].message,
        date: convertTimestamp(comments[j].date)
      })
    }
  }
  list.value.sort((a, b) => new Date(b.date) - new Date(a.date))
}

const getGithubComments = async () => {
  let obj = await getComments()
  let data = objectValuesToArray(obj)
  buildList(data)
}

onBeforeMount(async () => {
  getGithubComments()
})

</script>

<template>
  <el-card style="width: 65%; margin-top: 20px; height: 500px; overflow-y: scroll;">
    <template #header>
      <div class="card-header">
        <span>Discussion/Comments</span>
      </div>
    </template>
    <div class="card-body">
      <div class="card-body-item" v-for="(item, index) in list" :key="index">
        <div style="display: flex;">
          <el-avatar :size="32" :src="avatar.circleUrl" />
          <div style="display: flex; flex-direction: column; flex: 1;">
            <div class="card-body-item-name">{{ item.name }}</div>
            <div class="card-body-item-message">{{ item.message }}</div>
          </div>
          <div class="card-body-item-date">{{ item.date }}</div>
        </div>
        <el-divider />
      </div>
    </div>
  </el-card>
</template>

<style lang="scss" scoped>
:deep() {
  .el-card__header {
    padding: calc(var(--el-card-padding) - 2px) var(--el-card-padding) !important;
    border-bottom: 1px solid var(--el-card-border-color) !important;
    box-sizing: border-box;
  }

  .el-card__body {
    --el-card-padding: 0;
  }
}

.card-body {
  &-item {
    &-name {
      color: #262626;
      font-size: 14px;
      margin-top: 16px;
    }

    &-message {
      color: #8C8C8C;
      font-size: 12px;
      margin-top: 10px;
    }

    &-date {
      color: #8C8C8C;
      font-size: 12px;
      margin: 16px 20px 0 0;
    }
  }
}

.el-avatar {
  margin: 16px 20px 0 20px;
}

.el-divider--horizontal {
  margin: 16px 0 0 0;
}
</style>
