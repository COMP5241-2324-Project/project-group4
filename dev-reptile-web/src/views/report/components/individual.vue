<script setup>
import { ref } from 'vue'

const tableData = ref([
  {
    name: 'ZHU Yuanzhang',
    StudentID: '12344321g',
    score: '89'
  },
  {
    name: 'YING Zheng',
    StudentID: '12344320g',
    score: '92'
  },
  {
    name: 'LIU Che',
    StudentID: '14468321g',
    score: '94'
  },
  {
    name: 'LI Shimin',
    StudentID: '12342009g',
    score: '95'
  },
  {
    name: 'ZHAO Kuangying',
    StudentID: '12344391g',
    score: '80'
  },
])

const cellStyle = ({ row, column, rowIndex, columnIndex }) => {
  if (columnIndex == 1) {
    return { 'color': '#1890FF' }
  }
}



</script>

<template>
  <el-card style="width: 48%; margin-top: 20px;">
    <template #header>
      <div class="card-header">
        <span>Individual Rank</span>
      </div>
    </template>
    <div class="card-body">
      <div class="card-body-title">Overall Activities</div>
      <el-table :data="tableData" border style="width: 90%" :cell-style="cellStyle">
        <el-table-column type="index" label="Rank" width="80" />
        <el-table-column prop="name" label="Name" />
        <el-table-column prop="StudentID" sortable label="StudentID" />
        <el-table-column prop="score" sortable label="Score" />
      </el-table>
      <div class="card-body-pagination">
        <el-pagination layout="prev, pager, next" :total="50" />
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
}

.card-body {
  display: flex;
  flex-direction: column;
  align-items: center;

  &-title {
    margin-bottom: 20px;
  }

  &-pagination {
    padding-top: 30px;
    display: flex;
    justify-content: end;
    width: 100%;

  }
}
</style>
