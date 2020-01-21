<template>
  <div>
  </div>
</template>

<script>
  export default {
    name: 'Address',
    mounted() {
      this.getAddressH5();
    },
    data() {
      return {
      }
    },

    methods: {
      getAddressH5(){
        if(navigator.geolocation){
          navigator.geolocation.getCurrentPosition(this.onSuccess , this.onError);
        }else{
          this.$message({type: 'error', message:"您的浏览器不支持使用HTML 5来获取地理位置服务"});
        }
      },

      //定位数据获取成功响应
      onSuccess(position){
        that.$message({type: 'success', message:
        '纬度: ' + position.coords.latitude + '\n' +
        '经度: ' + position.coords.longitude + '\n' +
        '海拔: ' + position.coords.altitude + '\n' +
        '水平精度: ' + position.coords.accuracy + '\n' +
        '垂直精度: ' + position.coords.altitudeAccura
        });
      },

      //定位数据获取失败响应
      onError(error) {
        let that = this;
        switch (error.code) {
          case error.PERMISSION_DENIED:
            that.$message({type: 'error', message:"您拒绝对获取地理位置的请求"});
            break;
          case error.POSITION_UNAVAILABLE:
              that.$message({type: 'error', message:"位置信息是不可用的"});
              break;
          case error.TIMEOUT:
            that.$message({type: 'error', message:"请求您的地理位置超时"});
            break;
          case error.UNKNOWN_ERROR:
            that.$message({type: 'error', message:"未知错误"});
            break;
        }
      },
    },
  }
</script>
<style scoped>
</style>
