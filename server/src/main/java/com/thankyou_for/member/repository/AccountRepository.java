package com.thankyou_for.member.repository;

import com.thankyou_for.member.domain.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {
}
