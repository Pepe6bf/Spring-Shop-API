package shop.spring.dev.springshop.domain.member;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MemberSaveRequestDto {

    private String email;
    private String password;
    private String name;
    private String address;

    @Builder
    public MemberSaveRequestDto(String email, String password, String name, String address) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.address = address;
    }

}
