package shop.spring.dev.springshop.domain.member;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.transaction.annotation.Transactional;
import shop.spring.dev.springshop.constant.MemberRole;
import shop.spring.dev.springshop.constant.MemberStatus;
import shop.spring.dev.springshop.dto.member.MemberSaveRequestDto;
import shop.spring.dev.springshop.service.member.MemberService;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class MemberTest {

    @Autowired
    MemberRepository memberRepository;

    @PersistenceContext
    EntityManager em;

    public Member getMember() {
        return Member.builder()
                .email("test@email.com")
                .password("testPw1234")
                .name("tester")
                .address("Buasn")
                .role(MemberRole.USER)
                .status(MemberStatus.ACTIVE)
                .build();
    }

    @Test
    @DisplayName("Auditing 테스트")
    @WithMockUser(username = "tester@email.com", roles = "USER")    // 지정한 사용자가 로그인한 상태라고 가정
    public void auditingTest() throws Exception {
        // given
        Member member = getMember();
        memberRepository.save(member);

        em.flush();
        em.clear();
        // when
        Member findMember = memberRepository.findById(member.getId())
                .orElseThrow(EntityNotFoundException::new);

        // then
        assertThat(findMember.getCreatedAt()).isEqualTo(member.getCreatedAt());
        assertThat(findMember.getUpdatedAt()).isEqualTo(member.getUpdatedAt());
        assertThat(findMember.getCreatedBy()).isEqualTo(member.getCreatedBy());
        assertThat(findMember.getUpdatedBy()).isEqualTo(member.getUpdatedBy());

        System.out.println("findMember.getCreatedAt() = " + findMember.getCreatedAt());
        System.out.println("findMember.getUpdatedAt() = " + findMember.getUpdatedAt());
        System.out.println("findMember.getCreatedBy() = " + findMember.getCreatedBy());
        System.out.println("findMember.getUpdatedBy() = " + findMember.getUpdatedBy());
    }

}