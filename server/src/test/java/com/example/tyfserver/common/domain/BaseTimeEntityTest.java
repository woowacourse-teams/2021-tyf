package com.example.tyfserver.common.domain;

import com.example.tyfserver.member.domain.Member;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class BaseTimeEntityTest {

    @PersistenceContext
    private EntityManager em;

    @Test
    @DisplayName("createdDate 확인")
    public void checkCreateDate() {
        Member member = new Member("tyf@gmail.com");
        assertThat(member.getCreatedAt()).isNull();
        em.persist(member);
        assertThat(member.getCreatedAt()).isNotNull();
    }
}