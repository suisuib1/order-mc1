<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="产品名称" prop="productName">
        <el-input
          v-model="queryParams.productName"
          placeholder="请输入产品名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="产品种类" prop="category">
        <el-input
          v-model="queryParams.category"
          placeholder="请输入产品种类"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
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
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
        >删除</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="productList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="产品ID" align="center" prop="productId" />
      <el-table-column label="产品名称" align="center" prop="productName" />
      <el-table-column label="种类" align="center" prop="category" />
      <el-table-column label="规格" align="center" prop="spec" />
      <el-table-column label="价格" align="center" prop="price" />
      <el-table-column label="库存" align="center" prop="stock" />
      <el-table-column label="备注" align="center" prop="remark" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
          >修改</el-button>
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

    <!-- 添加或修改产品对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="产品名称" prop="productName">
          <el-input v-model="form.productName" placeholder="请输入产品名称" />
        </el-form-item>
        <el-form-item label="种类" prop="category">
          <el-input v-model="form.category" placeholder="请输入种类" />
        </el-form-item>
        <el-form-item label="规格" prop="spec">
          <el-input v-model="form.spec" placeholder="请输入规格" />
        </el-form-item>
        <el-form-item label="价格" prop="price">
          <el-input-number v-model="form.price" :precision="2" :step="0.1" placeholder="请输入价格"></el-input-number>
        </el-form-item>
        <el-form-item label="库存" prop="stock">
          <el-input-number v-model="form.stock" placeholder="请输入库存"></el-input-number>
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-input v-model="form.remark" type="textarea" placeholder="请输入内容" />
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
import { listProduct, getProduct, delProduct, addProduct, updateProduct } from "@/api/timber/product";

export default {
  name: "Product",
  data() {
    return {
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 产品表格数据
      productList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        productName: null,
        category: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        productName: [
          { required: true, message: "产品名称不能为空", trigger: "blur" }
        ],
        price: [
          { required: true, message: "价格不能为空", trigger: "blur" }
        ],
        stock: [
          { required: true, message: "库存不能为空", trigger: "blur" }
        ]
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询产品列表 */
    getList() {
      this.loading = true;
      // 模拟数据
      this.productList = [
        {
          productId: 1,
          productName: "松木原木",
          category: "原木",
          spec: "直径20-30cm，长度4m",
          price: 800.00,
          stock: 100,
          remark: ""
        },
        {
          productId: 2,
          productName: "橡木板材",
          category: "板材",
          spec: "2cm厚，30cm宽，2m长",
          price: 1500.00,
          stock: 200,
          remark: "优质橡木"
        }
      ];
      this.total = this.productList.length;
      this.loading = false;
      /*
      listProduct(this.queryParams).then(response => {
        this.productList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
      */
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        productId: null,
        productName: null,
        category: null,
        spec: null,
        price: null,
        stock: null,
        createBy: null,
        createTime: null,
        updateBy: null,
        updateTime: null,
        remark: null
      };
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.productId)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加产品";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const productId = row.productId || this.ids
      // 模拟获取数据
      this.form = this.productList.find(item => item.productId === productId);
      this.open = true;
      this.title = "修改产品";
      /*
      getProduct(productId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改产品";
      });
      */
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.productId != null) {
            // 模拟修改
            const index = this.productList.findIndex(item => item.productId === this.form.productId);
            if (index !== -1) {
              this.productList.splice(index, 1, this.form);
            }
            this.$modal.msgSuccess("修改成功");
            this.open = false;
            this.getList();
            /*
            updateProduct(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
            */
          } else {
            // 模拟新增
            this.form.productId = new Date().getTime(); // 简单生成一个ID
            this.productList.push(this.form);
            this.$modal.msgSuccess("新增成功");
            this.open = false;
            this.getList();
            /*
            addProduct(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
            */
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const productIds = row.productId || this.ids;
      this.$modal.confirm('是否确认删除产品编号为"' + productIds + '"的数据项？').then(() => {
        // 模拟删除
        if (Array.isArray(productIds)) {
            this.productList = this.productList.filter(item => !productIds.includes(item.productId));
        } else {
            const index = this.productList.findIndex(item => item.productId === productIds);
            if (index !== -1) {
                this.productList.splice(index, 1);
            }
        }
        this.getList();
        this.$modal.msgSuccess("删除成功");
        /*
        return delProduct(productIds);
        */
      }).then(() => {
        /*
        this.getList();
        this.$modal.msgSuccess("删除成功");
        */
      }).catch(() => {});
    }
  }
};
</script>