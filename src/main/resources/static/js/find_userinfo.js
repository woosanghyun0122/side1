function findId(){
    var userName = document.getElementById('userName').value;
    var phone = document.getElementById('phone').value;
    document.getElementById('userNameError').innerText = '';
    document.getElementById('phoneError').innerText = '';

    var findUserDto = {
        userName: userName,
        phone: phone
    };

    fetch('/api/user/find-userid', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(findUserDto)
    })
    .then(response => {
        if (response.ok) {
            return response.text().then(message => {
                alert("아이디는 " + message + " 입니다.");
                window.location.href = '/user/login';
            });
        } else if (response.status == 400) {
            return response.json().then(errorResponse => {
                for (const key in errorResponse) {
                    if (key === 'userName') {
                        document.getElementById('userNameError').innerText = errorResponse[key];
                    } else if (key === 'phone') {
                        document.getElementById('phoneError').innerText = errorResponse[key];
                    }
                }
            });
        } else {
            return response.text().then(message => {
                alert(message);
            });
        }
    })
    .catch(error => {
        console.error('Error:', error);
    });
}

function findPw(){
    var userid = document.getElementById('userid').value;
    var userName = document.getElementById('userName').value
    var phone = document.getElementById('phone').value;
    document.getElementById('useridError').innerText = '';
    document.getElementById('userNameError').innerText = '';
    document.getElementById('phoneError').innerText = '';

    var findUserDto = {
        userid: userid,
        phone: phone,
        userName: userName
    };

    fetch('/api/user/find-pw', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(findUserDto)
    })
    .then(response => {
        if (response.ok) {
            return response.text().then(message => {
                alert("비밀번호는 " + message + " 입니다.");
                window.location.href = '/user/login';
            });
        } else if (response.status == 400) {
            return response.json().then(errorResponse => {
                for (const key in errorResponse) {
                    if (key === 'userName') {
                        document.getElementById('userNameError').innerText = errorResponse[key];
                    } else if (key === 'phone') {
                        document.getElementById('phoneError').innerText = errorResponse[key];
                    } else if (key === 'userid') {
                        document.getElementById('useridError').innerText = errorResponse[key];
                    }
                }
            });
        } else {
            return response.text().then(message => {
                alert(message);
            });
        }
    })
    .catch(error => {
        console.error('Error:', error);
    });
}
