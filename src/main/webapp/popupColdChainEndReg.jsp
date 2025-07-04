<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="ko"> 
<head>
    <meta http-equiv="content-type" content="text/html; charset=utf-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
    <meta name="format-detection" content="telephone=no">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, user-scalable=no, target-densitydpi=device-dpi" />
    <title>백제약품 콜드체인 배송종료 처리 시스템</title>
</head>
<style type="text/css">
    #findInfo {position:absolute; top:3px; left:4px;}
</style>
<script type="text/javascript">
$(document).ready(function(){
});
</script>
<body>
    <div id="wrap">
        <div id="container">
            <div id="contents">
                <form name="infoFrm" id="infoFrm">
                    <div class="layout_box">
                        <p class="comment" style="font-size: 18px; padding: 0 20px; margin-top: 50px; font-weight: bold; color: #333; text-align: center;">${CUST_NM}</p>
                        <p class="comment" style="text-align: center; margin-bottom: 50px;">${MSG}</p>
                        <div class="table_box">
                            <table summary="" class="form">
                                <caption></caption>
                                <colgroup>
                                    <col width="30%" />
                                    <col width="70%" />
<!--                                     <col width="40%" /> -->
                                </colgroup>
                                <tbody>
                                    <tr>
                                        <th scope="row">거래처</th>
                                        <td>"${CUST_NM}"</td>
                                    </tr>
                                    <tr>
                                        <th scope="row">장기번호</th>
                                        <td>"${SN}"</td>
                                    </tr>
                                    <tr>
                                        <th scope="row">온도계</th>
                                        <td>"${THERMO_ID}"</td>
                                    </tr>
                                    <tr>
                                        <th scope="row">박스아이디</th>
                                        <td>"${BOX_ID}"</td>
                                    </tr>
                                </tbody>
                            </table>
                            
                        </div>
                    </div>
                    <div class="btn_wrap grid_bottom center" style="position:relative;" id="viewFile">
                    </div> 
                </form>
            </div>
        </div>
    </div>
</body>
</html>
