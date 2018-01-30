window.onload=function() {
	
	xmlrequest("reportservlet?id=set&language="+window.navigator.language);
	console.log("done");
}

function xmlrequest(url,func=null) {

	 console.log("checking");
	var request=new XMLHttpRequest();

	 request.onreadystatechange=function() {
		if(request.readyState==4&&request.status==200) {
			console.log(request.responseText+" just checking");
			if(func!=null) {
			func(request.responseText);
			}
		}
	}
	request.open("get",url,true);
	request.send();




}