import request from '@/utils/request'

export function listWork(query) {
  return request({
    url: '/system/work/list',
    method: 'get',
    params: query
  })
}

export function getWork(id) {
  return request({
    url: '/system/work/' + id,
    method: 'get'
  })
}

export function addWork(data) {
  return request({
    url: '/system/work',
    method: 'post',
    data: data
  })
}

export function updateWork(data) {
  return request({
    url: '/system/work',
    method: 'put',
    data: data
  })
}

export function delWork(id) {
  return request({
    url: '/system/work/' + id,
    method: 'delete'
  })
}
