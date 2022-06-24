package shop.spring.dev.springshop.service.member;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import shop.spring.dev.springshop.constant.MemberRole;
import shop.spring.dev.springshop.constant.MemberStatus;
import shop.spring.dev.springshop.domain.member.Member;
import shop.spring.dev.springshop.domain.member.MemberRepository;
import shop.spring.dev.springshop.dto.member.MemberSaveRequestDto;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
class MemberServiceTest {

    @Autowired PasswordEncoder passwordEncoder;
    @Autowired MemberService memberService;
    @Autowired MemberRepository memberRepository;

    @AfterEach
    public void cleanUp() {
        memberRepository.deleteAll();
    }

    private MemberSaveRequestDto getMemberSaveRequestDto() {
        String email = "test@email.com";
        String password = "testPw1234";
        String name = "tester";
        String address = "서울에 있는 어떤 외딴 집";

        MemberSaveRequestDto requestDto = MemberSaveRequestDto.builder()
                .email(email)
                .password(password)
                .name(name)
                .address(address)
                .build();
        return requestDto;
    }

    @Test
    @DisplayName("회원가입 테스트")
    public void saveMemberTest() throws Exception {
        // given
        MemberSaveRequestDto requestDto = getMemberSaveRequestDto();

        // when
        Member savedMember = memberService.saveMember(requestDto);

        // then
        assertThat(savedMember.getEmail()).isEqualTo(requestDto.getEmail());
        assertThat(passwordEncoder.matches(requestDto.getPassword(), savedMember.getPassword())).isTrue();
        assertThat(savedMember.getName()).isEqualTo(requestDto.getName());
        assertThat(savedMember.getAddress()).isEqualTo(requestDto.getAddress());
        assertThat(savedMember.getRole()).isEqualTo(MemberRole.USER);
        assertThat(savedMember.getStatus()).isEqualTo(MemberStatus.ACTIVE);
    }

    @Test
    @DisplayName("중복 회원 가입 테스트")
    public void saveDuplicateMemberTest() throws Exception {
        // given
        MemberSaveRequestDto requestDto1 = getMemberSaveRequestDto();
        MemberSaveRequestDto requestDto2 = getMemberSaveRequestDto();

        // when
        Member savedMember = memberService.saveMember(requestDto1);

        // then
        assertThatThrownBy(() -> memberService.saveMember(requestDto2))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("이미 가입된 회원입니다.");
    }
}