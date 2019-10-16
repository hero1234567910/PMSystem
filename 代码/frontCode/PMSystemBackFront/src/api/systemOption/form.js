import request from '@/utils/request'

export function listData(query) {
  return request({
    url: '/sys/form/listData',
    method: 'get',
    params: query
  })
}

export function add(data) {
  return request({
    url: '/sys/form/add',
    method: 'post',
    data
  })
}

export function deleteDept(data) {
  return request({
    url: '/sys/form/deleteTableInfo',
    method: 'post',
    data
  })
}

export function updateDept(data) {
	return request({
	    url: '/sys/form/updateTableInfo',
	    method: 'put',
	    data
	})
}
