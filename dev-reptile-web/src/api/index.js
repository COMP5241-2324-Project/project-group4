import request from '@/utils/request'

export const getCommits = () => {
  return request({
    url: '/commits',
    method: 'GET'
  })
}

export const getComments = () => {
  return request({
    url: '/comments',
    method: 'GET'
  })
}

export const getIssues = () => {
  return request({
    url: '/issues',
    method: 'GET'
  })
}

export const getPulls = () => {
  return request({
    url: '/pulls',
    method: 'GET'
  })
}

export const getMilestone = () => {
  return request({
    url: '/milestones',
    method: 'GET'
  })
}

export const getScore = () => {
  return request({
    url: '/score',
    method: 'GET'
  })
}