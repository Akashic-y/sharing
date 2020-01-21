<template>
  <div>
    <div class="map">
      <div id="map-core"></div>
    </div>
    <div id="r-result">
      <input type="button" @click="add_control" value="添加" />
      <input type="button" @click="delete_control" value="删除" />
    </div>
  </div>
</template>

<script>
  import BMap from 'BMap'
  export default {
    name: 'Address',
    mounted() {
      this.getAddressByBaidu();
    },
    data() {
      return {
        map:null,
        /*缩放控件type有四种类型:
        BMAP_NAVIGATION_CONTROL_SMALL：仅包含平移和缩放按钮；
        BMAP_NAVIGATION_CONTROL_PAN:仅包含平移按钮；
        BMAP_NAVIGATION_CONTROL_ZOOM：仅包含缩放按钮*/
        top_left_control : new BMap.ScaleControl({anchor: BMAP_ANCHOR_TOP_LEFT}),// 左上角，添加比例尺
        top_left_navigation : new BMap.NavigationControl(),  //左上角，添加默认缩放平移控件
        mapType1 : new BMap.MapTypeControl(
          {
            mapTypes: [BMAP_NORMAL_MAP,BMAP_HYBRID_MAP],
            anchor: BMAP_ANCHOR_BOTTOM_RIGHT
          }
        ),
       overView : new BMap.OverviewMapControl(),
       overViewOpen : new BMap.OverviewMapControl({isOpen:true, anchor: BMAP_ANCHOR_BOTTOM_RIGHT}),
      }
    },
    methods: {
      getAddressByBaidu(){
        // 百度地图API功能
        var map = new BMap.Map("map-core");
        this.map = map;
        var point = new BMap.Point(116.331398,39.897445);
        map.centerAndZoom(point,12);
        map.enableScrollWheelZoom(true);//开启鼠标滚轮缩放
        map.disableDragging();     //禁止拖拽
        setTimeout(function(){
          map.enableDragging();   //两秒后开启拖拽
          //map.enableInertialDragging();   //两秒后开启惯性拖拽
        }, 2000);
        let that = this;
        var geolocation = new BMap.Geolocation();
        geolocation.getCurrentPosition(function(r){
          if(this.getStatus() == BMAP_STATUS_SUCCESS){
            var mk = new BMap.Marker(r.point);
            map.addOverlay(mk);
            map.panTo(r.point);
            that.$message({type: 'success',message:'您的位置：'+r.point.lng+','+r.point.lat});
          } else {
            that.$message({type: 'error',message:'failed'+this.getStatus()});
          }
        },{enableHighAccuracy: true})
        //关于状态码
        //BMAP_STATUS_SUCCESS	检索成功。对应数值“0”。
        //BMAP_STATUS_CITY_LIST	城市列表。对应数值“1”。
        //BMAP_STATUS_UNKNOWN_LOCATION	位置结果未知。对应数值“2”。
        //BMAP_STATUS_UNKNOWN_ROUTE	导航结果未知。对应数值“3”。
        //BMAP_STATUS_INVALID_KEY	非法密钥。对应数值“4”。
        //BMAP_STATUS_INVALID_REQUEST	非法请求。对应数值“5”。
        //BMAP_STATUS_PERMISSION_DENIED	没有权限。对应数值“6”。(自 1.1 新增)
        //BMAP_STATUS_SERVICE_UNAVAILABLE	服务不可用。对应数值“7”。(自 1.1 新增)
        //BMAP_STATUS_TIMEOUT	超时。对应数值“8”。(自 1.1 新增)


        // 添加定位控件
        var geolocationControl = new BMap.GeolocationControl();
        geolocationControl.addEventListener("locationSuccess", function(e){
          // 定位成功事件
          // var address = '';
          // address += e.addressComponent.province;
          // address += e.addressComponent.city;
          // address += e.addressComponent.district;
          // address += e.addressComponent.street;
          // address += e.addressComponent.streetNumber;
          // alert("当前定位地址为：" + address);
        });
        geolocationControl.addEventListener("locationError",function(e){
          // 定位失败事件
          alert(e.message);
        });
        var size = new BMap.Size(10, 20);
        map.addControl(new BMap.CityListControl({
          anchor: BMAP_ANCHOR_TOP_RIGHT,
          offset: size,
          // 切换城市之前事件
          // onChangeBefore: function(){
          //    alert('before');
          // },
          // 切换城市之后事件
          // onChangeAfter:function(){
          //   alert('after');
          // }
        }));
        map.addControl(geolocationControl);
      },
      //添加控件和比例尺
      add_control(){
        this.map.addControl(this.top_left_control);
        this.map.addControl(this.top_left_navigation);
        this.map.addControl(this.mapType1);          //2D图，混合图
        this.map.addControl(this.overView);          //添加默认缩略地图控件
        // this.map.addControl(this.overViewOpen);      //右下角，打开
      },
      //移除控件和比例尺
      delete_control(){
        this.map.removeControl(this.top_left_control);
        this.map.removeControl(this.top_left_navigation);
        this.map.removeControl(this.mapType1);   //移除2D图，混合图
        this.map.removeControl(this.overView);
        // this.map.removeControl(this.overViewOpen);
      },
    },
  }
</script>
<style scoped>
  .map {
    width: 1000px;
    height: 500px;
    font-size: 14px;
  }
  #map-core {
    width: 100%;
    height: 90%;
  }
</style>
