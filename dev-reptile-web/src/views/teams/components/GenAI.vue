<script setup>
import { ref, onMounted } from 'vue'
import {
  Search
} from '@element-plus/icons-vue'

const inputContext = ref('')

const context = ref('GenAI can analyze the students performance by analyzing the types of the tasks performed by different team members.')
</script>

<template>
  <el-card style="width: 33%; margin-top: 20px;">
    <template #header>
      <div class="card-header">
        <span>GenAI</span>
      </div>
    </template>
    <el-input v-model="inputContext" style="width: 340px" placeholder="input search text">
      <template #append>
        <el-icon>
          <Search />
        </el-icon>
      </template>
    </el-input>
    <div class="genai-context">{{ context }}</div>
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
    --el-card-padding: 39px;
  }

  .el-input-group__append {
    padding: 0 10px;
    cursor: pointer;
  }
}

.genai-context {
  color: #8C8C8C;
  margin-top: 60px;
  font-size: 16px;
  line-height: 28px;

}
</style>
