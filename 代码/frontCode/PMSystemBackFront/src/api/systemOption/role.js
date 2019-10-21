import request from '@/utils/request'

export function listData(query) {
  return request({
    url: '/sys/role/listData',
    method: 'get',
    params: query
  })
}

export function addRole(data) {
  return request({
    url: '/sys/role/addRole',
    method: 'post',
    data
  })
}

export function deleteRole(data) {
  return request({
    url: '/sys/role/deleteRole',
    method: 'post',
    data
  })
}

export function updateRole(data) {
  return request({
    url: '/sys/role/updateRole',
    method: 'post',
    data
  })
}
