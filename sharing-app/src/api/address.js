import request from '@/request'


export function addAddress(data) {
  return request({
    url: '/addPosition',
    method: 'post',
    data: data
  })
}

