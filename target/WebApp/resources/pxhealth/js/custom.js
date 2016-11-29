// JavaScript Document

//Home page Blog Slider
(function() { 
  // store the slider in a local variable
  var $window = $(window),
      flexslider;
 
  // tiny helper function to add breakpoints
  function getGridSize() {
   return (window.innerWidth < 470) ? 1 :
			(window.innerWidth < 800) ? 2 :
		   (window.innerWidth < 1400) ? 3 : 4;	
  }
  $window.load(function() {
    $('#homeblog').flexslider({
      animation: "slide",
      animationLoop: true,
      itemWidth: 357,
	  itemMargin: 35,
	   move: 1,  
      minItems: getGridSize(), // use function to pull in initial value
      maxItems: getGridSize() // use function to pull in initial value
    });
  }); 
  // check grid size on resize event
  $window.resize(function() {
    var gridSize = getGridSize();
 
    flexslider.vars.minItems = gridSize;
    flexslider.vars.maxItems = gridSize;
  });
}());

/*----------------Window Load---------------------*/

$(window).load(function() {
		
	//main Slider
	$('#banner').flexslider({
    	animation: "slide",
		start: function(slider){
          $('body').removeClass('loading');
        }
  	});
	$('#banner6').flexslider({
    	animation: "fade",
		start: function(slider){
          $('body').removeClass('loading');
		  
        }
  	});
	
	//main Slider 2
	$("#mainslider2").carouFredSel({
		responsive: true,
	items		: 1,
	scroll		: {
		fx			: "crossfade"
	},
	auto: false,
	//mousewheel: true,	
	swipe: {
		onMouse: true,
		onTouch: true
	},
	
	pagination	: {
		container		: "#mainslider2_pag",
		anchorBuilder	: function( nr ) {
			var src = $("img", this).attr( "src" );
				//src = src.replace( "/large/", "/small/");
			return '<img src="' + src + '" style="width:200px" />';
			}
		}
	});
	
	//Colorbox popup
	$(".group1").colorbox({
		 onComplete:function(){
			$( "#datepicker").datetimepicker(
 				{
					showButtonPanel: true
			});}		
		 });
	
	//Testimonial Slider
	$('#hometestimonial').flexslider({
    	animation: "slide",
		start: function(slider){
          $('body').removeClass('loading');
        }
  	})
	
	//Blog Page Slider
	$('#blogsider').flexslider({
    	animation: "slide",
		start: function(slider){
          $('body').removeClass('loading');
        }
  	})	
});


/* ----------------Document Ready---------------- */

$(document).ready(function() {
	
	//Navigation
	$('#nav-bar .nav li').hover(function()
	{		$(this).children('a').addClass('active')
			$(this).children('ul').fadeIn(0)			
		},
		function()
		{
			$(this).children('ul').fadeOut(0)
			$(this).children('a').removeClass('active')
		}
		);	
	$('.navbar-toggle').click(function()
	{	$(this).toggleClass('minus')
		$(this).next('ul.submenu').slideToggle()
		$('ul.navbar-toggle > li > a').click(function(e){
			e.isImmediatePropagationStopped()
			$(this).toggleClass('minus')				
			$(this).next('ul.submenu').slideToggle()
			})	
		})
	
	// Twitter
	/*$("#twitter").tweet({
	join_text: "auto",
	username: "twitter", //replace this with your username
	modpath: './twitter/',
	avatar_size: 32,
	count: 3,
	auto_join_text_default: "we said,",
	auto_join_text_ed: "we",
	auto_join_text_ing: "we were",
	auto_join_text_reply: "we replied",
	auto_join_text_url: "we were checking out",
	loading_text: "loading tweets..."
	});*/

	// Parallax animation 	
	$(window).bind('load', function () {
		windowwidht = $(window).width()
		if (windowwidht > 700){
			$('#doctorparallax').parallax("80%", 0.05);	
			}
		else{}
  		})
	
	
	//  Accrodian	
	$('.ans').hide()
	$('.que').first().addClass('active').next('.ans').show();
	$('.que').click(function()
	{
		if( $(this).next().is(':hidden') )
		{
			$('.que').removeClass('active').next('.ans').slideUp(300)
			$(this).toggleClass('active').next().slideDown(300);
		}	
	});
	
	//Youtube Steam
	$('.youtube-feed').socialstream({
		socialnetwork: 'youtube',
		limit: 12,
		username: 'youtube'
	})
	
	// google map	
	$("#contactmap").gMap({
		address: "Astra Healthcare Pvt Ltd, Opposite Western Plaza, Melbourne, O.U Colony, Manikonda, Hyderabad 500089,Telangana. India",//replace this with your address
		zoom: 10,
		markers:[
			{
				latitude: 17.4088943, //replace this with your latitude
				longitude: 78.3873666,//replace this with your longitude
				html: "Astra Healthcare Pvt Ltd" //replace this with your text
			}		
		]
	});

	// Datepicker
 	$( "#datepicker" ).datetimepicker(
 	{showButtonPanel: true});
	$( "#datepicker1" ).datetimepicker(
 	{showButtonPanel: true});
	
	

// Scroll top
	$(window).scroll(function () {
			if ($(this).scrollTop() > 50) {
				$('#gotop').fadeIn(500);
							
			} else {
				$('#gotop').fadeOut(500);
			}
		});	
	$('#gotop').click(function()
			{
				
				$("html, body").animate({ scrollTop: 0 }, 600);
			})
			
	// Tooltip	
	$('.tooltip-test').tooltip();
	
	// alert 
	$( '.alert .fa-times-circle').click(function()
	{
		$(this).parent('.alert').fadeOut(300)
	})
	
	// Toggle			
	$('.togglehandle').click(function()
	{
		$(this).toggleClass('active')
		$(this).next('.toggledata').slideToggle()
	});
	
	//  Accrodian	
	$("#accrodian").collapse({toggle: false})
	$("#collapse").collapse({toggle: true})
	
			
})

// Doctor Grid
$(function() {
	Grid.init();
});

// Contact Form 
		$(".contactform").validate({
	   submitHandler: function(form) {
		   //var name = $("input#name").val();
		   //var email = $("input#email").val();
		   //var url = $("input#url").val();
		   //var message = $("textarea#message").val();
		   
		   //var dataString = 'name='+ name + '&email=' + email + '&url=' + url+'&message='+message;
		  $.ajax({
		  type: "POST",
		  url: "api/contactus",
          data : {
              "name" : $("input#name").val(),
              "email" : $("input#email").val(),
              "phone" : $("input#phone").val(),
			  "message" : $("textarea#message").val()
								},
		  success: function() {
			  $('#contactmsg').remove();
			  $('.contactform').prepend("<div id='contactmsg' class='successmsg'>Form submitted successfully!</div>");
			   $('#contactmsg').delay(1500).fadeOut(500);
			  $('#submit_id').attr('disabled','disabled');
			  }
		 	});   
	   return false;
	  	}
		});
		
		
var active = 0;
	var clickedelement = 0;
	$(window).load(function(){
		
		// Slider Home page 2
	 $('.flexslidertext').flexslider({
        animation: "fade",
		slideshowSpeed:9000,
        start: function(slider){
          	$('.flexslideloader').removeClass('loading');
		  	$('ul.slides li:first').addClass('zoomIn animated')		 	
		  		  											
			$(".flex-control-nav a").click(function(e) {
				$('ul.slides li').removeClass('zoomIn animated');				
				active = $(this).parent().index();		
				$('ul.slides li').eq(active).addClass('zoomIn animated')	
				clickedelement = 1;			
			});	
		  					
        },
		before: function (slider){					
			var slidelength = $('ul.slides').children().length - 1;							
			if(clickedelement == 0){	
				clickedelement = 0;
				$('ul.slides li').removeClass('zoomIn animated' );									
				var sliderindex = $('.flex-active').parent().index();
				if(slidelength == sliderindex){
					$('ul.slides li').eq(0).addClass('zoomIn animated')																		
				}else{
					$('ul.slides li').eq(sliderindex + 1).addClass('zoomIn animated')																		
				}		
			}						
		},
	  });
  });
  
  
  // Animation - you can remove below functions if you don't want animations
$(document).ready(function() {
	windowwidht = $(window).width()
	if (windowwidht > 700){

		$('.rotateOnView').one('inview', function (event, visible) {			
			if (visible == true) {
				$(this).addClass("animated rotateIn");        
			}else{
				$(this).removeClass("animated rotateIn");
			}
		});
		$('.fadeInUpOnView').one('inview', function (event, visible) {			
			if (visible == true) {
				$(this).addClass("animated fadeInUp");        
			}else{
				$(this).removeClass("animated fadeInUp");
			}
		});
        
        
		//headings
		$('.heading2, .heading3').one('inview', function (event, visible) {			
			if (visible == true) {
				$(this).addClass("animated zoomIn");        
			}else{
				$(this).removeClass("animated zoomIn");
			}
		});
		$('.icon-box').one('inview', function (event, visible) {			
			if (visible == true) {
				$(this).addClass("animated rollIn");        
			}else{
				$(this).removeClass("animated rollIn");
			}
		});
		$('.doctors-box').one('inview', function (event, visible) {			
			if (visible == true) {
				$(this).addClass("animated fadeInLeft");        
			}else{
				$(this).removeClass("animated fadeInLeft");
			}
		});
		$('#homeblog').one('inview', function (event, visible) {			
			if (visible == true) {
				$(this).addClass("animated fadeInUp");        
			}else{
				$(this).removeClass("animated fadeInUp");
			}
		});
		$('#hometestimonial').one('inview', function (event, visible) {			
			if (visible == true) {
				$(this).addClass("animated zoomIn");        
			}else{
				$(this).removeClass("animated zoomIn");
			}
		});
		$('.iphone-btn').one('inview', function (event, visible) {			
			if (visible == true) {
				$(this).addClass("animated fadeInUp");        
			}else{
				$(this).removeClass("animated fadeInUp");
			}
		});
		$('.android-btn').one('inview', function (event, visible) {			
			if (visible == true) {
				$(this).addClass("animated fadeInUp");        
			}else{
				$(this).removeClass("animated fadeInUp");
			}
		});
			$('.fcpart').one('inview', function (event, visible) {			
			if (visible == true) {
				$(this).addClass("animated fadeInUp");        
			}else{
				$(this).removeClass("animated fadeInUp");
			}
		});
		$('.rightsection').one('inview', function (event, visible) {			
			if (visible == true) {
				$(this).addClass("animated fadeInUp");        
			}else{
				$(this).removeClass("animated fadeInUp");
			}
		});
		$('.midrow').one('inview', function (event, visible) {			
			if (visible == true) {
				$(this).addClass("animated fadeInUp");        
			}else{
				$(this).removeClass("animated fadeInUp");
			}
		});
		$('.blogsider, .content, .comment').one('inview', function (event, visible) {			
			if (visible == true) {
				$(this).addClass("animated fadeInUp");        
			}else{
				$(this).removeClass("animated fadeInUp");
			}
		});
		
		
		$('#thumbimages .content').addClass("animated fadeInUpBig"); 
		$('#thumbimages ul li').addClass("animated zoomIn"); 
		
		$('#newslist ul li').one('inview', function (event, visible) {			
			if (visible == true) {
				$(this).addClass("animated fadeInUp");        
			}else{
				$(this).removeClass("animated fadeInUp");
			}
		});
		$('#og-grid li').one('inview', function (event, visible) {			
			if (visible == true) {
				$(this).addClass("animated fadeInUp");        
			}else{
				$(this).removeClass("animated fadeInUp");
			}
		});
		
		$('#faq ul li, .appointmentform').one('inview', function (event, visible) {			
			if (visible == true) {
				$(this).addClass("animated fadeInUp");        
			}else{
				$(this).removeClass("animated fadeInUp");
			}
		});
}
	else{}
});