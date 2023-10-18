<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch">
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
          v-hasPermi="['system:dept:add']"
        >入库</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table
      :data="crkList"
      border
      style="width: 100%">
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
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['system:dept:edit']"
          >出库</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-plus"
            @click="handleAddOrg(scope.row)"
            v-hasPermi="['system:dept:add']"
          >入库</el-button>
          <el-button
            v-if="scope.row.parentId != 0"
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['system:dept:remove']"
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

    <!-- 原有基础入库 -->
    <el-dialog :title="title" :visible.sync="open" width="600px" append-to-body>
      <el-form ref="form" :model="addOrgform" :rules="rules" label-width="80px">
        <el-row>
          <el-col :span="12">
            <el-form-item label="仓库名称" prop="inventoryCode">
              <el-input v-model="addOrgform.inventoryCode" disabled = "true"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="车辆信息" prop="carInformation" >
              <el-input v-model="addOrgform.carInformation" disabled = "true" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="剩余数量" prop="carNum">
              <el-input v-model="addOrgform.carNum" disabled = "true"/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="入库数量" prop="inNum">
              <el-input v-model="addOrgform.inNum"/>
            </el-form-item>
          </el-col>
        </el-row>

      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="addOrg ">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>



    <!-- 新增入库 -->
    <el-dialog :title="title" :visible.sync="addsee" width="600px" append-to-body>
      <el-form ref="form" :model="addform" :rules="rules" label-width="80px">
        <el-row>
          <el-col :span="12">
            <el-form-item label="仓库名称" prop="inventoryCode">
              <el-input v-model="addform.inventoryCode" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="车辆信息" prop="carInformation" >
              <el-input v-model="addform.carInformation"/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="入库数量" prop="carNum">
              <el-input v-model="addform.carNum" />
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="addOrg">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { getList,addOrg} from "@/api/crk/crk";
import "@riophae/vue-treeselect/dist/vue-treeselect.css";

export default {
  name: "Dept",
  data() {
    return {
      // 遮罩层
      loading: true,
      // 显示搜索条件
      showSearch: true,
      // 库存管理集合
      crkList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      //是否弹出新增入库弹层
      addsee: false,
      // 重新渲染表格状态
      refreshTable: true,
      // 查询参数
      queryParams: {
        pageNum:1,
        pageSize:10,
      },
      // 表单参数
      addOrgform: {},
      addform:{},
      total:0,
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询库存 */
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
      this.addsee = false;

      this.addOrgform = {
        inNum: undefined,
      }
      this.addform = {
        inventoryCode: undefined,
        carInformation: undefined,
        carNum: undefined,
      }
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
    /** 原有新增 */
    handleAddOrg(row) {
      this.addOrgform.id = row.id;
      this.addOrgform.inventoryCode = row.inventoryCode;
      this.addOrgform.carInformation = row.carInformation;
      this.addOrgform.carNum = row.carNum;
      this.open = true;
      this.title = "入库";
    },


    handleAdd() {
      this.addsee = true;
      this.title = "入库";
    },


    /** 提交按钮 */
    addOrg: function() {
      this.open = false;
      addOrg(this.addOrgform);
      this.getList();
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
