var currentForm="homePage";
var currentReport="";
var personalReportDate="";
var personalReportHour="";
window.onload=function() {
    init();
    xmlrequest("internationalisation?id=set&language="+window.navigator.language);

}

function init() {
	
	document.getElementById('header-button').addEventListener('click',function() {
        document.getElementById('navigator').classList.toggle('translate-normal');
    });
	
	document.getElementById('navigator').addEventListener('click',function(event) {
		navigator(event);
	});
	
    document.getElementById('check-report').addEventListener('click',function(event) {
        report(event);
    });
    
    document.querySelector('.close').addEventListener('click',function() {
    document.getElementById('editPersonalReport').classList.add('display-none');
    });
}

function navigator(event) {
	
	if(event.srcElement.id=='homePage-button') {
		if(currentForm!='homePage') {
			        	currentReport="";
			            toggle('homePage')
		}	
	}
	else if(event.srcElement.id=='fetchStatus-button') {
		if(currentForm!='fetchStatus'||currentReport==='fetchStatus-response') {
			            toggle('fetchStatus');
		}	
	}
	else if(event.srcElement.id=='submitReport-button') {
		if(currentForm!='submitReport'||currentReport==='submitReport-response') {
			            toggle('submitReport');
		}	
	}
	else if(event.srcElement.id=='fetchReport-button') {
		if(currentForm!='fetchReport'||currentReport==='fetchReport-response') {
			            toggle('fetchReport');
		}	
	}
	else if(event.srcElement.id=='fetchPersonalReport-button') {
		if(currentForm!='fetchPersonalReport'||currentReport==='fetchPersonalReport-response') {
			            toggle('fetchPersonalReport');
		}	
	}
	else if(event.srcElement.id=='submitStatus-button') {
		if(currentForm!='submitStatus'||currentReport==='submitStatus-response') {
			        
			            toggle('submitStatus');
		}	
	}
}
function report(event) {

    if(event.srcElement.id=='submit-Report') {
        document.querySelector('.report-section').classList.toggle('translate-report');
        setTimeout(submitReport,1000);
    }
    
    else if(event.srcElement.id=='submit-Status') {
        document.querySelector('.report-section').classList.toggle('translate-report');
        setTimeout(submitStatus,1000);
    }
    
    else if(event.srcElement.id=='fetch-Report') {
        document.querySelector('.report-section').classList.toggle('translate-report');
        setTimeout(fetchReport,1000);
    }
    else if(event.srcElement.id=='fetch-Personal-Report') {
        document.querySelector('.report-section').classList.toggle('translate-report');
        setTimeout(fetchPersonalReport,1000);
    }
    else if(event.srcElement.id=='fetch-Status') {
        document.querySelector('.report-section').classList.toggle('translate-report');
        setTimeout(fetchStatus,1000);
    }
    else if(event.srcElement.id.includes('personal-response-delete')) {
     
        var id=event.srcElement.id;
        personalReportHour=id.substring((id.length)-1,id.length);
        if(confirm("Are you sure you want to Delete your report on "+personalReportDate+" during hour "+personalReportHour+" from the database")) {
            document.querySelector('.report-section').classList.toggle('translate-report');
         setTimeout(function() {
            personalReportDelete();
            },1000);
        }
    }
    else if(event.srcElement.id.includes('personal-response-edit')) {    
        var id=event.srcElement.id;
        personalReportHour=id.substring((id.length)-1,id.length);
        personalReportEdit();
        
    }
    else if(event.srcElement.id.includes('edit-Report')) {
        document.querySelector('.report-section').classList.toggle('translate-report');
        document.getElementById('editPersonalReport').classList.add('display-none');
        setTimeout(function(){
        submitEditedChanges();
        },1000);
    }
}



function toggle(form) {
    currentReport="";
    document.querySelector('.report-section').classList.toggle('translate-report');
    document.getElementById(currentForm+"-button").classList.remove('selected');
    document.getElementById(form+"-button").classList.add('selected');
    setTimeout(function() {
		document.getElementById(currentForm).classList.add('display-none');
		document.getElementById(currentForm+"-response").classList.add('display-none');
		document.getElementById(form).classList.remove('display-none');
        document.querySelector('.report-section').classList.toggle('translate-report');
        currentForm=form;
            },1000);
}

function xmlrequest(url,func=null) {
    var request=new XMLHttpRequest();
    request.onreadystatechange=function() {
        if(request.readyState==4&&request.status==200) {
            if(func!=null) {
                func(request.responseText);
            }
        }
    }
    request.open("get",url,true);
    request.send();
}

function personalReportDelete() {
    xmlrequest("reportservlet?id=delete&date="+personalReportDate+"&hour="+personalReportHour,printResponse);
               
}
function personalReportEdit() {
    var report=document.getElementById('personal-response-row-'+personalReportHour);
    document.getElementById('edit-lecture').textContent=report.childNodes[1].innerHTML;
    document.getElementById('edit-hour').selectedIndex=personalReportHour-1;
    document.getElementById('edit-year').selectedIndex=report.childNodes[3].innerHTML-1;
    var editDepartment =document.getElementById('edit-department');
    var department=report.childNodes[4].innerHTML;
    for(var i=0;i<document.getElementById('edit-department').length;i++) {
        if(editDepartment.options[i].innerHTML==department) {
            editDepartment.selectedIndex=i;
            break;
        }
    }
    document.getElementById('editPersonalReport').classList.remove('display-none');
    
}

function submitEditedChanges() {
    console.log("i am here in edit changes");
    var date=personalReportDate;
    var currentHour=document.getElementById('')
    var year=document.getElementById('edit-year').value;
    var department=document.getElementById('edit-department').value;
    var hour=document.getElementById('edit-hour').value;
    var lecture=document.getElementById('edit-lecture').value;
    xmlrequest("reportservlet?id=edit&date="+date+"&year="+year+"&department="+department+"&hour="+hour+"&lecture="+lecture+"&currentHour="+personalReportHour,printResponse);
    
}

function submitReport() {
    var date=document.getElementById('submit-date').value;
    var year=document.getElementById('submit-year').value;
    var department=document.getElementById('submit-department').value;
    var hour=document.getElementById('submit-hour').value;
    var lecture=document.getElementById('submit-lecture').value;
    xmlrequest("submit?id=lectureReport&date="+date+"&year="+year+"&department="+department+"&hour="+hour+"&lecture="+lecture,printResponse);
    document.getElementById('submitReport').classList.add('display-none');
    currentReport='submitReport-response';
    document.getElementById('submitReport-response').classList.remove('display-none');
}

function fetchStatus() {
        	var date=document.getElementById('fetch-status-date').value;
        	var department=document.getElementById('fetch-status-department').value;
        	xmlrequest("fetch?id=hodReport&date="+date+"&department="+department,printFetchStatus);
        	document.getElementById('fetchStatus').classList.add('display-none');
        	currentReport='fetchStatus-response';
        	document.getElementById('fetchStatus-response').classList.remove('display-none');
        	
 }

function fetchPersonalReport() {
			var date=document.getElementById('fetch-personal-report-date').value;
            personalReportDate=date;
            xmlrequest("fetch?id=personalReport&date="+date,printFetchPersonalReport);
        	document.getElementById('fetchPersonalReport').classList.add('display-none');
        	currentReport='fetchPersonalReport-response';
        	document.getElementById('fetchPersonalReport-response').classList.remove('display-none');
}

 function fetchReport() {
            var date=document.getElementById('fetch-date').value;
            var year=document.getElementById('fetch-year').value;
            var department=document.getElementById('fetch-department').value;
            xmlrequest("fetch?id=lectureReport&date="+date+"&year="+year+"&department="+department,printFetchReport);
            document.getElementById('fetchReport').classList.add('display-none');
            currentReport='fetchReport-response';
            document.getElementById('fetchReport-response').classList.remove('display-none');
            
            
            
        
}
        
function submitStatus() {
            var date=document.getElementById('submit-status-date').value;
            var year=document.getElementById('submit-status-year').value;
            var status=document.getElementById('submit-status-on').value;
            xmlrequest("submit?id=hodReport&date="+date+"&year="+year+"&status="+status,printResponse);
            document.getElementById('submitStatus').classList.add('display-none');
            currentReport='submitStatus-response';
            document.getElementById('submitStatus-response').classList.remove('display-none');
 }
        
function printFetchReport(response) {
            var obj=JSON.parse(response);
            var table="<h2>Lecture Report </h2><table> <tr><th>Hour</th><th>workDone</th><th>Staff Reported</th><th>Updated on</th><th>Updated by(StaffId)</th></tr>";
            for(var i=0;i<obj.length;i++) {
                var staffId=(obj[i].staffId<1)?((obj[i].staffId<0)?'Should be Updated by staffId -> '+(obj[i].staffId*-1):'-'):obj[i].staffId;
                table+="<tr><td>"+(i+1)+"</td><td>"+obj[i].workDone+"</td><td>"+obj[i].staffName+"</td><td>"+obj[i].updatedOn+"</td><td>"+staffId+"</td></tr>";
            }
            table+="</table>";
            document.getElementById('fetchReport-response').innerHTML=table;
            document.querySelector('.report-section').classList.toggle('translate-report');
            
}
 function printFetchPersonalReport(response) {
    var obj=JSON.parse(response);
    if(obj.length>0) { 
	 		
             var table="<h2>Personal Report </h2><table> <tr><th>Hour</th><th>workDone</th><th>Updated on</th>"+
             "<th>Year of Department</th><th>Department</th></tr>";
            for(var i=0;i<obj.length;i++) {
                table+="<tr id=\"personal-response-row-"+obj[i].hourOfClass+"\"><td>"+obj[i].hourOfClass+"</td><td>"+obj[i].workDone+"</td><td>"+obj[i].updatedOn+"</td><td>"+obj[i].yearOfDepartment+"</td><td>"
                +obj[i].departmentName+"</td><td><button id=\"personal-response-edit-"+(obj[i].hourOfClass)+"\">Edit</button><button id=\"personal-response-delete-"+(obj[i].hourOfClass)+"\">Delete</button></td></tr>";
            }
            table+="</table>";
	 		document.getElementById('fetchPersonalReport-response').innerHTML=table;      
        }
        else {
            document.getElementById('fetchPersonalReport-response').innerHTML="<h2>The report is Empty</h2>";
        }
    document.querySelector('.report-section').classList.toggle('translate-report');
 }       
 function printFetchStatus(response) {
     
            var obj=JSON.parse(response);
        	var table="<h2>Hod Status</h2><table><tr><th>Year</th><th>Status</th><th>Updated On</th></tr>";
        	for(var i=0;i<obj.length;i++) {
                var status = (obj[i].status==-1) ? "No-Entry":((obj[i].status==0)?"Pending":"Validated");
        		table+="<tr><td>"+(i+1)+"</td><td>"+status+"</td><td>"+obj[i].updatedOn+"</td></tr>";
        	}
        	table+="</table>";
        	document.getElementById('fetchStatus-response').innerHTML=table;
            document.querySelector('.report-section').classList.toggle('translate-report');
                	
}

 function printResponse(response) {
            var h2="<h2> "+response+" </h2>";
            document.getElementById(currentReport).innerHTML=h2;
            document.querySelector('.report-section').classList.toggle('translate-report');
 }

 
                