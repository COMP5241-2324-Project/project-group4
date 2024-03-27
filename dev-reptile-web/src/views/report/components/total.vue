<script setup>
import { ref, onMounted } from 'vue'
import useEchart from '@/utils/useEchart'

const divRef = ref(null)
let myChart = null

const setupEchart = () => {
  if (!myChart) {
    myChart = useEchart(divRef.value)
  }
  let option = getOption()
  myChart.setOption(option)
}

const getOption = () => {
  return {
    backgroundColor: '#EEF0F2',
    legend: {
      show: true,
      bottom: 0
    },
    xAxis: {
      type: 'category',
      data: ['Team1', 'Team2', 'Team3', 'Team4', 'Team5', 'Team6', 'Team7', 'Team8', 'Team9', 'Team10', 'Team11', 'Team12', 'Team13', 'Team14', 'Team15', 'Team16']
    },
    yAxis: {
      type: 'value'
    },
    series: [
      {
        name: 'score',
        data: [120, 200, 150, 80, 70, 110, 130, 140, 120, 90, 180, 170, 140, 120, 79, 97],
        type: 'bar',
        // showBackground: true,
        // backgroundStyle: {
        //   color: 'rgba(180, 180, 180, 0.2)'
        // }
      }
    ],
    grid: {
      top: '10%',
      left: '5%',
      right: '5%',
      bottom: '20%'
    }
  }
}

onMounted(() => {
  setupEchart()
})

const rankingList = ref([
  { no: 1, name: 'Team1', score: 323232 },
  { no: 2, name: 'Team2', score: 323232 },
  { no: 3, name: 'Team3', score: 323232 },
  { no: 4, name: 'Team4', score: 323232 },
  { no: 5, name: 'Team5', score: 323232 },
  { no: 6, name: 'Team6', score: 323232 },
  { no: 7, name: 'Team7', score: 323232 },
  { no: 8, name: 'Team8', score: 323232 }
])

</script>

<template>
  <div class="container">
    <div class="score">
      <div class="score-title">Teams Score</div>
      <div class="score-columns">
        <div id="columnChart" style="height: 260px;" ref="divRef"></div>
      </div>
    </div>
    <div class="ranking">
      <div class="ranking-title">Teams Ranking</div>
      <div class="ranking-list">
        <div class="ranking-list-item" v-for="(item, index) in rankingList" :key="item.no">
          <div class="ranking-list-item-number">{{ item.no }}</div>
          <div class="ranking-list-item-name">{{ item.name }}</div>
          <div class="ranking-list-item-score">{{ item.score }}</div>
        </div>
      </div>
    </div>
  </div>
</template>

<style lang="scss" scoped>
.container {
  display: flex;
}

.score {
  flex: 1;

  &-title {
    margin-top: 16px;
    font-weight: 700;
  }

  &-columns {
    margin: 40px 60px 40px 0;
  }
}

.ranking {
  &-title {
    font-weight: 700;
    margin-top: 16px;
    margin-bottom: 40px;
  }

  &-list {
    &-item {
      display: flex;
      margin: 14px 0 14px 0;
      font-size: 14px;

      &-number {
        background-color: red;
        width: 16px;
        height: 16px;
        color: white;
        border-radius: 50%;
        font-size: 12px;
        text-align: center;
        line-height: 16px;
        margin-right: 40px;
        margin-top: 3px;
      }

      &-name {
        margin-right: 40px;
      }

      &-score {
        margin-right: 80px;
      }
    }
  }
}
</style>
