 q<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" >
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>


    <el-table
      :data="list"
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
            @click="getGoOut(scope.row)"
          >出库</el-button>
        </template>
      </el-table-column>
    </el-table>
    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getInList"
    />


    <el-dialog :title="auditInfo.title" :visible.sync="auditInfo.outing" width="600px" append-to-body>
      <el-form ref="auditForm" :model="auditInfo.form" :rules="auditInfo.rules" label-width="80px">


        <el-row>
          <el-col :span="12">
            <el-form-item label="仓库编号" prop="inventoryCode">
              <el-input v-model="auditInfo.form.inventoryManagement.inventoryCode" placeholder="仓库编号" maxlength="20" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="车辆信息" prop="carInformation">
              <el-input v-model="auditInfo.form.inventoryManagement.carInformation" placeholder="车辆信息" maxlength="11" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="数量" prop="carNum">
              <el-input v-model="auditInfo.form.inventoryManagement.carNum" type="number"  placeholder="数量" maxlength="20"/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="審核人" prop="firstPeople">
              <el-select  v-model="auditInfo.form.firstPeopleId" filterable placeholder="第一審核人">
                <el-option
                  v-for="item in auditInfo.form.firstPeople"
                  :key="item.userId"
                  :label="item.nickName"
                  :value="item.userId">
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>

          <el-col :span="12">
            <el-form-item label="是否结清" prop="settleStatus">
              <el-input v-model="auditInfo.form.settleStatus" type="number"  placeholder="是否结清" maxlength="20"/>
            </el-form-item>
          </el-col>
        </el-row>

        <el-form-item label="审批意见" prop="passed">
          <el-select v-model="auditInfo.form.passed" placeholder="请选择审批意见">
            <el-option label="同意" :value="true"/>
            <el-option label="拒绝" :value="false"/>
          </el-select>
        </el-form-item>
        <el-form-item label="批注" prop="info">
          <el-input type="textarea" v-model="auditInfo.form.info" placeholder="请输入批注"/>
        </el-form-item>

      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="handle">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>

  </div>
</template>

<script>
import { getToDoList,handle} from "@/api/flow/flow";
import { getGoOut, goOut } from '@/api/crk/crk'
import "@riophae/vue-treeselect/dist/vue-treeselect.css";

export default {
  name: "Dept",
  data() {
    return {
      // 遮罩层
      loading: true,
      // 表格树数据
      list: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
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


      auditInfo: {
        title: '发起审核',
        outing:false,
        form: {
          inventoryManagement:{},
          firstPeople:[],
          firstPeopleId:undefined,
          settleStatus:undefined,
          info:undefined,
          passed:undefined,

        },
        rules: {

        }
      }
    };
  },
  created() {
    this.getInList();
  },
  methods: {

    getInList() {
      this.loading = true;
      getToDoList().then(response => {
        this.list = response.list;
        this.total = response.total;
        this.loading = false;
      });
    },

    // 取消按钮
    cancel() {
      this.auditInfo.outing = false;
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
      this.getInList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },





    getGoOut(row) {
      this.auditInfo.outing = true;
      getGoOut(row.id).then(response => {
        this.auditInfo.form.inventoryManagement = response.inventoryManagement;
        this.auditInfo.form.firstPeople = response.firstPeople;
      });
    },


    handle : function() {
      let {form} = this.auditInfo;

      let param  = {
        auditId : form.inventoryManagement.id,
        passed: form.passed,
        info: form.info,
      }

      handle(param);
      this.auditInfo.outing = false;
      this.getList();
    }



  }
};
</script>
