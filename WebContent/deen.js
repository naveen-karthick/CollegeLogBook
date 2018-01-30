
var currentForm="fetchStatus";
var currentReport="FetchStatus-response";

window.onload=function() {
	console.log("hey man");
	console.log("hello world 3");
	init();
	
}

function init() {
	
	document.getElementById('fetchStatus-button').addEventListener('click',function(){
		document.getElementById(currentForm).classList.add('display-none');
		document.getElementById(currentForm+"-response").classList.add('display-none');
		document.getElementById(currentForm+"-button").classList.remove('selected');
		document.getElementById('fetchStatus').classList.remove('display-none');
		document.getElementById('fetchStatus-button').classList.add('selected');
		currentForm='fetchStatus';
	});
	document.getElementById('fetchReport-button').addEventListener('click',function(){
		document.getElementById(currentForm).classList.add('display-none');	
		document.getElementById(currentForm+"-response").classList.add('display-none');
		document.getElementById(currentForm+"-button").classList.remove('selected');
		document.getElementById('fetchReport').classList.remove('display-none');
		document.getElementById("fetchReport-button").classList.add('selected');
		currentForm='fetchReport';
	});
	document.getElementById('log-out').addEventListener('click',function(){
		logOut();
	});
	
	document.getElementById('fetch-Report').addEventListener('click',function() {
		fetchReport();
	});
	document.getElementById('fetch-Status').addEventListener('click',function() {
		fetchStatus();
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


function fetchReport() {
	console.log("Fetch Report");
	var date=document.getElementById('fetch-date').value;
	var year=document.getElementById('fetch-year').value;
	var department=document.getElementById('fetch-department').value;
	
	xmlrequest("reportservlet?id=fetch&date="+date+"&year="+year+"&department="+department,printFetchReport);
	document.getElementById('fetchReport').classList.add('display-none');
	document.getElementById('fetchReport-response').classList.remove('display-none');
	
	
	

}


function fetchStatus() {
	var date=document.getElementById('fetch-status-date').value;
	var department=document.getElementById('fetch-status-department').value;
	
	xmlrequest("reportservlet?id=fetchHod&date="+date+"&department="+department,printFetchStatus);
	document.getElementById('fetchStatus').classList.add('display-none');
	document.getElementById('fetchStatus-response').classList.remove('display-none');
	
	
	
}

function logOut() {
	console.log("Log out");

}

function printFetchReport(response) {
	var obj=JSON.parse(response);
	var table="<table> <tr><th>Hour</th><th>workDone</th><th>Staff Reported</th><th>Updated on</th><th>Updated by(StaffId)</th></tr>";
	console.log("joker");
	for(var i=0;i<obj.length;i++) {
		table+="<tr><td>"+(i+1)+"</td><td>"+obj[i].workDone+"</td><td>"+obj[i].staff+"</td><td>"+obj[i].updatedOn+"</td><td>"+obj[i].staffId+"</td></tr>";
	}
	table+="</table>";
	document.getElementById('fetchReport-response').innerHTML=table;

}



function printFetchStatus(response) {
	var obj=JSON.parse(response);
	var table="<table><tr><th>Year</th><th>Status</th><th>Updated On</th></tr>";
	for(var i=0;i<obj.length;i++) {
		table+="<tr><td>"+(i+1)+"</td><td>"+obj[i].statusReport+"</td><td>"+obj[i].updatedOn+"</td></tr>";
	}
	table+="</table>";
	document.getElementById('fetchStatus-response').innerHTML=table;
console.log(response);
	
	
	
}