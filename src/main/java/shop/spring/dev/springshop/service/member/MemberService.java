package shop.spring.dev.springshop.service.member;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.spring.dev.springshop.constant.MemberStatus;
import shop.spring.dev.springshop.constant.MemberRole;
import shop.spring.dev.springshop.domain.member.Member;
import shop.spring.dev.springshop.domain.member.MemberRepository;
import shop.spring.dev.springshop.dto.member.MemberSaveRequestDto;

@RequiredArgsConstructor
@Transactional
@Service
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public Member saveMember(MemberSaveRequestDto requestDto) {
        // 중복 회원 검증
        validateDuplicateMember(requestDto.getEmail());

        return memberRepository.save(
                Member.builder()
                        .email(requestDto.getEmail())
                        .password(passwordEncoder.encode(requestDto.getPassword()))
                        .name(requestDto.getName())
                        .address(requestDto.getAddress())
                        .role(MemberRole.USER)
                        .status(MemberStatus.ACTIVE)
                        .build()
        );
    }

    /**
     * 테스트용
     * 나중에 삭제 할 것!!
     */
    public Member saveAdmin(MemberSaveRequestDto requestDto) {
        // 중복 회원 검증
        validateDuplicateMember(requestDto.getEmail());

        return memberRepository.save(
                Member.builder()
                        .email(requestDto.getEmail())
                        .password(passwordEncoder.encode(requestDto.getPassword()))
                        .name(requestDto.getName())
                        .address(requestDto.getAddress())
                        .role(MemberRole.ADMIN)
                        .status(MemberStatus.ACTIVE)
                        .build()
        );
    }

    private void validateDuplicateMember(String checkingEmail) {
        Member findMember = memberRepository.findByEmail(checkingEmail);
        if (findMember != null) {
            throw new IllegalStateException("이미 가입된 회원입니다.");
        }
    }
}
