<script setup>
import { ref } from 'vue'
import Total from './components/total.vue'
import Individual from './components/individual.vue'
import Evaluation from './components/evaluation.vue'
const activeName = ref('first')

const handleClick = (tab, event) => {
  console.log(tab, event)
}
</script>

<template>
  <div>
    <page-container title="">
      <el-tabs v-model="activeName" class="demo-tabs" @tab-click="handleClick">
        <el-tab-pane label="Total" name="first">
          <Total />
        </el-tab-pane>
        <el-tab-pane label="Commit" name="second">Config</el-tab-pane>
        <el-tab-pane label="Issues" name="third">Role</el-tab-pane>
        <el-tab-pane label="Pull Requests" name="fourth">Task</el-tab-pane>
        <el-tab-pane label="Comments" name="fifth">Task</el-tab-pane>
        <el-tab-pane label="Project Board" name="sixth">Task</el-tab-pane>
        <el-tab-pane label="Tasks Completion" name="seventh">Task</el-tab-pane>
      </el-tabs>

      <!-- <Individual /> -->
    </page-container>

    <div style="display: flex; justify-content: space-between;">
      <Individual />
      <Evaluation />
    </div>
  </div>
</template>

<style lang="scss" scoped>
.page-container {
  min-height: 478px;
}

:deep() {
  .el-card__header {
    font-weight: 700;
    border-bottom: 0px;
    padding: 0px;
  }
}

.demo-tabs>.el-tabs__content {
  padding: 32px;
  color: #6b778c;
  font-size: 32px;
  font-weight: 600;
}
</style>
