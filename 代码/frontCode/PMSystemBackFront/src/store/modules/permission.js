import { asyncRoutes, constantRoutes } from '@/router'
import {getAllRoute} from '@/api/permission'
import Layout from '@/layout'
/**
 * 使用meta.role确定当前用户是否具有权限
 * @param roles
 * @param route
 */
function hasPermission(roles, route) {
  if (route.meta && route.meta.roles) {
    return roles.some(role => route.meta.roles.includes(role))
  } else {
    return true
  }
}



//const _import = require('@/router/_import_production.js')//获取组件的方法
/**
 * 递归过滤异步路由表
 * @param routes asyncRoutes
 * @param roles
 */
export function filterAsyncRoutes(routes, roles) {
	const res = []
	const _import = require('@/router/_import_' + process.env.NODE_ENV)//获取组件的方法
  routes.forEach(route => {
    const tmp = { ...route }
    if (tmp.component) {
	  if (tmp.component === 'Layout') {//Layout组件特殊处理
        tmp.component = Layout
      } else {
        tmp.component = _import(tmp.component)
      }
    }
    //处理true，false字符串
    if(tmp.hidden){
    	tmp.hidden = tmp.hidden === 'true' 
    }
    if(tmp.meta.breadcrumb){
    	tmp.meta.breadcrumb = tmp.meta.breadcrumb === 'true'
    }
    if(tmp.meta.noCache){
    	tmp.meta.noCache = tmp.meta.noCache === 'true'
    }
    if (hasPermission(roles, tmp)) {
      if (tmp.children.length && tmp.children.length) {
        tmp.children = filterAsyncRoutes(tmp.children, roles)
      }
      res.push(tmp)
    }
  })
  return res;
}

//const res = []
//
//routes.forEach(route => {
//  const tmp = { ...route }
//  if (hasPermission(roles, tmp)) {
//    if (tmp.children) {
//      tmp.children = filterAsyncRoutes(tmp.children, roles)
//    }
//    res.push(tmp)
//  }
//})
//
//return res


//function filterAsyncRouter(asyncRouterMap) { //遍历后台传来的路由字符串，转换为组件对象
//const accessedRouters = asyncRouterMap.filter(route => {
//  if (route.component) {
//	  if (route.component === 'Layout') {//Layout组件特殊处理
//      route.component = Layout
//    } else {
//      route.component = _import(route.component)
//    }
//  }
//  if (route.children && route.children.length) {
//    route.children = filterAsyncRouter(route.children)
//  }
//  return true
//})
//
//return accessedRouters
//}

const state = {
  routes: [],
  addRoutes: []
}

const mutations = {
  SET_ROUTES: (state, routes) => {
    state.addRoutes = routes
    state.routes = constantRoutes.concat(routes)
  }
}

const actions = {
	//通过角色获取路由（菜单）
  generateRoutes({ commit },roleNames) {
	return new Promise((resolve, reject) => {
      getAllRoute().then(response => {
      	const {code,data} = response
      		//对自定义路由进行修改
      		data.forEach(d =>{
      			asyncRoutes.unshift(d)
      		})
        	let accessedRoutes = filterAsyncRoutes(asyncRoutes, roleNames)
//		      if (roles.includes('admin')) {
//		        accessedRoutes = asyncRoutes || []
//		      } else {
//		        accessedRoutes = filterAsyncRoutes(asyncRoutes, roles)
//		      }
		      commit('SET_ROUTES', accessedRoutes)
		    resolve(accessedRoutes)
      	
      }).catch(error => {
        reject(error)
      })
    })
//    let accessedRoutes
//    if (roles.includes('admin')) {
//      accessedRoutes = asyncRoutes || []
//    } else {
//      accessedRoutes = filterAsyncRoutes(asyncRoutes, roles)
//    }
//    commit('SET_ROUTES', accessedRoutes)
//    resolve(accessedRoutes)
//  })
  }
}

export default {
  namespaced: true,
  state,
  mutations,
  actions
}
