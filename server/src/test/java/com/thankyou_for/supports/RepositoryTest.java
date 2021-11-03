package com.thankyou_for.supports;

import com.thankyou_for.common.config.JpaAuditingConfig;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(value = {ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@ActiveProfiles("test")
@Import(JpaAuditingConfig.class)
@DataJpaTest
public @interface RepositoryTest {
}
