function js_openImage() {
	window.imagelistner.test();
	var objs = document.getElementsByTagName("img");
	for(var i=0;i<objs.length;i++) {  
		objs[i].onclick=function(){
        	window.imagelistner.openImage(this.src);
		}   
     }   
}
