package com.thankyou_for.auth.repository;

import com.thankyou_for.auth.domain.VerificationCode;
import org.springframework.data.repository.CrudRepository;

public interface VerificationCodeRepository extends CrudRepository<VerificationCode, String> {
}
