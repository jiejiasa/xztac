import request from '@/utils/request'

export function getListg(data) {
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
    url: '/garagePrice/update',
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


export function selectList() {
  return request({
    url: '/garagePrice/selectList',
    method: 'get',
  })
}
