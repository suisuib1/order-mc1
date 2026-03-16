import request from '@/utils/request'

// 查询报表数据
export function getReportData(query) {
  return request({
    url: '/timber/report/data',
    method: 'get',
    params: query
  })
}
