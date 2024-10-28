
// 수정 버튼 함수
function modify() {

    const id = parseFloat(document.getElementById('id').value);
    const nickName = document.getElementById('nickName').value;
    const phone = document.getElementById('phone').value;
    const email = document.getElementById('email').value;
    const address1 = document.getElementById('address').value;
    const address2 = document.getElementById('address2').value;
    document.getElementById('nickNameError').innerText = '';
    document.getElementById('phoneError').innerText = '';
    document.getElementById('addressError').innerText = '';

    const updateUserDto = {

        id: id,
        nickName: nickName,
        phone: phone,
        email: email,
        address1: address1,
        address2: address2
    }
    if(confirm("변경된 정보를 저장하시겠습니까?")){

        fetch('/api/user/modify',{
            method:'PUT',
            headers: {
                'Content-Type':'application/json',
            },
            body: JSON.stringify(updateUserDto)
        })
        .then(response =>{
            if(response.ok){
                return response.text().then(message =>{
                   alert(message);
                   location.replace("http://localhost:8080/user/myPage");
                })
            }
            else if(response.status == 400){
               return response.json().then(errorMessage =>{
                    alert("수정에 실패하였습니다.");

                    for(const key in errorMessage){
                            if(key == 'nickName'){
                               document.getElementById('nickNameError').innerText = errorMessage[key];
                            }
                            else if(key == 'phone'){
                               document.getElementById('phoneError').innerText = errorMessage[key];
                            }
                            else if(key == 'address1'){
                               document.getElementById('addressError').innerText = errorMessage[key];
                            }
                    }
               })

            }
            else if(response.status == 500){
                return response.json().then(errorResponse =>{
                    alert(errorResponse.message);
                })
            }
        })
    }
}

function isExistNickName(){

    const nickName = document.getElementById('nickName').value;

    fetch(`/api/user/check-nickname/${nickName}`,{
        method: 'GET'
    })
    .then(response =>{
        if(response.ok){
          return response.text().then(message =>{
                    alert(message);
                    document.getElementById('nickName').readOnly = true;
                    document.getElementById('nickName').style.backgroundColor = '#c0c0c0'
          })
        }
       else if(response.status == 400){
            return response.json().then(errorResponse =>{
                var message = errorResponse.message;
                alert(message);
            })
        }
        else{
            return response.text().then(message =>{
                alert(message);
            })
        }

    })
}

function exit() {
    const id = parseFloat(document.getElementById('id').value);

    // confirm 함수를 호출해야 함
    if (confirm("정말로 삭제하시겠습니까?")) {
        fetch(`/api/user/delete/${id}`, {
            method: 'DELETE'
        })
        .then(response => {
            if (response.ok) {
                // 삭제 성공 시 홈으로 리다이렉트
                return response.text().then(message => {
                    alert(message);
                    location.href = "/";
                })
            } else {
                return response.text().then(message => {
                    alert(message);
                });
            }
        })
        .catch(error => {
            console.error('삭제 중 오류 발생:', error);
            alert('오류가 발생했습니다. 다시 시도해 주세요.');
        });
    }
}


function findAddress(){
    alert("개발 예정입니다.");
}

