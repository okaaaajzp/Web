<template>
	<div class="log" style="margin: 0;padding: 0;line-height: normal;min-height: 80vh;">
        <el-table
            :data="tableData"
            stripe
            style="width: 100%">
            <el-table-column
            prop="name"
            label="商品"
            width="180">
            </el-table-column>
            <el-table-column
            prop="num"
            label="订单量"
            width="180">
            </el-table-column>
            <el-table-column
            prop="sum"
            label="销售额">
            </el-table-column>
        </el-table>
	</div>
</template>

<script>
import request from '@/utils/request'


export default {
   created() {
    this.getAll();
  },
  data() {
    return{
      user: JSON.parse(sessionStorage.getItem('user')),
      tableData: []
    }
  },
  methods: {
    getAll(){
        request.post('/orders/table/' + this.user.id).then(res=>{
            if(res.code === 0){
                this.tableData = res.data;
            }
        })
    }
  }
}

</script>
