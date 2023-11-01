import request from '@/utils/request'

export function getList(data) {
  return request({
    url: '/garagePrice/getList',
    method: 'post',
    data: data
  })
}
