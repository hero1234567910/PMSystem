import request from '@/utils/request'

export function getAllRoute() {
  return request({
    url: '/sys/modular/getAllRoute',
    method: 'get'
  })
}