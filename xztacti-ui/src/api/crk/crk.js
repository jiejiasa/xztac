import request from '@/utils/request'
import * as url from 'url'

// 查询库存
export function getList(data) {
  return request({
    url: '/inventory/getList',
    method: 'post',
    data: data
  })
}

export function getInList(data) {
  return request({
    url: '/inventory/getInList',
    method: 'post',
    data: data
  })
}


export function getOUtList(data) {
  return request({
     url: '/inventory/getOutList',
    method: 'post',
    data: data
    }

  )
}

// 原有基础入库
export function save(data) {
  return request({
    url: '/inventory/save',
    method: 'post',
    data: data
  })
}

export function getInventoryInfo(id) {
  return request({
    url: '/inventory/getInventoryInfo' +'?id=' + id,
    method: 'get',
  })
}
// 删除库存信息
export function delInventory(id) {
  return request({
    url: '/inventory/delInventory' + '?id=' + id,
    method: 'get',
  })
}
export function getGoOut(id) {
    return request({
      url: '/inventory/getGoOut' + '?id=' + id,
      method: 'get',
    })
}


export function goOut(data) {
  return request({
    url: '/inventory/goOut',
    method: 'post',
    data: data
  })
}


export function getAllInfo(id) {
  return request({
    url: '/inventory/getAllInfo' + '?id=' + id,
    method: 'get',
  })
}


export function updateInventory(data) {
  return request({
    url: '/inventory/updateInventory',
    method: 'post',
    data: data
  })
}



export function updatePriceStatus(data) {
  return request({
    url: '/inventory/updatePriceStatus',
    method: 'post',
    data: data
  })
}


export function getAuditHistoryRVO(id) {
  return request({
    url: '/inventory/getAuditHistoryRVO' + '?id=' + id,
    method: 'get',
  })
}

export function importTemplates() {
  return request({
    url: '/inventory/importTemplate',
    method: 'get',
    responseType: 'blob',
  })
}


export function exports(data) {
  return request({
    url: '/inventory/export',
    method: 'post',
    data:data,
    responseType: 'blob',
  })
}


