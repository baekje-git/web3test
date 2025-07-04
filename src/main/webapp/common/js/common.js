/**
 * HTML UI 관련 동적 스크립트
 * @returns
 */
$(top.document).scroll( function() {/*console.log('scroll');*/ if($(this).scrollTop() > 50) {$('.ever-top',top.document).fadeIn();} else {$('.ever-top',top.document).fadeOut();}});
$(document).ready(function() {
    
    /**
     * 서브텝 제어 및 서브텝 컨텐츠 제어
     */
    $(document).on("click" ,".tab > ul > li", function(e) {
        $(".tab > ul > li").removeClass("on");
        $(this).addClass("on");
        var tabIndex = $(this).index();
        if($(".tab_content > ul > li").length > 0) {
            $(".tab_content > ul > li").removeClass("on").hide();
            $(".tab_content > ul > li:eq("+tabIndex+")").addClass("on").show();
            window.top.fnSetFrameAutoHeight();
        }
    });
    $(".tab > ul > li.on", document).trigger("click");
    
    
    /**
     * 스크롤 탑
     */
    $('.ever-top',top.document).on("click",function(){
        console.log('click');
        var edge = navigator.userAgent.toLowerCase().search("edge/")>-1?true:false;
        if(edge) {
            $(top.document.body).animate({ scrollTop:0 }, 300);
        } else {
            $('html',top.document).animate({ scrollTop:0 }, 300);
        }
    });
    
});

