import request from '@/utils/request'

export function listData(query) {
  return request({
    url: '/sys/modular/listData',
    method: 'get',
    params: query
  })
}

export function add(data) {
  return request({
    url: '/sys/modular/add',
    method: 'post',
    data
  })
}

export function deleteModule(data) {
  return request({
    url: '/sys/modular/deleteModule',
    method: 'post',
    data
  })
}

export function getTrees() {
  return request({
    url: '/sys/modular/getTrees',
    method: 'get'
  })
}

export function updateModule(data) {
	return request({
	    url: '/sys/modular/updateModule/',
	    method: 'put',
	    data
	})
}
