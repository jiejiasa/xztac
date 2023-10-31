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
        label="出庫發起時間"  width="auto">
      </el-table-column>

      <el-table-column
        prop="vehicleRecipient"
        label="车辆接收人"  width="auto">
      </el-table-column>


      <el-table-column
        prop="nickName"
        label="出庫發起人"
        width="auto">
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




    <el-dialog :title="title" :visible.sync="open" width="1200px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">

        <el-row>
          <el-col :span="12">
            <el-form-item label="客户名称:" prop="customerName">
              <el-input @input = "changeSequence"  v-model="form.customerName" placeholder="客户名称" maxlength="50" :disabled="edit"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="地区:" prop="region">
              <el-input  @input = "changeSequence" v-model="form.region" placeholder="地区" value="ajkdsfhk" maxlength="50" :disabled="edit"/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="业务类型:" prop="businessType">
              <el-input  @input = "changeSequence" v-model="form.businessType"   placeholder="业务类型" maxlength="50" :disabled="edit"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="车牌号:" prop="licensPlateNumber">
              <el-input  @input = "changeSequence" v-model="form.licensPlateNumber" placeholder="车牌号" maxlength="50" :disabled="edit"/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="入库日期:" prop="inboundDate">
              <el-date-picker
                value-format="yyyy-MM-dd"
                @input = "changeSequence"
                v-model="form.inboundDate"
                align="right"
                type="date"
                style="width:100%"
                :disabled="edit"
                placeholder="选择日期"
                :picker-options="pickerOptions">
              </el-date-picker>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="品牌型号:" prop="makeAndModel">
              <el-input  @input = "changeSequence" v-model="form.makeAndModel" placeholder="品牌型号" maxlength="50" :disabled="edit"/>
            </el-form-item>
          </el-col>

        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="停放车库:" prop="parkingGarage">
              <el-input @input = "changeSequence" v-model="form.parkingGarage"   placeholder="停放车库" maxlength="50":disabled="edit"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="清收团队:" prop="clearanceTeam">
              <el-input  @input = "changeSequence" v-model="form.clearanceTeam" placeholder="清收团队" maxlength="50" :disabled="edit"/>
            </el-form-item>
          </el-col>
        </el-row>

        <el-row>
          <el-col :span="12">
            <el-form-item label="清收费用:" prop="pickUpFee">
              <el-input @input = "changeSequence" v-model="form.pickUpFee" oninput ="value=value.replace(/[^0-9.]/g,'')"   placeholder="清收费用" maxlength="50" :disabled="edit"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="是否支付:" prop="settleStatus">
              <template>
                <el-select v-model="form.settleStatus" filterable placeholder="请选择"
                           style="width:100%">
                  <el-option
                    v-for="item in options"
                    :key="item.value"
                    :label="item.label"
                    :value="item.value">
                  </el-option>
                </el-select>
              </template>
            </el-form-item>
          </el-col>
        </el-row>
        <el-form-item label="备注:" prop="remark">
          <el-input  type="textarea" :rows="5" @input = "changeSequence" v-model="form.remark" placeholder="备注" maxlength="300" />
        </el-form-item>

      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>


  </div>
</template>

<script>
import { getList, getOUtList } from '@/api/crk/crk'
import Treeselect from "@riophae/vue-treeselect";
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
