import request from '@/utils/request'

export function listData(query) {
  return request({
    url: '/sys/user/listData',
    method: 'get',
    params: query
  })
}

export function addUser(data) {
  return request({
    url: '/sys/user/addUser',
    method: 'post',
    data
  })
}

export function deleteUser(data) {
  return request({
    url: '/sys/user/deleteUser',
    method: 'post',
    data
  })
}

export function getTrees() {
  return request({
    url: '/sys/dept/getTrees',
    method: 'get'
  })
}

export function updateUser(data) {
  return request({
    url: '/sys/user/updateUser',
    method: 'post',
    data
  })
}

export function getAllRoles() {
  return request({
    url: '/sys/role/getAllRole',
    method: 'get'
  })
}

export function getCheckedRole(data) {
  return request({
    url: '/sys/user/getCheckedRole?userGuid=' + data,
    method: 'get'
  })
}
