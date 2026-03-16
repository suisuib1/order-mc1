import request from '@/utils/request'

export function listSkill(query) {
  return request({ url: '/system/skill/list', method: 'get', params: query })
}

export function getSkill(id) {
  return request({ url: '/system/skill/' + id, method: 'get' })
}

export function addSkill(data) {
  return request({ url: '/system/skill', method: 'post', data: data })
}

export function updateSkill(data) {
  return request({ url: '/system/skill', method: 'put', data: data })
}

export function delSkill(id) {
  return request({ url: '/system/skill/' + id, method: 'delete' })
}
