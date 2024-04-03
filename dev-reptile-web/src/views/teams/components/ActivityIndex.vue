<script setup>
import { ref, onBeforeMount, onMounted } from 'vue'
import { getScore } from '@/api/index'
import useEchart from '@/utils/useEchart'
import '@/style.css'
import { objectValuesToArray } from '@/utils/format'

const echartData = ref([])

const buildEchart = array => {
  const len = array.length
  let commitsCount = 0,
    commentsCount = 0,
    issuesCount = 0,
    pullsCount = 0
  for (let i = 0; i < len; ++i) {
    commitsCount += Number(array[i].commitsCount)
    commentsCount += Number(array[i].commentsCount)
    issuesCount += Number(array[i].issuesCount)
    pullsCount += Number(array[i].pullsCount)
  }
  echartData.value = [commitsCount, issuesCount, pullsCount, commentsCount, 10, 10]
}

const getGithubScore = async () => {
  let obj = await getScore()
  let data = objectValuesToArray(obj)
  buildEchart(data)
  setupEchart(echartData.value)
}

onBeforeMount(async () => {
  getGithubScore()
})

let divRef = ref(null)
let myChart = null


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
        { name: 'Commit Frequency', max: 80 },
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
