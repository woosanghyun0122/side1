document.addEventListener('DOMContentLoaded', function () {
  main();
});

async function main() {
  const button = document.getElementById('payment-button');
  const totalAmount = document.getElementById('totalAmount').value;

  const widgetClientKey = 'test_gck_docs_Ovk5rk1EwkEbP0W43n07xlzm';
  const customerKey = 'xhhdbLrOD24-ywjgsdV2d';
  const tossPayments = PaymentWidget(widgetClientKey, customerKey);

  const paymentWidget = PaymentWidget(widgetClientKey, customerKey);
  const orderId = document.getElementById('orderNum').value;
  const orderName = document.getElementById("orderName").value;
  const customerEmail = document.getElementById('customerEmail').value;
  const customerName = document.getElementById('customerName').value;
  const userPhone = document.getElementById('customerPhone').value;
  const key = document.getElementById('key').value;

  const paymentMethodWidget = paymentWidget.renderPaymentMethods(
    "#payment-method",
    { value: totalAmount },
    { variantKey: "DEFAULT" }
  );

  paymentWidget.renderAgreement(
    "#agreement",
    { variantKey: "AGREEMENT" }
  );

  const confirm = {
    orderNum: orderId,
    price: totalAmount,
    orderKey: key
  };

  button.addEventListener("click", async function () {
    // 클릭 이벤트 내에서 key 값을 가져옵니다.


    fetch('/api/v1/payments/toss', {
      headers: {
        'Content-Type': 'application/json',
      },
      method: 'POST',
      body: JSON.stringify(confirm)
    })
    .then(response => {
      if (response.ok) {
        return response.text().then(paymentKey => {
          paymentWidget.requestPayment({
            orderId: orderId,
            orderName: orderName,
            successUrl: window.location.origin + '/payment/success', // 템플릿 리터럴 수정
            failUrl: window.location.origin + "/payment/fail",
            customerEmail: customerEmail,
            customerName: customerName,
            customerMobilePhone: userPhone
          });
        });
      } else {
        return response.text().then(errorMessage => {
          alert(errorMessage);
        });
      }
    })
    .catch(error => {
      console.error('Error:', error);
    });
  });
}
