<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="技能名称" prop="name">
        <el-input v-model="queryParams.name" placeholder="请输入技能名称" clearable @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button type="primary" plain icon="el-icon-plus" size="mini" @click="handleAdd" v-hasPermi="['system:skill:add']">新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="success" plain icon="el-icon-edit" size="mini" :disabled="single" @click="handleUpdate" v-hasPermi="['system:skill:edit']">修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="danger" plain icon="el-icon-delete" size="mini" :disabled="multiple" @click="handleDelete" v-hasPermi="['system:skill:remove']">删除</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="skillList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="编号" align="center" prop="id" width="60" />
      <el-table-column label="技能名称" align="center" prop="name" />
      <el-table-column label="图标" align="center" prop="icon" width="100" />
      <el-table-column label="熟练度" align="center" prop="level" width="200">
        <template slot-scope="scope">
          <el-progress :percentage="scope.row.level" :color="'#409EFF'"></el-progress>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button size="mini" type="text" icon="el-icon-edit" @click="handleUpdate(scope.row)" v-hasPermi="['system:skill:edit']">修改</el-button>
          <el-button size="mini" type="text" icon="el-icon-delete" @click="handleDelete(scope.row)" v-hasPermi="['system:skill:remove']">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination v-show="total>0" :total="total" :page.sync="queryParams.pageNum" :limit.sync="queryParams.pageSize" @pagination="getList" />

    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="技能名称" prop="name">
          <el-input v-model="form.name" placeholder="如: Vue" />
        </el-form-item>
        <el-form-item label="图标" prop="icon">
          <el-input v-model="form.icon" placeholder="如: 💚" />
        </el-form-item>
        <el-form-item label="熟练度" prop="level">
          <el-slider v-model="form.level" :min="0" :max="100" show-input />
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
import { listSkill, getSkill, delSkill, addSkill, updateSkill } from "@/api/system/skill";

export default {
  name: "Skill",
  data() {
    return {
      loading: true, ids: [], single: true, multiple: true, showSearch: true, total: 0,
      skillList: [], title: "", open: false,
      queryParams: { pageNum: 1, pageSize: 10, name: null },
      form: {}, rules: { name: [{ required: true, message: "技能名称不能为空", trigger: "blur" }] }
    };
  },
  created() { this.getList(); },
  methods: {
    getList() {
      this.loading = true;
      listSkill(this.queryParams).then(response => {
        this.skillList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    cancel() { this.open = false; this.reset(); },
    reset() { this.form = { id: null, name: "", icon: "", level: 0 }; this.resetForm("form"); },
    handleQuery() { this.queryParams.pageNum = 1; this.getList(); },
    resetQuery() { this.resetForm("queryForm"); this.handleQuery(); },
    handleSelectionChange(selection) { this.ids = selection.map(item => item.id); this.single = selection.length !== 1; this.multiple = !selection.length; },
    handleAdd() { this.reset(); this.open = true; this.title = "添加技能"; },
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids;
      getSkill(id).then(response => { this.form = response.data; this.open = true; this.title = "修改技能"; });
    },
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateSkill(this.form).then(response => { this.$modal.msgSuccess("修改成功"); this.open = false; this.getList(); });
          } else {
            addSkill(this.form).then(response => { this.$modal.msgSuccess("新增成功"); this.open = false; this.getList(); });
          }
        }
      });
    },
    handleDelete(row) {
      const ids = row.id || this.ids;
      this.$modal.confirm('是否确认删除技能编号为"' + ids + '"的数据项？').then(function() { return delSkill(ids); }).then(() => { this.getList(); this.$modal.msgSuccess("删除成功"); }).catch(() => {});
    }
  }
};
</script>
