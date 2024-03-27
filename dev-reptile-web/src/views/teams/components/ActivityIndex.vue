<script setup>
import { ref, onMounted } from 'vue'
import useEchart from '@/utils/useEchart'
import '@/style.css'

const echartData = ref([
  8, 5, 9, 5, 8, 4
])

let divRef = ref(null)
let myChart = null

onMounted(() => {
  setupEchart(echartData.value)
})

const setupEchart = echartData => {
  if (!myChart) {
    myChart = useEchart(divRef.value)
  }
  let option = getOption(echartData)
  myChart.setOption(option)
}
const getOption = echartData => {
  return {
    legend: {
      data: ['Team'],
      right: '20',
      top: '20'
    },
    radar: {
      // shape: 'circle',
      indicator: [
        { name: 'Commit Frequency', max: 10 },
        { name: 'Issues', max: 10 },
        { name: 'Pull Requests', max: 10 },
        { name: 'Discussion/Comments', max: 10 },
        { name: 'Project Board', max: 10 },
        { name: 'Tasks Completion', max: 10 }
      ]
    },
    series: [
      {
        name: 'Budget vs spending',
        type: 'radar',
        data: [
          {
            value: echartData,
            name: 'Team',
            label: {
              show: true
            }
          }
        ]
      }
    ]
  }
}

</script>

<template>
  <el-card style="width: 33%; margin-top: 20px;">
    <template #header>
      <div class="card-header">
        <span>Activity Index</span>
      </div>
    </template>
    <div class="center">
      <div class="chart" ref="divRef"></div>
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

.chart {
  // margin: 5px;
  width: 500px;
  height: 300px;


}
</style>
