function getCheckedValues() {
    const checkboxes = document.querySelectorAll('.cart-item-checkbox');
    return Array.from(checkboxes)
        .filter(checkbox => checkbox.checked)
        .map(checkbox => checkbox.value); // 체크박스의 value 값 반환
}


function purchaseSelected(){

    const checkboxes = getCheckedValues();

    if(checkboxes.length == 0){
        alert("구매할 항목을 선택하세요");
        return;
    }

    fetch('/api/order/buyList',{
        method: 'POST',
        headers:{
            'Content-Type':'application/json'
        },
        body: JSON.stringify(checkboxes)
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

function deleteSelected(){

        const carts = getCheckedValues();

        if(carts.length == 0){
            alert("삭제할 항목을 선택하세요");
            return;
        }

        fetch('/cart',{
            method: 'DELETE',
            headers:{
                'Content-Type':'application/json'
            },
            body: JSON.stringify(carts)
        })
        .then(response =>{
                if(response.ok){
                    return response.text().then(result =>{
                        alert(result);
                        window.location.reload();
                    })
                }
                else{
                    return response.text().then(errorMessage =>{
                        alert(errorMessage);
                    })
                }
            })
}