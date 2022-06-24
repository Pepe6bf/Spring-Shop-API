package shop.spring.dev.springshop.controller.member;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import shop.spring.dev.springshop.dto.member.MemberSaveRequestDto;
import shop.spring.dev.springshop.service.member.MemberService;

import javax.servlet.http.HttpServletRequest;

@RequiredArgsConstructor
@RestController
public class MemberApiController {

    private final MemberService memberService;

    @PostMapping("/signup")
    public Long saveMember(@RequestBody MemberSaveRequestDto requestDto) {
        return memberService.saveMember(requestDto).getId();
    }

    /**
     * 테스트용, 나중에 삭제할 것
     * USER, ADMIN 모두 호출 가능
     */
    @GetMapping("/user-access")
    public String testUserAccess(HttpServletRequest request) {
        return "BAAAAAAAAAM~~~~~~";

    }

    /**
     * 테스트용, 나중에 삭제할 것
     * ADMIN만 호출 가능
     */
    @GetMapping("/admin-access")
    @PreAuthorize("hasRole('ADMIN')")
    public String testAdminAccess(HttpServletRequest request) {
        return "이건 비밀 사이트야...";

    }

    /**
     * 테스트 메서드
     * 꼭 삭제할 것
     */
    @PostMapping("/signup-admin")
    public Long saveAdmin(@RequestBody MemberSaveRequestDto requestDto) {
        return memberService.saveAdmin(requestDto).getId();
    }

}
