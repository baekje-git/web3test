<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <script type="text/javascript">
		/* vue에 등록된 이벤트리스너를 호출 */
		alert("123");
		console.log(parent.window.top);
		console.log(window.top);
		window.top.postMessage(
		    // 전달할 data (부모창에서 호출할 함수명)
		    { functionName : 'inicisComplete' }
		    // 부모창의 도메인
		    , 'http://localhost:3050'
		    // 모든 도메인에 대하여 허용 ( 추천하지 않음 )
		    //, '*'
		);
    </script>
</head>
<body>
</body>
</html>