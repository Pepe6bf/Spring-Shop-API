package shop.spring.dev.springshop.domain.member;

import org.springframework.data.jpa.repository.JpaRepository;


public interface MemberRepository extends JpaRepository<Member, Long> {

    // query method
    Member findByEmail(String email);

    // query

}
