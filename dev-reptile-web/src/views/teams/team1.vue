<script setup>
import { ref } from 'vue'
import Activity from './components/Activity.vue'
import ActivityIndex from './components/ActivityIndex.vue'
import Commit from './components/Commit.vue'
import GenAI from './components/GenAI.vue'

const studentList = ref([
  { name: 'ZHANG Yi', stuId: '17863092g' },
  { name: 'ZHANG Er', stuId: '17863092g' },
  { name: 'ZHANG San', stuId: '17863092g' },
  { name: 'ZHANG Si', stuId: '17863092g' },
  { name: 'ZHANG Wu', stuId: '17863092g' }
])
</script>

<template>
  <div>
    <page-container title="">
      <div class="team-title">Team1</div>
      <div class="team-content">
        <div class="team-content-list">
          <div class="student-list" v-for="(item, index) in studentList" :key="item.name">
            Student {{ index + 1 }}: {{ item.name }} {{ item.stuId }}
          </div>
        </div>
        <div class="team-content-indicator">
          <div class="stat">
            <div class="stat-name">Files</div>
            <div class="stat-number">56</div>
          </div>
          <el-divider direction="vertical" />
          <div class="stat">
            <div class="stat-name">Team Ranking</div>
            <div class="stat-number">3/8</div>
          </div>
          <el-divider direction="vertical" />
          <div class="stat">
            <div class="stat-name">Project Visits</div>
            <div class="stat-number">233</div>
          </div>
        </div>

      </div>
    </page-container>
    <div style="display: flex; justify-content: space-between;">
      <Activity />
      <ActivityIndex />
    </div>
    <div style="display: flex; justify-content: space-between;">
      <Commit />
      <GenAI />
    </div>
  </div>
</template>

<style lang="scss" scoped>
.page-container {
  min-height: 240px;
}

:deep() {
  .el-card__header {
    font-weight: 700;
    border-bottom: 0px;
    padding: 0px;
  }
}

.team-title {
  font-weight: 700;
  margin-top: 16px;
  margin-bottom: 30px;
}

.team-content {
  display: flex;
  justify-content: space-between;
  align-items: center;

  .student-list {
    color: #8c8c8c;
    padding-top: 6px;
    font-size: 14px;
  }

  &-indicator {
    width: 400px;
    height: 64px;
    display: flex;

    .stat {
      margin: 0 16px 0;
      display: flex;
      flex-direction: column;
      align-items: center;
      justify-content: space-between;

      &-name {
        color: #8c8c8c;
        font-size: 14px;
      }

      &-number {
        color: #262626;
        font-size: 22px;
      }
    }
  }
}
</style>
