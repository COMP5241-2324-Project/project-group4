<script setup>
import { ref } from 'vue'
import Total from './components/total.vue'
import Individual from './components/individual.vue'
import Evaluation from './components/evaluation.vue'
</script>

<template>
  <div>
    <page-container title="">
      <Total />
    </page-container>

    <div style="display: flex; justify-content: space-between;">
      <Individual />
      <Evaluation />
    </div>
  </div>
</template>

<style lang="scss" scoped>
.page-container {
  min-height: 400px;
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
