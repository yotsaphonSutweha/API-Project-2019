var url = "localhost:8080/api/";
var requestMethod;
var URI;

function initRequest(request){
    requestType = request["method"];
    URI = request["uri"];
    
}

function executeRequest(fData){
    $.ajax({
        type: requestMethod,
        url: url + URI,
        //dataType: 'json',
        data: fData,
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


