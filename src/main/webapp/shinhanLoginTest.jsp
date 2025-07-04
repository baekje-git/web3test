<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" 
  "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
  <title>Shinhan Login Test</title> 
</head>
<body>

  <h2>신한 로그인 테스트</h2>

  <form method="POST" action="/shinhanLogin.act">
    <label>shinhanCust:</label>
    <input type="text" id="shinhanCust" name="shinhanCust" value="10001" />
    <br><br>

    <label>shinhanKey:</label>
    <input type="text" id="shinhanKey" name="shinhanKey" value="120e5ca1e41ec87b0135df3f49bbf15cf" />
    <br><br>

    <!-- ✅ 이 부분: Vue가 실행되면 값 넣어주도록 해둠 -->
    <input type="hidden" id="frontendUrl" name="frontendUrl" />

    <button type="submit">로그인 요청</button>
  </form>

  <!-- ✅ VUE_APP_FRONTEND_URL을 자동으로 심어주는 스크립트 -->
<script>
  const currentOrigin = window.location.origin;
  const frontendUrl = currentOrigin.replace(":1010", ":3050");
  document.getElementById("frontendUrl").value = frontendUrl;
</script>

</body>
</html>