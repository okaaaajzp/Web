<template>
	<div class="product" style="margin: 0;padding: 0;line-height: normal;">
    <el-input
      placeholder="请输入内容"
      v-model="input"
      @keydown.enter.native="getInput"
      style="width:600px;  padding: 0;line-height: normal;"
      clearable>
    </el-input>
		<el-row class="pr-row">
      <el-col class="pr-col" v-for="item in Data" :key="item.id" @click="handleClick()">
        <el-card @click.native="handleClick(item)" class="clickable-div" >
          <el-image class="card-image"
            :src="'http://8.134.217.250:8001/file/'+item.img"
            fit="contain">
          </el-image>
          <h2 class="pr-text">{{ item.name }}</h2>
          <h5 class="pr-text">{{ item.introduce }}</h5>
          <h3 class="pr-text price">￥{{ item.price }}</h3>
        </el-card>
      </el-col>
    </el-row>
    <el-pagination
      :curren-page="pageNo"
      :page-size="pageSize"
      @current-change="handleCurrentChange"
      :pager-count="11"
      layout="prev, pager, next"
      :total="totalCount">
    </el-pagination>
	</div>
</template>

<script>
import request from '@/utils/request';
export default {
    data() {
      return {
        user: JSON.parse(sessionStorage.getItem('user')),
        Data: [],
        input: '',
        pageNo: 1,
        pageSize: 8,
        totalCount: 0,
        name: '',
      }
    },
    created(){
      this.search();
    },
    methods:{
      handleClick(item){
        let myLog = {'userId': this.user.id,'sellerId': item.sellerId,'cId': item.id,'action': '浏览'};
        request.post('/orders/log',myLog).then();
        this.$router.push({name:'current',query:{
          param: item
        }});
      },
      getAll(){
        
        request.get('/commodities/findAll/'+this.pageNo+'/'+this.pageSize).then(res => {
        if(res.code === 0){
          this.Data=res.data.list;
          this.totalCount=res.data.total;
        }
        else{
          this.$message.error('错误');
        }
      })
      },
      handleCurrentChange(val) { 
        this.pageNo = val;
        this.search();
      },
      search(){
        request.get('/commodities/search/'+this.pageNo+'/'+this.pageSize+'?name='+this.name).then(res => {
        if(res.code === 0){
          this.Data=res.data.list;
          this.totalCount=res.data.total;
        }
        else{
        }
      })
      
      },
      getInput(){
        this.name = this.input;
        this.search();
      }
    }
}

</script>

<style>
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
   .card-image {
      width: 94%; 
      height: 30vh; 
      object-fit: contain;
      margin: 0;
      padding-top: 2%;
      display: inline-block;
      vertical-align: middle;
      pointer-events: auto;
  }
   .pr-text {
        width: 100%;
        margin: 0;
        padding-left: 3%;
        line-height: 1.5em;
        text-align: left;
        overflow: hidden;
        white-space: nowrap;
        text-overflow: ellipsis;
  }
  .pr-row {
    margin-bottom: 20px;
    padding-left: 5%;
    padding-right: 5%;
    width: 100%;
    display: grid;
    gap: 1%;
    grid-template-columns: repeat(4,24%);
  }
  .pr-col {
    background-color: rgb(255, 255, 255);
    border-radius: 4px;
    margin-top: 4%;
    pointer-events: auto;
    width: 100%;
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
</style>