function deleteZzim(productId){

    fetch(`/zzim/${productId}`,{
        method: 'DELETE'
    })
    .then(response=>{
        if(response.ok){
            window.location.reload();
        }
        else{
            alert("오류가 발생하였습니다.");
        }
    })
}