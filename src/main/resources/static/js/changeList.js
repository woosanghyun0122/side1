function switchTab(type) {

        const exchangeTable = document.getElementById('exchangeTable');
        const refundTable = document.getElementById('refundTable');
        const tabs = document.querySelectorAll('.tab');

        tabs.forEach(tab => tab.classList.remove('active'));

        if (type === 'exchange') {
            exchangeTable.style.display = 'table';
            refundTable.style.display = 'none';
            tabs[0].classList.add('active');
        } else {
            exchangeTable.style.display = 'none';
            refundTable.style.display = 'table';
            tabs[1].classList.add('active');
        }
}


function approve(id){

    const update ={
        id : id
    }

    fetch('/api/order/approve',{
        method: 'PUT',
        headers:{'Content-Type':'application/json'},
        body:JSON.stringify(update)
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

    if(confirm("교환/환불 요청을 거절하시겠습니까?")){
        let popOption = "width=650px, height=550px, top=300px, left=300px";
        let openUrl = 'http://localhost:8080/order/reject?id='+id;
        window.open(openUrl,'_blank',popOption);
    }
}