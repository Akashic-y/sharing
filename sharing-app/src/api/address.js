import request from '@/request'


export function addAddress(position) {
  return request({
    url: '/addPosition',
    method: 'post',
    data: position
  })
}

