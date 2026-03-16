<template>
  <div class="app-container">
    <el-form ref="form" :model="form" :rules="rules" label-width="100px">
      <el-row>
        <el-col :span="12">
          <el-form-item label="网站名称" prop="siteName">
            <el-input v-model="form.siteName" placeholder="请输入网站名称" />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="头像URL" prop="avatar">
            <el-input v-model="form.avatar" placeholder="请输入头像URL" />
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="24">
          <el-form-item label="网站描述" prop="siteDescription">
            <el-input v-model="form.siteDescription" type="textarea" :rows="2" placeholder="请输入网站描述" />
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="24">
          <el-form-item label="关于我" prop="about">
            <el-input v-model="form.about" type="textarea" :rows="6" placeholder="请输入关于我的内容，支持换行" />
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="8">
          <el-form-item label="邮箱" prop="email">
            <el-input v-model="form.email" placeholder="请输入邮箱" />
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="GitHub" prop="github">
            <el-input v-model="form.github" placeholder="请输入GitHub链接" />
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="掘金" prop="juejin">
            <el-input v-model="form.juejin" placeholder="请输入掘金链接" />
          </el-form-item>
        </el-col>
      </el-row>
      <el-form-item>
        <el-button type="primary" @click="submitForm">保存</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
import { getSettings, updateSettings } from "@/api/system/settings";

export default {
  name: "SiteSettings",
  data() {
    return {
      form: {},
      rules: {}
    };
  },
  created() { this.getInfo(); },
  methods: {
    getInfo() {
      getSettings().then(response => {
        this.form = response.data || {};
      });
    },
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          updateSettings(this.form).then(response => {
            this.$modal.msgSuccess("保存成功");
          });
        }
      });
    }
  }
};
</script>
