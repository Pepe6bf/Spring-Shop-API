package shop.spring.dev.springshop.domain.member;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shop.spring.dev.springshop.constant.MemberStatus;
import shop.spring.dev.springshop.constant.MemberRole;
import shop.spring.dev.springshop.domain.global.BaseEntity;
import shop.spring.dev.springshop.domain.global.BaseTimeEntity;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Table(name = "member")
@Entity
public class Member extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Column(unique = true, length = 100, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(length = 50, nullable = false)
    private String name;

    @Column(length = 200, nullable = false)
    private String address;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private MemberRole role;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private MemberStatus status;

    @Builder
    public Member(String email, String password, String name, String address, MemberRole role, MemberStatus status) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.address = address;
        this.role = role;
        this.status = status;
    }

    public Boolean isActivated() {
        return status == MemberStatus.ACTIVE;
    }
}
