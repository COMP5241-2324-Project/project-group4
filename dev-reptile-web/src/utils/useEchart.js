import * as echarts from 'echarts'

const useEchart = (divEl) => {
  const echartInstance = echarts.init(divEl, undefined, { renderer: 'svg' })
  // 组件卸载时，销毁echart实例
  // onUnmounted(() => {
  //   echartInstance.dispose()
  // })
  const setOption = (option) => {
    echartInstance.setOption(option)
  }
  const resizeEchart = () => {
    echartInstance.resize()
  }
  return { echartInstance, setOption, resizeEchart }
}

export default useEchart
