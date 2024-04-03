<script setup>
import { ref } from 'vue'
import { nanoid } from 'nanoid'

const commitList = ref([
  { id: nanoid(), avatar: '', name: 'Haoyun-PolyU', contribution: 'Backend Development' },
  { id: nanoid(), avatar: '', name: 'Andy-King2', contribution: 'Backend Development' },
  { id: nanoid(), avatar: '', name: 'Gangplank85', contribution: 'Frontend Development' },
  { id: nanoid(), avatar: '', name: 'MC572', contribution: 'UI & Presentation' },
  { id: nanoid(), avatar: '', name: 'Nomi-nn', contribution: 'UI & Presentation' },
])

const avatar = ref({
  circleUrl: 'https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png'
})

</script>

<template>
  <el-card style="width: 65%; margin-top: 20px;">
    <template #header>
      <div class="card-header">
        <span>Members Contribution</span>
      </div>
    </template>
    <div class="card-body">
      <div class="card-body-item" v-for="(item, index) in commitList" :key="item.id">
        <div style="display: flex;">
          <el-avatar :size="32" :src="avatar.circleUrl" />
          <div style="display: flex; flex-direction: column;">
            <div class="card-body-item-name">{{ item.name }}</div>
            <div class="card-body-item-contribution">{{ item.contribution }}</div>
          </div>
        </div>
        <el-divider />
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

  .el-card__body {
    --el-card-padding: 0;
  }
}

.card-body {
  &-item {
    &-name {
      color: #262626;
      font-size: 14px;
      margin-top: 16px;
    }

    &-contribution {
      color: #8C8C8C;
      font-size: 12px;
      margin-top: 10px;
    }
  }
}

.el-avatar {
  margin: 16px 20px 0 20px;
}

.el-divider--horizontal {
  margin: 16px 0 0 0;
}
</style>
