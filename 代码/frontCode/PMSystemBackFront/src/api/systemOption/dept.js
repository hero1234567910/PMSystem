import request from '@/utils/request'

export function listData(query) {
  return request({
    url: '/sys/dept/listData',
    method: 'get',
    params: query
  })
}

export function add(data) {
  return request({
    url: '/sys/dept/add',
    method: 'post',
    data
  })
}

export function deleteDept(data) {
  return request({
    url: '/sys/dept/deleteDept',
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

export function updateDept(data) {
	return request({
	    url: '/sys/dept/updateDept',
	    method: 'put',
	    data
	})
}
