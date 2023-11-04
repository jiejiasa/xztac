import request from '@/utils/request'

export function getList(data) {
  return request({
    url: '/garagePrice/getList',
    method: 'post',
    data: data
  })
}


export function insert(data) {
  return request({
    url: '/garagePrice/insert',
    method: 'post',
    data: data
  })
}

export function updatePrice(data) {
  return request({
    url: '/garagePrice/updatePrice',
    method: 'post',
    data: data
  })
}


export function getInfo(id) {
  return request({
    url: '/garagePrice/getInfo' + '?id=' + id,
    method: 'get',
  })
}
