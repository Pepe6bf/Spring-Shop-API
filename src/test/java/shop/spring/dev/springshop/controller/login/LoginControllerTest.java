package shop.spring.dev.springshop.controller.login;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import shop.spring.dev.springshop.domain.member.Member;
import shop.spring.dev.springshop.dto.login.LoginDto;
import shop.spring.dev.springshop.dto.member.MemberSaveRequestDto;
import shop.spring.dev.springshop.global.BaseControllerTest;
import shop.spring.dev.springshop.service.member.MemberService;

import java.nio.charset.StandardCharsets;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

public class LoginControllerTest extends BaseControllerTest {

    @Autowired private MemberService memberService;

    public Member createMember() {
        MemberSaveRequestDto memberSaveRequestDto = MemberSaveRequestDto.builder()
                .email("test@email.com")
                .password("testPw1234")
                .name("tester")
                .address("Busan")
                .build();

        return memberService.saveMember(memberSaveRequestDto);
    }

    @Test
    @DisplayName("로그인 테스트")
    public void loginTest() throws Exception {
        // given
        Member member = createMember();
        String url = "http://localhost:8080/login";
        LoginDto loginDto = LoginDto.builder()
                .email(member.getEmail())
                .password("testPw1234")
                .build();

        // when
        RequestBuilder requestBuilder = MockMvcRequestBuilders.post(url)
                .content(objectMapper.writeValueAsString(loginDto))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN)
                .characterEncoding(StandardCharsets.UTF_8.displayName());

        // then
        mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("token").exists())
                .andDo(print());
    }
}