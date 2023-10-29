 q<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" >
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
        >入库</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table
      :data="crkList"
      border
      style="width: 100%">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column
        prop="inventoryCode"
        label="仓库编号"
        width="180">
      </el-table-column>
      <el-table-column
        prop="carInformation"
        label="车辆信息"
        width="180">
      </el-table-column>
      <el-table-column
        prop="carNum"
        label="数量">
      </el-table-column>
      <el-table-column
        prop="time"
        label="时间">
      </el-table-column>
      <el-table-column
        prop="time"
        label="状态">
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
          >编辑</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="getGoOut(scope.row)"
          >出库</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
          >删除</el-button>
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

    <!-- 添加或修改库存管理对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="600px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">


        <el-row>
          <el-col :span="12">
            <el-form-item label="仓库编号" prop="inventoryCode">
              <el-input v-model="form.inventoryCode" placeholder="仓库编号" maxlength="20" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="车辆信息" prop="carInformation">
              <el-input v-model="form.carInformation" placeholder="车辆信息" maxlength="11" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="数量" prop="carNum">
              <el-input v-model="form.carNum" type="number"  placeholder="数量" maxlength="20"/>
            </el-form-item>
          </el-col>
        </el-row>

      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>



<!--    出庫申請頁面-->
    <el-dialog :title="title" :visible.sync="outing" width="600px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">


        <el-row>
          <el-col :span="12">
            <el-form-item label="仓库编号" prop="inventoryCode">
              <el-input v-model="form.inventoryCode" placeholder="仓库编号" maxlength="20" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="车辆信息" prop="carInformation">
              <el-input v-model="form.carInformation" placeholder="车辆信息" maxlength="11" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="数量" prop="carNum">
              <el-input v-model="form.carNum" type="number"  placeholder="数量" maxlength="20"/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="審核人" prop="nickName">
              <el-input v-model="firstpeople.nickName"  placeholder="第一審核人" maxlength="20"/>
            </el-form-item>
          </el-col>
        </el-row>

      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancelout">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { getList,getInventoryInfo,save,delInventory,getGoOut} from "@/api/crk/crk";

import "@riophae/vue-treeselect/dist/vue-treeselect.css";

export default {
  name: "inventoryManagement",
  data() {
    return {
      // 遮罩层
      loading: true,
      // 表格树数据
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
      firstpeople : {},
      total:0,
      outing:false,
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询库存列表 */
    getList() {
      this.loading = true;
      getList(this.queryParams).then(response => {
        this.crkList = response.list;
        this.total = response.total;
        this.loading = false;
      });
    },


    getGoOut(row) {
      this.outing = true;
      getGoOut(row.id).then(response => {
        this.form = response.inventoryManagement;
        this.firstpeople = response.firstPeople[0];
      });
    },

    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },

    // 取消按钮
    cancelout() {
      this.outing = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        id:undefined,
        inventoryCode: undefined,
        carInformation: undefined,
        carNum: undefined,

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
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "新增库存";


    },

    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      getInventoryInfo(row.id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改库存信息";
      });
    },
    /** 提交按钮 */
    submitForm: function() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != undefined) {
            updateDept(this.form).then(response => {
              this.$modal.msgSuccess("修改库存信息成功");
              this.open = false;
              this.getList();
            });
          } else {
            save(this.form).then(response => {
              this.$modal.msgSuccess("新增库存成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      this.$modal.confirm('是否确认删除id为"' + row.id + '"的数据项？').then(function() {
        return delInventory(row.id);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    }
  }
};
</script>
