function toggleWishlist() {

        const icon = document.getElementById('heart').querySelector('.fa-heart');
        icon.classList.toggle('fas'); // 채워진 하트로 변경
        icon.classList.toggle('far'); // 빈 하트로 변경
        icon.classList.toggle('filled'); // 색상을 위한 클래스 추가
}

function pay(){

    var list =[];
    var productId = document.getElementById('productId').value;
    list.push(productId);

    fetch('/api/order/findOrderList',{
        method:'POST',
        headers:{
            'Content-Type' : 'application/json'
        },
        body: JSON.stringify(list)
    })
    .then(response =>{
        if(response.ok){
            return response.json().then(result =>{
                window.href.
            })
        }
        else{
            return response.text().then(errorMessage =>{
                alert(errorMessage);
            })
        }
    })
}

