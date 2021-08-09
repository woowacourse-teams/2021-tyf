package com.example.tyfserver.auth.repository;

import com.example.tyfserver.auth.domain.CodeResendCoolTime;
import org.springframework.data.repository.CrudRepository;

public interface CodeResendCoolTimeRepository extends CrudRepository<CodeResendCoolTime, String> {
}
