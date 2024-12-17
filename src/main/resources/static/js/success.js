// 쿼리 파라미터 값이 결제 요청할 때 보낸 데이터와 동일한지 반드시 확인하세요.
  // 클라이언트에서 결제 금액을 조작하는 행위를 방지할 수 있습니다.
  const urlParams = new URLSearchParams(window.location.search);
  const paymentKey = urlParams.get("paymentKey");
  const orderId = urlParams.get("orderId");
  const amount = urlParams.get("amount");
  const method = urlParams.get("method");

  async function confirm() {

    const requestData = {
      paymentKey: paymentKey,
      orderId: orderId,
      amount: amount,
      method: method
    };

    const response = await fetch("/confirm", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(requestData),
    });

    const json = await response.json();

    if (!response.ok) {
      // 결제 실패 비즈니스 로직을 구현하세요.
      console.log(json);
      window.location.href = `/fail?message=${json.message}&code=${json.code}`;
    }

    // 결제 성공 비즈니스 로직을 구현하세요.
    console.log(json);
  }