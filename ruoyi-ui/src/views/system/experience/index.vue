<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="公司" prop="company">
        <el-input v-model="queryParams.company" placeholder="请输入公司名称" clearable @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button type="primary" plain icon="el-icon-plus" size="mini" @click="handleAdd" v-hasPermi="['system:experience:add']">新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="success" plain icon="el-icon-edit" size="mini" :disabled="single" @click="handleUpdate" v-hasPermi="['system:experience:edit']">修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="danger" plain icon="el-icon-delete" size="mini" :disabled="multiple" @click="handleDelete" v-hasPermi="['system:experience:remove']">删除</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="experienceList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="编号" align="center" prop="id" width="60" />
      <el-table-column label="公司/机构" align="center" prop="company" />
      <el-table-column label="职位" align="center" prop="position" />
      <el-table-column label="时间段" align="center" prop="period" width="150" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button size="mini" type="text" icon="el-icon-edit" @click="handleUpdate(scope.row)" v-hasPermi="['system:experience:edit']">修改</el-button>
          <el-button size="mini" type="text" icon="el-icon-delete" @click="handleDelete(scope.row)" v-hasPermi="['system:experience:remove']">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination v-show="total>0" :total="total" :page.sync="queryParams.pageNum" :limit.sync="queryParams.pageSize" @pagination="getList" />

    <el-dialog :title="title" :visible.sync="open" width="600px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="公司" prop="company">
          <el-input v-model="form.company" placeholder="请输入公司/机构名称" />
        </el-form-item>
        <el-form-item label="职位" prop="position">
          <el-input v-model="form.position" placeholder="请输入职位" />
        </el-form-item>
        <el-form-item label="时间段" prop="period">
          <el-input v-model="form.period" placeholder="如: 2022 - 至今" />
        </el-form-item>
        <el-form-item label="描述" prop="description">
          <el-input v-model="form.description" type="textarea" :rows="3" placeholder="请输入描述" />
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
import { listExperience, getExperience, delExperience, addExperience, updateExperience } from "@/api/system/experience";

export default {
  name: "Experience",
  data() {
    return {
      loading: true, ids: [], single: true, multiple: true, showSearch: true, total: 0,
      experienceList: [], title: "", open: false,
      queryParams: { pageNum: 1, pageSize: 10, company: null },
      form: {}, rules: { company: [{ required: true, message: "公司不能为空", trigger: "blur" }] }
    };
  },
  created() { this.getList(); },
  methods: {
    getList() {
      this.loading = true;
      listExperience(this.queryParams).then(response => {
        this.experienceList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    cancel() { this.open = false; this.reset(); },
    reset() { this.form = { id: null, company: "", position: "", period: "", description: "" }; this.resetForm("form"); },
    handleQuery() { this.queryParams.pageNum = 1; this.getList(); },
    resetQuery() { this.resetForm("queryForm"); this.handleQuery(); },
    handleSelectionChange(selection) { this.ids = selection.map(item => item.id); this.single = selection.length !== 1; this.multiple = !selection.length; },
    handleAdd() { this.reset(); this.open = true; this.title = "添加经历"; },
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids;
      getExperience(id).then(response => { this.form = response.data; this.open = true; this.title = "修改经历"; });
    },
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateExperience(this.form).then(response => { this.$modal.msgSuccess("修改成功"); this.open = false; this.getList(); });
          } else {
            addExperience(this.form).then(response => { this.$modal.msgSuccess("新增成功"); this.open = false; this.getList(); });
          }
        }
      });
    },
    handleDelete(row) {
      const ids = row.id || this.ids;
      this.$modal.confirm('是否确认删除经历编号为"' + ids + '"的数据项？').then(function() { return delExperience(ids); }).then(() => { this.getList(); this.$modal.msgSuccess("删除成功"); }).catch(() => {});
    }
  }
};
</script>
