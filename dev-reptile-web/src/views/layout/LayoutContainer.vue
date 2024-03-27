<script setup>
import {
  Management,
  Avatar,
  Menu,
  List,
  Document
} from '@element-plus/icons-vue'
import { onMounted } from 'vue'
import { useRouter } from 'vue-router'
const router = useRouter()

</script>

<template>
  <el-container class="layout-container">
    <el-aside width="200px">
      <div class="el-aside__logo"></div>
      <el-menu active-text-color="#ffd04b" background-color="#232323" :default-active="$route.path" text-color="#fff"
        router>
        <el-menu-item index="/account/index">
          <el-icon>
            <Avatar />
          </el-icon>
          <span>Account</span>
        </el-menu-item>
        <el-menu-item index="/teams/index">
          <el-icon>
            <Menu />
          </el-icon>
          <span>Teams</span>
        </el-menu-item>
        <el-menu-item index="/report/index">
          <el-icon>
            <List />
          </el-icon>
          <span>Report</span>
        </el-menu-item>
        <el-menu-item index="/teams/team1">
          <el-icon>
            <Management />
          </el-icon>
          <span>Dashboard</span>
        </el-menu-item>
      </el-menu>
    </el-aside>
    <el-container>
      <el-header>
        <div>
          COMP 5241 Group4 Project
        </div>
      </el-header>
      <el-main>
        <router-view></router-view>
      </el-main>
      <!-- <el-footer>大事件 ©2023 Created by 黑马程序员</el-footer> -->
    </el-container>
  </el-container>
</template>

<style lang="scss" scoped>
.layout-container {
  height: 100vh;

  .el-aside {
    background-color: #232323;

    &__logo {
      height: 120px;
      background: url('@/assets/polyu_logo.png') no-repeat center / 120px auto;
      background-size: 80%;
    }

    .el-menu {
      border-right: none;
    }
  }

  .el-header {
    background-color: #fff;
    display: flex;
    align-items: center;
    justify-content: space-between;

    .el-dropdown__box {
      display: flex;
      align-items: center;

      .el-icon {
        color: #999;
        margin-left: 10px;
      }

      &:active,
      &:focus {
        outline: none;
      }
    }
  }

  .el-footer {
    display: flex;
    align-items: center;
    justify-content: center;
    font-size: 14px;
    color: #666;
  }
}
</style>
