<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <!DOCTYPE html>
    <html>
    <head>
        <meta charset="utf-8">
        <title>장소 표시</title>
        
    </head>
    <body>

    <div id="map" style="width:1450px;height:600px;"></div>
    
    <script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=8be39cd3b6e0f06201953edf0efadf31&libraries=services"></script>

    <!--광운대 37.619726, 127.058887 -->
    <script>
    var mapContainer = document.getElementById('map'), // 지도를 표시할 div 
        mapOption = {
            center: new kakao.maps.LatLng(37.619726, 127.058887), // 지도의 중심좌표
            level: 3 // 지도의 확대 레벨
        };  
    
    // 지도를 생성합니다    
    var map = new kakao.maps.Map(mapContainer, mapOption); 
    
    // 주소-좌표 변환 객체를 생성합니다
    var geocoder = new kakao.maps.services.Geocoder();
    
    // 주소로 좌표를 검색합니다
    geocoder.addressSearch('서울특별시 노원구 월계1동 409-16', function(result, status) {
    
        // 정상적으로 검색이 완료됐으면 
         if (status === kakao.maps.services.Status.OK) {
    
            var coords = new kakao.maps.LatLng(result[0].y, result[0].x);
    
            // 결과값으로 받은 위치를 마커로 표시합니다
            var marker = new kakao.maps.Marker({
                map: map,
                position: coords
            });
    
            // 인포윈도우로 장소에 대한 설명을 표시합니다
            var infowindow = new kakao.maps.InfoWindow({
                content: '<div style="width:150px;text-align:center;padding:6px 0;">머슬톡</div>'
            });
            infowindow.open(map, marker);
    
            // 지도의 중심을 결과값으로 받은 위치로 이동시킵니다
            //map.setCenter(coords);
        } 
    });    
    </script>
    </body>
    </html>

<!--
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8"/>
	<title>Kakao 지도 시작하기</title>
</head>
<body>
	<div id="map" style="width:500px;height:400px;"></div>
	<script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=8be39cd3b6e0f06201953edf0efadf31"></script>
	<script>
		var container = document.getElementById('map');
		var options = {
			center: new kakao.maps.LatLng(37.619726, 127.058887),
			level: 3
		};

		var map = new kakao.maps.Map(container, options);

        // 마커가 표시될 위치입니다 
        var markerPosition  = new kakao.maps.LatLng(37.619726, 127.058887); 

        // 마커를 생성합니다
        var marker = new kakao.maps.Marker({
            position: markerPosition
        });

        // 마커가 지도 위에 표시되도록 설정합니다
        marker.setMap(map);
        

        // 아래 코드는 지도 위의 마커를 제거하는 코드입니다
        // marker.setMap(null);    
	</script>
</body>
</html>
-->
<!--
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>주소로 장소 표시하기</title>

</head>
<body>

<div id="map" style="width:100%;height:350px;"></div>

<script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=8be39cd3b6e0f06201953edf0efadf31&libraries=services"></script>

<script>
var mapContainer = document.getElementById('map'),
    mapOption = {
        center: new kakao.maps.LatLng(33.450701, 126.570667),
        level: 3
    };

var map = new kakao.maps.Map(mapContainer, mapOption);


var geocoder = new kakao.maps.services.Geocoder();


geocoder.addressSearch('서울 송파구 올림픽로 240', function(result, status) {


     if (status === kakao.maps.services.Status.OK) {

        var coords = new kakao.maps.LatLng(result[0].y, result[0].x);


        var marker = new kakao.maps.Marker({
            map: map,
            position: coords
        });


        var infowindow = new kakao.maps.InfoWindow({
            content: '<div style="width:150px;text-align:center;padding:6px 0;">롯데월드</div>'
        });
        infowindow.open(map, marker);


        map.setCenter(coords);
    }
});
</script>
</body>
</html>
-->