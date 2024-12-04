<template>
	<div class="collection" style="margin: 0;padding: 0;line-height: normal;min-height: 80vh;">
        <el-row style="min-height: 80vh;width: 100%;">
            <el-col :span="16" style="min-height: 80vh;border-radius: 8px;">
              <el-checkbox v-model="isChecked" @change="allSelect()">全选</el-checkbox>
                <el-row :gutter="20" class="c-row" v-for="item in Data" :key="item.id">
                    <el-col style="display: flex; justify-content: center; align-items: center;">
                        <el-checkbox v-model="item.checked" @change="changeSelect()"></el-checkbox>
                    </el-col>
                    <el-col>
                        <el-image class="my-image"
                            :src="'http://8.134.217.250:8001/file/'+item.img">
                        </el-image>
                    </el-col>
                    <el-col class="c-text">{{ item.name }}</el-col>
                    <el-col style="display: flex; justify-content: center; align-items: center;" class="price c-text">￥{{item.price}}</el-col>
                    <el-col style="display: flex; justify-content: center; align-items: center;">
                        <el-input-number style="width: 100%;" size="small" v-model="item.num" @change="getSum" :min="1" :max="99" label=""></el-input-number>
                    </el-col>
                    <el-col style="display: flex; justify-content: center; align-items: center;">
                      <el-popconfirm
                        title="确定把该商品移出购物车吗" @confirm="myDelete(item.id)">
                        <el-button type="danger" icon="el-icon-delete" circle slot="reference"></el-button>
                      </el-popconfirm>
                    </el-col>
                </el-row>
            </el-col >
            <el-col :span="8">
                <h2>已选商品数：{{ num }}</h2>
                <span>合计总价：</span>
                <h3 class="price">￥{{sum}}</h3>
                <el-button class="clear-button" style="width: 40%;" @click="clear">购买</el-button>
                <!-- <span>{{this.user}},{{ this.orders }}</span> -->
            </el-col>
        </el-row>
        <el-dialog
          title="购买"
          :visible.sync="dialogVisible"
          width="30%">
          <h3>本次共购买{{ num }}件商品</h3>
          <h3>共计：{{ sum }}元</h3>
          <h3>(点击确定表示确认购买并付款)</h3>
          <el-input
            placeholder="请输入地址"
            v-model="input"
            style="width:60%;  padding: 0;line-height: normal;"
            clearable>
          </el-input>
          <span slot="footer" class="dialog-footer">
            <el-button @click="dialogVisible = false">取 消</el-button>
            <el-button type="primary" @click="buyAll">确 定</el-button>
          </span>
        </el-dialog>
	</div>
</template>

<script>
import request from '@/utils/request';
export default {
    data() {
      return {
        Data: [],
        empty: '',
        user: JSON.parse(sessionStorage.getItem('user')),
        radio: 0,
        isChecked: false,
        sum: 0,
        num: 0,
        dialogVisible: false,
        orders: [],
        input: '',
      }
    },
    created(){
      this.getAll();
    },
    methods:{
      getAll(){
        request.get('/collection/get/'+this.user.id).then(res => {
        if(res.code === 0){
          this.Data = res.data.map(item=>({
            ...item,
            checked: false,
            num: 1,
            allow: true,
          }))
        }
        else{
          this.$message.error('错误');
        }
      })
      },
      select(item){
      },
      allSelect(){
        if(this.allow === false) return ;
        this.allow = false;
        for(let i=0;i<this.Data.length;++i){
          this.Data[i].checked = this.isChecked;
        }
        if(this.isChecked === true) this.num = this.Data.length;
        else this.num = 0;
        this.getSum();
        this.allow = true;
      },
      changeSelect(){
        if(this.allow === false) return ;
        this.allow = false;
        let k = true;
        let num =0;
        for(let i=0;i<this.Data.length;++i){
          k = this.Data[i].checked && k;
          num = num + this.Data[i].checked;
        }
        this.isChecked = k;
        this.num = num;
        this.getSum();
        this.allow = true;
      },
      getSum(){
        let sum = 0;
        for(let i = 0;i<this.Data.length;++i ){
          if(this.Data[i].checked == true){
            sum = sum + this.Data[i].num*this.Data[i].price;
          }
        }
        this.sum = sum;
      },
      myDelete(id){
        console.log(this.user.id,id);
        
        request.delete('/collection/delete/'+this.user.id+'/'+id).then(res=>{
          if(res.code === 0){
            let arr = this.Data;
            let id1 = arr.findIndex((arr) => arr.id == id);
            this.Data.splice(id1, 1);
            //this.$message.success("删除成功");
          }
        })
      },
      clear(evt) { // 按钮点击事件的回调函数
        if(this.num > 0) 
        {
          this.dialogVisible = true;
        }
        else this.$message.error("请选择至少一件商品");
        let target = evt.target;
        if(target.nodeName == "SPAN"){
            target = evt.target.parentNode;
        }
        target.blur();
      },
      buyAll(){
        if(Number(this.sum) > Number(this.user.balance)) {
          this.$message.error("账号余额不足");
          return;
        }
        let user = this.user.id;
        let orders = [];
        for(let i = 0;i<this.Data.length;++i ){
          if(this.Data[i].checked == true){
            let order = {'userId':user,'cId':this.Data[i].id,'num':this.Data[i].num,'sellerId':this.Data[i].sellerId,'address':this.input};
            this.myDelete(this.Data[i].id);
            orders.push(order);
          }
        }
        request.post('/orders/buyAll',orders).then(res=>{
          if(res.code === 0){
            this.user.balance = res.data;
            sessionStorage.setItem('user',JSON.stringify(this.user));
            this.$message.success("购买成功");
          }
        })
        this.num = 0;
        this.sum = 0;
        this.dialogVisible = false;
      },
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
   .c-text {
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
  .c-row {
    margin-left: 0px;
    margin-right: 0px;
    margin-top: 0;
    margin-bottom: 0;
    width: 100%;
    height: 20vh;
    display: grid;
    gap: 1%;
    grid-auto-flow: row;
    grid-template-columns: 1fr 6fr 10fr 10fr 6fr 6fr;
  }
  .c-col {
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