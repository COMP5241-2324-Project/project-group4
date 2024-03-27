<script setup>
import { ref } from 'vue'
import { nanoid } from 'nanoid'

const commitList = ref([
  { id: nanoid(), avatar: '', message: 'Added student Info', time: '1 minute ago' },
  { id: nanoid(), avatar: '', message: 'Update app.py', time: '30 minutes ago' },
  { id: nanoid(), avatar: '', message: 'Modify system message with author information', time: '2 days ago' },
  { id: nanoid(), avatar: '', message: 'Added student Info', time: '2 days ago' },
  { id: nanoid(), avatar: '', message: 'Added student Info', time: '3 days ago' },
  { id: nanoid(), avatar: '', message: 'Added student Info', time: '5 days ago' }
])

const avatar = ref({
  circleUrl: 'https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png'
})

</script>

<template>
  <el-card style="width: 65%; margin-top: 20px;">
    <template #header>
      <div class="card-header">
        <span>Commit Frequency</span>
      </div>
    </template>
    <div class="card-body">
      <div class="card-body-item" v-for="(item, index) in commitList" :key="item.id">
        <div style="display: flex;">
          <el-avatar :size="32" :src="avatar.circleUrl" />
          <div style="display: flex; flex-direction: column;">
            <div class="card-body-item-message">{{ item.message }}</div>
            <div class="card-body-item-time">{{ item.time }}</div>
          </div>
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
    &-message {
      color: #262626;
      font-size: 14px;
      margin-top: 16px;
    }

    &-time {
      color: #8C8C8C;
      font-size: 12px;
      margin-top: 10px;
    }
  }
}

.el-avatar {
  margin: 16px 20px 0 20px;
}

.el-divider--horizontal {
  margin: 16px 0;
}
</style>
