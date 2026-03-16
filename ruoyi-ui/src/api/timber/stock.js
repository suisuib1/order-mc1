import request from '@/utils/request'

export function listStock(query) {
  return request({
    url: '/timber/stock/list',
    method: 'get',
    params: query
  })
}

export function listStockFlow(query) {
  return request({
    url: '/timber/stock/flow/list',
    method: 'get',
    params: query
  })
}

export function stockIn(data) {
  return request({
    url: '/timber/stock/in',
    method: 'post',
    data
  })
}

export function stockAdjust(data) {
  return request({
    url: '/timber/stock/adjust',
    method: 'post',
    data
  })
}
