function exchange(){

    if(confirm("교환하시겠습니까?")){
        let popOption = "width=650px, height=550px, top=300px, left=300px, scrollbars=no";
        let openUrl = '/order/exchange';
        window.open(openUrl,'pop',popOption);
    }
}

function refund(){

    if(confirm("환불하시겠습니까?")){
        let popOption = "width=650px, height=550px, top=300px, left=300px, scrollbars=no";
        let openUrl = '/order/refund';
        window.open(openUrl,'pop',popOption);
    }
}