
var currentForm="submitReport";
var currentReport="FetchReport-response";

window.onload=function() {
	console.log("hey man");
	console.log("hello world 3");
	init();
	
}

function init() {

	document.getElementById('submitReport-button').addEventListener('click',function(){
		document.getElementById(currentForm).classList.add('display-none');
		document.getElementById(currentForm+'-response').classList.add('display-none');
		document.getElementById('submitReport').classList.remove('display-none');
		document.getElementById('submitReport-button').classList.add('selected');
		currentForm='submitReport';
	});
	document.getElementById('log-out').addEventListener('click',function(){
		logOut();
	});
	
	document.getElementById('submit-Report').addEventListener('click',function() {
		submitReport();
	});

}
 function xmlrequest(url,func) {

	 console.log("checking");
	var request=new XMLHttpRequest();

	 request.onreadystatechange=function() {
		if(request.readyState==4&&request.status==200) {
			console.log(request.responseText+" just checking");
			func(request.responseText);
		}
	}
	request.open("get",url,true);
	request.send();




 }
function submitReport() {
	console.log("Submit Report");
	
	var date=document.getElementById('submit-date').value;
	var year=document.getElementById('submit-year').value;
	var department=document.getElementById('submit-department').value;
	var hour=document.getElementById('submit-hour').value;
	var lecture=document.getElementById('submit-lecture').value;
	
	xmlrequest("reportservlet?id=submit&date="+date+"&year="+year+"&department="+department+"&hour="+hour+"&lecture="+lecture,printSubmitReport);
	document.getElementById('submitReport').classList.add('display-none');
	document.getElementById('submitReport-response').classList.remove('display-none');
	
	
}





function logOut() {
	console.log("Log out");

}



function printSubmitReport(response) {
	console.log(response);
	var h2="<h2> "+response+" </h2>";
	document.getElementById('submitReport-response').classList.remove('display-none');
	document.getElementById('submitReport-response').innerHTML=h2;


}


