
function pay(){

    var key = document.getElementById('key').value;
    var name = document.getElementById('name').value;
    var zipCode = document.getElementById('address.zipCode').value;
    var address = document.getElementById('address.address').value;
    var addressDetail = document.getElementById('address.addressDetail').value;
    var phone = document.getElementById('phone').value;
    var method = document.getElementByName('method').value;

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

    fetch('/api/v1/payments/checkout/${key}',{
        method:'POST',
        headers: {
            'Content-Type':'application/json',
        },
        body: JSON.stringify(order)
    })
    .then(response =>{
        if(response.ok){
            return response.json().then(myOrder =>{

                 var clientKey = '테스트_클라이언트_키'
                 var tossPayments = TossPayments(clientKey)

                 tossPayments.requestPayment('CARD', {
                            amount: 58500,
                            orderId: 'bec1d544-2a34-4f44-ada0-c5213d8fd8dd',
                            orderName: '포인트 충전',
                            customerName: '첫번째',
                            customerEmail: 'test1@gmail.com',
                            successUrl: 'http://localhost:8081/api/v1/payments/toss/success',
                            failUrl: 'http://localhost:8081/api/v1/payments/toss/fail'
                        })
            })
        }

        else{
             return response.json().then(errorResponse =>{
                    alert(errorResponse.message);
                    })
             }
    })

}