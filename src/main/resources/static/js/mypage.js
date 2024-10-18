
// 수정 버튼 함수
function modify() {

    const id = parseFloat(document.getElementById('id').value);
    const nickName = document.getElementById('nickName').value;
    const phone = document.getElementById('phone').value;
    const email = document.getElementById('email').value;
    const address1 = document.getElementById('address').value;
    const address2 = document.getElementById('address2').value;

    const updateUserDto = {

        id: id,
        nickName: nickName,
        phone: phone,
        email: email,
        address1: address1,
        address2: address2
    }
    if(confirm("변경된 정보를 저장하시겠습니까?")){

        fetch('/user/update',{
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
                return response.json().then(errorMessage =>{
                    alert(errorMessage);
                })
            }
        })
    }
}

function isExistNickName(){

    const nickName = document.getElementById('nickName').value;

    fetch(`/user/isExistNickName?nickName=${nickName}`,{
        method: 'GET'
    })
    .then(response =>{
        if(response.ok){
            return response.text().then(message =>{
                alert(message);
            })
        }else if(response.status == 208){
            return response.text().then(message =>{
                alert(message);
            })
        }
        else{
            return response.json().then(errorMessage=>{
                alert(errorMessage);
            })
        }
    })
}

function findAddress(){
    alert("개발 예정입니다.");
}
