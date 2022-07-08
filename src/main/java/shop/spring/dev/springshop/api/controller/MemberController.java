package shop.spring.dev.springshop.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import shop.spring.dev.springshop.api.response.CommonResult;
import shop.spring.dev.springshop.api.response.ResponseService;
import shop.spring.dev.springshop.dto.member.MemberSaveRequestDto;
import shop.spring.dev.springshop.service.member.MemberService;

import static org.springframework.http.HttpStatus.*;
import static shop.spring.dev.springshop.constant.BaseResponseStatus.*;

@RequiredArgsConstructor
@RestController
public class MemberController {

    private final MemberService memberService;
    private final ResponseService responseService;

    /**
     * 일반 계정 회원 가입 API
     */
    @PostMapping("/signup")
    public ResponseEntity<CommonResult> saveMember(@RequestBody MemberSaveRequestDto requestDto) {
        memberService.saveMember(requestDto);
        return new ResponseEntity<>(responseService.getSuccessResult(CREATED_USER), CREATED);
    }

    /**
     * 관리자 계정 회원 가입 API
     */
    @PostMapping("/signup-admin")
    public ResponseEntity<CommonResult> saveAdmin(@RequestBody MemberSaveRequestDto requestDto) {
        memberService.saveAdmin(requestDto);
        return new ResponseEntity<>(responseService.getSuccessResult(CREATED_USER), CREATED);
    }

//    @GetMapping("/user-access")
//    public String testUserAccess(HttpServletRequest request) {
//        return "BAAAAAAAAAM~~~~~~";
//
//    }
//    @GetMapping("/admin-access")
//    @PreAuthorize("hasRole('ADMIN')")
//    public String testAdminAccess(HttpServletRequest request) {
//        return "이건 비밀 사이트야...";
//    }

}
