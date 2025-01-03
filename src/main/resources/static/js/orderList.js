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

function reloadData() {
    // 데이터 재조회 로직 (AJAX 또는 페이지 새로고침)
    console.log("Reloading data...");
    location.reload(); // 페이지 새로고침
}