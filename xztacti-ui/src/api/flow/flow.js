import request from '@/utils/request'
import * as url from 'url'

// 查询库存
export function getToDoList() {
  return request({
    url: '/inventoryFlow/ToDoInventory',
    method: 'get',
  })
}


export function handle(date) {
  return request({
    url: '/inventoryFlow/handle',
    method: 'post',
    date: date,
  })
}
