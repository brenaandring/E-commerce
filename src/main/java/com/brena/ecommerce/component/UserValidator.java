package com.brena.ecommerce.component;

import com.brena.ecommerce.models.User;
import org.springframework.stereotype.Component;
import org.springframework.validation.*;

@Component
public class UserValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return User.class.equals(clazz);
    }

    @Override
    public void validate(Object object, Errors errors) {
        User user = (User) object;

        if (!user.getPasswordConfirmation().equals(user.getPassword())) {
            errors.rejectValue("passwordConfirmation", "Match");
        }
    }
}
