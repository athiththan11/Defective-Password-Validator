package com.athiththan.sample.constants;

import org.wso2.carbon.utils.CarbonUtils;

/**
 * this class contains all defective-password related constants and paths The
 * filename is "password.txt" and which is placed inside the
 * IS_HOME/repository/deployment/server/defective
 */
public class DefectivePasswordValidatorConstants {

    public static final String PASSWORD_FILE_NAME = "passwords.txt";
    public static final String PASSWORD_FILE_DIR = CarbonUtils.getCarbonHome()
            + "/repository/deployment/server/defective";
    public static final String PASSWORD_FILE_PATH = PASSWORD_FILE_DIR + "/" + PASSWORD_FILE_NAME;

    public enum ErrorMessages {

        // error message
        ERROR_CODE_CRACKED_PASSWORD_VALIDATION("40001",
                "Password validation failed. Your password contains defective keywords");

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