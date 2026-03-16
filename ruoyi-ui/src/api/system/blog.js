import request from '@/utils/request'

export function listBlog(query) {
  return request({
    url: '/system/blog/list',
    method: 'get',
    params: query
  })
}

export function getBlog(id) {
  return request({
    url: '/system/blog/' + id,
    method: 'get'
  })
}

export function addBlog(data) {
  return request({
    url: '/system/blog',
    method: 'post',
    data: data
  })
}

export function updateBlog(data) {
  return request({
    url: '/system/blog',
    method: 'put',
    data: data
  })
}

export function delBlog(id) {
  return request({
    url: '/system/blog/' + id,
    method: 'delete'
  })
}
