<script setup>
import { ref } from 'vue'
import { nanoid } from 'nanoid'

const inputContext = ref('')

const teamInfo = ref([
  { id: nanoid(), name: 'Team1', members: 4, time: 'Just now' },
  { id: nanoid(), name: 'Team2', members: 6, time: '2 hrs ago' },
  { id: nanoid(), name: 'Team3', members: 4, time: '4 hrs ago' },
  { id: nanoid(), name: 'Team4', members: 5, time: '6 hrs ago' },
  { id: nanoid(), name: 'Team5', members: 5, time: 'Just now' },
  { id: nanoid(), name: 'Team6', members: 5, time: 'Just now' },
  { id: nanoid(), name: 'Team7', members: 5, time: 'Just now' },
  { id: nanoid(), name: 'Team8', members: 5, time: 'Just now' },
  { id: nanoid(), name: 'Team9', members: 5, time: 'Just now' },
  { id: nanoid(), name: 'Team10', members: 5, time: 'Just now' },
  { id: nanoid(), name: 'Team11', members: 4, time: 'Just now' },
  { id: nanoid(), name: 'Team12', members: 5, time: 'Just now' },
  { id: nanoid(), name: 'Team13', members: 4, time: 'Just now' },
  { id: nanoid(), name: 'Team14', members: 4, time: 'Just now' },
  { id: nanoid(), name: 'Team15', members: 4, time: 'Just now' },
  { id: nanoid(), name: 'Team16', members: 3, time: 'Just now' }
])
</script>

<template>
  <page-container title="Teams list">
    <template #extra>
      <!-- <el-input v-model="input" style="width: 240px" placeholder="Please input" /> -->
      <el-input v-model="inputContext" style="width: 400px" placeholder="Please input" class="input-with-select">
        <template #append>
          <el-button type="primary">Search</el-button>
        </template>
      </el-input>
    </template>

    <div class="team-card-container">
      <div class="team-card-item" v-for="(item, index) in teamInfo" :key="item.id">
        <img src="@/assets/team_image.jpg" class="team-card-item-image" />
        <div class="team-card-item-name">{{ item.name }}</div>
        <div class="team-card-item-member">{{ item.members }} members</div>
        <!-- <div class="team-card-item-time">{{ item.time }}</div> -->
      </div>
    </div>
  </page-container>
</template>

<style lang="scss" scoped>
:deep() {
  .el-card__header {
    position: relative;
    font-weight: 700;
    height: 150px;

    .extra {
      position: absolute;
      left: 50%;
      top: 50%;
      transform: translateX(-50%) translateY(-50%);

      .el-input-group__append {
        background-color: var(--el-color-primary);
        color: var(--el-color-white);
      }

      .el-input-group__append:active {
        background-color: var(--el-color-primary-dark-2);
      }

      .el-input-group__append:hover {
        background-color: var(--el-color-primary-light-3);
      }
    }
  }
}

.team-card-container {
  display: flex;
  flex-wrap: wrap;
  // justify-content: space-between;

  .team-card-item {
    height: 250px;
    width: 284px;
    background-color: #efefef;
    margin: 15px;
    border-radius: 10px;
    box-shadow: 5px 5px 5px #ccc;

    &-image {
      width: 284px;
      border-radius: 10px 10px 0 0;
    }

    &-name {
      font-weight: bold;
      margin: 10px 0 0 20px;
      font-size: 16px;
    }

    &-member {
      margin: 12px 0 0 20px;
      color: #333;
      font-size: 14px;
    }

    &-time {
      margin: 16px 0 0 20px;
      color: #999;
      font-size: 12px;
    }
  }
}
</style>
