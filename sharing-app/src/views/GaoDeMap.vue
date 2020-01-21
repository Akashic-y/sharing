<template>
  <div>
    <div class="info">
      <h4 id='status'></h4><hr>
      <p id='result'></p><hr>
      <p >由于众多浏览器已不再支持非安全域的定位请求，为保位成功率和精度，请升级您的站点到HTTPS。</p>
    </div>
    <div id='container'></div>
    <div id="r-result">
      <input type="button" @click="show" value="显示地理图" />
      <input type="button" @click="hide" value="隐藏" />
    </div>
  </div>
</template>

<script>
  import AMap from 'AMap'
  import {addAddress} from '@/api/address'
  export default {
    name: 'gaodeMap',
    mounted() {
      this.getAddressByGaode();
    },
    data() {
      return {
        map:null,
        satellite:new AMap.TileLayer.Satellite(),
      }
    },
    methods: {
      getAddressByGaode(){
        let that = this;
        let map = new AMap.Map('container', {
          resizeEnable: true
        });
        this.map = map;
        AMap.plugin('AMap.Geolocation', function() {
          var geolocation = new AMap.Geolocation({
            enableHighAccuracy: true,//是否使用高精度定位，默认:true
            timeout: 10000,          //超过10秒后停止定位，默认：5s
            buttonPosition:'RB',    //定位按钮的停靠位置
            buttonOffset: new AMap.Pixel(10, 20),//定位按钮与设置的停靠位置的偏移量，默认：Pixel(10, 20)
            zoomToAccuracy: true,   //定位成功后是否自动调整地图视野到定位点

          });
          map.addControl(geolocation);
          geolocation.getCurrentPosition(function(status,result){
            if(status=='complete'){
              that.onComplete(result)
            }else{
              that.onError(result)
            }
          });
        });
      },
      //显示地理图
      show(){
        this.satellite.setMap(this.map);
        this.satellite.show();
      },
      //隐藏地理图
      hide(){
        this.satellite.hide();
      },
      //解析定位结果
      onComplete(data) {
        document.getElementById('status').innerHTML='定位成功';
        var str = [];
        str.push('定位结果：' + data.position);
        str.push('定位类别：' + data.location_type);
        if(data.accuracy){
          str.push('精度：' + data.accuracy + ' 米');
        }//如为IP精确定位结果则没有精度信息
        str.push('是否经过偏移：' + (data.isConverted ? '是' : '否'));
        document.getElementById('result').innerHTML = str.join('<br>');
        addAddress({position:data.position}).then((data => {
        })).catch(error => {

        })
      },
      //解析定位错误信息
      onError(data) {
        document.getElementById('status').innerHTML='定位失败';
        document.getElementById('result').innerHTML = '失败原因排查信息:'+data.message;
      },
    },
  }
</script>
<style scoped>
  #container{
    height:500px;
    width: 1000px;
  }
  .info{
    width:26rem;
    padding: .75rem 1.25rem;
    margin-bottom: 1rem;
    border-radius: .25rem;
    top: 1rem;
    background-color: white;
    min-width: 22rem;
    border-width: 0;
    box-shadow: 0 2px 6px 0 rgba(114, 124, 245, .5);
  }
</style>
