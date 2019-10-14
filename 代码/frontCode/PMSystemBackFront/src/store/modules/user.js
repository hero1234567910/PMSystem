import { login, logout, getRoles } from '@/api/user'
import { getToken, setToken, removeToken } from '@/utils/auth'
import { resetRouter } from '@/router'

const state = {
  token: getToken(),
  name: '',
  avatar: '',
  roles: []
}

const mutations = {
  SET_TOKEN: (state, token) => {
    state.token = token
  },
  SET_NAME: (state, name) => {
    state.name = name
  },
  SET_AVATAR: (state, avatar) => {
    state.avatar = avatar
  },
  SET_ROLES: (state, roles) => {
    state.roles = roles
  }
}

const actions = {
  // user login
  login({ commit }, userInfo) {
    const { loginId, password } = userInfo
    return new Promise((resolve, reject) => {
      login({ loginId: loginId.trim(), password: password }).then(response => {
        const { data } = response
        commit('SET_TOKEN', data.token)
        setToken(data.token)
        window.localStorage.setItem('hero-userGuid',data.userRowGuid)
        resolve()
      }).catch(error => {
        reject(error)
      })
    })
  },

  // get user info
  getRoles({ commit, state },userGuid) {
    return new Promise((resolve, reject) => {
      getRoles(userGuid).then(response => {
        const { data } = response

        if (!data) {
          reject('Verification failed, please Login again.')
        }
//      const { roleGuid, roleName } = data
				let roles = []
				let name = []
				for(var i=0;i<data.length;i++){
					roles.push(data[i].roleGuid)
					name.push(data[i].roleName)
				}

        // roles must be a non-empty array
        if (!roles || roles.length <= 0) {
          reject('getInfo: roles must be a non-null array!')
        }

        commit('SET_ROLES', roles)
        commit('SET_NAME', name)
//      commit('SET_AVATAR', avatar)

				//定义对象返回
				var o = {
					roles:roles,
					name:name
				}
        resolve(o);
      }).catch(error => {
        reject(error)
      })
    })
  },

  // user logout
  logout({ commit, state }) {
    return new Promise((resolve, reject) => {
      logout(state.token).then(() => {
        commit('SET_TOKEN', '')
        commit('SET_ROLES', [])
        removeToken()
        resetRouter()
        resolve()
      }).catch(error => {
        reject(error)
      })
    })
  },

  // remove token
  resetToken({ commit }) {
    return new Promise(resolve => {
      commit('SET_TOKEN', '')
      commit('SET_ROLES', [])
      removeToken()
      resolve()
    })
  }
}

export default {
  namespaced: true,
  state,
  mutations,
  actions
}

