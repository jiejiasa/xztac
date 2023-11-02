 q<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true">
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-table
      :data="crkList"
      border
      style="width: 100%"
      :header-cell-style="{'text-align':'center'}"
    >
      <el-table-column label="序号" width="50">
        <template slot-scope="scope">
          {{(queryParams.pageNum - 1) * queryParams.pageSize + scope.$index + 1}}
        </template>
      </el-table-column>

      <el-table-column
        prop="customerName"
        label="客户名称"  width="auto">
      </el-table-column>

      <el-table-column
        prop="outboundReason"
        label="出库原因"  width="auto">
      </el-table-column>

      <el-table-column
        prop="parkingFees"
        label="停车费用"  width="auto">
      </el-table-column>



      <el-table-column
        prop="createTime"
        label="出库发起时间"  width="auto">
      </el-table-column>

      <el-table-column
        prop="vehicleRecipient"
        label="车辆接收人"  width="auto">
      </el-table-column>


      <el-table-column
        prop="nickName"
        label="出库发起人"
        width="auto">
      </el-table-column>

      <el-table-column
        prop="status"
        label="状态" width="auto">
        <template slot-scope="scope">
          <span v-if="scope.row.status === 0">在库</span>
          <span v-if="scope.row.status === 1">审核中</span>
          <span v-if="scope.row.status === 2">审核未通过</span>
          <span v-if="scope.row.status === 3">出库</span>
        </template>
      </el-table-column>

      <el-table-column
        prop="paid"
        label="是否已付"  width="auto">
        <template slot-scope="scope">
          <span v-if="scope.row.status === 0">未付</span>
          <span v-if="scope.row.status === 1">已付</span>
        </template>
      </el-table-column>

      <el-table-column label="操作" align="center" class-name="small-padding fixed-width" width="200">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="getInfo(scope.row)"
          >查看</el-button>
        </template>
      </el-table-column>
    </el-table>
    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />







  </div>
</template>

<script>
import { getList, getOUtList } from '@/api/crk/crk'
import "@riophae/vue-treeselect/dist/vue-treeselect.css";

export default {

  data() {
    return {
      // 遮罩层
      loading: true,
      // 表格数据
      crkList: [],

      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 重新渲染表格状态
      refreshTable: true,
      // 查询参数
      queryParams: {
        pageNum:1,
        pageSize:10,
      },
      // 表单参数
      form: {},
      total:0,
    };
  },
  created() {
    this.getList();
  },
  methods: {



    getInfo() {


    },



    /** 查询出庫列表 */
    getList() {
      this.loading = true;
      getOUtList(this.queryParams).then(response => {
        this.crkList = response.list;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
      };
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },


    /** 删除按钮操作 */
    handleDelete(row) {
      this.$modal.confirm('是否确认删除名称为"' + row.deptName + '"的数据项？').then(function() {
        return delDept(row.deptId);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    }
  }
};
</script>
