<%@page import="com.clipsoft.clipreport.oof.connection.OOFConnectionMemo"%>
<%@page import="com.clipsoft.clipreport.oof.OOFFile"%>
<%@page import="com.clipsoft.clipreport.oof.OOFDocument"%>
<%@page import="java.io.File"%>
<%@page import="com.clipsoft.clipreport.server.service.ReportUtil"%>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String content0   = request.getParameter("content0");
String fileNm 	   = request.getParameter("fileNm");
String eformDir	   = request.getParameter("eformDir");
if(eformDir.equals("null") || eformDir==null || eformDir.equals("")){
	eformDir ="/file/eform/";
}
OOFDocument oof = OOFDocument.newOOF();
OOFFile file = oof.addFile("crfe.root", eformDir + "/haccp_comp_form.crfe");  //crfe 경로 변경해야함~~

OOFConnectionMemo memo = file.addConnectionMemo("content0", content0);
memo.addContentParamJSON("*", "utf-8", "{%dataset.json.root%}");	

%><%@include file="Property.jsp"%><%
//세션을 활용하여 리포트키들을 관리하지 않는 옵션
//request.getSession().setAttribute("ClipReport-SessionList-Allow", false);
String resultKey =  ReportUtil.createEForm(request, oof, "false", "false", request.getRemoteAddr(), propertyPath);
//리포트의 특정 사용자 ID를 부여합니다.
//clipreport5.properties 의 useuserid 옵션이 true 일 때만 적용됩니다. 
//clipreport5.properties 의 useuserid 옵션이 true 이고 기본 예제[String resultKey =  ReportUtil.createEForm(request, oof, "false", "false", request.getRemoteAddr(), propertyPath);] 사용 했을 때 세션ID가 userID로 사용 됩니다.
//String resultKey =  ReportUtil.createEForm(request, oof, "false", "false", request.getRemoteAddr(), propertyPath, "userID");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Haccp 컨설팅</title>
<meta name="viewport" content="width=800, user-scalable=no">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<link rel="stylesheet" type="text/css" href="./css/clipreport.css">
<link rel="stylesheet" type="text/css" href="./css/eform.css">
<link rel="stylesheet" type="text/css" href="./css/UserConfig.css">
<link rel="stylesheet" type="text/css" href="./css/font.css">
<script type='text/javascript' src='./js/jquery-1.11.1.js'></script>
<script type='text/javascript' src='./js/clipreport.js?ver=1.0'></script>
<script type='text/javascript' src='./js/UserConfig.js'></script>
<script type='text/javascript'>
var eform
$(document).ready(function () {
	var urlPath = document.location.protocol + "//" + document.location.host;
	var eformkey = "<%=resultKey%>";
	var fileNm   = "<%=fileNm%>";
	eform = createImportJSPEForm(urlPath + "/Clip.jsp", eformkey, document.getElementById('targetDiv1'));

	eform.setStyle('save_button',   'display:inline;left:550px');
	eform.setStyle('close_button',  'display:none;');
	eform.setStyle('memo_button',   'display:none;');
	eform.setStyle('doodle_button', 'display:none;');
	eform.setStyle("print_button","display:inline;left:600px");
	eform.setStyle('pdf_button', 'display:none;');
	eform.setNecessaryEnabled(true);
	eform.setClientPaintView(true);
	eform.view();
	eform.setEndReportEvent(function(){
		if(eform.findControl("Inputbox20").getValue() == '1'){
			var Calendar1 = eform.findControl("Calendar1");
			var Calendar2 = eform.findControl("Calendar2");
			var Inputbox = eform.findControl("Inputbox23");
			var date = Inputbox.getValue();
			var year = date.substr(0,4);
			var month = date.substr(4,2);
			var day = date.substr(6,2);
			Calendar1.setValue(year, month, day);
			Calendar2.setValue(year, month, day);

			var cont61 = eform.findControl("Inputbox6");
			var Calendar3 = eform.findControl("Calendar3");
			var date1 = cont61.getValue();
			var year1 = date1.substr(0,4);
			var month1 = date1.substr(4,2);
			var day1 = date1.substr(6,2); 
			Calendar3.setValue(year, month, day);
			
		}
		
	})
	
	

	var isSaved = true;

	var test = '1'
	eform.setEndSaveButtonEvent(function (){
		if(test != '1'){
				test = '1'
				return false
		}

		var values = eform.getEFormData();

		var msg;
		if(values.Inputbox20.data[0] == '1'){
			if(values.clipsign2.data[0] == ''){
				msg = '저장하시겠습니까?';
			}else{
				msg = '고객에게 최종 요청됩니다.';
			}
		}else if(values.Inputbox20.data[0] == '2'){
			msg = '최종 확인 하시겠습니까?';
		}else if(values.Inputbox20.data[0] == '3'){
			msg = 'PDF로 저장하시겠습니까?';
		}
		
		if(confirm(msg)) {
			
			if(values.Inputbox20.data[0] == '1'){
				if(values.Calendar1.data.length > 0) {
					if(values.Calendar1.data[0] != values.Calendar2.data[0]) {
						alert('완료일자가 일치해야 합니다.');
						eform.pageMove(1);
						$('#loading').hide();
						return false;
					}
				}
			}
			if(values.Inputbox20.data[0] == '2'){
				if(values.clipsign1.data[0] == ''){
					alert('고객 확인자 서명란에 서명을 해야합니다.');
					eform.pageMove(1);
					$('#loading').hide();
					return false;
				}
			}
			
			
			console.log(eform.getEFormData())
			isSaved = false;
			
			var resultVal = {};
			$('#loading').show();

			if(values.Inputbox21.data[0] == 'Y' && values.Inputbox22.data[0] == 'Y'){
				//pdf다운로드
				//eform.pdfDownLoad();
				eform.saveFileDownLoad("완료확인서", 3,2);
				//return false;
			}

			if(values.clipsign1.data[0] != ''){
				$.ajax({
						type 	: "POST",
						url 	: "exportForPartPDF.jsp", //같은경로에 위치
						data 	: {"report_key":eform.getReportKey(), "fileNm" : fileNm},
						async 	: false,
						cache 	: false,
						success : function(result){
							var resultVal =  result.trim();
							var resultJson  = JSON.parse(resultVal);
							console.log(resultJson)
							resultJson.values = values;
							resultJson.type = 1;
							//isSaved = true;
							window.parent.postMessage({ resultJson : resultJson}, 'http://localhost:8080/consulting/consult/hcComplete');
							window.open('', '_self', '');
							window.close();
								
						}
					});
			}else{
				var resultJson = {};
				console.log(resultJson)
				resultJson.errorCode = '0';
				resultJson.values = values;
				resultJson.type = 1;
				window.parent.postMessage({ resultJson : resultJson}, 'http://localhost:8080/consulting/consult/hcComplete');
				window.open('', '_self', '');
				window.close();							
			}

			test = test + 1;
		}
	});

});

</script>
</head>
<body>
<div id='targetDiv1' style='position:absolute;top:5px;left:5px;right:5px;bottom:5px;'></div>
</body>
</html>