package com.nnk.springboot.security;

import org.passay.*;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Class that configure the validation of the password for the annotation @validPassword
 * implements {@link ConstraintValidator}
 *
 * @author Christine Duarte
 */
public class PasswordConstraintValidator implements ConstraintValidator<ValidPassword, String> {
    /**
     * Method that initializes the validator in preparation
     * for isValid(Object, ConstraintValidatorContext) calls.
     *
     * @param arg0
     */
    @Override
    public void initialize(ValidPassword arg0) {
    }

    /**
     * Method that implements the validation logic
     *
     * @param password – object to validate
     * @param context  – context in which the constraint is evaluated
     * @return false if value does not pass the constraint
     */
    @Override
    public boolean isValid(String password, ConstraintValidatorContext context) {
        PasswordValidator validator = new PasswordValidator(
                Arrays.asList(
                        // at least 8 characters
                        new LengthRule(8, 125),

                        // at least one upper-case character
                        new CharacterRule(EnglishCharacterData.UpperCase, 1),

                        // at least one lower-case character
                        new CharacterRule(EnglishCharacterData.LowerCase, 1),

                        // at least one digit character
                        new CharacterRule(EnglishCharacterData.Digit, 1),

                        // at least one symbol (special character)
                        new CharacterRule(EnglishCharacterData.Special, 1),

                        // no whitespace
                        new WhitespaceRule()

                ));
        RuleResult result = validator.validate(new PasswordData(password));
        if (result.isValid()) {
            return true;
        }
        List<String> messages = validator.getMessages(result);

        String messageTemplate = messages.stream()
                .collect(Collectors.joining(","));
        context.buildConstraintViolationWithTemplate(messageTemplate)
                .addConstraintViolation()
                .disableDefaultConstraintViolation();
        return false;
    }
}
