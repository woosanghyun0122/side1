package side.shopping.domain.users;

import jakarta.persistence.*;
import lombok.*;
import side.shopping.repository.users.dto.users.PersistUserDto;
import side.shopping.repository.users.dto.users.UpdateUserDto;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Getter
@Entity
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

    @Column(name = "phone")
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


    public Users() {
    }

    public void updateUserInfo(UpdateUserDto dto) {
        this.id = dto.getId();
        this.nickName = dto.getNickName();
        this.phone = dto.getPhone();
        this.email = dto.getEmail();
        this.address1 = dto.getAddress1();
        this.address2 = dto.getAddress2();
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
