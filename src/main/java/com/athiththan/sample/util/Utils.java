package com.athiththan.sample.util;

import com.athiththan.sample.constants.DefectivePasswordValidatorConstants;

import org.apache.commons.lang.StringUtils;
import org.wso2.carbon.identity.base.IdentityException;
import org.wso2.carbon.identity.event.IdentityEventException;

/**
 * utility class for exceptions
 */
public class Utils {

    public static IdentityEventException handleEventException(DefectivePasswordValidatorConstants.ErrorMessages error,
            String data) throws IdentityEventException {
        String errorDescription;
        if (StringUtils.isNotBlank(data)) {
            errorDescription = String.format(error.getMessage(), data);
        } else {
            errorDescription = error.getMessage();
        }
        return IdentityException.error(IdentityEventException.class, error.getCode(), errorDescription);
    }

    public static IdentityEventException handleEventException(DefectivePasswordValidatorConstants.ErrorMessages error,
            String data, Throwable throwable) throws IdentityEventException {
        String errorDescription;
        if (StringUtils.isNotBlank(data)) {
            errorDescription = String.format(error.getMessage(), data);
        } else {
            errorDescription = error.getMessage();
        }
        return IdentityException.error(IdentityEventException.class, error.getCode(), errorDescription, throwable);
    }
}