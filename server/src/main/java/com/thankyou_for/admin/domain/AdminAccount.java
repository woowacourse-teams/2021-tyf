package com.thankyou_for.admin.domain;

import com.thankyou_for.admin.exception.InvalidAdminException;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@Getter
public class AdminAccount {

    @Value("${admin.id}")
    private String id;
    @Value("${admin.password}")
    private String password;

    public void validateLogin(String id, String password) {
        if (!Objects.equals(this.id, id) || !Objects.equals(this.password, password)) {
            throw new InvalidAdminException();
        }
    }
}
