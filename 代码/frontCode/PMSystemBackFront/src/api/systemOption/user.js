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
    url: '/sys/user/add',
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
