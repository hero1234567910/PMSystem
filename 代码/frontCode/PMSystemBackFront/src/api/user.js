import request from '@/utils/request'

export function login(data) {
  return request({
    url: '/sys/login',
    method: 'post',
    data
  })
}

export function getRoles(userGuid) {
  return request({
    url: '/sys/user/getCheckedRole',
    method: 'get',
    params: {userGuid}	
  })
}

export function logout() {
  return request({
    url: '/user/logout',
    method: 'post'
  })
}
