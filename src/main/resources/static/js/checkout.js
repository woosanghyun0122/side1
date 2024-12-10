main();

    async function main() {
      const button = document.getElementById("payment-button");
      const amount = {
        currency: "KRW",
        value: document.getElementById('totalPrice').value,
      };
      // ------  결제위젯 초기화 ------
      // TODO: clientKey는 개발자센터의 결제위젯 연동 키 > 클라이언트 키로 바꾸세요.
      // TODO: 구매자의 고유 아이디를 불러와서 customerKey로 설정하세요. 이메일・전화번호와 같이 유추가 가능한 값은 안전하지 않습니다.
      // @docs https://docs.tosspayments.com/sdk/v2/js#토스페이먼츠-초기화
      const clientKey = document.getElementById('clientKey').value;
      const customerKey = document.getElementById('orderNum');
      const tossPayments = TossPayments(clientKey);
      // 회원 결제
      const widgets = tossPayments.widgets({
        customerKey,
      });
      // 비회원 결제
      // const widgets = tossPayments.widgets({customerKey: TossPayments.ANONYMOUS});

      // ------  주문서의 결제 금액 설정 ------
      // TODO: 위젯의 결제금액을 결제하려는 금액으로 초기화하세요.
      // TODO: renderPaymentMethods, renderAgreement, requestPayment 보다 반드시 선행되어야 합니다.
      await widgets.setAmount(amount);

      // ------  결제 UI 렌더링 ------
      // @docs https://docs.tosspayments.com/sdk/v2/js#widgetsrenderpaymentmethods
      await widgets.renderPaymentMethods({
        selector: "#payment-method",
        // 렌더링하고 싶은 결제 UI의 variantKey
        // 결제 수단 및 스타일이 다른 멀티 UI를 직접 만들고 싶다면 계약이 필요해요.
        // @docs https://docs.tosspayments.com/guides/v2/payment-widget/admin#새로운-결제-ui-추가하기
        variantKey: "DEFAULT",
      });

      // ------  이용약관 UI 렌더링 ------
      // @docs https://docs.tosspayments.com/reference/widget-sdk#renderagreement선택자-옵션
      await widgets.renderAgreement({ selector: "#agreement", variantKey: "AGREEMENT" });

      // ------  주문서의 결제 금액이 변경되었을 경우 결제 금액 업데이트 ------
      // @docs https://docs.tosspayments.com/sdk/v2/js#widgetssetamount

        await widgets.setAmount({
          currency: "KRW",
          value: amount,
        });
      });

      // ------ '결제하기' 버튼 누르면 결제창 띄우기 ------
      // @docs https://docs.tosspayments.com/sdk/v2/js#widgetsrequestpayment
      button.addEventListener("click", async function () {
        // 결제를 요청하기 전에 orderId, amount를 서버에 저장하세요.
        // 결제 과정에서 악의적으로 결제 금액이 바뀌는 것을 확인하는 용도입니다.
        await widgets.requestPayment({
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
