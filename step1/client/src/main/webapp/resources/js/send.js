function send() {
    var ItemJSON = '{"clientId":123,"productIds":[15,64,82],"deliveryAddress":{"address":"Berggeiststrasse 31","city":"50321 Bruhl"}}';

    URL = "https://localhost:8443/service/data/order";

    var xmlhttp = new XMLHttpRequest();
    xmlhttp.onreadystatechange = callbackFunction(xmlhttp);
    xmlhttp.open("POST", URL, false);
    xmlhttp.setRequestHeader("Content-Type", "application/json");
    xmlhttp.onreadystatechange = callbackFunction(xmlhttp);
    xmlhttp.send(ItemJSON);
    document.getElementById("div").innerHTML = xmlhttp.statusText + ":" + xmlhttp.status;
}

function callbackFunction(xmlhttp) {
    //console.log(xmlhttp.responseXML);
}