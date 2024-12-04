<template>
	<div class="current" >
        <el-row class="cur-row">
            <el-col class="cur-col"><div class="grid-content">
                <el-image
                    style="height: 400px; width:100%; object-fit: contain;"
                    :src="'http://8.134.217.250:8001/file/'+product.img"
                    fit="contain">
                </el-image>
                
            </div></el-col>
            <el-col><div class="grid-content cur-col">
                <h2 class="intro-text">{{ product.name }}</h2>
                <p class="intro-text" style="white-space: normal;">{{ product.introduce }}</p>
                <h3 class="intro-text" style="color: #ffc800;">￥{{ product.price }}</h3>
                <el-input-number style="width: 20%;" v-model="num" @change="handleChange" :min="1" :max="99" label="描述文字"></el-input-number>
                <el-button-group style="margin-top: 40px; width: 100%; display: flex; justify-content: center;">
                    <el-button class="custom-button buy-button" style="width: 40%;" @click="clickBuy">立即购买</el-button>
                    <el-button class="custom-button add-button" style="width: 40%;" @click="clickAdd">加入购物车</el-button>
                </el-button-group>
            </div></el-col>
        </el-row>
        <el-dialog
          title="购买"
          :visible.sync="dialogVisible"
          width="30%">
          <h3>本次购买商品：{{ product.name }}{{ num }}件</h3>
          <h3>共计：{{ product.price*num }}元</h3>
          <h3>(点击确定表示确认购买并付款)</h3>
          <el-input
            placeholder="请输入地址"
            v-model="input"
            style="width:60%;  padding: 0;line-height: normal;"
            clearable>
          </el-input>
          <span slot="footer" class="dialog-footer">
            <el-button @click="dialogVisible = false">取 消</el-button>
            <el-button type="primary" @click="buy()">确 定</el-button>
          </span>
        </el-dialog>
	</div>
</template>

<script>
import request from '@/utils/request';
export default {
    data() {
      return {
        product: this.$route.query.param,
        user: JSON.parse(sessionStorage.getItem('user')),
        num: 1,
        dialogVisible: false,
        input: "",
      }
    },
    created(){
    },
    methods:{
      clickBuy(evt) { // 按钮点击事件的回调函数
        this.dialogVisible = true;
        let target = evt.target;
        if(target.nodeName == "SPAN"){
            target = evt.target.parentNode;
        }
        target.blur();
      },
      clickAdd(evt) { // 按钮点击事件的回调函数
        this.addCollcetion();
        let target = evt.target;
        if(target.nodeName == "SPAN"){
            target = evt.target.parentNode;
        }
        target.blur();
      },
      addCollcetion(){
        request.post('/collection/add/'+this.user.id+'/'+this.product.id).then(res=>{
          if(res.code === 0) this.$message.success("已加入购物车");
          else{
            this.$message.error("加入失败");
          }
        })
      },
      buy(){
        let sum = this.num * this.product.price;
        if(Number(sum) > Number(this.user.balance)){
          this.$message.error("账户余额不足");
          return;
        }
        let order={'userId':this.user.id,'num':this.num,'cId':this.product.id,'sellerId':this.product.sellerId,'address':this.input};
        request.post('/orders/add',order).then(res=>{
          if(res.code === 0){
            this.user.balance = res.data;
            sessionStorage.setItem('user',JSON.stringify(this.user));
            this.$message.success('购买成功');
          }
          else this.$message.error(res.msg);
        })
        this.dialogVisible = false;
      }
    }
}

</script>


<style>
    .custom-button {
        font-size: large;
        color: #fff; /* 字体颜色 */
        border-radius: 200px; 
    }
    .buy-button {
        background-color: #ff5100; /* 主按钮颜色 */
    }
    .buy-button:hover {
        outline: none; /* 去掉默认的聚焦轮廓 */
        background-color: #ff5100; /* 聚焦时的背景颜色 */
        transform: scale(1.05); /* 聚焦时的放大效果 */
        color: #fff;
    }
    .add-button {
        background-color: #ffa200; /* 主按钮颜色 */
    }
    .add-button:hover {
        outline: none; /* 去掉默认的聚焦轮廓 */
        background-color: #ffa200; /* 聚焦时的背景颜色 */
        transform: scale(1.05); /* 聚焦时的放大效果 */
        color: #fff;
    }
   .intro-text {
        margin: 0;
        padding: 10px;
        word-break: break-all;
        word-wrap: break-word;
        white-space: pre-line;
        line-height: 1.5em;
        text-align: left;
        pointer-events: auto;
  }
  .cur-row {
    margin-bottom: 20px;
    padding-left: 40px;
    padding-right: 40px;
    display: flex;
    &:last-child {
      margin-bottom: 0;
    }
  }
  .cur-col {
    background-color: rgb(255, 255, 255);
    border-radius: 4px;
    margin-top: 20px;
    pointer-events: auto;
  }
  .bg-purple-dark {
    background: #99a9bf;
  }
  .bg-purple {
    background: #d3dce6;
  }
  .bg-purple-light {
    background: #e5e9f2;
  }
  .grid-content {
    border-radius: 4px;
    min-height: 36px;
  }
  .row-bg {
    padding: 10px 0;
    background-color: #f9fafc;
  }
  .current{
    margin: 0px;
    padding: 0;
    line-height: normal;
    height: 100%;
  }
</style>