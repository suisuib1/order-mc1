<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="作品标题" prop="title">
        <el-input v-model="queryParams.title" placeholder="请输入作品标题" clearable @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button type="primary" plain icon="el-icon-plus" size="mini" @click="handleAdd" v-hasPermi="['system:work:add']">新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="success" plain icon="el-icon-edit" size="mini" :disabled="single" @click="handleUpdate" v-hasPermi="['system:work:edit']">修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="danger" plain icon="el-icon-delete" size="mini" :disabled="multiple" @click="handleDelete" v-hasPermi="['system:work:remove']">删除</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="workList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="编号" align="center" prop="id" width="60" />
      <el-table-column label="作品标题" align="center" prop="title" :show-overflow-tooltip="true" />
      <el-table-column label="描述" align="center" prop="description" :show-overflow-tooltip="true" />
      <el-table-column label="链接" align="center" prop="link" width="150">
        <template slot-scope="scope">
          <a v-if="scope.row.link" :href="scope.row.link" target="_blank">查看</a>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button size="mini" type="text" icon="el-icon-edit" @click="handleUpdate(scope.row)" v-hasPermi="['system:work:edit']">修改</el-button>
          <el-button size="mini" type="text" icon="el-icon-delete" @click="handleDelete(scope.row)" v-hasPermi="['system:work:remove']">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination v-show="total>0" :total="total" :page.sync="queryParams.pageNum" :limit.sync="queryParams.pageSize" @pagination="getList" />

    <el-dialog :title="title" :visible.sync="open" width="700px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="标题" prop="title">
          <el-input v-model="form.title" placeholder="请输入标题" />
        </el-form-item>
        <el-form-item label="描述" prop="description">
          <el-input v-model="form.description" type="textarea" :rows="3" placeholder="请输入描述" />
        </el-form-item>
        <el-form-item label="标签" prop="tags">
          <el-input v-model="form.tags" placeholder="如: Vue,TypeScript (逗号分隔)" />
        </el-form-item>
        <el-form-item label="图片" prop="image">
          <el-input v-model="form.image" placeholder="请输入图片URL" />
        </el-form-item>
        <el-form-item label="链接" prop="link">
          <el-input v-model="form.link" placeholder="请输入作品链接" />
        </el-form-item>
        <el-form-item label="日期" prop="createdAt">
          <el-date-picker v-model="form.createdAt" type="date" placeholder="选择日期" value-format="yyyy-MM-dd" style="width: 100%" />
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
import { listWork, getWork, delWork, addWork, updateWork } from "@/api/system/work";

export default {
  name: "Work",
  data() {
    return {
      loading: true,
      ids: [],
      single: true,
      multiple: true,
      showSearch: true,
      total: 0,
      workList: [],
      title: "",
      open: false,
      queryParams: { pageNum: 1, pageSize: 10, title: null },
      form: {},
      rules: { title: [{ required: true, message: "标题不能为空", trigger: "blur" }] }
    };
  },
  created() { this.getList(); },
  methods: {
    getList() {
      this.loading = true;
      listWork(this.queryParams).then(response => {
        this.workList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    cancel() { this.open = false; this.reset(); },
    reset() {
      this.form = { id: null, title: "", description: "", tags: "", image: "", link: "", createdAt: null };
      this.resetForm("form");
    },
    handleQuery() { this.queryParams.pageNum = 1; this.getList(); },
    resetQuery() { this.resetForm("queryForm"); this.handleQuery(); },
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id);
      this.single = selection.length !== 1;
      this.multiple = !selection.length;
    },
    handleAdd() { this.reset(); this.open = true; this.title = "添加作品"; },
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids;
      getWork(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改作品";
      });
    },
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateWork(this.form).then(response => { this.$modal.msgSuccess("修改成功"); this.open = false; this.getList(); });
          } else {
            addWork(this.form).then(response => { this.$modal.msgSuccess("新增成功"); this.open = false; this.getList(); });
          }
        }
      });
    },
    handleDelete(row) {
      const ids = row.id || this.ids;
      this.$modal.confirm('是否确认删除作品编号为"' + ids + '"的数据项？').then(function() { return delWork(ids); }).then(() => { this.getList(); this.$modal.msgSuccess("删除成功"); }).catch(() => {});
    }
  }
};
</script>
