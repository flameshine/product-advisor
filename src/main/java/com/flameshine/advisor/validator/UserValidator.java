package com.flameshine.advisor.validator;

import org.springframework.stereotype.Component;
import org.springframework.context.annotation.Primary;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import lombok.RequiredArgsConstructor;
import lombok.NonNull;

import com.flameshine.advisor.entity.User;

@Component
@Primary
@RequiredArgsConstructor
public class UserValidator implements Validator {

    @Override
    public boolean supports(@NonNull Class<?> type) {
        return User.class.equals(type);
    }

    @Override
    public void validate(@NonNull Object target, @NonNull Errors errors) {

        var user = (User) target;

        if (!user.getPassword().equals(user.getPasswordConfirmation())) {
            errors.rejectValue("passwordConfirmation", "error.user", "Password mismatch");
        }
    }
}