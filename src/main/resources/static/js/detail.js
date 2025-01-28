let productId, productName, amount, price;
let item ={};

function toggleWishlist(productId) {

    const heartIcon = document.getElementById('heart').querySelector('.fa-heart');
    const isZzim = heartIcon.classList.contains('far');
    console.log(isZzim);

    if(isZzim){
        fetch(`/zzim/${productId}`,{
           method: 'POST'
        })
        .then(response =>{
            if(response.ok){
                heartIcon.classList.remove('far');
                heartIcon.classList.add('fas');
                heartIcon.classList.toggle('filled');
            }
            else if(response.status == 500){
                alert("로그인이 필요합니다.");
                window.location.href='/user/login';
            }
        })
    }
    else{
        fetch(`/zzim/${productId}`,{
                   method: 'DELETE'
                })
                .then(response =>{
                    if(response.ok){
                        heartIcon.classList.add('far');
                        heartIcon.classList.remove('fas');
                        heartIcon.classList.remove('filled');
                    }
                    else{
                        alert("오류가 발생하였습니다.");
                    }
                })
    }
}

function pay(){

    initializeVariables();

    fetch('/api/order/buyInstant?Id='+productId+'&amount='+amount,{
        method:'GET'
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
    amount = document.getElementById('quantity').value;

    item = {
        productId : productId,
        amount : amount
    }

}

