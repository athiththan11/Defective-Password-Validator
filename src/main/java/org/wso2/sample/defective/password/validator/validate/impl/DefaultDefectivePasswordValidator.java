package org.wso2.sample.defective.password.validator.validate.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.wso2.sample.defective.password.validator.constants.DefectivePasswordValidatorConstants;
import org.wso2.sample.defective.password.validator.validate.DefectivePasswordValidator;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * default implementation of defective password validator.
 */
public class DefaultDefectivePasswordValidator implements DefectivePasswordValidator {

    private List<String> crackedPasswords = new ArrayList<>();
    private static DefaultDefectivePasswordValidator passwordValidator = new DefaultDefectivePasswordValidator();

    private static final Log log = LogFactory.getLog(DefectivePasswordValidator.class);

    private DefaultDefectivePasswordValidator() {
    }

    public static DefectivePasswordValidator getInstance() {
        return passwordValidator;
    }

    /**
     * initialize defective password values from the given text file.
     */
    public void initValues() {
        try {
            crackedPasswords = Files.readAllLines(Paths.get(DefectivePasswordValidatorConstants.PASSWORD_FILE_PATH),
                    StandardCharsets.UTF_8);
        } catch (IOException e) {
            log.error("Exception occured while initializing cracked password values in "
                    + DefectivePasswordValidatorConstants.PASSWORD_FILE_NAME, e);
        }
    }

    public boolean validate(Object credential) {
        return !crackedPasswords.contains((String) credential);
    }
}
