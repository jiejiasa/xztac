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
      style="width: 100%"
      :header-cell-style="{'text-align':'center'}">
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
          <span v-if="scope.row.status === 2">审核未通过</span>
          <span v-if="scope.row.status === 3">出库</span>
        </template>
      </el-table-column>


      <el-table-column
        prop="creatorId"
        label="入库人名称"
        width="auto">
      </el-table-column>

      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="getGoOut(scope.row)"
          >审核</el-button>
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


        <el-dialog :title="auditForm.title" :visible.sync="auditForm.outing" width="1200px" append-to-body>
          <el-form ref="auditForm" :model="auditForm" :rules="rules" label-width="auto">

            <el-row>
              <el-col :span="12">
                <el-form-item label="客户名称:" prop="customerName">
                  <el-input @input = "changeSequence"  v-model="auditForm.inventoryManagement.customerName" placeholder="客户名称" maxlength="50" :disabled="true"/>
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="地区:" prop="region">
                  <el-input  @input = "changeSequence" v-model="auditForm.inventoryManagement.region" placeholder="地区" value="ajkdsfhk" maxlength="50" :disabled="true"/>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="12">
                <el-form-item label="业务类型:" prop="businessType">
                  <el-input  @input = "changeSequence" v-model="auditForm.inventoryManagement.businessType"   placeholder="业务类型" maxlength="50" :disabled="true"/>
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="车牌号:" prop="licensPlateNumber">
                  <el-input  @input = "changeSequence" v-model="auditForm.inventoryManagement.licensPlateNumber" placeholder="车牌号" maxlength="50" :disabled="true"/>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="12">
                <el-form-item label="入库日期:" prop="inboundDate">
                  <el-date-picker
                    value-format="yyyy-MM-dd"
                    @input = "changeSequence"
                    v-model="auditForm.inventoryManagement.inboundDate"
                    align="right"
                    type="date"
                    :disabled="true"
                    style="width:100%"
                    placeholder="选择日期"
                    :picker-options="pickerOptions">
                  </el-date-picker>
                </el-form-item>
              </el-col>

              <el-col :span="12">
                <el-form-item label="品牌型号:" prop="makeAndModel">
                  <el-input  @input = "changeSequence" v-model="auditForm.inventoryManagement.makeAndModel" placeholder="品牌型号" maxlength="50" :disabled="true"/>
                </el-form-item>
              </el-col>

            </el-row>
            <el-row>
              <el-col :span="12">
                <el-form-item label="停放车库:" prop="parkingGarage">
                  <el-cascader
                    v-model="auditForm.inventoryManagement.parkingGarage"
                    :options="garageList"
                    :props="props"
                    :show-all-levels="false"
                    :disabled="true"
                    @change="handleChange"></el-cascader>
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="清收团队:" prop="clearanceTeam">
                  <el-input  @input = "changeSequence" v-model="auditForm.inventoryManagement.clearanceTeam" placeholder="清收团队" maxlength="50" :disabled="true"/>
                </el-form-item>
              </el-col>
            </el-row>

            <el-row>
              <el-col :span="12">
                <el-form-item label="清收费用:" prop="pickUpFee">
                  <el-input @input = "changeSequence" v-model="auditForm.inventoryManagement.pickUpFee" oninput ="value=value.replace(/[^0-9.]/g,'')"   placeholder="清收费用" maxlength="50" :disabled="true"/>
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="是否支付:" prop="settleStatus">
                  <template>
                    <el-select v-model="auditForm.inventoryManagement.settleStatus" filterable placeholder="请选择"
                               style="width:100%"
                               :disabled = "true">
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
              <el-input  type="textarea" :rows="5" @input = "changeSequence" v-model="auditForm.inventoryManagement.remark" placeholder="备注" maxlength="300" :disabled="true"/>
            </el-form-item>
            <el-row>
              <el-col :span="12">
                <el-form-item label="出库原因:" prop="outboundReason" >
                  <el-input @input = "changeSequence" v-model="auditForm.outboundReason"   placeholder="出库原因" maxlength="50" :disabled = "true" />
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="停车费用:" prop="parkingFees" >
                  <el-input  @input = "changeSequence" v-model="auditForm.parkingFees" placeholder="停车费用" maxlength="50"   :disabled = "true"/>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="12">
                <el-form-item label="出库日期:" prop="outDate">
                  <el-date-picker
                    value-format="yyyy-MM-dd"
                    @input = "changeSequence"
                    v-model="auditForm.outDate"
                    align="right"
                    type="date"
                    :disabled="true"
                    style="width:100%"
                    placeholder="选择日期"
                    :picker-options="pickerOptions">
                  </el-date-picker>
                </el-form-item>
              </el-col>
            </el-row>


            <el-row>
              <el-col :span="12">
                <el-form-item label="是否已付:" prop="paid" >
                  <template>
                    <el-select v-model="auditForm.paid" filterable placeholder="请选择"
                               style="width:100%" :disabled = "true" >
                      <el-option
                        v-for="item in options"
                        :key="item.value"
                        :label="item.label"
                        :value="item.value"
                      >
                      </el-option>
                    </el-select>
                  </template>
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="车辆接收人:" prop="vehicleRecipient">
                  <el-input  @input = "changeSequence" v-model="auditForm.vehicleRecipient" placeholder="车辆接收人" maxlength="50" :disabled = "true"/>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-form-item label="备注:" prop="outRemark">
                <el-input  type="textarea" :rows="5" @input = "changeSequence" v-model="auditForm.outRemark" placeholder="备注" maxlength="300" :disabled = "true"/>
              </el-form-item>
            </el-row>
        <el-form-item label="审批意见" prop="passed">
          <el-select @input = "changeSequence" v-model="auditForm.passed" placeholder="请选择审批意见">
            <el-option label="同意" :value="true"/>
            <el-option label="拒绝" :value="false"/>
          </el-select>
        </el-form-item>
        <el-form-item label="批注" prop="info">
          <el-input type="textarea" @input = "changeSequence" v-model="auditForm.info" placeholder="请输入批注"/>
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
import { getAuditHistoryRVO } from '@/api/crk/crk'
import "@riophae/vue-treeselect/dist/vue-treeselect.css";
import { getListg } from '@/api/crk/garage'

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

      rules:{
        passed:[{required : true, message:"请选择审批意见", trigger :"change"}],
        info:[{required : true,message:"请输入批注",trigger:'blur'}],
      },



      props:
        {label:'garageTypeOrName',value:'id',children:'chileds',emitPath:false},


      //是否支付
      options:[
        {
          value: 0,
          label: '未支付'
        }, {
          value: 1,
          label: '已支付'
        },
      ],

      normals:[
        {
          value: 0,
          label: '正常'
        }, {
          value: 1,
          label: '异常'
        }, {
          value: 2,
          label: '未知'
        },
      ],
      garageList:[],


      pickerOptions: {
        disabledDate(time) {
          return time.getTime() > Date.now();
        },
        shortcuts: [{
          text: '今天',
          onClick(picker) {
            picker.$emit('pick', new Date());
          }
        }, {
          text: '昨天',
          onClick(picker) {
            const date = new Date();
            date.setTime(date.getTime() - 3600 * 1000 * 24);
            picker.$emit('pick', date);
          }
        }, {
          text: '一周前',
          onClick(picker) {
            const date = new Date();
            date.setTime(date.getTime() - 3600 * 1000 * 24 * 7);
            picker.$emit('pick', date);
          }
        }]
      },



      auditForm: {
        title: '发起审核',
        outing:false,
        inventoryManagement:{},
        firstPeople:[],
        twoPeople:[],
        id:undefined,
        ooutInventory :{},
        firstPeopleId:undefined,
        settleStatus:undefined,
        outboundReason:undefined,
        parkingFees:undefined,
        paid:undefined,
        vehicleRecipient:undefined,
        twoPeopleId:undefined,
        outRemark:undefined,
        info:undefined,
        passed:undefined,
        outDate:new Date(),
      },





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
      this.auditForm.passed = undefined;
      this.auditForm.info = undefined;
      this.auditForm.outing = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.resetForm("form");
      this.resetForm("auditForm");
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
        this.Allinfoedit = true;
        this.auditForm.outing = true;
        getListg({pageNum:1,pageSize:10}).then(response => {
          this.garageList = this.getTreeData(response.list);
        });
        getAuditHistoryRVO(row.id).then(response => {
          this.auditForm.id = response.inventoryManagement.id;
          this.auditForm.inventoryManagement = response.inventoryManagement;
          this.auditForm.firstPeople = response.firstPeople;
          this.auditForm.twoPeople = response.twoPeople;
          this.auditForm.outboundReason = response.outboundReason;
          this.auditForm.outRemark = response.outRemark;
          this.auditForm.parkingFees = response.parkingFees;
          this.auditForm.paid = response.paid;
          this.auditForm.vehicleRecipient = response.vehicleRecipient;
          this.auditForm.outDate = response.outDate;

        });

      },

    cancelout() {
      this.auditForm.outing = false;
      this.Allinfoedit = false;
      this.reset();
    },
    changeSequence(){
      this.$forceUpdate();
    },


    handleChange(value) {
      console.log(value);
    },

    getTreeData(data) {
      for (var i = 0; i < data.length; i++) {
        if (data[i].chileds.length < 1) {
          // children若为空数组，则将children设为undefined
          data[i].chileds = undefined;
        } else {
          // children若不为空数组，则继续 递归调用 本方法
          this.getTreeData(data[i].chileds);
        }
      }
      return data;
    },


    handle : function() {
      this.$refs["auditForm"].validate(valid => {
        if (valid) {
          let param  = {
            auditId :this.auditForm.id,
            passed: this.auditForm.passed,
            info: this.auditForm.info,
          }

          handle(param).then(response => {
            this.getInList();
            this.auditForm.outing = false;
            this.reset();
          });
        }
      });

    }



  }
};
</script>
