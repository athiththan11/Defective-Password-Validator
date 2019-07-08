package org.wso2.sample.defective.password.validator.constants;

import org.wso2.carbon.utils.CarbonUtils;

/**
 * constants of defective password validator component.
 */
public class DefectivePasswordValidatorConstants {

    public static final String PASSWORD_FILE_NAME = "passwords.txt";
    public static final String PASSWORD_DIR_NAME = "crackedPassword";

    public static final String PASSWORD_FILE_DIR = CarbonUtils.getCarbonHome()
            + "/repository/deployment/server/" + PASSWORD_DIR_NAME;
    public static final String PASSWORD_FILE_PATH = PASSWORD_FILE_DIR + "/" + PASSWORD_FILE_NAME;

    /**
     * error messages (enum) for password validation errors.
     */
    public enum ErrorMessages {

        ERROR_CODE_CRACKED_PASSWORD_VALIDATION("40002", "Your password contains defective keywords");

        private final String code;
        private final String message;

        ErrorMessages(String code, String message) {
            this.code = code;
            this.message = message;
        }

        public String getCode() {
            return code;
        }

        public String getMessage() {
            return message;
        }

        @Override
        public String toString() {
            return code + " - " + message;
        }
    }
}
