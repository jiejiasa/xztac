 q<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" style="width:auto">
      <el-form-item label="客户名称" prop="customerName">
        <el-input @input = "changeSequence"  v-model="queryParams.customerName" placeholder="客户名称" maxlength="50"  />
      </el-form-item>
      <el-form-item label="地区" prop="region">
        <el-input @input = "changeSequence"  v-model="queryParams.region" placeholder="地区" maxlength="50"  />
      </el-form-item>
      <el-form-item label="业务类型" prop="businessType">
        <el-input @input = "changeSequence"  v-model="queryParams.businessType" placeholder="业务类型" maxlength="50"  />
      </el-form-item>
      <el-form-item label="清收团队" prop="clearanceTeam">
        <el-input @input = "changeSequence"  v-model="queryParams.clearanceTeam" placeholder="清收团队" maxlength="50"  />
      </el-form-item>
      <el-form-item label="是否支付" prop="settleStatus">
        <template>
          <el-select v-model="queryParams.settleStatus" filterable placeholder="清收费用是否支付">
            <el-option
              v-for="item in options"
              :key="item.value"
              :label="item.label"
              :value="item.value">
            </el-option>
          </el-select>
        </template>
      </el-form-item>
      <el-form-item label="库存状态" prop="status">
        <el-select v-model="queryParams.status" placeholder="库存状态" clearable>
          <el-option
            v-for="item in status"
            :key="item.value"
            :label="item.label"
            :value="item.value">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="入库日期" prop="inboundDates">
        <el-date-picker
          v-model="queryParams.inboundDates"
          style="width: 240px"
          value-format="yyyy-MM-dd"
          type="daterange"
          range-separator="-"
          start-placeholder="开始日期"
          end-placeholder="结束日期">
        </el-date-picker>
      </el-form-item>

      <el-form-item label="出库日期" prop="outDates">
        <el-date-picker
          v-model="queryParams.outDates"
          style="width: 240px"
          value-format="yyyy-MM-dd"
          type="daterange"
          range-separator="-"
          start-placeholder="开始日期"
          end-placeholder="结束日期">
        </el-date-picker>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
        <el-button
          type="info"
          plain
          icon="el-icon-upload2"
          size="mini"
          @click="handleImport"
        >导入</el-button>
      </el-form-item>
      <el-form-item>
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="testexport"
        >导出</el-button>
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
        prop="garageTypeOrName"
        label="停放车库" width="auto">
      </el-table-column>
      <el-table-column
        prop="outDate"
        label="出库日期" width="auto">
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
        prop="inDayMony"
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

      <el-table-column
        prop="isNormal"
        label="车钥匙是否正常" width="auto">
        <template slot-scope="scope">
          <span v-if="scope.row.isNormal === 0">正常</span>
          <span v-if="scope.row.isNormal === 1">异常</span>
          <span v-if="scope.row.isNormal === 2">未知</span>

        </template>
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
                style="width:73%"
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
              <el-cascader
                v-model="form.parkingGarage"
                :options="garageList"
                :props="props"
                :disabled="edit"
                style="width :80%"
                :show-all-levels="false"
                @change="handleChange"></el-cascader>

            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="清收团队:" prop="clearanceTeam"  label-width="auto">
              <el-input  @input = "changeSequence" v-model="form.clearanceTeam" placeholder="清收团队" maxlength="50" :disabled="edit" style="width :73%"/>
            </el-form-item>
          </el-col>
        </el-row>

        <el-row>
          <el-col :span="12">
            <el-form-item label="清收费用:" prop="pickUpFee"  >
              <el-input @input = "changeSequencePickNumber"v-model="form.pickUpFee"    placeholder="清收费用" maxlength="50" :disabled="edit" style="width :80%"/>
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
<!--              <el-input @input = "changeSequence" v-model="auditForm.inventoryManagement.parkingGarage"   placeholder="停放车库" maxlength="50":disabled="true"/>-->
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
              <el-input @input = "changeSequencePickNumber" v-model="auditForm.inventoryManagement.pickUpFee"    placeholder="清收费用" maxlength="50" :disabled="true"/>
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
            <el-form-item label="法务主管:" prop="firstPeopleId" >
              <el-select  v-model="auditForm.firstPeopleId" filterable placeholder="法务主管" :disabled = "Allinfoedit" style="width:100%">
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
            <el-form-item label="风险经理:" prop="twoPeopleId" >
              <el-select  v-model="auditForm.twoPeopleId" filterable placeholder="风险经理"   :disabled = "Allinfoedit" style="width:100%">
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
            <el-col :span="12"  >
              <el-form-item label="停车费用:" prop="parkingFees"  >
                <el-input    @input = "changeSequenceNumber" v-model="auditForm.parkingFees"   placeholder="停车费用" maxlength="50"   :disabled = "true"/>
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




    <el-dialog :title="upload.title" :visible.sync="upload.open" width="400px" append-to-body>
      <el-upload
        ref="upload"
        :limit="1"
        accept=".xlsx, .xls"
        :headers="upload.headers"
        :action="upload.url + '?updateSupport=' + upload.updateSupport"
        :disabled="upload.isUploading"
        :on-progress="handleFileUploadProgress"
        :on-success="handleFileSuccess"
        :auto-upload="false"
        drag
      >
        <i class="el-icon-upload"></i>
        <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
        <div class="el-upload__tip text-center" slot="tip">
          <span>仅允许导入xls、xlsx格式文件。</span>
          <el-link type="primary" :underline="false" style="font-size:12px;vertical-align: baseline;" @click="importTemplate">下载模板</el-link>
        </div>
      </el-upload>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitFileForm">确 定</el-button>
        <el-button @click="upload.open = false">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { getList,getInventoryInfo,save,delInventory,getGoOut,goOut,getAllInfo,updateInventory,updatePriceStatus,importTemplates,exports} from "@/api/crk/crk";
import { getListg,selectList } from '@/api/crk/garage'
import "@riophae/vue-treeselect/dist/vue-treeselect.css";
import { getToken } from '@/utils/auth'

export default {
  name: "inventoryManagement",
  data() {
    return {

      upload: {
        // 是否显示弹出层（用户导入）
        open: false,
        // 弹出层标题（用户导入）
        title: "",
        // 是否禁用上传
        isUploading: false,
        // 设置上传的请求头部
        headers: { Authorization: "Bearer " + getToken() },
        // 上传的地址
        url: process.env.VUE_APP_BASE_API + "/inventory/InventoryImport"
      },

      value :[],

    props:
      {label:'garageTypeOrName',value:'id',children:'chileds',emitPath:false},

      status:[
        {
          value: 0,
          label: '在库'
        }, {
          value: 1,
          label: '审核中'
        }, {
          value: 2,
          label: '审核未通过'
        }, {
          value: 3,
          label: '出库'
        },
      ],
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
        businessType: undefined,
        clearanceTeam: undefined,
        customerName: undefined,
        region: undefined,
        settleStatus:undefined,
        status:undefined,
        inboundDates:[],
        outDates:[],
        pageNum:1,
        pageSize:10,
      },
      // 表单参数
      form: {
      },
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
        firstPeopleId:[{required : true, message:"请选择法务主管", trigger :["blur",'change']}],
        twoPeopleId:[{required : true, message:"请选择风险经理", trigger :"change",type:"number"}],
        outboundReason:[{required : true, message:"请输入出库原因", trigger :'blur'}],
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
        outDate:this.formatDate(new Date()),
      },
      auditInfo: {
        title: '发起审核',
        outing:false,
      },
      pickerOptions: {
        disabledDate(time) {
          return false;
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

    /** 查询库存列表 */
    getList() {
      this.loading = true;
      getListg({pageNum:1,pageSize:10}).then(response => {
        this.garageList = this.getTreeData(response.list);
      });
      getList(this.queryParams).then(response => {
        this.crkList = response.list;
        this.total = response.total;
        this.loading = false;
      });
    },


    getAllInfo(row) {
      this.Allinfoedit = true;
      this.auditInfo.outing = true;
      this.auditInfo.title = "费用结清";
      getListg({pageNum:1,pageSize:10}).then(response => {
        this.garageList = this.getTreeData(response.list);
      });
      getAllInfo(row.id).then(response => {
        this.auditForm.inventoryManagement = response.inventoryManagement;
        this.auditForm.firstPeople = response.firstPeople;
        this.auditForm.twoPeople = response.twoPeople;
        // this.auditForm.firstPeopleId= response.firstPeopleId;
        // this.auditForm.twoPeopleId = response.twoPeopleId;
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

      getListg({pageNum:1,pageSize:10}).then(response => {
        this.garageList = this.getTreeData(response.list);
      });
      this.auditForm.parkingFees = row.inDayMony;
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
      getListg({pageNum:1,pageSize:10}).then(response => {
        this.garageList = this.getTreeData(response.list);
      });
      getInventoryInfo(row.id).then(response => {
        this.form = response.data;
        this.open = true;
        this.edit = true;
        this.title = "修改库存信息";
      });
    },

    handleImport() {
      this.upload.title = "库存导入";
      this.upload.open = true;
    },

    handleFileUploadProgress(event, file, fileList) {
      this.upload.isUploading = true;
    },
    // 文件上传成功处理
    handleFileSuccess(response, file, fileList) {
      this.upload.open = false;
      this.upload.isUploading = false;
      this.$refs.upload.clearFiles();
      // this.$alert("<div style='overflow: auto;overflow-x: hidden;max-height: 70vh;padding: 10px 20px 0;'>" + response.msg + "</div>", "导入结果", { dangerouslyUseHTMLString: true });
      this.$modal.msgSuccess("导入成功");
      this.getList();
    },
    // 提交上传文件
    submitFileForm() {
      this.$refs.upload.submit();
    },


    /** 提交按钮 */
    submitForm: function() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          this.form.parkingGarage = this.form.parkingGarage.toString();
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
            this.form.pickUpFee =
            save(this.form).then(response => {
              this.$modal.msgSuccess("新增库存成功");
              this.open = false;
              this.getList();
            });
          }
          this.reset();
        }
      });
    },



    submitOut :function() {
      this.$refs["auditForm"].validate(valid => {
        if(valid) {
          if (this.Allinfoedit === false) {
            let param = {
              id: this.auditForm.inventoryManagement.id,
              settleStatus: this.auditForm.settleStatus,
              outboundReason: this.auditForm.outboundReason,
              parkingFees: this.auditForm.parkingFees,
              paid: this.auditForm.paid,
              outRemark: this.auditForm.outRemark,
              vehicleRecipient: this.auditForm.vehicleRecipient,
              firstPeopleId: this.auditForm.firstPeopleId,
              twoPeopleId: this.auditForm.twoPeopleId,
              outDate: this.auditForm.outDate,
              inDate: this.auditForm.inventoryManagement.inboundDate,
              parkingGarage: this.auditForm.inventoryManagement.parkingGarage,
            }
            goOut(param).then(response => {
                this.edit = false;
                this.getList();
                this.auditInfo.outing = false;
              }
            )

          } else {
            let param = {
              id: this.auditForm.inventoryManagement.id,
              paid: this.auditForm.paid,
              outRemark: this.auditForm.outRemark,
            }
            updatePriceStatus(param).then(response => {
              this.edit = false;
              this.getList();
              this.auditInfo.outing = false;
              this.Allinfoedit = false;
            })

          }
          this.reset();
        }

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


    handleChange(value) {
      console.log(value);
    },

    formatDate(date) {
      const year = date.getFullYear();
      const month = (date.getMonth() + 1).toString().padStart(2, '0');
      const day = date.getDate().toString().padStart(2, '0');
      return `${year}-${month}-${day}`;
    },


    changeSequenceNumber(value) {
      this.auditForm.parkingFees = value.replace(/[^0-9]/g, '');
    },
    changeSequencePickNumber(value) {
      this.form.pickUpFee = value.replace(/[^0-9]/g, '');
    },

    importTemplate() {
      importTemplates().then(res => {
        console.log(res)
        const fileName = '模板文件'
        const url = window.URL.createObjectURL(new Blob([res]))
        const link = document.createElement('a')
        link.style.display = 'none'
        link.href = url
        link.setAttribute('download', fileName + '.xls')
        document.body.appendChild(link)
        link.click()
        // 下载完成移除元素
        document.body.removeChild(link)
        // 释放掉blob对象
        window.URL.revokeObjectURL(url)

      }).catch(err => {
        console.log(err.response.data.message)
      })

    },


    testexport() {
      exports(this.queryParams).then(res => {
        console.log(res)
        const fileName = '库存信息导出1'
        const url = window.URL.createObjectURL(new Blob([res]))
        const link = document.createElement('a')
        link.style.display = 'none'
        link.href = url
        link.setAttribute('download', fileName + '.xls')
        document.body.appendChild(link)
        link.click()
        // 下载完成移除元素
        document.body.removeChild(link)
        // 释放掉blob对象
        window.URL.revokeObjectURL(url)
        this.$modal.msgSuccess("导出成功");
      }).catch(err => {
        console.log(err.response.data.message)
      })
    },

    changeSequence(){
      this.$forceUpdate();
    }
  },



};
</script>
