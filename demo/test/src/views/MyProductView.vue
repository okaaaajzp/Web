<template>
	<div class="MyProduct" style="margin: 0;padding: 0;line-height: normal;">
    <el-button type="text" @click="dialogVisible = true">新增商品</el-button>
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
    
    <el-dialog
    title="商品属性"
    :visible.sync="dialogVisible"
    width="30%"
    :before-close="handleclose">
    <el-row style="margin-top: 0px;" label="">
      <el-input type="text" v-model="editData.name" placeholder="商品名称" autocomplete="off" maxlength="16"></el-input>
    </el-row>
    <el-row style="margin-top: 20px;" label="">
      <el-input type="text" v-model="editData.price" placeholder="商品价格" autocomplete="off" maxlength="20"></el-input>
    </el-row>
      <el-row style="margin-top: 20px;" label="">
      <el-input type="text" v-model="editData.introduce" placeholder="商品介绍" autocomplete="off" maxlength="20"></el-input>
    </el-row>
    <el-row style="margin-top: 20px;">
        <el-select v-model="editData.typeId" placeholder="请选择商品类型" autocomplete="off" maxlength="20">
            <el-option
            v-for="item in types"
            :key="item.id"
            :label="item.name"
            :value="item.id">
            </el-option>
        </el-select>
    </el-row>
    <el-upload
        class="upload-demo"
        ref="upload"
        action="http://8.134.217.250:8001/file/upload"
        :on-preview="handlePreview"
        :on-remove="handleRemove"
        :file-list="fileList"
        :auto-upload="false"
        :on-change="handleChange"
        :limit="1"
        list-type="picture"
        style="margin-top: 20px;">
        <el-button size="small" type="primary">点击上传</el-button>
        <div slot="tip" class="el-upload__tip">只能上传jpg/png文件，且不超过500kb</div>
    </el-upload>
    <el-row slot="footer" class="dialog-footer" style="margin-top: 20px;">
        <el-button type="danger" v-if="editData.id!=null" @click="myDelete">删除</el-button>
        <el-button @click="dialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="edit()">确 定</el-button>
    </el-row>
    </el-dialog>
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
        dialogVisible: false,
        editData: {},
        types: [],
        file: {},
        fileList: [],
        pageNo: 1,
        pageSize: 8,
        totalCount: 0,
      }
    },
    created(){
        this.getMyProduct();
        this.getType();
    },
    methods:{
      handleClick(item){
        this.editData = {...item};
        this.dialogVisible = true;
      },
      getMyProduct(){
        request.get('/commodities/getBySeller/'+this.pageNo+'/'+this.pageSize+'/'+this.user.id).then(res => {
        if(res.code === 0){
          this.Data=res.data.list;
          this.totalCount=res.data.total;
        }
        else{
          this.$message.error('错误');
        }
      })
      },
      handleclose(){
        this.editData = {};
        this.file={};
        this.fileList=[];
        this.dialogVisible = false;
      },
      getType(){
        request.get('/user/getType').then(res=>{
          if(res.code === 0){
            this.types = res.data;
          }
        })
      },
      edit(){
        let fd = new FormData();
        if(this.fileList.length == 0) {
          this.editData.sellerId = this.user.id;
              request.post('/commodities/edit',this.editData).then(res=>{
                if(res.code === 0 ) this.$message.success("更改成功");
                this.dialogVisible = false;
                this.editData = {};
                this.getMyProduct();
              })
              this.fileList=[];
        }
        else {
          fd.append('file',this.fileList[0].raw);
          this.$axios.post('http://8.134.217.250:8001/file/upload',fd,{
            headers: {
              'token': this.user.token
            }
          }).then(res => {
              console.log(res);
              if(res.data.code === 0) {
                console.log("success");
                this.editData.img = res.data.data;
                this.editData.sellerId = this.user.id;
                console.log(this.editData);
                request.post('/commodities/edit',this.editData).then(res=>{
                  if(res.code === 0 ) this.$message.success("更改成功");
                  this.dialogVisible = false;
                  this.editData = {};
                  this.getMyProduct();
                })
                this.fileList=[];
              }
          
          })
        }
      },
      handleChange(file,fileList){
        this.fileList  = fileList;
      },
      myDelete(){
        request.delete('/commodities/delete/'+this.editData.id).then(res=>{
          if(res.code === 0) console.log("delete---"+this.editData.id);
          this.editData = {};
          this.getMyProduct();
          this.dialogVisible = false;
        })
        
        
      },
      handleCurrentChange(val) { 
        this.pageNo = val;   
        this.getMyProduct();         
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