package com.example.tyfserver.auth.repository;

import com.example.tyfserver.auth.domain.VerificationCode;
import org.springframework.data.repository.CrudRepository;

public interface VerificationCodeRepository extends CrudRepository<VerificationCode, String> {
}
