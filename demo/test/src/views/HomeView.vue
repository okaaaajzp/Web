<template>
  <div class="home" >
    <el-container>
      <el-header>
        <el-menu :default-active="activeIndex" class="el-menu-demo" mode="horizontal" @select="handleSelect" >
          
          <el-submenu index="1">
            <template slot="title"">{{user.name}}</template>
            <el-menu-item index="1-1" @click="dialogVisible2 = true">账号充值</el-menu-item>
            <el-menu-item index="1-2" @click="logout">退出登录</el-menu-item>
            <el-menu-item index="1-3" @click="dialogVisible = true">注销账号</el-menu-item>
          </el-submenu>
          <el-menu-item index="2" @click="router('product')">首页</el-menu-item>
          <el-submenu index="3">
            <template slot="title">分类</template>
            <el-menu-item v-for="item in type" :key="item" @click="router('type',item.id)">{{ item.name }}</el-menu-item>
          </el-submenu>
          <el-submenu index="4" v-if="user.role">
            <template slot="title" v-if="user.role === 1">商家销售管理</template>
            <el-menu-item index="4-1" @click="router('orders',2)" v-if="user.role === 1">商家订单管理</el-menu-item>
            <el-menu-item index="4-2" @click="router('MyProduct')" v-if="user.role === 1">商家商品管理</el-menu-item>
            <el-menu-item index="4-2" @click="router('table')" v-if="user.role === 1">销售报表</el-menu-item>
            <el-menu-item index="4-3" @click="router('log')" v-if="user.role === 1">日志</el-menu-item>
          </el-submenu>
          <el-submenu index="5">
            <template slot="title"">用户相关</template>
            <el-menu-item index="5-1" @click="router('collection')">购物车</el-menu-item>
            <el-menu-item index="5-2" @click="router('orders',1)">我的订单</el-menu-item>
          </el-submenu>
          
          
        </el-menu>
      </el-header>
      <el-main>
        <router-view/>
      </el-main>
    </el-container>
    <el-dialog
      title="注销账号"
      :visible.sync="dialogVisible"
      width="30%">
      <el-input
        placeholder="请确认密码"
        v-model="input"
        type="password"
        style="width:60%;  padding: 0;line-height: normal;"
        clearable>
      </el-input>
      <span slot="footer" class="dialog-footer">
      <el-button @click="dialogVisible = false">取 消</el-button>
      <el-button type="primary" @click="logOff()">确 定</el-button>
      </span>
    </el-dialog>
    <el-dialog
      title="账号充值"
      :visible.sync="dialogVisible2"
      width="30%">
      <el-select v-model="value" placeholder="请选择金额">
        <el-option
          v-for="item in options"
          :key="item.value"
          :label="item.label"
          :value="item.value">
        </el-option>
      </el-select>
      <span slot="footer" class="dialog-footer">
      <el-button @click="dialogVisible = false">取 消</el-button>
      <el-button type="primary" @click="recharge(value)">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import request from '@/utils/request'


export default {
  name: 'HomeView',
   created() {
    if(sessionStorage.getItem('user')==null) this.$router.push('/login');
    this.getType();
  },
  data() {
    return{
      input:"",
      user: JSON.parse(sessionStorage.getItem('user')),
      type: [],
      dialogVisible: false,
      dialogVisible2: false,
      options: [{
          value: '10',
          label: '10元'
        }, {
          value: '100',
          label: '100元'
        }, {
          value: '1000',
          label: '1000元'
        }, {
          value: '10000',
          label: '10000元'
        }],
        value:0,
    }
  },
  methods: {
    router(name,type=""){
      this.$router.push('/')
      this.$nextTick(()=>{
        this.$router.push({name: name,query:{
         param:type,
       }});
      })
    },
    logout(){
      sessionStorage.removeItem('user');
      this.$router.push('/login');
    },
    logOff(){
      if(this.input != '')
      request.post('/user/logOff/'+this.user.id+'/'+this.input).then(res=>{
        if(res.code === 0) this.logout();
        else{
          this.dialogVisible = false;
          this.$message.error(res.msg);
        }
      })
      else this.$message.error("请输入密码");
    },
    recharge(value){
      request.post('/user/recharge/'+this.user.id+'/'+value).then(res=>{
        if(res.code === 0){
          this.user.balance = res.data;
          sessionStorage.setItem('user',JSON.stringify(this.user));
          this.$message.success("充值成功");
        }
        this.dialogVisible2 = false;
        value = 0;
      })
      
    },
    getType(){
      request.get('/user/getType').then(res=>{
        if(res.code === 0){
          this.type = res.data;
        }
      })
    }
  }
}
</script>

<style>
  .el-header, .el-footer {
    background-color: #ffffff;
    color: #333;
    text-align: left;
    line-height: 60px;
    margin-left: 2%;
    margin-right: 2%;
    background-color: rgba(0,0,0,0);
  }
  
  .el-main {
    background-color: #ffffff;
    color: #333;
    text-align: center;
    line-height: 160px;
    margin-top: 2%;
    margin-left: 1%;
    margin-right: 1%;
    min-height: 80vh;
    border-radius: 2vh;
  }
  
  body > .el-container {
    margin-bottom: 40px;
    height: 100vh;
  }
  
  .el-container:nth-child(5) .el-aside,
  .el-container:nth-child(6) .el-aside {
    line-height: 260px;
  }
  
  .el-container:nth-child(7) .el-aside {
    line-height: 320px;
  }
  .grid-content {
    border-radius: 4px;
    min-height: 36px;
  }
  .el-menu{
    background-color: #ffffff;
    border-radius: 2vh;
  }
  html::before {
    content: '';
    width: 100%;
    height: 100%;
    position: fixed;
    z-index: -1;
    background-size:cover;
    background-color: #d3dce6;
  }
</style>
