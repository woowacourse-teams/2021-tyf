package com.example.tyfserver.member.repository;

import com.example.tyfserver.auth.domain.Oauth2Type;
import com.example.tyfserver.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long>, MemberQueryRepository {

    Optional<Member> findByEmail(String email);

    Optional<Member> findByEmailAndOauth2Type(String email, Oauth2Type oauth2Type);

    boolean existsByPageName(String pageName);

    boolean existsByNickname(String nickname);

    Optional<Member> findByPageName(String pageName);
}
