<template>
  <div class="app-container stock-page">
    <el-row :gutter="16">
      <el-col :lg="13" :md="24">
        <el-card shadow="never" class="panel-card">
          <div slot="header" class="panel-header">
            <span>当前库存</span>
            <el-tag type="success" effect="plain">订单出库自动扣减</el-tag>
          </div>

          <el-form ref="queryForm" :model="queryParams" :inline="true" size="small" label-width="82px">
            <el-form-item label="产品名称" prop="productName">
              <el-input v-model="queryParams.productName" placeholder="请输入产品名称" clearable @keyup.enter.native="getStockList" />
            </el-form-item>
            <el-form-item label="产品种类" prop="category">
              <el-input v-model="queryParams.category" placeholder="请输入产品种类" clearable @keyup.enter.native="getStockList" />
            </el-form-item>
            <el-form-item>
              <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
              <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
            </el-form-item>
          </el-form>

          <el-table v-loading="loading" :data="stockList" highlight-current-row @current-change="handleCurrentChange">
            <el-table-column label="产品名称" prop="productName" min-width="140" />
            <el-table-column label="种类" prop="category" min-width="100" />
            <el-table-column label="规格" prop="specification" min-width="180" />
            <el-table-column label="单价" prop="unitPrice" width="110" align="right">
              <template slot-scope="scope">{{ formatMoney(scope.row.unitPrice) }}</template>
            </el-table-column>
            <el-table-column label="当前库存" prop="stockQuantity" width="110" align="center">
              <template slot-scope="scope">
                <el-tag :type="scope.row.stockQuantity > 50 ? 'success' : scope.row.stockQuantity > 0 ? 'warning' : 'danger'" effect="plain">
                  {{ scope.row.stockQuantity }}
                </el-tag>
              </template>
            </el-table-column>
            <el-table-column label="操作" width="190" align="center">
              <template slot-scope="scope">
                <el-button size="mini" type="text" @click="openInbound(scope.row)" v-hasPermi="['timber:stock:edit']">手动入库</el-button>
                <el-button size="mini" type="text" @click="openAdjust(scope.row)" v-hasPermi="['timber:stock:edit']">库存调整</el-button>
              </template>
            </el-table-column>
          </el-table>

          <pagination
            v-show="total > 0"
            :total="total"
            :page.sync="queryParams.pageNum"
            :limit.sync="queryParams.pageSize"
            @pagination="getStockList"
          />
        </el-card>
      </el-col>

      <el-col :lg="11" :md="24">
        <el-card shadow="never" class="panel-card">
          <div slot="header" class="panel-header">
            <span>库存流水</span>
            <span class="panel-tip">入库 / 出库 / 调整，支持关联订单与任务单</span>
          </div>

          <el-form ref="flowForm" :model="flowQueryParams" size="small" label-width="74px">
            <el-row :gutter="12">
              <el-col :span="12">
                <el-form-item label="产品名称">
                  <el-input v-model="flowQueryParams.productName" placeholder="产品名称" clearable />
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="流水类型">
                  <el-select v-model="flowQueryParams.flowType" placeholder="请选择" clearable style="width: 100%">
                    <el-option label="入库" value="in" />
                    <el-option label="出库" value="out" />
                    <el-option label="调整" value="adjust" />
                  </el-select>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row :gutter="12">
              <el-col :span="12">
                <el-form-item label="订单单号">
                  <el-input v-model="flowQueryParams.orderNumber" placeholder="订单单号" clearable />
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="任务单号">
                  <el-input v-model="flowQueryParams.taskNo" placeholder="任务单号" clearable />
                </el-form-item>
              </el-col>
            </el-row>
            <el-form-item label="业务时间">
              <el-date-picker
                v-model="flowDateRange"
                type="daterange"
                range-separator="-"
                start-placeholder="开始时间"
                end-placeholder="结束时间"
                value-format="yyyy-MM-dd HH:mm:ss"
                style="width: 100%"
              />
            </el-form-item>
            <el-form-item>
              <el-button type="primary" size="mini" icon="el-icon-search" @click="handleFlowQuery">查询流水</el-button>
              <el-button size="mini" icon="el-icon-refresh" @click="resetFlowQuery">重置</el-button>
            </el-form-item>
          </el-form>

          <el-table v-loading="flowLoading" :data="flowList" height="540">
            <el-table-column label="产品" prop="productName" min-width="120" />
            <el-table-column label="类型" width="90" align="center">
              <template slot-scope="scope">
                <el-tag :type="flowTag(scope.row.flowType)" effect="plain">{{ flowLabel(scope.row.flowType) }}</el-tag>
              </template>
            </el-table-column>
            <el-table-column label="变动" width="80" align="center" prop="changeQuantity" />
            <el-table-column label="库存变化" min-width="130">
              <template slot-scope="scope">{{ scope.row.beforeQuantity }} -> {{ scope.row.afterQuantity }}</template>
            </el-table-column>
            <el-table-column label="关联信息" min-width="180">
              <template slot-scope="scope">
                <div v-if="scope.row.orderNumber">订单：{{ scope.row.orderNumber }}</div>
                <div v-if="scope.row.taskNo">任务：{{ scope.row.taskNo }}</div>
                <span v-if="!scope.row.orderNumber && !scope.row.taskNo">-</span>
              </template>
            </el-table-column>
            <el-table-column label="业务时间" min-width="160">
              <template slot-scope="scope">{{ parseTime(scope.row.businessTime) }}</template>
            </el-table-column>
          </el-table>

          <pagination
            v-show="flowTotal > 0"
            :total="flowTotal"
            :page.sync="flowQueryParams.pageNum"
            :limit.sync="flowQueryParams.pageSize"
            @pagination="getFlowList"
          />
        </el-card>
      </el-col>
    </el-row>

    <el-dialog :title="dialogMode === 'in' ? '手动入库' : '库存调整'" :visible.sync="dialogOpen" width="520px" append-to-body>
      <el-form ref="stockDialogForm" :model="stockForm" :rules="rules" label-width="92px">
        <el-form-item label="产品名称">
          <el-input :value="stockForm.productName" disabled />
        </el-form-item>
        <el-form-item label="当前库存">
          <el-input :value="stockForm.currentQuantity" disabled />
        </el-form-item>
        <el-form-item v-if="dialogMode === 'in'" label="入库数量" prop="quantity">
          <el-input-number v-model="stockForm.quantity" :min="1" :step="1" style="width: 100%" />
        </el-form-item>
        <el-form-item v-else label="调整后库存" prop="targetQuantity">
          <el-input-number v-model="stockForm.targetQuantity" :min="0" :step="1" style="width: 100%" />
        </el-form-item>
        <el-form-item label="订单单号">
          <el-input v-model="stockForm.orderNumber" placeholder="可选，关联订单单号" />
        </el-form-item>
        <el-form-item label="任务单号">
          <el-input v-model="stockForm.taskNo" placeholder="可选，生产完成入库可关联任务单" />
        </el-form-item>
        <el-form-item label="备注">
          <el-input v-model="stockForm.remark" type="textarea" :rows="3" placeholder="请输入备注" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitStockForm">确 定</el-button>
        <el-button @click="dialogOpen = false">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listStock, listStockFlow, stockAdjust, stockIn } from '@/api/timber/stock'

export default {
  name: 'TimberStock',
  data() {
    return {
      loading: false,
      flowLoading: false,
      total: 0,
      flowTotal: 0,
      stockList: [],
      flowList: [],
      flowDateRange: [],
      currentRow: null,
      dialogOpen: false,
      dialogMode: 'in',
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        productName: null,
        category: null
      },
      flowQueryParams: {
        pageNum: 1,
        pageSize: 10,
        productName: null,
        flowType: null,
        orderNumber: null,
        taskNo: null
      },
      stockForm: {
        productId: null,
        productName: null,
        currentQuantity: 0,
        quantity: 1,
        targetQuantity: 0,
        orderNumber: null,
        taskNo: null,
        remark: null
      },
      rules: {
        quantity: [{ required: true, message: '请输入入库数量', trigger: 'blur' }],
        targetQuantity: [{ required: true, message: '请输入调整后库存', trigger: 'blur' }]
      }
    }
  },
  created() {
    this.getStockList()
    this.getFlowList()
  },
  methods: {
    getStockList() {
      this.loading = true
      listStock(this.queryParams).then(response => {
        this.stockList = response.rows
        this.total = response.total
        this.loading = false
      }).catch(() => {
        this.loading = false
      })
    },
    getFlowList() {
      this.flowLoading = true
      listStockFlow(this.addDateRange(this.flowQueryParams, this.flowDateRange)).then(response => {
        this.flowList = response.rows
        this.flowTotal = response.total
        this.flowLoading = false
      }).catch(() => {
        this.flowLoading = false
      })
    },
    handleQuery() {
      this.queryParams.pageNum = 1
      this.getStockList()
    },
    resetQuery() {
      this.resetForm('queryForm')
      this.handleQuery()
    },
    handleFlowQuery() {
      this.flowQueryParams.pageNum = 1
      this.getFlowList()
    },
    resetFlowQuery() {
      this.flowDateRange = []
      this.resetForm('flowForm')
      this.handleFlowQuery()
    },
    handleCurrentChange(row) {
      this.currentRow = row
    },
    openInbound(row) {
      this.dialogMode = 'in'
      this.openDialog(row)
    },
    openAdjust(row) {
      this.dialogMode = 'adjust'
      this.openDialog(row)
    },
    openDialog(row) {
      this.stockForm = {
        productId: row.productId,
        productName: row.productName,
        currentQuantity: row.stockQuantity,
        quantity: 1,
        targetQuantity: row.stockQuantity,
        orderNumber: null,
        taskNo: null,
        remark: null
      }
      this.dialogOpen = true
      this.$nextTick(() => this.resetForm('stockDialogForm'))
    },
    submitStockForm() {
      this.$refs.stockDialogForm.validate(valid => {
        if (!valid) {
          return
        }
        const request = this.dialogMode === 'in'
          ? stockIn(this.stockForm)
          : stockAdjust(this.stockForm)
        request.then(() => {
          this.$modal.msgSuccess(this.dialogMode === 'in' ? '入库成功' : '库存调整成功')
          this.dialogOpen = false
          this.getStockList()
          this.getFlowList()
        })
      })
    },
    flowLabel(type) {
      const labels = { in: '入库', out: '出库', adjust: '调整' }
      return labels[type] || type
    },
    flowTag(type) {
      const tags = { in: 'success', out: 'danger', adjust: 'warning' }
      return tags[type] || 'info'
    },
    formatMoney(value) {
      return value === null || value === undefined || value === '' ? '0.00' : Number(value).toFixed(2)
    }
  }
}
</script>

<style scoped>
.panel-card {
  margin-bottom: 16px;
}

.panel-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.panel-tip {
  color: #909399;
  font-size: 12px;
}
</style>
