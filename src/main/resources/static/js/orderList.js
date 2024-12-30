function exchange(id){

    if(confirm("교환하시겠습니까?")){
        let popOption = "width=650px, height=550px, top=300px, left=300px";
        let openUrl = 'http://localhost:8080/order/exchange?id='+id;
        window.open(openUrl,'_blank',popOption);
    }
}

function refund(id){

    if(confirm("환불하시겠습니까?")){
        let popOption = "width=650px, height=550px, top=300px, left=300px";
        let openUrl = '/order/refund?id='+id;
        window.open(openUrl,'_blank',popOption);
    }
}