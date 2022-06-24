package shop.spring.dev.springshop.dto.login;

import lombok.*;

/**
 * Token 정보를 Response 할 때 사용
 */
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TokenDto {

    private String token;
}