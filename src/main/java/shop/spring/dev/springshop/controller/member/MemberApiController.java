package shop.spring.dev.springshop.controller.member;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import shop.spring.dev.springshop.domain.member.MemberSaveRequestDto;
import shop.spring.dev.springshop.service.member.MemberService;

@RequiredArgsConstructor
@RestController
public class MemberApiController {

    private final MemberService memberService;

    @PostMapping("/members")
    public Long saveMember(@RequestBody MemberSaveRequestDto requestDto) {
        return memberService.saveMember(requestDto).getId();
    }
}
