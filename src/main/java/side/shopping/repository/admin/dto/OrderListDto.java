package side.shopping.repository.admin.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
public class OrderListDto {

    /**
     * 관리자 조회 시 사용되는  dto
     */

    String orderNum;
    String orderName;       // 주문명
    String userid;          // 실구매자 id
    String userName;        // 실구매자 명
    String customerName;    // 주문자 명
    String customerPhone;   // 주문자 핸드폰번호
    String orderDate;       // 최초주문일시
    String orderModifyDate; // 주문수정일시


    @QueryProjection
    public OrderListDto(String orderNum, String orderName, String userid, String userName, String customerName, String customerPhone, String orderDate, String orderModifyDate) {
        this.orderNum = orderNum;
        this.orderName = orderName;
        this.userid = userid;
        this.userName = userName;
        this.customerName = customerName;
        this.customerPhone = customerPhone;
        this.orderDate = orderDate;
        this.orderModifyDate = orderModifyDate;
    }
}
