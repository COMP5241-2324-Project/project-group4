<script setup>
import { ref } from 'vue'

const activities = ref([
  { activity: 'Commit Frequency', action: 'Update README.md', studentName: 'ZHANG Yi', time: '2 days ago' },
  { activity: 'Issues', action: 'Update README.md', studentName: 'ZHANG Yi', time: '2 days ago' },
  { activity: 'Pull Requests', action: 'Update README.md', studentName: 'ZHANG Yi', time: '2 days ago' },
  { activity: 'Discussion/Comments', action: 'Update README.md', studentName: 'ZHANG Yi', time: '2 days ago' },
  { activity: 'Project Board', action: 'Update README.md', studentName: 'ZHANG Yi', time: '2 days ago' },
  { activity: 'Tasks Completion', action: 'Update README.md', studentName: 'ZHANG Yi', time: '2 days ago' }
])

</script>

<template>
  <el-card style="width: 65%; margin-top: 20px;">
    <template #header>
      <div class="card-header">
        <span>Activity</span>
      </div>
    </template>
    <div class="card-body">
      <div class="card-body-item" v-for="(item, index) in activities" :key="item.activity">
        <div class="card-body-item-activity">{{ item.activity }}</div>
        <div class="card-body-item-action">{{ item.action }}</div>
        <div style="display: flex;justify-content: space-between;">
          <div class="card-body-item-studentname">{{ item.studentName }}</div>
          <div class="card-body-item-time">{{ item.time }}</div>
        </div>
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
  display: flex;
  flex-wrap: wrap;

  &-item {
    border: .5px solid #E2E2E2;
    width: 33.33%;
    // height: 200px;
    box-sizing: border-box;
    padding: 30px 20px;

    &-activity {
      color: #222222;
      font-size: 16px;
      padding-bottom: 15px;
    }

    &-action {
      color: #828282;
      font-size: 14px;
      padding-bottom: 15px;
    }

    &-studentname {
      color: #828282;
      font-size: 14px;
    }

    &-time {
      color: #BFBFBF;
      font-size: 12px;
    }
  }
}
</style>
