/**
*  하위 카테고리 선택 함수
*/

function changeLowerCategory(){

        // 부모 카테고리에서 선택된 값 가져오기
        var parentCategory = document.getElementById("parentCategory").value;

        if(parentCategory != ""){

            var lowerCategory = document.getElementById('lowerCategory');
            lowerCategory.disabled = false;
        }
        // 모든 하위 카테고리 옵션을 가져오기
        var lowerCategoryOptions = document.querySelectorAll("#lowerCategory option");

        // 하위 카테고리를 필터링해서 표시
        lowerCategoryOptions.forEach(function(option) {
            var parentId = option.getAttribute("data-parent-id");

            // 선택된 부모 카테고리의 ID와 하위 카테고리의 parentId가 일치하면 보이고, 아니면 숨김
            if (parentCategory === "" || parentCategory === parentId) {
                option.style.display = "block";
            } else {
                option.style.display = "none";
            }
        });
}


/**
저장 함수
*/
function save(){

    var name = document.getElementById('name').value;
    var price = document.getElementById('price').value;
    var content = document.getElementById('content').value;
    var quantity = document.getElementById('quantity').value;
    var parentId = document.getElementById('parentCategory').value;
    var categoryId = document.getElementById('lowerCategory').value;
    document.getElementById('nameError').innerText = '';
    document.getElementById('pricedError').innerText = '';
    document.getElementById('contentError').innerText = '';
    document.getElementById('quantityError').innerText = '';

    const saveProduct = {
        name : name,
        price : price,
        content : content,
        quantity : quantity,
        parentId : parentId,
        categoryId :categoryId
    }
    if(confirm("상품저장을 하시겠습니까?")){
        fetch('/api/product/add',{
            method: 'POST',
            headers:{
                'Content-Type':'application/json'
            },
            body: JSON.stringify(saveProduct)
        })
        .then(response =>{
            if(response.status == 201){
                            return response.text().then(message =>{
                                alert(message);
                                window.location.href = '/user/login';
                            })
                        }
                        else if(response.status == 409){
                            return response.text().then(errorMessage =>{
                                alert(errorMessage);
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

