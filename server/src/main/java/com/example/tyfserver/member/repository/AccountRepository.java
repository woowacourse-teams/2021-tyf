package com.example.tyfserver.member.repository;

import com.example.tyfserver.member.domain.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {
}
