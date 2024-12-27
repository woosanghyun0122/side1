function toggleWishlist() {

        const icon = document.getElementById('heart').querySelector('.fa-heart');
        icon.classList.toggle('fas'); // 채워진 하트로 변경
        icon.classList.toggle('far'); // 빈 하트로 변경
        icon.classList.toggle('filled'); // 색상을 위한 클래스 추가
}

function pay(){

    var productName = document.getElementById('name').innerText;
    var productId = document.getElementById('productId').value;
    var amount = document.getElementById('quantity').value;
    var price = document.getElementById('price').innerText;


    var item ={
        productId: productId,
        amount: amount,
        productName: productName,
        productPrice: price
    };

    fetch('/api/order/buyInstant',{
        method:'POST',
        headers:{
            'Content-Type' : 'application/json'
        },
        body: JSON.stringify(item)
    })
    .then(response =>{
        if(response.ok){
            return response.text().then(result =>{
                const key = result;
                window.location.href = `http://localhost:8080/order/register/orderList?key=${key}`;
            })
        }
        else{
            return response.text().then(errorMessage =>{
                alert(errorMessage);
            })
        }
    })
}

function goCart(){

    var id = document.getElementById('productId').value;

    fetch(`/cart/${id}`,{
        method: 'POST'
    })
    .then(response =>{
        if(response.ok){
            return response.text().then(response =>{
                if(confirm("장바구니에 저장되었습니다. 장바구니로 이동하시겠습니까?")){
                    window.location.href = '/cart';
                }
                else{
                    return;
                }
            }
        }
         else{
              return response.json().then(errorResponse =>{
                    alert(errorResponse.message);
              })
         }
    })
}

