import request from '@/utils/request'

export function listData(query) {
  return request({
    url: '/sys/msuser/listData',
    method: 'get',
    params: query
  })
}

export function addMsUser(data) {
  return request({
    url: '/sys/msuser/add',
    method: 'post',
    data
  })
}

export function deleteMsUser(data) {
  return request({
    url: '/sys/msuser/delete',
    method: 'post',
    data
  })
}

export function updateMsUser(data) {
  return request({
    url: '/sys/msuser/update',
    method: 'post',
    data
  })
}

