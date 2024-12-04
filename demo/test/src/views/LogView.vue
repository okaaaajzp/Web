<template>
	<div class="log" style="margin: 0;padding: 0;line-height: normal;min-height: 80vh;">
        <el-table
            :data="tableData"
            stripe
            style="width: 100%">
            <el-table-column
            prop="userName"
            label="用户"
            width="180">
            </el-table-column>
            <el-table-column
            prop="action"
            label="操作"
            width="180">
            </el-table-column>
            <el-table-column
            prop="cName"
            label="商品">
            </el-table-column>
        </el-table>
	</div>
</template>

<script>
import request from '@/utils/request'


export default {
   created() {
    this.getMyLog();
  },
  data() {
    return{
      user: JSON.parse(sessionStorage.getItem('user')),
      tableData: []
    }
  },
  methods: {
    getMyLog(){
      console.log("/user/getMyLog/"+this.user.id);
      request.get("/user/getMyLog/"+this.user.id).then(res=>{
        if(res.code === 0){
          this.tableData = res.data;
        }
      })
    }
  }
}

</script>
