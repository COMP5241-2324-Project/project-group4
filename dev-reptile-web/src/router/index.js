import { createRouter, createWebHistory } from 'vue-router'
// import { useUserStore } from '@/stores'

// createRouter 创建路由实例
// 配置 history 模式
// 1. history模式：createWebHistory     地址栏不带 #
// 2. hash模式：   createWebHashHistory 地址栏带 #
// console.log(import.meta.env.DEV)

// vite 中的环境变量 import.meta.env.BASE_URL  就是 vite.config.js 中的 base 配置项
const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      component: () => import('@/views/layout/LayoutContainer.vue'),
      redirect: '/account/index',
      children: [
        {
          path: 'account/index',
          component: () => import('@/views/account/index.vue')
        },
        {
          path: '/teams/index',
          component: () => import('@/views/teams/index.vue')
        },
        {
          path: '/teams/team4',
          component: () => import('@/views/teams/team4.vue'),
          redirect: 'teams/team4/1',
          children: [
            {
              path: '1',
              component: () => import('@/views/teams/components/Commit.vue')
            },
            {
              path: '2',
              component: () => import('@/views/teams/components/Issue.vue')
            },
            {
              path: '3',
              component: () => import('@/views/teams/components/Pull.vue')
            },
            {
              path: '4',
              component: () => import('@/views/teams/components/Comment.vue')
            },
            {
              path: '5',
              component: () => import('@/views/teams/components/Milestone.vue')
            },
            {
              path: '6',
              component: () => import('@/views/teams/components/Completion.vue')
            },
          ]
        },
        {
          path: '/report/index',
          component: () => import('@/views/report/index.vue')
        }
      ]
    }
  ]
})

// 登录访问拦截 => 默认是直接放行的
// 根据返回值决定，是放行还是拦截
// 返回值：
// 1. undefined / true  直接放行
// 2. false 拦回from的地址页面
// 3. 具体路径 或 路径对象  拦截到对应的地址
//    '/login'   { name: 'login' }
// router.beforeEach((to) => {
//   // 如果没有token, 且访问的是非登录页，拦截到登录，其他情况正常放行
//   const useStore = useUserStore()
//   if (!useStore.token && to.path !== '/login') return '/login'
// })

export default router
