
var tabs = $('.tab-a');
var selector = $('.tabs-a').find('a').length;
//var selector = $(".tabs").find(".selector");
var activeItem = tabs.find('.active');
var activeWidth = activeItem.innerWidth();
$(".selector").css({
  "left": activeItem.position.left + "px", 
  "width": activeWidth + "px"
});

$(".tabs-a").on("click","a",function(e){
  e.preventDefault();
  $('.tabs-a a').removeClass("active");
  $(this).addClass('active');
  var activeWidth = $(this).innerWidth();
  var itemPos = $(this).position();
  $(".selector").css({
    "left":itemPos.left + "px", 
    "width": activeWidth + "px"
  });
  if($(this)[0].id == "guia-b"){
    $(".turista-disp").css("display", "none");
    $(".guia-disp").css("display", "inherit");
    $(".turista-disp").removeClass("active");
    $(".guia-first").addClass('active');
  }else{
    $(".turista-disp").css("display", "inherit");
    $(".guia-disp").css("display", "none");
    $(".turista-first").addClass("active");
    $(".guia-disp").removeClass('active');
  }
});
