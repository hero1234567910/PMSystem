<template>
  <div class="app-container" v-cloak>
    <el-row>
      <el-col :span="24">
        <div class="filter-container">
          <el-input
            v-model="listQuery.consigneeName"
            placeholder="收货人姓名"
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
        </div>

        <el-table
          ref="msOrderTable"
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
          <el-table-column label="订单流水号" align="center" width="130">
            <template slot-scope="scope">
              <span>{{ scope.row.orderNumber }}</span>
            </template>
          </el-table-column>
          <el-table-column label="收货人姓名" align="center" width="130">
            <template slot-scope="scope">
              <span>{{ scope.row.consigneeName }}</span>
            </template>
          </el-table-column>
          <el-table-column label="收货人手机号" align="center" width="150">
            <template slot-scope="scope">
              <span>{{ scope.row.consigneeMobile }}</span>
            </template>
          </el-table-column>
          <el-table-column label="订单状态" align="center" width="100">
            <template slot-scope="scope">
              <el-tag
                :type="scope.row.orderStatus |getColorType"
                size="small"
                disable-transitions
              >{{scope.row.orderStatus|getColorName}}</el-tag>
            </template>
          </el-table-column>
          <el-table-column label="订单金额" align="center" width="150">
            <template slot-scope="scope">
              <span>{{ scope.row.orderMoney }}</span>
            </template>
          </el-table-column>
          <el-table-column label="商户订单号" align="center" width="150">
            <template slot-scope="scope">
              <span>{{ scope.row.merchantNumber }}</span>
            </template>
          </el-table-column>
          <el-table-column label="支付时间" align="center" width="150">
            <template slot-scope="scope">
              <span>{{ scope.row.payTime }}</span>
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
            <el-form-item label="订单流水号" prop="orderNumber">
              <el-input v-model="temp.orderNumber" />
            </el-form-item>
            <el-form-item label="下订单所属用户标识">
              <el-input v-model="temp.orderUserGuid" />
            </el-form-item>

            <el-form-item label="订单商品标识">
              <el-input v-model="temp.orderItemGuid" />
            </el-form-item>
            <el-form-item label="收货人姓名">
              <el-input v-model="temp.consigneeName" />
            </el-form-item>
            <el-form-item label="收货人手机号">
              <el-input v-model="temp.consigneeMobile" />
            </el-form-item>
            <el-form-item label="订单状态">
              <el-input v-model="temp.orderStatus" />
            </el-form-item>
            <el-form-item label="支付金额">
              <el-input v-model="temp.orderMoney" />
            </el-form-item>
            <el-form-item label="商户订单号">
              <el-input v-model="temp.merchantNumber" />
            </el-form-item>
            <el-form-item label="支付时间">
              <el-input v-model="temp.payTime" />
            </el-form-item>
            <el-form-item label="备注">
              <el-input type="textarea" :rows="3" v-model="temp.remark" />
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
      </el-col>
    </el-row>
  </div>
</template>

<script>
import {
  listData,
  addMsOrder,
  updateMsOrder,
  deleteMsOrder
} from "@/api/systemOption/msOrder";
import waves from "@/directive/waves"; // waves directive
import Pagination from "@/components/Pagination"; // secondary package based on el-pagination

//代码项

//数组转对象

export default {
  name: "msOrder",
  components: { Pagination },
  directives: { waves },
  filters: {
    getColorType(colorId) {
      const colorMap = {
        0: "warning",
        1: "success",
        2: "info",
        3: "danger"
      };
      return colorMap[colorId];
    },
    getColorName(colorId) {
      const colorMap = {
        0: "待支付",
        1: "已支付",
        2: "已完成",
        3: "已取消"
      };
      return colorMap[colorId];
    }
  },
  data() {
    return {
      tableKey: 0,
      list: null,
      userList: null,
      total: 0,
      userTotal: 0,
      listLoading: true,
      userListLoading: true,
      listQuery: {
        page: 1,
        limit: 20
      },
      showReviewer: false,
      temp: {
        orderNumber: "",
        orderUserGuid: "",
        orderItemGuid: "",
        consigneeName: "",
        consigneeMobile: "",
        orderStatus: "",
        orderMoney: "",
        merchantNumber: "",
        payTime: "",
        remark: ""
      },
      dialogFormVisible: false,
      dialogListVisible: false,
      dialogStatus: "",
      textMap: {
        update: "Edit",
        create: "Create",
        details: "Details"
      },
      rules: {
        orderNumber: [
          {
            required: true,
            message: "orderNumber is required",
            trigger: "blur"
          }
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
        //清空过滤条件
        this.$refs["dataForm"].clearValidate();
      });
    },
    createData() {
      this.$refs["dataForm"].validate(valid => {
        if (valid) {
          addMsOrder(this.temp).then(res => {
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
        this.$refs["dataForm"].clearValidate();
      });
    },
    updateData() {
      this.$refs["dataForm"].validate(valid => {
        if (valid) {
          const tempData = Object.assign({}, this.temp);
          tempData.timestamp = +new Date(tempData.timestamp);
          //console.log(tempData); // change Thu Nov 30 2017 16:41:05 GMT+0800 (CST) to 1512031311464
          updateMsOrder(tempData).then(() => {
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
          deleteMsOrder(arr).then(res => {
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
          });
        })
        .catch(() => {
          //        this.$message({
          //          type: 'info',
          //          message: '已取消删除'
          //        });
        });
    },

    resetTemp() {
      this.temp = {};
    },

    //导出当页word
    handleDownload() {
      this.downloadLoading = true;
      import("@/vendor/Export2Excel").then(excel => {
        const tHeader = [
          "创建时间",
          "更新时间",
          "收货人姓名",
          "收货人手机号",
          "支付金额"
        ];
        const filterVal = [
          "createTime",
          "updateTime",
          "consigneeName",
          "consigneeMobile",
          "orderMoney"
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