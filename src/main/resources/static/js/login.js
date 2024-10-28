
function login() {

    const loginId = document.getElementById('loginId').value;
    const password = document.getElementById('password').value;
    const redirectURL = document.getElementById('url').value;
    document.getElementById('loginIdError').innerText = '';
    document.getElementById('passwordError').innerText = '';

    const loginDto = {
               loginId : loginId,
               password : password
    }

    fetch( `/api/user/login?redirectURL=${redirectURL}`,{
        method: 'POST',
        headers:{
            'Content-Type': 'application/json',
        },
         body: JSON.stringify(loginDto)
    })
    .then(response =>{
        if(response.ok){
            return response.text().then(response =>{
                console.log("response="+response);
                window.location.href = response;
            })
        }
        else if(response.status == 400){
            return response.json().then(errorResponse =>{

                if(errorResponse.code == 1005 || errorResponse.code == 1006){
                    alert(errorResponse.message);
                }else{
                    for(const key in errorResponse){
                        if(key == 'loginId'){
                            document.getElementById('loginIdError').innerText = errorResponse[key];
                        }
                        else if(key == 'password'){
                            document.getElementById('passwordError').innerText = errorResponse[key];
                        }
                    }
                }

            })
        }
    })
}


