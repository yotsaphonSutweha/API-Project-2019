var url = 'http://localhost:8080/api/';
var requestMethod;
var URI;
var userId;

function initRequest(request){
    // userId = '1'; //document.cookie;
    requestMethod = request["method"];
    URI = request["uri"];
}

function executeRequest(requestBody){
    $.ajax({
        type: "POST",
        url: url + URI,
        data: JSON.stringify(requestBody),
        dataType: "json",
        contentType: "application/json",
//        headers: {'customerId':userId},
//        processData: false,
//        contentType: false,
        success: function(data){
           try {
                var newdata;
                console.debug("data is =" + data);
                newdata = JSON.parse(data);
              
            } catch (e) {
                console.log("caught error " + e);
            }
        },
        error:function(xhrm, statusText){
            console.debug("Error: " + xhrm.status + " " + statusText);
        }
    });
}



function test(emailText) {
    console.log(emailText);
}

