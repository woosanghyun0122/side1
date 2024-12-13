document.addEventListener('DOMContentLoaded', function () {
  main();
});

async function main() {
  const button = document.getElementById('payment-button');
  const value = document.getElementById('totalAmount').value;

  const widgetClientKey = 'test_gck_docs_Ovk5rk1EwkEbP0W43n07xlzm';
  const customerKey = 'xhhdbLrOD24-ywjgsdV2d';
  const tossPayments = PaymentWidget(widgetClientKey, customerKey);

  const paymentWidget = PaymentWidget(widgetClientKey, customerKey);
  const orderId = document.getElementById('orderNum').value;
  const orderName = document.getElementById("orderName").value;
  const customerEmail = document.getElementById('customerEmail').value;
  const customerName = document.getElementById('customerName').value;
  const userPhone = document.getElementById('userPhone').value;

  const order ={
    orderName : orderName,

  }

      const paymentMethodWidget = paymentWidget.renderPaymentMethods(
        "#payment-method",
        { value: value },
        { variantKey: "DEFAULT" }
      );

      paymentWidget.renderAgreement(
        "#agreement",
        { variantKey: "AGREEMENT" }
      )

  button.addEventListener("click", async function () {

    const key = document.getElementById('key').value;


    .then(response =>{
        if(response.ok){
                paymentWidget.requestPayment({
                  orderId: orderId,
                  orderName: orderName,
                  successUrl: window.location.href = `/payment/success?key=${key}`,
                  failUrl: window.location.origin + "/payment/fail",
                  customerEmail: customerEmail,
                  customerName: customerName,
                  customerMobilePhone: userPhone
                });
        }
    })


  });
}
