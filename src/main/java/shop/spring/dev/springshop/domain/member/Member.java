package shop.spring.dev.springshop.domain.member;

import lombok.Getter;
import shop.spring.dev.springshop.constant.Role;

import javax.persistence.*;

@Getter
@Entity
@Table(name = "member")
public class Member {

    @Id
    @Column(name = "member_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, length = 100)
    private String email;

    private String password;

    @Column(length = 50)
    private String name;

    @Column(length = 200)
    private String address;

    @Enumerated(EnumType.STRING)
    private Role role;
}
