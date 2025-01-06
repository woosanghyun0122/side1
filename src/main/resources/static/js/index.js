function zzim(productId){

    const heartIcon = document.querySelector(`i[data-id='${productId}']`);
    const isZzim = heartIcon.classList.contains('far')

    if(isZzim){
        fetch(`/zzim/${productId}`,{
           method: 'POST'
        })
        .then(response =>{
            if(response.ok){
                heartIcon.classList.remove('far');
                heartIcon.classList.add('fas');
                heartIcon.classList.toggle('filled');
            }
            else if(response.status == 500){
                alert("로그인이 필요합니다.");
                window.location.href='/user/login';
            }
        })
    }
    else{
        fetch(`/zzim/${productId}`,{
                   method: 'DELETE'
                })
                .then(response =>{
                    if(response.ok){
                        heartIcon.classList.remove('fas');
                        heartIcon.classList.add('far');
                        heartIcon.classList.remove('filled');
                    }
                    else{
                        alert("오류가 발생하였습니다.");
                    }
                })
    }

}