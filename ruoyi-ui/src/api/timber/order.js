import request from '@/utils/request'

export function listOrder(query) {
  return request({
    url: '/timber/order/list',
    method: 'get',
    params: query
  })
}

export function getOrder(orderId) {
  return request({
    url: '/timber/order/' + orderId,
    method: 'get'
  })
}

export function addOrder(data) {
  return request({
    url: '/timber/order',
    method: 'post',
    data
  })
}

export function updateOrder(data) {
  return request({
    url: '/timber/order',
    method: 'put',
    data
  })
}

export function delOrder(orderId) {
  return request({
    url: '/timber/order/' + orderId,
    method: 'delete'
  })
}

export function getOrderBaseData() {
  return request({
    url: '/timber/order/baseData',
    method: 'get'
  })
}

export function changeOrderStatus(orderId, data) {
  return request({
    url: '/timber/order/' + orderId + '/status',
    method: 'post',
    data
  })
}

export function generateProductionTask(orderId, remark) {
  return request({
    url: '/timber/order/' + orderId + '/generateTask',
    method: 'post',
    params: { remark }
  })
}
