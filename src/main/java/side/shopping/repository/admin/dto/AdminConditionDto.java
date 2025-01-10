package side.shopping.repository.admin.dto;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdminConditionDto {

    private String entity;

    private String userid;

    private String orderNum;

    private LocalDateTime startDate;

    private LocalDateTime endDate;


}
