package side.shopping.domain.users;

import jakarta.persistence.*;
import lombok.*;
import side.shopping.domain.order.Order;
import side.shopping.repository.users.dto.users.UpdateUserDto;
import side.shopping.repository.admin.dto.UserListDto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@Builder
@AllArgsConstructor
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "userid")
    private String userid;

    @Column(name = "password")
    private String password;

    @Column(name = "username")
    private String userName;

    @Column(name = "nickname")
    private String nickName;

    @Column(name = "phone",unique = true)
    private String phone;

    @Column(name = "email")
    private String email;

    @Column(name = "address1")
    private String address1;

    @Column(name = "address2")
    private String address2;

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime created_at;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updated_at;

    @OneToMany(mappedBy = "user")
    @Builder.Default
    private List<Order> orderList = new ArrayList<Order>();




    public Users() {
    }

    public Users(String userid, String password, String userName, String nickName, String phone, String email, String address1, String address2, Role role) {
        this.userid = userid;
        this.password = password;
        this.userName = userName;
        this.nickName = nickName;
        this.phone = phone;
        this.email = email;
        this.address1 = address1;
        this.address2 = address2;
        this.role = role;
    }

    public void updateUserInfo(UpdateUserDto dto) {
        this.id = dto.getId();
        this.nickName = dto.getNickName();
        this.phone = dto.getPhone();
        this.email = dto.getEmail();
        this.address1 = dto.getAddress1();
        this.address2 = dto.getAddress2();
    }

    public UserListDto toUserListDto() {
        return UserListDto.builder()
                .userid(this.userid)
                .userName(this.userName)
                .nickName(this.nickName)
                .phone(this.phone)
                .email(this.email)
                .role(this.role)
                .build();
    }

    @PrePersist
    public void onCreate() {
        this.created_at = LocalDateTime.now();
        this.updated_at = LocalDateTime.now();
    }

    @PreUpdate
    public void onUpdate() {
        this.updated_at = LocalDateTime.now();
    }

}
