
var key,clientKey,name,zipCode,address,addressDetail,phone,method;

function pay(){

    key = document.getElementById('key').value;
    clientKey = document.getElementById('clientKey').value;
    name = document.getElementById('name').value;
    zipCode = document.getElementById('address.zipCode').value;
    address = document.getElementById('address.address').value;
    addressDetail = document.getElementById('address.addressDetail').value;
    phone = document.getElementById('phone').value;
    method = getSelectedMethod();

    var addressDto = {
            zipCode: zipCode,
            address: address,
            addressDetail: addressDetail
    };

    var order ={
        key: key,
        name: name,
        phone: phone,
        method: method,
        address: addressDto
    }

    fetch(`/api/v1/payments/checkout/${key}`,{

        method:'POST',
        headers: {
            'Content-Type':'application/json',
        },
        body: JSON.stringify(order)
    })
    .then(response =>{
        if(response.ok){
            console.log("response.ok");
            return response.text().then(response =>{
                var newKey = response;
                window.location.href = `/payment/checkout?key=${newKey}`;
            })
        }
        else{
            console.log("response error");
             return response.json().then(errorResponse =>{
                    alert(errorResponse.message);
                    })
             }
    })

    function getSelectedMethod(){

        var selectedMethod = document.querySelector('input[name="method"]:checked');
        if(selectedMethod){
            return selectedMethod.value;
        }
        else{
            return null;
        }
    }

}