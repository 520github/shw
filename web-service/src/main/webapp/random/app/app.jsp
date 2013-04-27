<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/common/taglibs.jsp" %>
<!DOCTYPE HTML>
<html>
<head>
<meta content="text/html; charset=UTF-8" http-equiv="content-type">
<meta content="width=241, initial-scale=0.5, user-scalable=no" name="viewport"/>
<style>
*{margin:0; padding:0;}
.clearfix:after{content:".";display:block;height:0;clear:both;visibility:hidden;}
.clearfix{*zoom:1;}
body{font:300 14px/25px "微软雅黑", Arial, Helvetica, sans-serif;color:#333;background:#000;width:241px;height:281px;overflow-x:hidden;overflow-y:hidden;}
.warp{width:241px;height:281px;overflow:hidden;position:relative;background:#fff;}
.warp dl{overflow:hidden;height:140px;}
.warp dl dt{float:left;width:75px; position:relative;}
.warp dl dt img{border-radius:14px; box-shadow: 0 1px 1px #666666;}
.warp dl dt b{ position:absolute;top:0;left:0;}
.warp dl dt p{text-align:center;}
.warp dl dt img{width:75px;}
.warp dl dd h2{font-size:20px;font-weight:700;margin:0 0 6px 0;word-wrap: break-word;height:75px;}
.warp dl dd{float:right;width:158px;}
.warp dl dd .star_box{float:right;margin:6px 14px 0 0;text-align:right;}
.warp dl dd .star{width:75px;height:14px;background:url(${ctx}/images/star02.png) repeat-x 0 0;margin:6px 4px 8px 0;float:right;}
.warp dl dd .star i{ display:block;height:14px;background:url(${ctx}/images/star.png) repeat-x 0 0;}
.warp article{color:#7f7f88;height:24px;overflow:hidden;margin:6px 0 0 0;}
.warp .pics{text-align:center;margin:0;height:100px;overflow:hidden;padding:6px 0 0 0;} 
.warp .pics img{width:223px;margin:6px auto;}
.warp .button{display:block;width:70px;height:30px;margin:4px auto 0;line-height:30px;font-weight:700;text-align:center;color:#fff;font-size:16px;border-radius:6px;box-shadow:inset 1px 1px 2px #a28d51;border:none;background-image: -webkit-gradient(
linear,
left bottom,
left top,
color-stop(0.20, #ffa81f),
color-stop(0.20, #ffa712),
color-stop(1, #ffde80)
); text-decoration:none;}
.img_box{width:75px;height:75px;overflow:hidden;}
</style>
<script type="text/javascript">
function testAuto(thisId,needLeng){
var ididid = document.getElementById(thisId);
var nowLeng = ididid.innerHTML.length;
if(nowLeng > needLeng){
var nowWord = ididid.innerHTML.substr(0,needLeng)+'...';
ididid.innerHTML = nowWord;
}
}
</script>
<title></title>
</head>

<body>
<div class="warp">
   <dl class="clearfix">
     <dt><div class="img_box"><img src="${app.logo}"></div>
     <p>${app.price}</p>
     <a href="view://app/${app.id}" class="button">去看看</a>
     </dt>
     <dd>
     	<h2 id="ididid">${app.name}</h2>
        <div class="star_box"><div class="star"><i style="width:${app.wholeVersionScore.star/5*75}px;"></i></div>${app.wholeVersionScore.vote}</div>
     </dd>
   </dl>
   <article>${app.introduction}</article>
   <div class="pics">
   <img src="${ctx}/images/pic.png"/>
</div>
<script type="text/javascript">
testAuto('ididid',20)
</script>
</body>
</html>
