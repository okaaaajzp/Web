<template>
	<div class="Type" style="margin: 0;padding: 0;line-height: normal;">
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
        Data: [],
        input: '',
        type: this.$route.query.param,
        pageNo: 1,
        pageSize: 4,
        totalCount: 0,
      }
    },
    created(){
      this.getType();
      this.type = this.$route.query.param;
      sessionStorage.setItem('test',this.type);
    },
    methods:{
      handleClick(item){
        this.$router.push({name:'current',query:{
          param: item
        }});
      },
      getType(){
        request.get('/commodities/getByType/'+this.pageNo+'/'+this.pageSize+'/'+this.type).then(res => {
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
        this.getType();         
      },
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