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


      const paymentMethodWidget = paymentWidget.renderPaymentMethods(
        "#payment-method",
        { value: 50000 },
        { variantKey: "DEFAULT" }
      );

      paymentWidget.renderAgreement(
        "#agreement",
        { variantKey: "AGREEMENT" }
      )

  button.addEventListener("click", async function () {
    paymentWidget.requestPayment({
      orderId: document.getElementById('orderNum').value,
      orderName: document.getElementById("orderName").value,
      successUrl: window.location.origin + "/success.html",
      failUrl: window.location.origin + "/fail.html",
      customerEmail: document.getElementById('customerEmail').value,
      customerName: document.getElementById('customerName').value,
      customerMobilePhone: document.getElementById('userPhone').value
    });
  });
}
