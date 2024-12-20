
var key,clientKey,name,zipCode,address,addressDetail,phone,method,email;

function pay(){

    key = document.getElementById('key').value;
    clientKey = document.getElementById('clientKey').value;
    name = document.getElementById('customerName').value;
    zipCode = document.getElementById('address.zipCode').value;
    address = document.getElementById('address.address').value;
    addressDetail = document.getElementById('address.addressDetail').value;
    phone = document.getElementById('customerPhone').value;
    email = document.getElementById('customerEmail').value;
    totalAmount = document.getElementById('totalPrice').innerText.replace('원','');
/*
    method = getSelectedMethod();
*/

    var addressDto = {
            zipCode: zipCode,
            address: address,
            addressDetail: addressDetail
    };

    var order ={
        orderItemKey: key,
        customerName: name,
        customerPhone: phone,
        customerEmail: email,
        zipCode: zipCode,
        address: address,
        addressDetail: addressDetail,
        totalAmount: totalAmount
    }

    fetch('/api/v1/payments/checkout',{
        method:'POST',
        headers: {
            'Content-Type':'application/json',
        },
        body: JSON.stringify(order)
    })
    .then(response =>{
        if(response.ok){
            return response.text().then(response =>{
                var newKey = response;
                window.location.href = `/payment/checkout?key=${newKey}`;
            })
        }
        else{
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