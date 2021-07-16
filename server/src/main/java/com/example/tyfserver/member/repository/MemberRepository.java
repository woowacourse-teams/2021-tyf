package com.example.tyfserver.member.repository;

import com.example.tyfserver.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long>, MemberQueryRepository {

    Optional<Member> findByEmail(String email);

    Optional<Member> findByEmailAndOauth2Type(String email, String oauth2Type);

    boolean existsByEmail(String email);

    boolean existsByPageName(String pageName);

    boolean existsByNickname(String nickname);

    Optional<Member> findByPageName(String pageName);
}
