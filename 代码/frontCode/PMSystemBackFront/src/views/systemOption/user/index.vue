<template>
  <div class="app-container" v-cloak>
    <el-row>
      <el-col :span="4">
        <el-tree :data="data" :props="defaultProps" node-key="rowGuid" ref="tree"></el-tree>
      </el-col>
      <el-col :span="20">
        <div class="filter-container">
          <el-input
            v-model="listQuery.userName"
            placeholder="用户名"
            style="width: 200px;"
            class="filter-item"
            @keyup.enter.native="handleFilter"
          />
          <el-button
            v-waves
            class="filter-item"
            type="primary"
            icon="el-icon-search"
            @click="handleFilter"
          >Search</el-button>
          <el-button
            class="filter-item"
            style="margin-left: 10px;"
            type="primary"
            icon="el-icon-edit"
            @click="handleCreate"
          >Add</el-button>
          <el-button
            v-waves
            :loading="downloadLoading"
            class="filter-item"
            type="primary"
            icon="el-icon-download"
            @click="handleDownload"
          >ExportThisPage</el-button>
          <!-- <el-button
            class="filter-item"
            style="margin-left: 10px;"
            type="danger"
            icon="el-icon-delete"
            @click="handleDeleteBatch"
          >Delete</el-button>-->
        </div>

        <el-table
          ref="userTable"
          :key="tableKey"
          v-loading="listLoading"
          :data="list"
          border
          fit
          highlight-current-row
          style="width: 100%;margin-top: 13px;"
          @sort-change="sortChange"
        >
          <el-table-column
            label="ID"
            prop="rowId"
            sortable="custom"
            :class-name="getSortClass('rowId')"
            width="80"
            align="center"
          >
            <template slot-scope="scope">
              <span>{{ scope.row.rowId }}</span>
            </template>
          </el-table-column>
          <el-table-column
            label="创建时间"
            sortable="custom"
            prop="createTime"
            :class-name="getSortClass('createTime')"
            width="155"
            align="center"
          >
            <template slot-scope="scope">
              <span>{{ scope.row.createTime }}</span>
            </template>
          </el-table-column>
          <el-table-column label="用户名称" align="center" width="130">
            <template slot-scope="scope">
              <span>{{ scope.row.userName }}</span>
            </template>
          </el-table-column>
          <el-table-column label="登录名" align="center" width="130">
            <template slot-scope="scope">
              <span>{{ scope.row.loginId }}</span>
            </template>
          </el-table-column>
          <el-table-column label="手机号" align="center" width="150">
            <template slot-scope="scope">
              <span>{{ scope.row.mobile }}</span>
            </template>
          </el-table-column>
          <el-table-column
            label="是否禁用"
            align="center"
            width="100"
          >
            <template slot-scope="scope">
              <el-tag
                :type="scope.row.isForbid |getColorType" size="small"
                disable-transitions
              >{{scope.row.isForbid|getColorName}}</el-tag>
            </template>
          </el-table-column>
          <el-table-column label="排序号" align="center" width="200">
            <template slot-scope="scope">
              <span>{{ scope.row.sortSq }}</span>
            </template>
          </el-table-column>
          <el-table-column
            label="Actions"
            align="center"
            min-width="300"
            class-name="small-padding fixed-width"
          >
            <template slot-scope="{row}">
              <el-button size="mini" type="success" @click="handleDetail(row)">Details</el-button>
              <el-button type="primary" size="mini" @click="handleUpdate(row)">Edit</el-button>
              <el-button size="mini" type="danger" @click="handleDelete(row)">Delete</el-button>
            </template>
          </el-table-column>
        </el-table>

        <pagination
          v-show="total>0"
          :total="total"
          :page.sync="listQuery.page"
          :limit.sync="listQuery.limit"
          @pagination="getList"
        />

        <el-dialog :title="textMap[dialogStatus]" :visible.sync="dialogFormVisible">
          <el-form
            ref="dataForm"
            :rules="rules"
            :model="temp"
            label-position="left"
            label-width="170px"
            style="width: 400px; margin-left:50px;"
          >
            <el-form-item label="用户名称" prop="userName">
              <el-input v-model="temp.userName" />
            </el-form-item>
            <el-form-item label="登录名">
              <el-input v-model="temp.loginId" />
            </el-form-item>
            <el-form-item label="性别">
              <el-radio v-model="temp.sex" label="0">男</el-radio>
              <el-radio v-model="temp.sex" label="1">女</el-radio>
            </el-form-item>
            <el-form-item label="密码">
              <el-input v-model="temp.password" type="password" auto-complete="off" />
            </el-form-item>
			<el-form-item label="账号状态">
			  <el-radio v-model="temp.isForbid" border label="0">启用</el-radio>
              <el-radio v-model="temp.isForbid" border label="1">禁用</el-radio>
            </el-form-item>
            <el-form-item label="手机号">
              <el-input v-model="temp.mobile" />
            </el-form-item>
            <el-form-item label="工号">
              <el-input v-model="temp.gongHao" />
            </el-form-item>
            <el-form-item label="排序号">
              <el-input v-model="temp.sortSq" />
            </el-form-item>
            <!--选择父级部门-->
            <el-form-item label="选择部门">
              <el-tree
                :data="data"
                :props="defaultProps"
                show-checkbox
                @check-change="checkChange"
                ref="tree"
                node-key="rowGuid"
                :check-strictly="true"
              ></el-tree>
            </el-form-item>
          </el-form>
          <div slot="footer" class="dialog-footer">
            <el-button @click="dialogFormVisible = false">Cancel</el-button>
            <el-button
              v-if="dialogStatus !== 'details'"
              type="primary"
              @click="dialogStatus==='create'?createData():updateData()"
            >Confirm</el-button>
          </div>
        </el-dialog>

        <el-dialog :visible.sync="dialogPvVisible" title="Reading statistics">
          <el-table :data="pvData" border fit highlight-current-row style="width: 100%">
            <el-table-column prop="key" label="Channel" />
            <el-table-column prop="pv" label="Pv" />
          </el-table>
          <span slot="footer" class="dialog-footer">
            <el-button type="primary" @click="dialogPvVisible = false">Confirm</el-button>
          </span>
        </el-dialog>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import {
  listData,
  addUser,
  getTrees,
  updateUser,
  deleteUser
} from "@/api/systemOption/user";
import waves from "@/directive/waves"; // waves directive
import Pagination from "@/components/Pagination"; // secondary package based on el-pagination

//代码项

//数组转对象

export default {
  name: "user",
  components: { Pagination },
  directives: { waves },
  filters: {
	  getColorType(colorId){
		  const colorMap = {
			  0:'success',
			  1:'danger'
		  };
		  return colorMap[colorId]
	  },
	  getColorName(colorId){
		  const colorMap = {
			  0:'启用',
			  1:'禁用'
		  };
		  return colorMap[colorId]
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
        importance: undefined,
        title: undefined,
        type: undefined
      },
      importanceOptions: [1, 2, 3],
      showReviewer: false,
      temp: {
        userName: "",
        loginId: "",
        mobile: "",
        sex: "",
        gongHao: "",
        password: "",
        deptGuid: "",
        sortSq: "",
		isForbid:""
      },
      dialogFormVisible: false,
      dialogStatus: "",
      textMap: {
        update: "Edit",
        create: "Create",
        details: "Details"
      },
      dialogPvVisible: false,
      pvData: [],
      rules: {
        userName: [
          { required: true, message: "userName is required", trigger: "blur" }
        ]
      },
      downloadLoading: false,
      data: [],
      defaultProps: {
        children: "children",
        label: "label"
      },
      multipleSelection: []
    };
  },
  created() {
    //获取树数据
    this.getTreeData(),
      //获取列表数据
      this.getList();
  },
  methods: {
    //获取主列表
    getList() {
      this.listLoading = true;
      listData(this.listQuery).then(response => {
        if (response.code == "0") {
          this.list = response.data;
          this.total = response.count;
          this.listLoading = false;
        }
      });
    },
    //新增数据
    handleCreate() {
      this.resetTemp();
      this.dialogStatus = "create";
      this.dialogFormVisible = true;
      this.$nextTick(() => {
        //清空树勾选
        this.$refs.tree.setCheckedKeys([]);
        //清空过滤条件
        this.$refs["dataForm"].clearValidate();
      });
    },
    createData() {
      this.$refs["dataForm"].validate(valid => {
        if (valid) {
          let param = {};
          param["roles"] = new Array();
          param["user"] = this.temp;
          addUser(param).then(res => {
            //静态刷新
            this.list.unshift(res.data);
            this.dialogFormVisible = false;
            this.$notify({
              title: "Success",
              message: "Created Successfully",
              type: "success",
              duration: 2000
            });
          });
        }
      });
    },

    //查看详细
    handleDetail(row) {
      this.temp = Object.assign({}, row);
      this.dialogStatus = "details";
      this.dialogFormVisible = true;
      //$nextTick用于延迟执行一段代码
      //有些还未加载完成 所以需要延迟函数
      this.$nextTick(() => {
        this.$refs.tree.setCheckedKeys([this.temp.deptGuid]);
        this.$refs["dataForm"].clearValidate();
      });
    },

    //更新数据
    handleUpdate(row) {
      this.temp = Object.assign({}, row); // copy obj
      this.temp.timestamp = new Date(this.temp.timestamp);
      this.dialogStatus = "update";
      this.dialogFormVisible = true;
      this.$nextTick(() => {
        this.$refs.tree.setCheckedKeys([this.temp.deptGuid]);
        this.$refs["dataForm"].clearValidate();
      });
    },
    updateData() {
      this.$refs["dataForm"].validate(valid => {
        if (valid) {
          let param = {};
          param["roleGuids"] = new Array();
          param["roleUser"] = this.temp;
          const tempData = Object.assign({}, param);
          tempData.timestamp = +new Date(tempData.timestamp);
          //console.log(tempData); // change Thu Nov 30 2017 16:41:05 GMT+0800 (CST) to 1512031311464
          updateUser(tempData).then(() => {
            //静态更新
            for (const v of this.list) {
              if (v.rowGuid === this.temp.rowGuid) {
                const index = this.list.indexOf(v);
                this.list.splice(index, 1, this.temp);
                break;
              }
            }
            this.$notify({
              title: "Success",
              message: "Update Successfully",
              type: "success",
              duration: 2000
            });
            this.dialogFormVisible = false;
            //刷新树
            this.getTreeData();
          });
        }
      });
    },

    //删除数据
    handleDelete(row) {
      this.$confirm("此操作将永久删除, 是否继续?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      })
        .then(() => {
          //后端定义数组入参
          let arr = [];
          arr.push(row.rowGuid);
          deleteUser(arr).then(res => {
            this.$notify({
              title: "Success",
              message: "Delete Successfully",
              type: "success",
              duration: 2000
            });
            //静态刷新，增加性能
            const index = this.list.indexOf(row);
            this.list.splice(index, 1);
            //刷新树
            this.getTreeData();
          });
        })
        .catch(() => {
          //        this.$message({
          //          type: 'info',
          //          message: '已取消删除'
          //        });
        });
    },
    //批量删除
    // handleDeleteBatch(rows){
    // 	this.$confirm("此操作将永久删除, 是否继续?", "提示", {
    //     confirmButtonText: "确定",
    //     cancelButtonText: "取消",
    //     type: "warning"
    //   })
    //     .then(() => {
    //       //后端定义数组入参
    // 	  this.multipleSelection = rows;
    // 	  console.log(rows)
    //       let arr = [];
    //       arr.push(rows.rowGuid);
    //       deleteUser(arr).then(res => {
    //         this.$notify({
    //           title: "Success",
    //           message: "Delete Successfully",
    //           type: "success",
    //           duration: 2000
    //         });
    //         //静态刷新，增加性能
    //         const index = this.list.indexOf(arr);
    //         this.list.splice(index, 1);
    //         //刷新树
    //         this.getTreeData();
    //       });
    //     })
    //     .catch(() => {
    //       //        this.$message({
    //       //          type: 'info',
    //       //          message: '已取消删除'
    //       //        });
    //     });
    // },
    //获取树数据
    async getTreeData() {
      this.listLoading = true;
      await getTrees().then(res => {
        let obj = [
          {
            label: "根目录",
            rowGuid: "0",
            children: res.data
          }
        ];
        this.data = obj;
      });
      this.listLoading = false;
    },

    //选择上级菜单
    checkChange(item, node, self) {
      if (node == true) {
        this.$refs.tree.setCheckedKeys([item.rowGuid]);
        this.temp.deptGuid = item.rowGuid;
      }
      if (item.rowGuid === "0" && node == true) {
        this.temp.deptGuid = "0";
      }
    },

    resetTemp() {
      this.temp = {};
    },

    //导出当页word
    handleDownload() {
      this.downloadLoading = true;
      import("@/vendor/Export2Excel").then(excel => {
        const tHeader = ["创建时间", "更新时间", "部门名", "地址", "联系电话"];
        const filterVal = [
          "createTime",
          "updateTime",
          "userName",
          "mobile",
          "tel"
        ];
        const data = this.formatJson(filterVal, this.list);
        excel.export_json_to_excel({
          header: tHeader,
          data,
          filename: "module-list"
        });
        this.downloadLoading = false;
      });
    },
    formatJson(filterVal, jsonData) {
      return jsonData.map(v =>
        filterVal.map(j => {
          if (j === "timestamp") {
            return parseTime(v[j]);
          } else {
            return v[j];
          }
        })
      );
    },

    //排序
    sortChange(data) {
      const { prop, order } = data;
      if (prop === "rowId") {
        this.sortBy(order, "row_id");
      }
      if (prop === "createTime") {
        this.sortBy(order, "create_time");
      }
      if (prop === "updateTime") {
        this.sortBy(order, "update_time");
      }
    },
    sortBy(order, s) {
      this.listQuery.sidx = s;
      if (order === "ascending") {
        this.listQuery.order = "asc";
      } else {
        this.listQuery.order = "desc";
      }
      this.handleFilter();
    },
    handleFilter() {
      this.listQuery.page = 1;
      this.getList();
    },
    getSortClass: function(key) {
      const sort = this.listQuery.sort;
      return sort === `+${key}`
        ? "ascending"
        : sort === `-${key}`
        ? "descending"
        : "";
    }
  }
};
</script>