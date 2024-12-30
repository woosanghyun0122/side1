let productId, productName, amount, price;
let item ={};

function toggleWishlist() {

        const icon = document.getElementById('heart').querySelector('.fa-heart');
        icon.classList.toggle('fas'); // 채워진 하트로 변경
        icon.classList.toggle('far'); // 빈 하트로 변경
        icon.classList.toggle('filled'); // 색상을 위한 클래스 추가
}

function pay(){

    initializeVariables();

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

    initializeVariables();

    fetch('/cart',{
        method: 'POST',
        headers: {'Content-Type':'application/json'},
        body: JSON.stringify(item)
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
            })
        }
         else{
              return response.json().then(errorResponse =>{
                    alert(errorResponse.message);
              })
         }
    })
}


function initializeVariables() {
    // DOM 요소로부터 값을 초기화
    productId = document.getElementById('productId').value;
    productName = document.getElementById('name').innerText;
    amount = document.getElementById('quantity').value;
    price = document.getElementById('price').innerText;

    item = {
        productId: productId,
        amount: amount,
        productName: productName,
        productPrice: price
    };
}

