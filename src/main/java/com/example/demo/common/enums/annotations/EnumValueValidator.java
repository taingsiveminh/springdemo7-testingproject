package com.example.demo.common.enums.annotations;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class EnumValueValidator implements ConstraintValidator<ValidEnum,String> {
    private List<String> acceptedValues; //[USER,ADMIN]
    @Override
    public void initialize(ValidEnum constrainAnnotation) {
        acceptedValues = Arrays.stream(constrainAnnotation.enumClass().getEnumConstants()
        )
                .map(Enum::name)
                .collect(Collectors.toList());

    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        // return true , because we will let other annotation handles it
        if (value == null) {
            return true;
        }
        return acceptedValues.contains(value.toUpperCase());
    }
}
