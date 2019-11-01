import request from '@/utils/request'

export function listData(query) {
  return request({
    url: '/sys/msorder/listData',
    method: 'get',
    params: query
  })
}

export function addMsOrder(data) {
  return request({
    url: '/sys/msorder/add',
    method: 'post',
    data
  })
}

export function deleteMsOrder(data) {
  return request({
    url: '/sys/msorder/delete',
    method: 'post',
    data
  })
}

export function updateMsOrder(data) {
  return request({
    url: '/sys/msorder/update',
    method: 'post',
    data
  })
}

