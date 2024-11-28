
function modify(){

    var productId = document.getElementById('productId').value;
    var name = document.getElementById('name').value;
    var price = document.getElementById('price').value;
    var quantity = document.getElementById('quantity').value;
    var content = document.getElementById('content').value;

    var modifyDto ={
        productId : productId,
        name : name,
        price : price,
        quantity : quantity,
        content : content
    }

    if(confirm("상품을 수정하시겠습니까?")){

        fetch('/api/product/modify',{
            method:'PUT',
            headers:{
                'Content-Type':'application/json'
            },
            body: JSON.stringify(modifyDto)
        })
        .then(response =>{
            if(response.status == 200){
                return response.text().then(message =>{
                    alert(message);
                    window.location.href = '/product/seller/list';
                })
            }
            else if(response.status == 500){
                return response.text().then(message =>{
                    alert(message);
                })
            }
            else if(response.status == 400){
                            return response.json().then(errorMessage =>{

                                alert("회원가입에 실패하였습니다.");

                                for(const key in errorMessage){

                                          if(key == 'name'){
                                              document.getElementById('nameError').innerText = errorMessage[key];
                                          }
                                          else if(key == 'price'){
                                               document.getElementById('priceError').innerText = errorMessage[key];
                                          }
                                          else if(key == 'content'){
                                               document.getElementById('contentError').innerText = errorMessage[key];
                                          }
                                          else if(key == 'quantity'){
                                               document.getElementById('quantityError').innerText = errorMessage[key];
                                          }
                                }
                            })

                        }else{
                            return response.json().then(errorResponse =>{
                                                alert(errorResponse.message);
                            })
                        }
        })
    }
}


function remove(){

    if(confirm('이 상품을 삭제하시겠습니까?')){
        var productId = parseInt(document.getElementById('productId').value);
        fetch(`/api/product/delete/${productId}`,{
            method:'DELETE'
        })
        .then(response =>{
            return response.text().then(message =>{
                alert(message);
                window.location.href = '/product/seller/list';
            })
        })
    }
}
