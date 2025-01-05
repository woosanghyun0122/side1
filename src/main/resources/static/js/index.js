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
            }
            else{
                alert("오류가 발생하였습니다.");
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
                    }
                    else{
                        alert("오류가 발생하였습니다.");
                    }
                })
    }

}