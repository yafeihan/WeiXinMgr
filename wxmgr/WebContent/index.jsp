<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
    String path = request.getContextPath();
			String basePath = request.getScheme() + "://"
					+ request.getServerName() + ":" + request.getServerPort()
					+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">

<title>My JSP 'index.jsp' starting page</title>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
<script src="http://code.jquery.com/jquery-1.8.0.min.js"></script>
</head>

<body>
	This is my JSP page.
	<br>
	<input type="button" onclick="onClick2();" value="测试"><br><br>
	<!--  <input type="button" onclick="getVerifyCode();" value="获取验证码"><br><br>
	<input type="button" onclick="login();" value="登录"><br><br>-->
</body>

<script>
	function getVerifyCode() {
		var url = "/shop_3.7_api/gjwfx/";
		url = url + "getVerifyCode";//临时TOKEN获取用户信息
		sendAjax(url);
	}

	function login() {
		var url = "/shop_3.7_api/gjwfx/";
		url = url + "login";//临时TOKEN获取用户信息
		sendAjax(url);
	}
	function onClick() {
		var url = "/shop_3.7_api/wfxtest/getLoginBanner";
		var data = {};
		sendAjax(url,data);
	}
	function onClick2() {
		var url = "/shop_3.7_api/wfxtest/";
		//url = "http://10.10.2.178:8085/wfx/";
		//url =url+ "test";//测试
		//url =url+ "oauthWechat";//测试
		//url = url+ "getOrderList";//订单列表
		//url = url+ "getOrderDetail";//订单详情
		//url = url+ "refuseDelivery";//拒绝发货
		//url = url+ "deliverGoods";//发货
		//url = url+ "replenishmentSign";//补签
		//url = url+ "login";//前端登录
		//url = url+ "regApplicantInfo";//申请人注册
		//url = url+ "getBusinessCardInfo";//我的名片
		//url = url+ "setAddress";//修改住址
		//url = url+ "setSuperiorNumber";//修改上级  逻辑修改
		//url = url+ "getRewardRecordList";//奖励纪录接口   逻辑修改
		//url = url+ "pointRoll";//积分转出  
		//url = url+ "getSuperiorInfo";//获取上级信息
		//url = url+ "getMenuInfo";//主菜单页面信息
		//url = url+ "getTeamMemberList";//团队成员列表 avatar  region customCount
		//url = url+ "getAuditMemberList";//待审核团队成员列表  逻辑问题
		//url = url+ "setMemberRemarks";//设置团队成员备注接口
		//url = url+ "qualificationRelieve";//解除代表资格接口
		//url = url+ "teamSearchList";//搜索团队成员列表
		//url = url+ "qualificationRequest";//通过资格审核接口
		//url = url+ "getTeamSortList";//团队成员排名接口
		//url = url+ "getPerformanceDetail";//绩效详情接口
		//url = url+ "getCustomerList";//客户成员列表 region key dateOfBirth
		//url = url+ "setCustomerRemarks";//客户备注
		//url = url+ "getLoginBanner";//商城测试
		//url = url+ "setCustomerRecord";//客户跟进记录接口
		//url = url+ "getVerifyCode";//验证码
		//url = url+ "uploadImg";//上传图片
		//url = url + "getUserInfo";//临时TOKEN获取用户信息
		//url = url + "getSign";//临时TOKEN获取用户信息
		//url = "/shop_3.7_api/test/wxBandPhone";
		url = url + "getPhoneInfo";//手机号获取用户信息
		
		sendAjax(url);
	}

	
	function   _getSignData(mainData){
        var platform = "IOS";
        var data = {
            data:mainData,
            ipAddress:"192.168.1.1",
            platform:platform,//platform:device.platform,  //cordova 获取设备名称方法
            timestamp:new Date().getTime(),
            version:"1.20",
        }
        data.sign = _getSignString(data);
        return data;
    }
	
	function  _getSignString(data){
        var temp = "";
        for(key in data){
            var value = data[key];
            if(key == "data"){
                value = JSON.stringify(value);
                console.log(value);
            }
            temp = temp + key + "=" + value;
            temp = temp + "&";
        }
        console.log(temp);
        temp = temp.substr(0,temp.length-1);
        return md5(encodeURIComponent(temp));
    }
	
	function sendAjax(url,data) {

		var data = {
			"data" : {},
			"ipAddress" : "192.168.1.1",
			"platform" : "Android",
			"timestamp" : 1461649629176,
			"version" : "1.1",
			"sign" : "82c559b95ef32044ec8d985cfbc9dd66"
		//"sign": "6d70f0a125a4873d9ab1a55871b07df9"
		};
		
		//data = _getSignData(JSON.stringify(data));
		data = JSON.stringify(data);

		$.ajax({
			type : 'POST',
			url : url,
			data : data,
			dataType : 'json',
			contentType : "application/json; charset=utf-8",
			//contentType : "application/x-www-form-urlencoded;charset=utf-8",
			success : function(data) {
				if (console) {
					console.log('--success:-data:', data);
				}

			},
			error : function(data, state, xhr) {
				if (console) {
					console.log('--error:-data:', data, '-state:', state,
							'-xhr:', xhr);
				}
			}
		});
	}
</script>
</html>
