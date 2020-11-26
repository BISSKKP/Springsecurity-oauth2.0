import axios from 'axios'
import store from '@/store'
const Qs = require('qs');
import { data } from 'autoprefixer'
import { Message } from 'iview'
// import { Spin } from 'iview'
const addErrorLog = errorInfo => {
  const { statusText, status, request: { responseURL } } = errorInfo
  let info = {
    type: 'ajax',
    code: status,
    mes: statusText,
    url: responseURL
  }
  if (!responseURL.includes('saveErrorLogger')) store.dispatch('addErrorLog', info)
}

class HttpRequest {
  constructor (baseUrl = baseURL,proxyPrefix = proxyPrefix) {
    this.baseUrl = baseUrl
    this.proxyPrefix=proxyPrefix
    this.queue = {}
  }
  getInsideConfig (options) {
    const config = {
      baseURL: this.baseUrl,
      headers: {
        //
      }
    }
    //参数中是普通表单
    if(options.data&&options.headers&&options.headers["Content-Type"]&&options.headers["Content-Type"].indexOf('x-www-form-urlencoded')!=-1){
      options.data=Qs.stringify(options.data);
    }

    return config
  }
  destroy (url) {
    delete this.queue[url]
    if (!Object.keys(this.queue).length) {
      // Spin.hide()
    }
  }
  //请求响应200 之后的默认处理
  //当开发者在config中 disableSuccessHandler 等于true 时
  //系统不再自动处理成功之后的结果
  successHandler(res){
    const { data, status } = res
    if(!data.success){
      Message.error(data.msg);
    }
    return   { data, status } ;
  }

  interceptors (instance, url) {
    console.log(url);
    // 请求拦截
    instance.interceptors.request.use(config => {
      // 添加全局的loading...
      if (!Object.keys(this.queue).length) {
        // Spin.show() // 不建议开启，因为界面不友好
      }
      this.queue[url] = true
      return config
    }, error => {
      return Promise.reject(error)
    })
    // 响应拦截
    instance.interceptors.response.use(res => {
      this.destroy(url)
      if(res.config.disableSuccessHandler){
        return res;
      }
      return this.successHandler(res);
    }, error => {
      console.log("出错啦",error,url);
      this.destroy(url)
      let errorInfo = error.response
      if (!errorInfo) {
        const { request: { statusText, status }, config } = JSON.parse(JSON.stringify(error))
        errorInfo = {
          statusText,
          status,
          request: { responseURL: config.url }
        }
      }
      this.dealError(errorInfo)//处理异常信息
      addErrorLog(errorInfo)
      return Promise.reject(error)
    })
  }
  request (options) {
    let url=options.url;
    //增加代理前缀
    let mind="";
    if(url.length>1&&(url.indexOf("/")>0||url.indexOf("/")==-1)){
      mind="/";
    }
    options.url=(this.proxyPrefix?this.proxyPrefix+mind:mind)+url;//重构url

    const instance = axios.create()
    options = Object.assign(this.getInsideConfig(options), options);
    this.interceptors(instance, options.url)
    return instance(options)
  }
  //只处理请求码错误的请求
  dealError (errorInfo){
    if(!errorInfo.config.url.includes('saveErrorLogger')){
      if(errorInfo.data){
        Message.error(errorInfo.data.msg);
      }
    }
  }
}
export default HttpRequest
