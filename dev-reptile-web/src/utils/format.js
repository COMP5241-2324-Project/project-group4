import { dayjs } from 'element-plus'

export const formatTime = (time) => dayjs(time).format('YYYY年MM月DD日')

export const convertTimestamp = (timestamp) => {
  // 创建一个新的Date对象，传入时间戳（以毫秒为单位）
  var date = new Date(timestamp);

  // 获取年、月、日、小时、分钟和秒
  var year = date.getFullYear();
  var month = ('0' + (date.getMonth() + 1)).slice(-2);
  var day = ('0' + date.getDate()).slice(-2);
  var hours = ('0' + date.getHours()).slice(-2);
  var minutes = ('0' + date.getMinutes()).slice(-2);
  var seconds = ('0' + date.getSeconds()).slice(-2);

  // 拼接日期时间字符串
  var formattedDate = year + '-' + month + '-' + day + ' ' + hours + ':' + minutes + ':' + seconds;

  return formattedDate;
}

export const objectValuesToArray = obj => Object.values(obj)
