
var zipCode,address,addressDetail,phone,email;

document.addEventListener("DOMContentLoaded", function(){

    const status = document.querySelectorAll('input[name="status"]');
    const button = document.getElementById('modify');

    for (let result of status){
        console.log("result=",result.value);
        if(result.value == 'DELIVERING' || result.value == 'ARRIVE'
        || result.value == 'EXCHANGE' || result.value == 'EXCHANGE_DENIED'
        || result.value == 'REFUND' || result.value == 'REFUND_DENIED'
        ){
            button.disabled = true;
            button.style.backgroundColor = '#868e96';
            return;
        }
    }

});


function modify(){

    var orderNum = document.getElementById('orderNum').value;
    name = document.getElementById('customerName').value;
    zipCode = document.getElementById('address.zipCode').value;
    address = document.getElementById('address.address').value;
    addressDetail = document.getElementById('address.addressDetail').value;
    phone = document.getElementById('customerPhone').value;
    email = document.getElementById('customerEmail').value;


    var addressDto = {
            zipCode: zipCode,
            address: address,
            addressDetail: addressDetail
    };

    var order ={

        orderNum: orderNum,
        customerName: name,
        customerPhone: phone,
        customerEmail: email,
        address: addressDto
    }

    if(confirm("주문 정보를 수정하시겠습니까?")){

        fetch('/api/order/modify',{
                method:'PUT',
                headers: {
                    'Content-Type':'application/json',
                },
                body: JSON.stringify(order)
            })
            .then(response =>{
                if(response.ok){
                   alert("수정이 완료되었습니다.");
                   window.location.href = '/order/myOrderList';
                }
                else{
                    alert("수정에 실패하였습니다.");
                    return;
                }
            })
    }

}


