/*
package side.shopping.web.payment;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.parser.JSONParser;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import side.shopping.cache.CacheService;
import side.shopping.config.TossPaymentConfig;
import side.shopping.domain.order.Method;
import side.shopping.domain.order.Order;
import side.shopping.domain.order.OrderItem;
import side.shopping.domain.payment.Payment;
import side.shopping.domain.product.Product;
import side.shopping.domain.users.Users;
import side.shopping.repository.order.dto.OrderItemDto;
import side.shopping.repository.order.dto.OrderToPayDto;
import side.shopping.repository.users.dto.users.LoginResponseDto;
import side.shopping.web.order.service.OrderService;
import side.shopping.web.payment.service.PaymentService;
import side.shopping.web.product.service.ProductService;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Controller
public class WidgetController {


    @Autowired
    private TossPaymentConfig key;

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private CacheService cacheService;

    @Autowired
    private OrderService orderService;

    @RequestMapping(value = "/confirm")
    public ResponseEntity<JSONObject> confirmPayment(@RequestBody String jsonBody, HttpServletRequest request) throws Exception {

        JSONParser parser = new JSONParser();
        String orderId;
        String amount;
        String paymentKey;

        try {
            // 클라이언트에서 받은 JSON 요청 바디입니다.
            JSONObject requestData = (JSONObject) parser.parse(jsonBody);
            paymentKey = (String) requestData.get("paymentKey");
            orderId = (String) requestData.get("orderId");
            amount = (String) requestData.get("amount");
        } catch (ParseException e) {
            throw new RuntimeException(e);
        };

        JSONObject obj = new JSONObject();
        obj.put("orderId", orderId);
        obj.put("amount", amount);
        obj.put("paymentKey", paymentKey);

        // 토스페이먼츠 API는 시크릿 키를 Basic 인증의 사용자 ID로 사용하고, 비밀번호는 사용하지 않습니다.
        // 비밀번호가 없다는 것을 알리기 위해 시크릿 키 뒤에 콜론을 추가합니다.
        String widgetSecretKey = key.getTestSecretKey();
        Base64.Encoder encoder = Base64.getEncoder();
        byte[] encodedBytes = encoder.encode((widgetSecretKey + ":").getBytes("UTF-8"));
        String authorizations = "Basic " + new String(encodedBytes, 0, encodedBytes.length);

        // 결제를 승인하면 결제수단에서 금액이 차감돼요.
        URL url = new URL("https://api.tosspayments.com/v1/payments/confirm");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestProperty("Authorization", authorizations);
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setRequestMethod("POST");
        connection.setDoOutput(true);

        OutputStream outputStream = connection.getOutputStream();
        outputStream.write(obj.toString().getBytes("UTF-8"));

        int code = connection.getResponseCode();
        boolean isSuccess = code == 200 ? true : false;

        InputStream responseStream = isSuccess ? connection.getInputStream() : connection.getErrorStream();

        // 결제 성공 및 실패 비즈니스 로직을 구현하세요.
        Reader reader = new InputStreamReader(responseStream, StandardCharsets.UTF_8);
        JSONObject jsonObject = (JSONObject) parser.parse(reader);

        try {
            // 결제 성공일 때
            if (isSuccess) {

                log.info("order save success");
                OrderToPayDto dto = (OrderToPayDto) cacheService.getCacheValue(paymentKey);
                List<OrderItemDto> dtoList = (List<OrderItemDto>) cacheService.getCacheValue(dto.getOrderItemKey());

                HttpSession session = request.getSession(false);
                LoginResponseDto loginUser = (LoginResponseDto) session.getAttribute("loginUser");
                Users customer = loginUser.toUsers();
                dto.setUser(customer);

                Order order = orderService.registerOrder(dto, dtoList);

                jsonObject.put("message", "Payment success");
                jsonObject.put("orderId", order.getOrderNum());
                jsonObject.put("status", "success");


            } else {

                log.info("order save fail");
                Payment payment = paymentService.findByPaymentKey(paymentKey);

                // 결제 실패 응답
                jsonObject.put("message", "Payment failed");
                jsonObject.put("reason", payment.getCancelReason());
                jsonObject.put("status", "fail");
            }
        }catch (Exception e) {
            // 예외 처리
            jsonObject.put("message", "An error occurred");
            jsonObject.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(jsonObject);
        } finally {
            responseStream.close();
        }

        return ResponseEntity.status(code).body(jsonObject);
    }

}
*/
