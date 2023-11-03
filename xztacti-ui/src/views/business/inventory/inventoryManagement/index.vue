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
    </el-row>

    <el-table
      :data="crkList"
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
        <span v-if="scope.row.status === 2">审核未通过</span>
          <span v-if="scope.row.status === 3">出库</span>
        </template>
      </el-table-column>

      <el-table-column
        prop="nickName"
        label="入库人名称"
        width="auto">
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width" width="200">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            v-if="scope.row.status !== 1 && scope.row.status !== 3"
            @click="handleUpdate(scope.row)"
          >编辑</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            v-if="scope.row.status !== 1 && scope.row.status !== 3"
            @click="getGoOut(scope.row)"
          >出库</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            v-if="scope.row.status === 3"
            @click="getAllInfo(scope.row)"
          >费用结清</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            v-if="scope.row.status !== 1"
            @click="handleDelete(scope.row)"
          >删除</el-button>

          <el-button type="primary"
                     size="small"
                     v-if="scope.row.status === 1"
                     round>审核中</el-button>
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
    <el-dialog :title="title" :visible.sync="open" width="1200px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules"  label-width="auto">

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
            <el-form-item label="业务类型:" prop="businessType" >
              <el-input  @input = "changeSequence" v-model="form.businessType"   placeholder="业务类型" maxlength="50" :disabled="edit" style="width :80%"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="车牌号:" prop="licensPlateNumber"  >
              <el-input  @input = "changeSequence" v-model="form.licensPlateNumber" placeholder="车牌号" maxlength="50" :disabled="edit" style="width :80%"/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="入库日期:" prop="inboundDate"  label-width="auto">
              <el-date-picker
                value-format="yyyy-MM-dd"
                @input = "changeSequence"
                v-model="form.inboundDate"
                align="right"
                type="date"
                style="width:80%"
                :disabled="edit"
                placeholder="选择日期"
                :picker-options="pickerOptions">
              </el-date-picker>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="品牌型号:" prop="makeAndModel"  >
              <el-input  @input = "changeSequence" v-model="form.makeAndModel" placeholder="品牌型号" maxlength="50" :disabled="edit" style="width :80%"/>
            </el-form-item>
          </el-col>

        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="停放车库:" prop="parkingGarage" >
              <el-input @input = "changeSequence" v-model="form.parkingGarage"   placeholder="停放车库" maxlength="50":disabled="edit" style="width :80%"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="清收团队:" prop="clearanceTeam"  label-width="auto">
              <el-input  @input = "changeSequence" v-model="form.clearanceTeam" placeholder="清收团队" maxlength="50" :disabled="edit" style="width :80%"/>
            </el-form-item>
          </el-col>
        </el-row>

        <el-row>
          <el-col :span="12">
            <el-form-item label="清收费用:" prop="pickUpFee"  >
              <el-input @input = "changeSequence" v-model="form.pickUpFee" oninput ="value=value.replace(/[^0-9.]/g,'')"   placeholder="清收费用" maxlength="50" :disabled="edit" style="width :80%"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="是否支付:" prop="settleStatus"  >
              <template>
              <el-select v-model="form.settleStatus" filterable placeholder="请选择"
                         style="width:80%">
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
        <el-row>
          <el-col :span="12">
            <el-form-item label="车钥匙是否好用:" prop="isNormal"  >
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
        <el-form-item label="备注:" prop="remark">
          <el-input  type="textarea" :rows="5" @input = "changeSequence" v-model="form.remark" placeholder="备注" maxlength="300" />
        </el-form-item>

      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>


<!--    出庫申請頁面-->
    <el-dialog :title="auditInfo.title" :visible.sync="auditInfo.outing" width="1200px" append-to-body>
      <el-form ref="auditForm" :model="auditForm" :rules="auditrules" label-width="auto">

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
                v-model="form.inboundDate"
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
              <el-input @input = "changeSequence" v-model="auditForm.inventoryManagement.parkingGarage"   placeholder="停放车库" maxlength="50":disabled="true"/>
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
            <el-form-item label="第一审核人:" prop="firstPeopleId" >
              <el-select  v-model="auditForm.firstPeopleId" filterable placeholder="第一审核人" :disabled = "Allinfoedit" style="width:100%">
                <el-option
                  v-for="item in auditForm.firstPeople"
                  :key="item.userId"
                  :label="item.nickName"
                  :value="item.userId">
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="第二审核人:" prop="twoPeopleId" >
              <el-select  v-model="auditForm.twoPeopleId" filterable placeholder="第二审核人"   :disabled = "Allinfoedit" style="width:100%">
                <el-option
                  v-for="item in auditForm.twoPeople"
                  :key="item.userId"
                  :label="item.nickName"
                  :value="item.userId">
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>

          <el-row>
            <el-col :span="12">
              <el-form-item label="出库原因:" prop="outboundReason" >
                <el-input @input = "changeSequence" v-model="auditForm.outboundReason"   placeholder="出库原因" maxlength="50" :disabled = "Allinfoedit" />
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="停车费用:" prop="parkingFees" >
                <el-input  @input = "changeSequence" v-model="auditForm.parkingFees" placeholder="停车费用" maxlength="50"   :disabled = "Allinfoedit"/>
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
                :disabled="Allinfoedit"
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
                             style="width:100%" >
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
            <el-col :span="12">
              <el-form-item label="车辆接收人:" prop="vehicleRecipient">
                <el-input  @input = "changeSequence" v-model="auditForm.vehicleRecipient" placeholder="车辆接收人" maxlength="50" :disabled = "Allinfoedit"/>
              </el-form-item>
            </el-col>
          </el-row>
        <el-row>
          <el-form-item label="备注:" prop="outRemark">
            <el-input  type="textarea" :rows="5" @input = "changeSequence" v-model="auditForm.outRemark" placeholder="备注" maxlength="300" />
          </el-form-item>
        </el-row>


      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitOut">确 定</el-button>
        <el-button @click="cancelout">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { getList,getInventoryInfo,save,delInventory,getGoOut,goOut,getAllInfo,updateInventory,updatePriceStatus} from "@/api/crk/crk";

import "@riophae/vue-treeselect/dist/vue-treeselect.css";

export default {
  name: "inventoryManagement",
  data() {
    return {

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


      Allinfoedit : false,

      edit: false,
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
      total:0,

      rules: {
        customerName:[{required : true, message:"请输入客户姓名", trigger :'blur'}],
        region:[{required : true, message:"请输入地区", trigger :'blur'}],
        businessType:[{required : true, message:"请输入业务类型", trigger :'blur'}],
        licensPlateNumber:[{required : true, message:"请输入车牌号", trigger :'blur'}],
        inboundDate:[{required : true, message:"请选择入库日期", trigger :'blur'}],
        makeAndModel:[{required : true, message:"请输入品牌型号", trigger :'blur'}],
        parkingGarage:[{required : true, message:"请输入停放车库", trigger :'blur'}],
        clearanceTeam:[{required : true, message:"请输入清收团队", trigger :'blur'}],
        settleStatus:[{required : true, message:"请选择是否支付",trigger :"change",type:"number"}],
        isNormal:[{required : true, message:"请选择车钥匙是否正常",trigger :"change",type:"number"}],
      },

      auditrules: {
        firstPeopleId:[{required : true, message:"请选择第一审核人", trigger :"change",type:"number"}],
        twoPeopleId:[{required : true, message:"请选择第二审核人", trigger :"change",type:"number"}],
        outboundReason:[{required : true, message:"请输入出库原因", trigger :'blur'}],
        parkingFees:[{required : true, message:"请输入停车费用", trigger :'blur'}],
        paid:[{required : true, message:"请选择是否已付", trigger :'blur'}],
        outDate:[{required : true, message:"请选择出库日期", trigger :'blur'}],
        vehicleRecipient:[{required : true, message:"请输入车辆接收人", trigger :'blur'}],
      },

      auditForm: {
        inventoryManagement:{},
        firstPeople:[],
        twoPeople:[],
        ooutInventory :{},
        firstPeopleId:undefined,
        settleStatus:undefined,
        outboundReason:undefined,
        parkingFees:undefined,
        paid:undefined,
        vehicleRecipient:undefined,
        twoPeopleId:undefined,
        outRemark:undefined,
        outDate:new Date(),
      },
      auditInfo: {
        title: '发起审核',
        outing:false,
      },
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


    getAllInfo(row) {
      this.Allinfoedit = true;
      this.auditInfo.outing = true;
      getAllInfo(row.id).then(response => {
        this.auditForm.inventoryManagement = response.inventoryManagement;
        this.auditForm.firstPeople = response.firstPeople;
        this.auditForm.twoPeople = response.twoPeople;
        this.auditForm.firstPeopleId= response.firstPeopleId;
        this.auditForm.twoPeopleId = response.twoPeopleId;
        this.auditForm.outboundReason= response.outboundReason;
        this.auditForm.outRemark=response.outRemark;
        this.auditForm.parkingFees = response.parkingFees;
        this.auditForm.paid= response.paid;
        this.auditForm.vehicleRecipient=response.vehicleRecipient;
        this.auditForm.outDate=response.outDate;

      });



    },


    getGoOut(row) {

      this.auditInfo.outing = true;
      getGoOut(row.id).then(response => {
        this.auditForm.inventoryManagement = response.inventoryManagement;
        this.auditForm.firstPeople = response.firstPeople;
        this.auditForm.twoPeople = response.twoPeople;


      });
    },

    // 取消按钮
    cancel() {
      this.open = false;
      this.edit = false;
      this.reset();
    },

    // 取消按钮
    cancelout() {
      this.auditInfo.outing = false;
      this.Allinfoedit = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.resetForm("form");
      this.resetForm("auditForm")
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
        this.edit = true;
        this.title = "修改库存信息";
      });
    },


    /** 修改按钮操作 */
    priceSettle(row) {
      this.reset();
      getAllInfo(row.id).then(response => {
        this.form = response.data;
        this.open = true;
        this.edit = true;
        this.title = "修改库存信息";
      });
    },
    /** 提交按钮 */
    submitForm: function() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != undefined) {
            let param  = {
              id : this.form.id,
              remark : this.form.remark,
              isNormal:this.form.isNormal,
              settleStatus:this.form.settleStatus,
            }
            updateInventory(param).then(response => {
              this.$modal.msgSuccess("修改库存信息成功");
              this.open = false;
              this.edit = false;
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


    submitOut :function() {
      this.$refs["auditForm"].validate(valid => {
        if (this.Allinfoedit === false) {
          let param  = {
            id : this.auditForm.inventoryManagement.id,
            settleStatus:this.auditForm.settleStatus,
            outboundReason:this.auditForm.outboundReason,
            parkingFees:this.auditForm.parkingFees,
            paid:this.auditForm.paid,
            outRemark:this.auditForm.outRemark,
            vehicleRecipient:this.auditForm.vehicleRecipient,
            firstPeopleId: this.auditForm.firstPeopleId,
            twoPeopleId:this.auditForm.twoPeopleId,
            outDate:this.auditForm.outDate,
          }
          goOut(param).then(response => {
            this.edit = false;
            this.getList();
            this.auditInfo.outing = false;
            }

          )

        }else{
          let param = {
            id : this.auditForm.inventoryManagement.id,
            paid:this.auditForm.paid,
            outRemark:this.auditForm.outRemark,
          }
          updatePriceStatus(param).then(response =>{
            this.edit = false;
            this.getList();
            this.auditInfo.outing = false;
            this.Allinfoedit =false;
          })

        }

        this.reset();
      });

    },
    /** 删除按钮操作 */
    handleDelete(row) {
      this.$modal.confirm('是否确认删除客户为"' + row.customerName+ '"的数据项？').then(function() {
        return delInventory(row.id);
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
