
document.getElementById("loginBtn").addEventListener("click", function(event) {

    fetch('/user/login',{
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: formData
    })
    .then(response ->{
        if(response.ok){
            window.location.href = "/";
        }
        else if(response.status == 404){
            return response.text().then(errorMessage =>{
                alert(errorMessage);
            })
        }
        else if(response.status == 408){
            return response.text.then(errorMessage =>{
                alert(errorMessage);
            })
        }
    })
}


