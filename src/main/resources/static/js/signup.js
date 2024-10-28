
var idCheck = 0;
var nickNameCheck =0;


function isExistId(){

    var userid = document.getElementById('userid').value;
    console.log(userid);

    fetch(`/api/user/check-userid/${userid}`,{
        method: 'GET'
    })
    .then(response =>{

        if(response.ok){
            return response.text().then(message =>{
                alert(message);
                document.getElementById('userid').readOnly = true;
                document.getElementById('userid').style.backgroundColor = '#c0c0c0'
                idCheck ++;
            })
        }else if(response.status == 400){
            return response.json().then(errorResponse =>{
                 alert(errorResponse.message);
            })
        }
        else{
            return response.text().then(errorMessage =>{
                alert(errorMessage);
            })
        }
    })
}

function isExistNickName(){

    var nickName = document.getElementById('nickName').value;

    fetch(`/api/user/check-nickname/${nickName}`,{
        method: 'GET'
    })
    .then(response =>{
        if(response.ok){
            return response.text().then(message =>{
                alert(message);
                document.getElementById('nickName').readOnly = true;
                document.getElementById('nickName').style.backgroundColor = '#c0c0c0'
                nickNameCheck ++;
            })
        }else if(response.status == 400){
            return response.text().then(errorMessage =>{
                alert(errorMessage);
            })
        }
        else if(response.status == 409){
            return response.text().then(errorMessage =>{
                alert(errorMessage);
            })
        }else{
            return response.text.then(errorMessage =>{
                alert(errorMessage);
            })
        }
    })
}


function save(){

    var userid = document.getElementById('userid').value;
    var password = document.getElementById('password').value;
    var userName = document.getElementById('userName').value;
    var nickName = document.getElementById('nickName').value;
    var phone = document.getElementById('phone').value;
    var email = document.getElementById('email').value;
    var address1 = document.getElementById('address').value;
    var address2 = document.getElementById('address2').value;
    var role = document.getElementById('role').value;
    document.getElementById('phoneError').innerText = '';
    document.getElementById('passwordError').innerText = '';
    document.getElementById('userNameError').innerText = '';
    document.getElementById('addressError').innerText = '';

    const persistUserDto = {
        userid : userid,
        password : password,
        userName : userName,
        nickName : nickName,
        phone : phone,
        email : email,
        address1 : address1,
        address2 : address2,
        role : role
    }
    if(confirm("회원가입을 하시겠습니까?")){

        if(idCheck < 1){
            alert("아이디 중복 확인을 해야합니다.");
            return;
        }
        if(nickNameCheck <1){
            alert("닉네임 중복 확인을 해야합니다.");
            return;
        }

        fetch('/api/user/signup',{
            method : 'POST',
            headers:{
                'Content-Type':'application/json'
            },
            body: JSON.stringify(persistUserDto)
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

                              if(key == 'password'){
                                  document.getElementById('passwordError').innerText = errorMessage[key];
                              }
                              else if(key == 'userName'){
                                   document.getElementById('userNameError').innerText = errorMessage[key];
                              }
                              else if(key == 'phone'){
                                   document.getElementById('phoneError').innerText = errorMessage[key];
                              }
                              else if(key == 'address1'){
                                   document.getElementById('addressError').innerText = errorMessage[key];
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

function searchAddress(){
    alert("주소 개발 api 개발중");
}
