<template>
  <div class="app-container">
    <div class="filter-container">
      <el-input v-model="listQuery.title" placeholder="Title" style="width: 200px;" class="filter-item" @keyup.enter.native="handleFilter" />
      <el-button v-waves class="filter-item" type="primary" icon="el-icon-search" @click="handleFilter">
        Search
      </el-button>
      <el-button class="filter-item" style="margin-left: 10px;" type="primary" icon="el-icon-edit" @click="handleCreate">
        Add
      </el-button>
      <el-button v-waves :loading="downloadLoading" class="filter-item" type="primary" icon="el-icon-download" @click="handleDownload">
        ExportThisPage
      </el-button>
    </div>

    <el-table
      :key="tableKey"
      v-loading="listLoading"
      :data="list"
      border
      fit
      highlight-current-row
      style="width: 100%;margin-top: 13px;"
      @sort-change="sortChange"
    >
    	<el-table-column label="ID" prop="rowId" sortable="custom" :class-name="getSortClass('rowId')" width="80" align="center">
        <template slot-scope="scope">
          <span>{{ scope.row.rowId }}</span>
        </template>
      </el-table-column>
      <el-table-column label="创建时间" sortable="custom" prop="createTime" :class-name="getSortClass('createTime')" width="155" align="center">
        <template slot-scope="scope">
          <span>{{ scope.row.createTime }}</span>
        </template>
      </el-table-column>
      <el-table-column label="更新时间"  sortable="custom" prop="updateTime" align="center" width="155" :class-name="getSortClass('updateTime')">
        <template slot-scope="scope">
          <span>{{ scope.row.updateTime }}</span>
        </template>
      </el-table-column>
      <el-table-column label="菜单标题"   align="center" width="130">
        <template slot-scope="scope">
          <span>{{ scope.row.title }}</span>
        </template>
      </el-table-column>
      <el-table-column label="图标"   align="center" width="130" >
        <template slot-scope="scope">
          <span>{{ scope.row.icon }}</span>
        </template>
      </el-table-column>
      <el-table-column label="菜单路径"  align="center" width="150" >
        <template slot-scope="scope">
          <span>{{ scope.row.path }}</span>
        </template>
      </el-table-column>
      
      <el-table-column label="是否隐藏"  align="center" width="80" >
        <template slot-scope="scope">
        	<el-tag :type="scope.row.hidden | hiddenType">
            {{ scope.row.hidden | hiddenFilter}}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column label="路由名"  align="center" width="130">
        <template slot-scope="scope">
          <span>{{ scope.row.name }}</span>
        </template>
      </el-table-column>
      <el-table-column label="Actions" align="center" min-width="300" class-name="small-padding fixed-width">
        <template slot-scope="{row}">
        	<el-button  size="mini" type="success" @click="handleDetail(row)">
            Details
          </el-button>
          <el-button type="primary" size="mini" @click="handleUpdate(row)">
            Edit
          </el-button>
          <el-button size="mini" type="danger" @click="handleDelete(row)">
            Delete
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination v-show="total>0" :total="total" :page.sync="listQuery.page" :limit.sync="listQuery.limit" @pagination="getList" />

    <el-dialog :title="textMap[dialogStatus]" :visible.sync="dialogFormVisible">
      <el-form ref="dataForm" :rules="rules" :model="temp" label-position="left" label-width="170px" style="width: 400px; margin-left:50px;">
        <el-form-item label="是否隐藏" prop="hidden">
          <el-select v-model="temp.hidden" class="filter-item" placeholder="Please select" clearable>
            <el-option v-for="item in hiddenOptions" :key="item.key" :label="item.display_name" :value="item.key" />
          </el-select>
        </el-form-item>
        <!--是否缓存-->
        <el-form-item label="是否缓存" prop="noCache">
          <el-select v-model="temp.noCache" class="filter-item" placeholder="Please select" clearable>
            <el-option v-for="item in noCacheOptions" :key="item.key" :label="item.display_name" :value="item.key" />
          </el-select>
        </el-form-item>
        <!--是否在面包屑中显示-->
        <el-form-item label="是否在面包屑中显示" prop="breadcrumb">
          <el-select v-model="temp.breadcrumb" class="filter-item" placeholder="Please select" clearable>
            <el-option v-for="item in breadcrumbOptions" :key="item.key" :label="item.display_name" :value="item.key" />
          </el-select>
        </el-form-item>
        <!--重定向-->
        <el-form-item label="redirect">
          <el-select v-model="temp.redirect" class="filter-item" placeholder="Please select" clearable>
            <el-option v-for="item in directOptions" :key="item" :label="item" :value="item" />
          </el-select>
        </el-form-item>
        <!--路由名-->
        <el-form-item label="name" prop="name">
          <el-input v-model="temp.name" />
        </el-form-item>
        <!--路由侧边栏显示名称-->
        <el-form-item label="title" prop="title">
          <el-input v-model="temp.title" />
        </el-form-item>
        <!--图标选择-->
        <el-form-item label="icon">
          <icon-picker v-model="temp.icon"></icon-picker>
        </el-form-item>
        <!--组件-->
        <el-form-item label="component">
          <el-input v-model="temp.component" />
        </el-form-item>
        <!--路径-->
        <el-form-item label="path">
          <el-input v-model="temp.path" />
        </el-form-item>
        <!--选择父级菜单-->
        <el-form-item label="选择父级菜单">
        	<el-tree :data="data" 
        		:props="defaultProps" 
        		show-checkbox 
        		@check-change="checkChange" 
        		ref="tree" 
        		node-key="moduleCode"
        		:check-strictly="true"></el-tree>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">
          Cancel
        </el-button>
        <el-button v-if="dialogStatus !== 'details'"
        	 type="primary" @click="dialogStatus==='create'?createData():updateData()">
          Confirm
        </el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listData,add,getTrees,updateModule,deleteModule } from '@/api/systemOption/module'
import waves from '@/directive/waves' // waves directive
import Pagination from '@/components/Pagination' // secondary package based on el-pagination

//代码项
const hiddenOptions = [
  { key: 0, display_name: 'true' },
  { key: 1, display_name: 'false' }
]

const noCacheOptions = [
//方便理解 已经把01调换位置
  { key: 1, display_name: 'true' },
  { key: 0, display_name: 'false' }
]

const breadcrumbOptions = [
  { key: 0, display_name: 'true' },
  { key: 1, display_name: 'false' }
]

//数组转对象
const hiddenKeyValue = hiddenOptions.reduce((acc, cur) => {
	acc[cur.key] = cur.display_name
	return acc
}, {})


export default {
  name: 'module',
  components: { Pagination },
  directives: { waves },
  filters: {
    hiddenType(hidden){
    	const hiddenMap = {
        1: 'success',
        0: 'danger'
      }
      return hiddenMap[hidden]
    },
		hiddenFilter(data){
			return hiddenKeyValue[data]
		}
  },
  data() {
    return {
      tableKey: 0,
      list: null,
      total: 0,
      listLoading: true,
      listQuery: {
        page: 1,
        limit: 20,
        title: undefined,
        type: undefined
      },
      breadcrumbOptions,
      hiddenOptions,
      noCacheOptions,
      directOptions:['noRedirect'],
      showReviewer: false,
      temp: {
        hidden:'',
        redirect:'',
        name:'',
        title:'',
        icon:'',
        noCache:'',
        breadcrumb:'',
        component:'',
        path:'',
        pmoduleCode:''
      },
      dialogFormVisible: false,
      dialogStatus: '',
      textMap: {
        update: 'Edit',
        create: 'Create',
        details: 'Details'
      },
      rules: {
        hidden: [{ required: true, message: 'hidden is required', trigger: 'change' }],
        name: [{ required: true, message: 'name is required', trigger: 'blur' }],
        title: [{ required: true, message: 'title is required', trigger: 'blur' }],
//      icon: [{ required: true, message: 'icon is required', trigger: 'blur' }],
        noCache: [{ required: true, message: 'noCache is required', trigger: 'change' }],
        breadcrumb: [{ required: true, message: 'breadcrumb is required', trigger: 'change' }],
      },
      downloadLoading: false,
      data: [],
	    defaultProps: {
	      children: 'children',
	      label: 'label'
	    },
    }
  },
  created() {
  	//获取树数据
  	this.getTreeData(),
  	//获取列表数据
    this.getList()
  },
  methods: {
  	//获取主列表
    getList() {
      this.listLoading = true
      listData(this.listQuery).then(response => {
      	if(response.code == '0'){
	        this.list = response.data
	        this.total = response.count
	        this.listLoading = false
      	}
      })
    },
    //新增数据
    handleCreate() {
      this.resetTemp()
      this.dialogStatus = 'create'
      this.dialogFormVisible = true
      this.$nextTick(() => {
      	//清空树勾选
      	this.$refs.tree.setCheckedKeys([])
      	//清空过滤条件
        this.$refs['dataForm'].clearValidate()
      })
    },
    createData() {
      this.$refs['dataForm'].validate((valid) => {
        if (valid) {
          add(this.temp).then(res => {
          	//静态刷新
            this.list.unshift(res.data)
            this.dialogFormVisible = false
            this.$notify({
              title: 'Success',
              message: 'Created Successfully',
              type: 'success',
              duration: 2000
            })
            //刷新树
            this.getTreeData()
          })
        }
      })
    },
    
    //查看详细
    handleDetail(row){
    	this.temp = Object.assign({},row)
    	this.dialogStatus = 'details'
    	this.dialogFormVisible = true
    	//$nextTick用于延迟执行一段代码
    	//有些还未加载完成 所以需要延迟函数
    	this.$nextTick(() => {
    		this.$refs.tree.setCheckedKeys([this.temp.pmoduleCode])
    		this.$refs['dataForm'].clearValidate()
    	})
    },
    
    //更新数据
    handleUpdate(row) {
      this.temp = Object.assign({}, row) // copy obj
      this.temp.timestamp = new Date(this.temp.timestamp)
      this.dialogStatus = 'update'
      this.dialogFormVisible = true
      this.$nextTick(() => {
      	this.$refs.tree.setCheckedKeys([this.temp.pmoduleCode])
        this.$refs['dataForm'].clearValidate()
      })
    },
    updateData() {
      this.$refs['dataForm'].validate((valid) => {
        if (valid) {
          const tempData = Object.assign({}, this.temp)
          tempData.timestamp = +new Date(tempData.timestamp) // change Thu Nov 30 2017 16:41:05 GMT+0800 (CST) to 1512031311464
          updateModule(tempData).then(() => {
          	//静态更新
            for (const v of this.list) {
              if (v.rowGuid === this.temp.rowGuid) {
                const index = this.list.indexOf(v)
                this.list.splice(index, 1, this.temp)
                break
              }
            }
            this.dialogFormVisible = false
            this.$notify({
              title: 'Success',
              message: 'Update Successfully',
              type: 'success',
              duration: 2000
            })
            //刷新树
            this.getTreeData()
          })
        }
      })
    },
    
    //删除数据
    handleDelete(row) {
    	this.$confirm('此操作将永久删除, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          //后端定义数组入参
		    	let arr = []
		    	arr.push(row.rowGuid)
		    	deleteModule(arr).then(res => {
			    		this.$notify({
			        title: 'Success',
			        message: 'Delete Successfully',
			        type: 'success',
			        duration: 2000
			      })
			    	//静态刷新，增加性能
				    const index = this.list.indexOf(row)
				    this.list.splice(index, 1)
				    //刷新树
            this.getTreeData()
		    	})
        }).catch(() => {
//        this.$message({
//          type: 'info',
//          message: '已取消删除'
//        });          
        });
    },
    
  	//获取树数据
  	async getTreeData(){
  		this.listLoading = true
     	await getTrees().then(res => {
     		let obj = [{
	      	label: '根目录',
	      	moduleCode:'0',
	        children: res.data
      	}]
     		this.data = obj
     	})
     	this.listLoading = false
  	},
  	//选择上级菜单
    checkChange(item,node,self){
        if (node == true) {
           this.$refs.tree.setCheckedKeys([item.moduleCode])
           this.temp.pmoduleCode = item.moduleCode
        }
        if(item.deptCode === '0' && node == true){
    			this.temp.pmoduleCode = '0'
    		}
   	},
   	
    resetTemp() {
      this.temp = {}
    },
    
    //导出当页word
    handleDownload() {
      this.downloadLoading = true
      import('@/vendor/Export2Excel').then(excel => {
        const tHeader = ['创建时间', '更新时间','菜单标题', '路由名', '路径']
        const filterVal = ['createTime', 'updateTime','title', 'name','path']
        const data = this.formatJson(filterVal, this.list)
        excel.export_json_to_excel({
          header: tHeader,
          data,
          filename: 'module-list'
        })
        this.downloadLoading = false
      })
    },
    formatJson(filterVal, jsonData) {
      return jsonData.map(v => filterVal.map(j => {
        if (j === 'timestamp') {
          return parseTime(v[j])
        } else {
          return v[j]
        }
      }))
    },
    
    //排序
    sortChange(data) {
      const { prop, order } = data
      if (prop === 'rowId') {
        this.sortBy(order,'row_id')
      }
      if(prop === 'createTime'){
      	this.sortBy(order,'create_time')
      }
      if(prop === 'updateTime'){
      	this.sortBy(order,'update_time')
      }
    },
    sortBy(order,s) {
    	this.listQuery.sidx = s
      if (order === 'ascending') {
        this.listQuery.order = 'asc'
      } else {
        this.listQuery.order = 'desc'
      }
      this.handleFilter()
    },
    handleFilter() {
      this.listQuery.page = 1
      this.getList()
    },
    getSortClass: function(key) {
      const sort = this.listQuery.sort
      return sort === `+${key}`
        ? 'ascending'
        : sort === `-${key}`
          ? 'descending'
          : ''
    }
  }
}
</script>
