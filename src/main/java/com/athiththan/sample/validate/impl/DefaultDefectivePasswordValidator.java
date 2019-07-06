package com.athiththan.sample.validate.impl;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import com.athiththan.sample.constants.DefectivePasswordValidatorConstants;
import com.athiththan.sample.validate.DefectivePasswordValidator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * a singleton class implementation for defective password validations and to
 * initialize defective password lists.
 */
public class DefaultDefectivePasswordValidator implements DefectivePasswordValidator {

    private List<String> crackedPasswords = new ArrayList<>();
    private static DefaultDefectivePasswordValidator dPasswordValidator = new DefaultDefectivePasswordValidator();

    private static final Logger log = LoggerFactory.getLogger(DefaultDefectivePasswordValidator.class);

    private DefaultDefectivePasswordValidator() {
    }

    public static DefaultDefectivePasswordValidator getInstance() {
        return dPasswordValidator;
    }

    /**
     * initialize defective password values from the given text file
     */
    @Override
    public void initValues() {
        try {
            crackedPasswords = Files.readAllLines(Paths.get(DefectivePasswordValidatorConstants.PASSWORD_FILE_PATH),
                    StandardCharsets.UTF_8);
        } catch (IOException e) {
            log.error("Exception occured while reading and initializing values from "
                    + DefectivePasswordValidatorConstants.PASSWORD_FILE_NAME, e);
        }
    }

    /**
     * validate method
     */
    @Override
    public boolean validate(Object credential) {
        return !crackedPasswords.contains((String) credential);
    }
}
