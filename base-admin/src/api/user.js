import axios from '@/libs/api.request'
import cloudPrefix from  '@/libs/cloudPrefix'

export const login = ({ username, password }) => {
  const data = {
    username,
    password
  }
  return axios.request({
    url: cloudPrefix.auth+'/dologin',
    data,
    method: 'post',
    disableSuccessHandler:false,
    noToken:true,
    postContentType:'postForm',
    // postContentType:'postFrom' 等价于 headers:{'Content-Type':'application/x-www-form-urlencoded'},
  })
}

export const getUserInfo = () => {
  return axios.request({
    url: cloudPrefix.auth+'/user',
    params: {
      
    },
    method: 'get'
  })
}

export const logout = (token) => {
  return axios.request({
    url: cloudPrefix.auth+'/oauth/logout',
    method: 'get',
    params:{
      token
    }
  })
}

export const getUnreadCount = () => {
  return axios.request({
    url: cloudPrefix.auth+'/message/count',
    method: 'get'
  })
}

export const getMessage = () => {
  return axios.request({
    url:  cloudPrefix.auth+ '/message/init',
    method: 'get'
  })
}

export const getContentByMsgId = msg_id => {
  return axios.request({
    url: cloudPrefix.auth+ '/message/content',
    method: 'get',
    params: {
      msg_id
    }
  })
}

export const hasRead = msg_id => {
  return axios.request({
    url: cloudPrefix.auth+'/message/hasRead',
    method: 'get',
    data: {
      msg_id
    }
  })
}

export const removeReaded = msg_id => {
  return axios.request({
    url: 'message/remove_readed',
    method: 'post',
    data: {
      msg_id
    }
  })
}

export const restoreTrash = msg_id => {
  return axios.request({
    url: 'message/restore',
    method: 'post',
    data: {
      msg_id
    }
  })
}
