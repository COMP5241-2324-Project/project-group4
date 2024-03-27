<script setup>
import { ref, onMounted } from 'vue'
import useEchart from '@/utils/useEchart'

const echartData = ref([
  { name: 'Commit', value: 10 },
  { name: 'Issues', value: 15 },
  { name: 'Pull Requests', value: 10 },
  { name: 'Comments', value: 15 },
  { name: 'Project Board', value: 20 },
  { name: 'Tasks Completion', value: 30 }
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
const getOption = pieDatas => {
  let colors = ['#5970C0', '#9ECA7E', '#F3C96B', '#DE6E6A', '#84BEDB', '#599F76']
  let total = pieDatas.reduce((a, b) => {
    return a + b.value
  }, 0)
  let data = pieDatas.map((item, index) => {
    let rate = Math.round((item.value / total) * 100)
    return {
      value: item.value,
      name: item.name,
      percentage: rate
    }
  })
  return {
    grid: {
      // show: true,
      top: 0,
      left: 0,
      right: 0,
      bottom: 0
    },
    color: colors,
    legend: {
      orient: 'vertical',
      right: 20,
      bottom: '20%',
      icon: 'circle',
      itemGap: 28,
      itemWidth: 12,
      formatter: name => {
        let currentItem = data.find(item => item.name === name)
        return `{nameStyle|${currentItem.name}}` + `{percentageStyle|${currentItem.percentage}%}`
      },
      textStyle: {
        rich: {
          nameStyle: {
            fontSize: 14,
            color: '#262626',
            padding: [5, 5],
            width: 150
          },
          percentageStyle: {
            fontSize: 12,
            color: '#8a8a8a',
            padding: [0, 5]
          }
        }
      }
    },
    series: [
      {
        type: 'pie',
        center: ['25%', '40%'],
        radius: ['40%', '80%'],
        data: data,
        label: {
          show: false
        }
        // roseType: "area",
      }
    ]
  }
}

</script>

<template>
  <el-card style="width: 48%; margin-top: 20px;">
    <template #header>
      <div class="card-header">
        <span>Evaluation Rubrics</span>
      </div>
    </template>
    <div class="card-body">
      <div>Team/Individual</div>
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
}

.card-body {
  display: flex;
  flex-direction: column;
  // align-items: center;
}

.chart {
  margin-top: 60px;
  width: 100%;
  height: 300px;
}
</style>
