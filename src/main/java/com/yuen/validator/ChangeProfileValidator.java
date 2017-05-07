package com.yuen.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.yuen.domain.User;
import com.yuen.service.UserService;

@Component
public class ChangeProfileValidator implements Validator {

	@Autowired
	private UserService userService;
	
	@Override
	public boolean supports(Class<?> cls) {
		return User.class.isAssignableFrom(cls);
	}

	@Override
	public void validate(Object o, Errors errors) {
		User user = (User) o;
		
		// Confirm password validation
		// empty
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "confirmPassword", "NotEmpty");
        
        // match
        if (!user.getConfirmPassword().equals(user.getPassword())) {
            errors.rejectValue("confirmPassword", "Match.user.confirmPassword");
        }
        
        
		// Username validation
        User dbUser = userService.findByUsername(user.getUsername());
		if (dbUser != null && dbUser.getId() != user.getId()) {
            errors.rejectValue("username", "Exists.user.username");
        }
	}

}
