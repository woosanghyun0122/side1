function approve(id){

    fetch('/api/order/approve/'+id,{
        method: 'PUT'
    })
    .then(response =>{
        if(response.ok){
            return response.text().then(message =>{
                alert(message);
                window.location.reload();
            })
        }
    })
}

function reject(id){

    fetch('/api/order/reject/'+id,{
            method: 'PUT'
        })
        .then(response =>{
            if(response.ok){
                return response.text().then(message =>{
                    alert(message);
                    window.location.reload();
                })
            }
        })
}