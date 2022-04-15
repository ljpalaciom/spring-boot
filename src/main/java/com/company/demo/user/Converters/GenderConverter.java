package com.company.demo.user.Converters;

import com.company.demo.exception.AppException;
import com.company.demo.user.Gender;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.Arrays;

@Converter(autoApply = true)
public class GenderConverter implements AttributeConverter<Gender, String> {

    @Override
    public String convertToDatabaseColumn(Gender gender) {
        if (gender == null) {
            return null;
        }
        return gender.getCode();
    }

    @Override
    public Gender convertToEntityAttribute(String code) {
        if (code == null) {
            return null;
        }
        return Arrays.stream(Gender.values())
                .filter(gender -> gender.getCode().equals(code))
                .findFirst()
                .orElseThrow(() -> new AppException(String.format("Error converting %s to gender enum", code)));
    }
}
