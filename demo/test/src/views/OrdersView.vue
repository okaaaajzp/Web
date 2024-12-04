<template>
	<div class="collection" style="margin: 0;padding: 0;line-height: normal;min-height: 80vh;">
        <el-row style="min-height: 80vh;width: 100%;">
            <el-col :span="24" style="min-height: 80vh;border-radius: 8px;">
                <el-row :gutter="20" class="o-row" v-for="item in Data" :key="item.id">
                    <el-col>
                        <el-image class="my-image"
                            :src="'http://8.134.217.250:8001/file/'+item.img">
                        </el-image>
                    </el-col>
                    <el-col class="o-text">{{ item.cName }}*{{ item.num }}</el-col>
                    <el-col class="o-text">
                      {{ item.intro }}
                    </el-col>
                    <el-col class="o-text">
                      {{ item.address }}
                    </el-col>
                    <el-col style="display: flex; justify-content: center; align-items: center;" class="o-text price">￥{{item.price}}</el-col>
                    <el-col style="display: flex; justify-content: center; align-items: center;" class="o-text">{{state[item.state]}}</el-col>
                    
                    <el-col style="display: flex; justify-content: center; align-items: center;">
                      <el-button type="success" v-if="item.state != 3 && type === 1" @click="access(item)">确认收货</el-button>
                      <el-button type="success" v-if="item.state === 1 && type === 2" @click="sendOut(item)">发货</el-button>
                    </el-col>
                </el-row>
            </el-col >
        </el-row>
	</div>
</template>

<script>
import request from '@/utils/request'


export default {
   created() {
    this.getOrders();
  },
  data() {
    return{
        user: JSON.parse(sessionStorage.getItem('user')),
        Data: [],
        state: ["","待发货","配送中","已完成"],
        type: this.$route.query.param,
        
    }
  },
  methods: {
    getOrders(){
        request.get('/orders/getMyOrders/'+this.user.id+'/'+this.type).then(res=>{
            if(res.code === 0){
                this.Data = res.data;
                this.Data.sort(function(a,b){
                  return a.id < b.id ? 1 : -1;
                });
            }
        })
    },
    sendOut(item){
      request.post('/orders/sendOut/'+item.id+'/'+this.user.id).then(res=>{
        if(res.code === 0){
          this.$message.success("已发货");
          item.state=2;
        }
      })
    },
    access(item){
      request.post('/orders/access/'+item.id+'/'+this.user.id).then(res=>{
        if(res.code === 0){
          this.$message.success("已确认收货");
          item.state=3;
        }
      })
    }
  }
}

</script>

<style>
    .checkbox{
        margin: 0;
        padding: 0;
    }
    .price{
      color: #ffc800;
    }
    .el-row::before,.el-row::after{
      content: none;
    }
    .el-card__body{
      max-height: 100%;
      width: 100%;
      margin: 0;
      padding: 0;
    }
   .my-image {
      width: 100%; 
      height: 96%; 
      object-fit: fill;
      margin: 0;
      padding-top: 0;
      display: inline-block;
      vertical-align: middle;
      pointer-events: auto;
  }
   .o-text {
    margin-top: 5%; 
    margin-left: 5%;
    padding: 10px;
    word-break: break-all;
    word-wrap: break-word;
    white-space: pre-line;
    line-height: 1.5em;
    text-align: left;
    pointer-events: auto;
  }
  .o-row {
    margin-left: 0px;
    margin-right: 0px;
    margin-top: 0;
    margin-bottom: 0;
    width: 100%;
    height: 20vh;
    display: grid;
    gap: 1%;
    grid-auto-flow: row;
    grid-template-columns: 6fr 6fr 10fr 10fr 4fr 6fr 6fr;
  }
  .o-col {
    background-color: rgb(255, 255, 255);
    border-radius: 4px;
    margin-top: 4%;
    pointer-events: auto;
    height: 100%;
  }
  .grid-content {
    border-radius: 4px;
    min-height: 36px;
  }
  .row-bg {
    padding: 10px 0;
    background-color: #f9fafc;
  }
  .clickable-div {
    cursor: pointer;
    transition: transform 0.2s;
    align-items: center;
    display: flex;
    pointer-events: auto;
    width: 100%;
    margin: 0;
    padding: 0;
  }
.clickable-div:hover {
  transform: scale(1.05);
  }
  .clear-button{
    font-size: large;
    color: #fff; /* 字体颜色 */
    border-radius: 200px; 
    background-color: #ff5100;
  }
  .clear-button:hover {
    outline: none; /* 去掉默认的聚焦轮廓 */
    background-color: #ff5100; /* 聚焦时的背景颜色 */
    transform: scale(1.05); /* 聚焦时的放大效果 */
    color: #fff;
  }
</style>