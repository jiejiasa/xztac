 q<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true">
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-table
      :data="garageList"
      style="width: 100%"
      row-key="id" :tree-props="{children: 'chileds', hasChildren: 'hasChildren'}"
    >


      <el-table-column
        prop="id"
        label="id"  width="auto">
      </el-table-column>

      <el-table-column
        prop="pid"
        label="父id"  width="auto">
      </el-table-column>

      <el-table-column label="操作" align="center" class-name="small-padding fixed-width" width="200">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleAdd"
          >新增</el-button>
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


    <el-dialog :title="title" :visible.sync="open" width="1200px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules"  label-width="auto">

        <el-row>
          <el-col :span="12">
            <el-form-item label="请选择车库费用计算类型:" prop="isNormal"  >
              <template>
                <el-select v-model="form.isNormal" filterable placeholder="请选择"
                           style="width:80%">
                  <el-option
                    v-for="item in normals"
                    :key="item.value"
                    :label="item.label"
                    :value="item.value">
                  </el-option>
                </el-select>
              </template>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="客户名称:" prop="customerName"  >
              <el-input @input = "changeSequence"  v-model="form.customerName" placeholder="客户名称" maxlength="50" :disabled="edit" style="width :80%"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="地区:" prop="region" >
              <el-input  @input = "changeSequence" v-model="form.region" placeholder="地区" value="ajkdsfhk" maxlength="50" :disabled="edit" style="width :80%"/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="价格:" prop="pickUpFee"  >
              <el-input @input = "changeSequence" v-model="form.pickUpFee" oninput ="value=value.replace(/[^0-9.]/g,'')"   placeholder="清收费用" maxlength="50" :disabled="edit" style="width :80%"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="7天内固定价格:" prop="pickUpFee"  >
              <el-input @input = "changeSequence" v-model="form.pickUpFee" oninput ="value=value.replace(/[^0-9.]/g,'')"   placeholder="清收费用" maxlength="50" :disabled="edit" style="width :80%"/>
            </el-form-item>
          </el-col>
        </el-row>


      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>

  </div>
</template>

<script>
import {  getList, insert } from '@/api/crk/garage'
import "@riophae/vue-treeselect/dist/vue-treeselect.css";

export default {

  data() {
    return {
      // 遮罩层
      loading: true,
      // 表格数据
      garageList: [],
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

    insert() {

    },


    handleAdd(){
      this.reset();
      this.open = true;
      this.title = '新增车库';
    },


    /** 查询出庫列表 */
    getList() {
      this.loading = true;
      getList(this.queryParams).then(response => {
        this.garageList = response.list;
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
    },

    changeSequence(){
      this.$forceUpdate();
    }
  }
};
</script>
