import request from '@/utils/request'

export function listExperience(query) {
  return request({ url: '/system/experience/list', method: 'get', params: query })
}

export function getExperience(id) {
  return request({ url: '/system/experience/' + id, method: 'get' })
}

export function addExperience(data) {
  return request({ url: '/system/experience', method: 'post', data: data })
}

export function updateExperience(data) {
  return request({ url: '/system/experience', method: 'put', data: data })
}

export function delExperience(id) {
  return request({ url: '/system/experience/' + id, method: 'delete' })
}
