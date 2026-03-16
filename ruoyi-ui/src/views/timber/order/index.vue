<template>
  <div class="app-container order-page">
    <el-form ref="queryForm" :model="queryParams" :inline="true" size="small" v-show="showSearch" label-width="82px">
      <el-form-item label="订单号" prop="orderNumber">
        <el-input v-model="queryParams.orderNumber" placeholder="请输入订单号" clearable @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="客户" prop="customerId">
        <el-select v-model="queryParams.customerId" placeholder="请选择客户" clearable filterable>
          <el-option v-for="item in customerOptions" :key="item.customerId" :label="item.customerName" :value="item.customerId" />
        </el-select>
      </el-form-item>
      <el-form-item label="产品名称" prop="productName">
        <el-input v-model="queryParams.productName" placeholder="请输入产品名称" clearable @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="订单状态" prop="orderStatus">
        <el-select v-model="queryParams.orderStatus" placeholder="请选择状态" clearable>
          <el-option v-for="item in statusOptions" :key="item.value" :label="item.label" :value="item.value" />
        </el-select>
      </el-form-item>
      <el-form-item label="创建时间">
        <el-date-picker
          v-model="dateRange"
          type="daterange"
          range-separator="-"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
          value-format="yyyy-MM-dd HH:mm:ss"
        />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button type="primary" plain icon="el-icon-plus" size="mini" @click="handleAdd" v-hasPermi="['timber:order:add']">新建订单</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="warning" plain icon="el-icon-download" size="mini" @click="handleExport" v-hasPermi="['timber:order:export']">导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList" />
    </el-row>

    <el-table v-loading="loading" :data="orderList">
      <el-table-column label="订单号" prop="orderNumber" min-width="180" />
      <el-table-column label="客户" prop="customerName" min-width="140" />
      <el-table-column label="产品数" prop="itemCount" width="90" align="center" />
      <el-table-column label="总金额" prop="totalAmount" width="120" align="right">
        <template slot-scope="scope">
          <span>{{ formatMoney(scope.row.totalAmount) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="订单状态" width="130" align="center">
        <template slot-scope="scope">
          <el-tag :type="statusTagMap[scope.row.orderStatus] || 'info'" effect="plain">{{ statusLabel(scope.row.orderStatus) }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="任务单" min-width="170">
        <template slot-scope="scope">
          <div v-if="scope.row.productionTaskNo">
            <div>{{ scope.row.productionTaskNo }}</div>
            <el-tag size="mini" :type="taskTagMap[scope.row.productionTaskStatus] || 'info'" effect="plain">{{ taskStatusLabel(scope.row.productionTaskStatus) }}</el-tag>
          </div>
          <span v-else class="text-muted">未生成</span>
        </template>
      </el-table-column>
      <el-table-column label="创建时间" prop="createTime" width="170">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createTime) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" width="360" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button size="mini" type="text" icon="el-icon-view" @click="handleDetail(scope.row)">详情</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-if="canEdit(scope.row)"
            v-hasPermi="['timber:order:edit']"
          >编辑</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-s-operation"
            @click="openStatusDialog(scope.row)"
            v-if="nextStatusOptions(scope.row).length"
            v-hasPermi="['timber:order:edit']"
          >流转</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-document-add"
            @click="handleGenerateTask(scope.row)"
            v-if="canGenerateTask(scope.row)"
            v-hasPermi="['timber:order:edit']"
          >生成任务单</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-if="canDelete(scope.row)"
            v-hasPermi="['timber:order:remove']"
          >删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="total > 0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <el-dialog :title="title" :visible.sync="open" width="980px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="90px">
        <el-row :gutter="18">
          <el-col :span="12">
            <el-form-item label="客户" prop="customerId">
              <el-select v-model="form.customerId" placeholder="请选择客户" filterable style="width: 100%" @change="syncCustomerName">
                <el-option v-for="item in customerOptions" :key="item.customerId" :label="item.customerName" :value="item.customerId" />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="订单号">
              <el-input :value="form.orderNumber || '保存后自动生成'" disabled />
            </el-form-item>
          </el-col>
        </el-row>

        <el-card shadow="never" class="item-card">
          <div slot="header" class="card-header">
            <span>订单明细</span>
            <div>
              <el-button type="primary" plain size="mini" icon="el-icon-plus" @click="addOrderItem">添加产品</el-button>
              <el-button type="danger" plain size="mini" icon="el-icon-delete" @click="removeSelectedItems">删除选中</el-button>
            </div>
          </div>
          <el-table ref="itemTable" :data="form.orderItemList" border @selection-change="handleItemSelectionChange">
            <el-table-column type="selection" width="50" align="center" />
            <el-table-column label="产品" min-width="240">
              <template slot-scope="scope">
                <el-select v-model="scope.row.productId" placeholder="请选择产品" filterable style="width: 100%" @change="val => handleProductChange(scope.row, val)">
                  <el-option
                    v-for="item in productOptions"
                    :key="item.productId"
                    :label="item.productName + ' / ' + item.specification"
                    :value="item.productId"
                  />
                </el-select>
              </template>
            </el-table-column>
            <el-table-column label="规格" min-width="180">
              <template slot-scope="scope">
                <span>{{ scope.row.specification || '-' }}</span>
              </template>
            </el-table-column>
            <el-table-column label="数量" width="120" align="center">
              <template slot-scope="scope">
                <el-input-number v-model="scope.row.quantity" :min="1" :step="1" @change="refreshAmount(scope.row)" />
              </template>
            </el-table-column>
            <el-table-column label="单价" width="120" align="right">
              <template slot-scope="scope">
                <span>{{ formatMoney(scope.row.unitPrice) }}</span>
              </template>
            </el-table-column>
            <el-table-column label="小计" width="120" align="right">
              <template slot-scope="scope">
                <span>{{ formatMoney(scope.row.amount) }}</span>
              </template>
            </el-table-column>
          </el-table>
          <div class="total-bar">
            <span>总价自动计算：</span>
            <strong>{{ formatMoney(form.totalAmount) }}</strong>
          </div>
        </el-card>

        <el-form-item label="备注" prop="remark">
          <el-input v-model="form.remark" type="textarea" :rows="3" placeholder="请输入备注" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>

    <el-dialog title="订单详情" :visible.sync="detailOpen" width="1000px" append-to-body>
      <div v-if="detailData.orderId">
        <el-descriptions :column="3" border class="detail-block">
          <el-descriptions-item label="订单号">{{ detailData.orderNumber }}</el-descriptions-item>
          <el-descriptions-item label="客户">{{ detailData.customerName }}</el-descriptions-item>
          <el-descriptions-item label="总金额">{{ formatMoney(detailData.totalAmount) }}</el-descriptions-item>
          <el-descriptions-item label="当前状态">
            <el-tag :type="statusTagMap[detailData.orderStatus] || 'info'" effect="plain">{{ statusLabel(detailData.orderStatus) }}</el-tag>
          </el-descriptions-item>
          <el-descriptions-item label="合同编号">{{ detailData.contractNo || '-' }}</el-descriptions-item>
          <el-descriptions-item label="创建时间">{{ parseTime(detailData.createTime) }}</el-descriptions-item>
          <el-descriptions-item label="任务单号">{{ detailData.productionTaskNo || '-' }}</el-descriptions-item>
          <el-descriptions-item label="任务状态">{{ taskStatusLabel(detailData.productionTaskStatus) || '-' }}</el-descriptions-item>
          <el-descriptions-item label="取消原因">{{ detailData.cancelReason || '-' }}</el-descriptions-item>
        </el-descriptions>

        <el-card shadow="never" class="detail-block">
          <div slot="header">状态节点</div>
          <el-steps :active="stepIndex(detailData.orderStatus)" finish-status="success" process-status="process" simple>
            <el-step v-for="item in statusFlow" :key="item.value" :title="item.label" />
          </el-steps>
        </el-card>

        <el-card shadow="never" class="detail-block">
          <div slot="header">订单明细</div>
          <el-table :data="detailData.orderItemList || []" border>
            <el-table-column label="产品" prop="productName" min-width="180" />
            <el-table-column label="规格" prop="specification" min-width="160" />
            <el-table-column label="数量" prop="quantity" width="100" align="center" />
            <el-table-column label="单价" prop="unitPrice" width="120" align="right">
              <template slot-scope="scope">{{ formatMoney(scope.row.unitPrice) }}</template>
            </el-table-column>
            <el-table-column label="小计" width="120" align="right">
              <template slot-scope="scope">{{ formatMoney(calcItemAmount(scope.row)) }}</template>
            </el-table-column>
          </el-table>
        </el-card>

        <el-row :gutter="16" class="detail-block">
          <el-col :span="12">
            <el-card shadow="never">
              <div slot="header">生产任务</div>
              <div v-if="detailData.productionTaskList && detailData.productionTaskList.length">
                <div v-for="item in detailData.productionTaskList" :key="item.taskId" class="task-item">
                  <div class="task-title">{{ item.taskNo }}</div>
                  <div class="task-meta">状态：{{ taskStatusLabel(item.taskStatus) }} / 计划数量：{{ item.plannedQuantity }}</div>
                  <div class="task-meta">生成方式：{{ item.createMode === 'auto' ? '自动' : '手动' }} / 创建时间：{{ parseTime(item.createTime) }}</div>
                </div>
              </div>
              <el-empty v-else description="暂无生产任务" :image-size="90" />
            </el-card>
          </el-col>
          <el-col :span="12">
            <el-card shadow="never">
              <div slot="header">状态流转记录</div>
              <el-timeline v-if="detailData.statusLogList && detailData.statusLogList.length">
                <el-timeline-item
                  v-for="item in detailData.statusLogList"
                  :key="item.logId"
                  :timestamp="parseTime(item.operationTime)"
                  placement="top"
                >
                  <div>{{ statusLabel(item.fromStatus) || '初始' }} -> {{ statusLabel(item.toStatus) }}</div>
                  <div class="text-muted">{{ item.operationBy || '-' }} / {{ item.operationRemark || '无备注' }}</div>
                </el-timeline-item>
              </el-timeline>
              <el-empty v-else description="暂无流转记录" :image-size="90" />
            </el-card>
          </el-col>
        </el-row>
      </div>
    </el-dialog>

    <el-dialog title="状态流转" :visible.sync="statusOpen" width="460px" append-to-body>
      <el-form ref="statusForm" :model="statusForm" :rules="statusRules" label-width="100px">
        <el-form-item label="当前状态">
          <el-input :value="statusLabel(currentRow.orderStatus)" disabled />
        </el-form-item>
        <el-form-item label="目标状态" prop="targetStatus">
          <el-select v-model="statusForm.targetStatus" placeholder="请选择目标状态" style="width: 100%">
            <el-option v-for="item in nextStatusOptions(currentRow)" :key="item.value" :label="item.label" :value="item.value" />
            <el-option v-if="canCancel(currentRow)" label="已取消" value="canceled" />
          </el-select>
        </el-form-item>
        <el-form-item label="自动任务单" v-if="statusForm.targetStatus === 'approved'">
          <el-switch v-model="statusForm.autoCreateTask" />
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-input v-model="statusForm.remark" type="textarea" :rows="3" placeholder="请输入备注" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitStatusChange">确 定</el-button>
        <el-button @click="statusOpen = false">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {
  addOrder,
  changeOrderStatus,
  delOrder,
  generateProductionTask,
  getOrder,
  getOrderBaseData,
  listOrder,
  updateOrder
} from '@/api/timber/order'

const STATUS_OPTIONS = [
  { label: '草稿', value: 'draft' },
  { label: '待审核', value: 'pending_review' },
  { label: '审核通过', value: 'approved' },
  { label: '合同签订', value: 'contract_signed' },
  { label: '生产中', value: 'in_production' },
  { label: '已生产', value: 'produced' },
  { label: '出库', value: 'outbound' },
  { label: '完成', value: 'completed' },
  { label: '已取消', value: 'canceled' }
]

const STATUS_FLOW = STATUS_OPTIONS.filter(item => item.value !== 'canceled')

const NEXT_STATUS_MAP = {
  draft: 'pending_review',
  pending_review: 'approved',
  approved: 'contract_signed',
  contract_signed: 'in_production',
  in_production: 'produced',
  produced: 'outbound',
  outbound: 'completed'
}

export default {
  name: 'TimberOrder',
  data() {
    return {
      loading: false,
      showSearch: true,
      total: 0,
      dateRange: [],
      orderList: [],
      customerOptions: [],
      productOptions: [],
      selectedItems: [],
      open: false,
      detailOpen: false,
      statusOpen: false,
      title: '',
      currentRow: {},
      detailData: {},
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        orderNumber: null,
        customerId: null,
        productName: null,
        orderStatus: null
      },
      form: {},
      statusForm: {
        targetStatus: null,
        remark: null,
        autoCreateTask: true
      },
      rules: {
        customerId: [{ required: true, message: '请选择客户', trigger: 'change' }]
      },
      statusRules: {
        targetStatus: [{ required: true, message: '请选择目标状态', trigger: 'change' }]
      },
      statusOptions: STATUS_OPTIONS,
      statusFlow: STATUS_FLOW,
      statusTagMap: {
        draft: 'info',
        pending_review: 'warning',
        approved: 'success',
        contract_signed: '',
        in_production: 'warning',
        produced: 'success',
        outbound: '',
        completed: 'success',
        canceled: 'danger'
      },
      taskTagMap: {
        pending: 'warning',
        in_progress: '',
        completed: 'success',
        canceled: 'danger'
      }
    }
  },
  created() {
    this.reset()
    this.loadBaseData()
    this.getList()
  },
  methods: {
    loadBaseData() {
      getOrderBaseData().then(response => {
        this.customerOptions = response.data.customers || []
        this.productOptions = response.data.products || []
      })
    },
    getList() {
      this.loading = true
      listOrder(this.addDateRange(this.queryParams, this.dateRange)).then(response => {
        this.orderList = response.rows
        this.total = response.total
        this.loading = false
      }).catch(() => {
        this.loading = false
      })
    },
    reset() {
      this.form = {
        orderId: null,
        orderNumber: null,
        customerId: null,
        customerName: null,
        totalAmount: 0,
        remark: null,
        orderItemList: []
      }
      this.selectedItems = []
      this.resetForm('form')
    },
    handleQuery() {
      this.queryParams.pageNum = 1
      this.getList()
    },
    resetQuery() {
      this.dateRange = []
      this.resetForm('queryForm')
      this.handleQuery()
    },
    handleAdd() {
      this.reset()
      this.addOrderItem()
      this.title = '新建订单'
      this.open = true
    },
    handleUpdate(row) {
      getOrder(row.orderId).then(response => {
        this.reset()
        this.form = this.normalizeOrder(response.data)
        this.title = '编辑订单'
        this.open = true
      })
    },
    handleDetail(row) {
      getOrder(row.orderId).then(response => {
        this.detailData = this.normalizeOrder(response.data)
        this.detailOpen = true
      })
    },
    cancel() {
      this.open = false
      this.reset()
    },
    addOrderItem() {
      this.form.orderItemList.push({
        productId: null,
        productName: null,
        specification: null,
        quantity: 1,
        unitPrice: 0,
        amount: 0
      })
    },
    handleItemSelectionChange(selection) {
      this.selectedItems = selection
    },
    removeSelectedItems() {
      if (!this.selectedItems.length) {
        this.$modal.msgWarning('请先选择要删除的产品')
        return
      }
      this.form.orderItemList = this.form.orderItemList.filter(item => !this.selectedItems.includes(item))
      this.recalculateTotal()
    },
    handleProductChange(row, productId) {
      const product = this.productOptions.find(item => item.productId === productId)
      if (!product) {
        row.productName = null
        row.specification = null
        row.unitPrice = 0
      } else {
        row.productName = product.productName
        row.specification = product.specification
        row.unitPrice = product.unitPrice
      }
      this.refreshAmount(row)
    },
    refreshAmount(row) {
      const price = Number(row.unitPrice || 0)
      const quantity = Number(row.quantity || 0)
      row.amount = (price * quantity).toFixed(2)
      this.recalculateTotal()
    },
    recalculateTotal() {
      const total = this.form.orderItemList.reduce((sum, item) => {
        return sum + Number(item.unitPrice || 0) * Number(item.quantity || 0)
      }, 0)
      this.form.totalAmount = total.toFixed(2)
    },
    syncCustomerName(value) {
      const customer = this.customerOptions.find(item => item.customerId === value)
      this.form.customerName = customer ? customer.customerName : null
    },
    submitForm() {
      if (!this.form.orderItemList.length) {
        this.$modal.msgError('请至少添加一个产品')
        return
      }
      if (this.form.orderItemList.some(item => !item.productId)) {
        this.$modal.msgError('订单明细中存在未选择产品的数据')
        return
      }
      this.$refs.form.validate(valid => {
        if (!valid) {
          return
        }
        const payload = {
          orderId: this.form.orderId,
          customerId: this.form.customerId,
          remark: this.form.remark,
          orderItemList: this.form.orderItemList.map(item => ({
            productId: item.productId,
            quantity: item.quantity
          }))
        }
        const request = this.form.orderId ? updateOrder(payload) : addOrder(payload)
        request.then(() => {
          this.$modal.msgSuccess(this.form.orderId ? '修改成功' : '新增成功')
          this.open = false
          this.getList()
        })
      })
    },
    handleDelete(row) {
      this.$modal.confirm('确认删除订单“' + row.orderNumber + '”吗？').then(() => {
        return delOrder(row.orderId)
      }).then(() => {
        this.$modal.msgSuccess('删除成功')
        this.getList()
      }).catch(() => {})
    },
    openStatusDialog(row) {
      this.currentRow = row
      this.statusForm = {
        targetStatus: null,
        remark: null,
        autoCreateTask: true
      }
      this.statusOpen = true
      this.$nextTick(() => this.resetForm('statusForm'))
    },
    submitStatusChange() {
      this.$refs.statusForm.validate(valid => {
        if (!valid) {
          return
        }
        changeOrderStatus(this.currentRow.orderId, this.statusForm).then(() => {
          this.$modal.msgSuccess('状态流转成功')
          this.statusOpen = false
          this.getList()
        })
      })
    },
    handleGenerateTask(row) {
      this.$prompt('可填写任务备注', '生成生产任务单', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        inputPlaceholder: '请输入备注（可选）'
      }).then(({ value }) => {
        return generateProductionTask(row.orderId, value)
      }).then(() => {
        this.$modal.msgSuccess('生产任务单已生成')
        this.getList()
      }).catch(() => {})
    },
    handleExport() {
      this.download('timber/order/export', {
        ...this.addDateRange(this.queryParams, this.dateRange)
      }, `timber_order_${new Date().getTime()}.xlsx`)
    },
    normalizeOrder(data) {
      const detail = data || {}
      detail.orderItemList = (detail.orderItemList || []).map(item => ({
        ...item,
        amount: (Number(item.unitPrice || 0) * Number(item.quantity || 0)).toFixed(2)
      }))
      detail.totalAmount = Number(detail.totalAmount || 0).toFixed(2)
      return detail
    },
    nextStatusOptions(row) {
      const next = NEXT_STATUS_MAP[row.orderStatus]
      if (!next) {
        return []
      }
      return STATUS_OPTIONS.filter(item => item.value === next)
    },
    canEdit(row) {
      return ['draft', 'pending_review'].includes(row.orderStatus)
    },
    canDelete(row) {
      return !['outbound', 'completed'].includes(row.orderStatus)
    },
    canCancel(row) {
      return row && !['outbound', 'completed', 'canceled'].includes(row.orderStatus)
    },
    canGenerateTask(row) {
      return ['approved', 'contract_signed', 'in_production', 'produced'].includes(row.orderStatus) && !row.productionTaskId
    },
    statusLabel(value) {
      const target = STATUS_OPTIONS.find(item => item.value === value)
      return target ? target.label : ''
    },
    taskStatusLabel(value) {
      const map = {
        pending: '待生产',
        in_progress: '生产中',
        completed: '已完成',
        canceled: '已取消'
      }
      return map[value] || ''
    },
    formatMoney(value) {
      return value === null || value === undefined || value === '' ? '0.00' : Number(value).toFixed(2)
    },
    stepIndex(status) {
      const index = this.statusFlow.findIndex(item => item.value === status)
      return index < 0 ? 0 : index + 1
    },
    calcItemAmount(row) {
      return Number(row.unitPrice || 0) * Number(row.quantity || 0)
    }
  }
}
</script>

<style scoped>
.order-page .text-muted {
  color: #909399;
}

.item-card,
.detail-block {
  margin-bottom: 16px;
}

.card-header,
.total-bar {
  display: flex;
  align-items: center;
  justify-content: space-between;
}

.total-bar {
  padding-top: 14px;
  font-size: 14px;
}

.task-item + .task-item {
  margin-top: 12px;
  padding-top: 12px;
  border-top: 1px solid #ebeef5;
}

.task-title {
  font-weight: 600;
  margin-bottom: 6px;
}

.task-meta {
  color: #606266;
  line-height: 1.7;
}
</style>
