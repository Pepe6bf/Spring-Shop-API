package shop.spring.dev.springshop.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import shop.spring.dev.springshop.api.response.ResponseService;
import shop.spring.dev.springshop.api.response.SingleResult;
import shop.spring.dev.springshop.dto.login.LoginDto;
import shop.spring.dev.springshop.dto.login.TokenDto;
import shop.spring.dev.springshop.jwt.JwtFilter;
import shop.spring.dev.springshop.service.login.LoginService;

import javax.validation.Valid;

import static org.springframework.http.HttpStatus.*;
import static shop.spring.dev.springshop.constant.BaseResponseStatus.*;

@RestController
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;
    private final ResponseService responseService;

    /**
     * 로그인 API
     */
    @PostMapping("/login")
    public ResponseEntity<SingleResult<TokenDto>> authorize(@Valid @RequestBody LoginDto loginDto) {

        String jwt = loginService.login(loginDto);
        TokenDto tokenDto = TokenDto.builder()
                .token(jwt)
                .build();

        // 헤더와 바디(dto)에 넣음
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(JwtFilter.AUTHORIZATION_HEADER, "Bearer " + jwt);

        return ResponseEntity
                .status(OK)
                .headers(httpHeaders)
                .body(responseService.getSingleResult(LOGIN_SUCCESS,
                        tokenDto));
    }
}
