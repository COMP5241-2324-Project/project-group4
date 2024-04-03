<script setup>
import { ref } from 'vue'
import { useActStore } from '@/store/activity'

const activities = ref([
  { activity: 'Commit Frequency', action: 'Update README.md', studentName: 'Haoyun-PolyU', time: '2024-03-18 12:50:11' },
  { activity: 'Issues', action: 'About the commit method', studentName: 'Haoyun-PolyU', time: '2024-03-26 20:08:20' },
  { activity: 'Pull Requests', action: 'Apply for merger', studentName: 'Haoyun-PolyU', time: '2024-03-27 13:11:39' },
  { activity: 'Discussion/Comments', action: 'I think we may ...', studentName: 'Haoyun-PolyU', time: '2024-03-26 15:46:41' },
  { activity: 'Milestone', action: 'Configure the basic backend', studentName: 'Andy-King2', time: '2024-03-27 03:24:25' },
  { activity: 'Members Contribution', action: 'frontend development', studentName: 'Gangplank85', time: '2024-03-27 13:04:20' }
])


const store = useActStore()

const toggle = index => {
  store.index = index + 1
}

</script>

<template>
  <el-card style="width: 65%; margin-top: 20px;">
    <template #header>
      <div class="card-header">
        <span>Activity</span>
      </div>
    </template>
    <div class="card-body">
      <router-link class="card-body-item" v-for="(item, index) in activities" :key="item.activity"
        :to="`/teams/team4/${index + 1}`" @click="toggle(index)">
        <div class="card-body-item-activity">{{ item.activity }}</div>
        <div class="card-body-item-action">{{ item.action }}</div>
        <div style="display: flex;justify-content: space-between;">
          <div class="card-body-item-studentname">{{ item.studentName }}</div>
          <div class="card-body-item-time">{{ item.time }}</div>
        </div>
      </router-link>
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
    cursor: pointer;
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
