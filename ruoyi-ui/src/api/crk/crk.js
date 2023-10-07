import request from '@/utils/request'



// // 查询部门详细
// export function getDept(deptId) {
//   return request({
//     url: '/system/dept/' + deptId,
//     method: 'get'
//   })
// }

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

// 原有基础入库
export function addOrg(data) {
  return request({
    url: '/inventory/addOrg',
    method: 'post',
    data: data
  })
}
//
// // 删除部门
// export function delDept(deptId) {
//   return request({
//     url: '/system/dept/' + deptId,
//     method: 'delete'
//   })
// }
