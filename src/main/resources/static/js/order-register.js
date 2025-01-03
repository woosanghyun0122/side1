
var key,clientKey,name,zipCode,address,addressDetail,phone,method,email;

document.addEventListener("DOMContentLoaded", function(){

    calculator();

    const checkboxes = document.querySelectorAll('input[name="index"]');

    // 체크박스 변경 감지 (함수 밖으로 이동)
    checkboxes.forEach(checkbox => {
        checkbox.addEventListener("change", calculator);
    });

});


function pay(){

    key = document.getElementById('key').value;
    clientKey = document.getElementById('clientKey').value;
    name = document.getElementById('customerName').value;
    zipCode = document.getElementById('address.zipCode').value;
    address = document.getElementById('address.address').value;
    addressDetail = document.getElementById('address.addressDetail').value;
    phone = document.getElementById('customerPhone').value;
    email = document.getElementById('customerEmail').value;
    totalAmount = parseInt(document.getElementById('totalPrice').innerText.replace(/[^0-9]/g, ""), 10);
    console.log("totalAmount="+totalAmount);


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
}

function calculator(){
        let total = 0;
        console.log("total:" + total);
        const checkboxes = document.querySelectorAll('input[name="index"]');
        const totalPrice = document.getElementById('totalPrice');

        checkboxes.forEach(checkbox => {
            if(checkbox.checked){
                const row = checkbox.closest("tr");
                const priceText = row.querySelector('td:nth-child(4)').innerText;
                const amount = parseInt(row.querySelector('td:nth-child(5)').textContent);
                const price = parseInt(priceText.replace(/[^0-9]/g, ""), 10);
                total += price * amount;
            }
        });

        totalPrice.innerText = total.toLocaleString() + '원';
}


