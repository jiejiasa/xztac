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
        prop="region"
        label="地区"  width="auto">
      </el-table-column>
      <el-table-column
        prop="businessType"
        label="业务类型"  width="auto">
      </el-table-column>
      <el-table-column
        prop="licensPlateNumber"
        label="车牌号"
        width="auto">
      </el-table-column>
      <el-table-column
        prop="inboundDate"
        label="入库日期" width="auto">
      </el-table-column>
      <el-table-column
        prop="clearanceTeam"
        label="清收团队" width="auto">
      </el-table-column>
      <el-table-column
        prop="pickUpFee"
        label="清收费用" width="auto">
      </el-table-column>

      <el-table-column
        prop="inDay"
        label="在库天数" width="auto">
      </el-table-column>

      <el-table-column
        prop="inDayMany"
        label="停车费用" width="auto">
      </el-table-column>


      <el-table-column
        prop="settleStatus"
        label="是否支付" width="auto">
        <template slot-scope="scope">
          <span v-if="scope.row.settleStatus === 0">未支付</span>
          <span v-if="scope.row.settleStatus === 1">已支付</span>
        </template>
      </el-table-column>
      <el-table-column
        prop="status"
        label="状态" width="auto">
        <template slot-scope="scope">
          <span v-if="scope.row.status === 0">在库</span>
          <span v-if="scope.row.status === 1">审核中</span>
          <span v-if="scope.row.status === 2">出库</span>
        </template>
      </el-table-column>


      <el-table-column
        prop="creatorId"
        label="入库人名称"
        width="auto">
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
import { getList} from "@/api/crk/crk";
import Treeselect from "@riophae/vue-treeselect";
import "@riophae/vue-treeselect/dist/vue-treeselect.css";

export default {
  name: "Dept",
  dicts: ['sys_normal_disable'],
  components: { Treeselect },
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
      // 是否展开，默认全部展开
      isExpandAll: true,
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
    /** 查询部门列表 */
    getList() {
      this.loading = true;
      getList(this.queryParams).then(response => {
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
    /** 新增按钮操作 */
    handleAdd(row) {
      this.reset();
      if (row != undefined) {
        this.form.parentId = row.deptId;
      }
      this.open = true;
      this.title = "添加部门";
      listDept().then(response => {
        this.deptOptions = this.handleTree(response.data, "deptId");
      });
    },
    /** 展开/折叠操作 */
    toggleExpandAll() {
      this.refreshTable = false;
      this.isExpandAll = !this.isExpandAll;
      this.$nextTick(() => {
        this.refreshTable = true;
      });
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      getDept(row.deptId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改部门";
        listDeptExcludeChild(row.deptId).then(response => {
          this.deptOptions = this.handleTree(response.data, "deptId");
          if (this.deptOptions.length == 0) {
            const noResultsOptions = { deptId: this.form.parentId, deptName: this.form.parentName, children: [] };
            this.deptOptions.push(noResultsOptions);
          }
        });
      });
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
