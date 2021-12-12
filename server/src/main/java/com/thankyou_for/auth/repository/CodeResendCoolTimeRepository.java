package com.thankyou_for.auth.repository;

import com.thankyou_for.auth.domain.CodeResendCoolTime;
import org.springframework.data.repository.CrudRepository;

public interface CodeResendCoolTimeRepository extends CrudRepository<CodeResendCoolTime, String> {
}
