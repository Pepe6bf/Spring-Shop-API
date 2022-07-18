package shop.spring.dev.springshop.service.login;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import shop.spring.dev.springshop.domain.member.Member;
import shop.spring.dev.springshop.domain.member.MemberRepository;

@Component("userDetailsService")
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    /**
     * 로그인시에 DB에서 유저정보와 권한정보를 가져오게 된다.
     * 해당 정보를 기반으로 userdetails.User객체를 생성해서 리턴한다.
     */
    @Override
    @Transactional
    public UserDetails loadUserByUsername(final String email) {

        Member member = memberRepository.findByEmail(email);
        if (member == null) {
            throw new UsernameNotFoundException(email);
        }

        if (!member.isActivated()) {
            throw new RuntimeException(email + " -> 활성화되어 있지 않습니다.");
        }

        return User.builder()
                .username(member.getEmail())
                .password(member.getPassword())
                .roles(member.getRole().toString())
                .build();
    }
}
