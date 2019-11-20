var url = 'http://localhost:8080/api/account/current';
var requestMethod;
var URI;

function initRequest(request){
    requestType = request["method"];
    URI = request["uri"];
}

function executeRequest(fData){
    document.cookie = "customerId=1";
    $.ajax({
        type: 'GET',//requestMethod,
        url: url,// + URI,
        //dataType: 'json',
        //data: fData,
        //headers: {"customerId":1},
        xhrFields: {
            withCredentials: true
        },
        processData: false,
        contentType: false,
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


