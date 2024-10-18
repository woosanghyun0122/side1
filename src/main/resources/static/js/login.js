
function login() {

    const loginId = document.getElementById('loginId').value;
    const password = document.getElementById('password').value;
    document.getElementById('loginIdError').innerText = '';
    document.getElementById('passwordError').innerText = '';

    const loginDto = {
               loginId : loginId,
               password : password
    }

    fetch('/user/login',{
        method: 'POST',
        headers:{
            'Content-Type': 'application/json',
        },
         body: JSON.stringify(loginDto)
    })
    .then(response =>{
        if(response.ok){
            window.location.href = "/";
        }
        else if(response.status == 404){
           return response.text().then(errorMessage =>{
                alert(errorMessage);
            })
        }
        else if(response.status == 408){
           return response.text().then(errorMessage =>{
                alert(errorMessage);
            })
        }
        else if(response.status == 400){
            return response.json().then(errorMessage =>{
                    for(const key in errorMessage){
                        if(key == 'loginId'){
                            document.getElementById('loginIdError').innerText = errorMessage[key];
                        }
                        else if(key == 'password'){
                            document.getElementById('passwordError').innerText = errorMessage[key];
                        }
                    }
            })
        }
    })
}


