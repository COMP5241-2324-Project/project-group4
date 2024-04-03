<script setup>
import { ref, onMounted, onBeforeMount } from 'vue'
import useEchart from '@/utils/useEchart'

const array = ref([])
const generateRandomArray = () => {
  var arr = []
  for (var i = 0; i < 16; i++) {
    var randomNum = Math.floor(Math.random() * 101) + 100
    arr.push(randomNum)
  }
  return arr
}

const divRef = ref(null)
let myChart = null

const setupEchart = array => {
  if (!myChart) {
    myChart = useEchart(divRef.value)
  }

  let option = getOption(array)
  myChart.setOption(option)
}

const getOption = array => {
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
        data: array,
        type: 'bar',
        barWidth: 40
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

onBeforeMount(() => {
  array.value = generateRandomArray()
  buildRankingList(array.value)
})

onMounted(() => {
  setupEchart(array.value)
})

const buildRankingList = array => {
  const len = array.length
  for (let i = 0; i < len; ++i) {
    rankingList.value.push({
      name: `Team${i + 1}`,
      score: array[i]
    })
  }
  rankingList.value.sort((a, b) => b.score - a.score)
  rankingList.value.splice(8, 8)
  console.log(rankingList.value)
}

const rankingList = ref([])

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
        <div class="ranking-list-item" v-for="(item, index) in rankingList" :key="index">
          <div class="ranking-list-item-number">{{ index + 1 }}</div>
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
    margin: 40px 30px 40px 0;
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
        margin-right: 20px;
        width: 60px;
      }

      &-score {
        margin-right: 40px;
      }
    }
  }
}
</style>
