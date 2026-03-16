<template>
  <div class="app-container home">
    <el-row :gutter="20">
      <el-col :sm="24" :lg="12" style="padding-left: 20px">
        <h2>木材管理系统</h2>
        <p>
          这是一个面向木材业务的管理系统，用于管理客户、产品、订单、生产和库存流程，覆盖木材企业日常经营场景。
        </p>
        <p>
          <b>当前版本:</b> <span>v1.0.0</span>
        </p>
        <p>
          <el-tag type="success">木材业务管理平台</el-tag>
        </p>
      </el-col>

      <el-col :sm="24" :lg="12" style="padding-left: 50px">
        <el-row>
          <el-col :span="12">
            <h2>技术选型</h2>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="6">
            <h4>后端技术</h4>
            <ul>
              <li>SpringBoot</li>
              <li>Spring Security</li>
              <li>JWT</li>
              <li>MyBatis</li>
              <li>Druid</li>
              <li>Fastjson</li>
            </ul>
          </el-col>
          <el-col :span="6">
            <h4>前端技术</h4>
            <ul>
              <li>Vue</li>
              <li>Vuex</li>
              <li>Element UI</li>
              <li>Axios</li>
              <li>Sass</li>
              <li>Quill</li>
            </ul>
          </el-col>
        </el-row>
      </el-col>
    </el-row>

    <el-divider />

    <el-row :gutter="20">
      <el-col :xs="24" :sm="12" :lg="6">
        <el-card class="overview-card">
          <div class="overview-title">系统定位</div>
          <div class="overview-value">木材业务全流程管理</div>
        </el-card>
      </el-col>
      <el-col :xs="24" :sm="12" :lg="6">
        <el-card class="overview-card">
          <div class="overview-title">核心模块</div>
          <div class="overview-value">客户 / 产品 / 订单 / 生产 / 库存</div>
        </el-card>
      </el-col>
      <el-col :xs="24" :sm="12" :lg="6">
        <el-card class="overview-card">
          <div class="overview-title">适用场景</div>
          <div class="overview-value">木材加工、板材贸易、仓储出入库</div>
        </el-card>
      </el-col>
      <el-col :xs="24" :sm="12" :lg="6">
        <el-card class="overview-card">
          <div class="overview-title">默认账号</div>
          <div class="overview-value">admin / admin123</div>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script>
export default {
  name: 'Index'
}
</script>

<style scoped lang="scss">
.home {
  font-family: 'open sans', 'Helvetica Neue', Helvetica, Arial, sans-serif;
  font-size: 13px;
  color: #676a6c;
  overflow-x: hidden;

  ul {
    padding: 0;
    margin: 0;
    list-style-type: none;
  }

  h4 {
    margin-top: 0;
  }

  h2 {
    margin-top: 10px;
    font-size: 26px;
    font-weight: 100;
  }

  p {
    margin-top: 10px;

    b {
      font-weight: 700;
    }
  }

  .overview-card {
    margin-bottom: 20px;
    min-height: 122px;
  }

  .overview-title {
    font-size: 14px;
    color: #909399;
    margin-bottom: 14px;
  }

  .overview-value {
    font-size: 18px;
    line-height: 1.7;
    color: #303133;
    font-weight: 600;
  }
}
</style>
