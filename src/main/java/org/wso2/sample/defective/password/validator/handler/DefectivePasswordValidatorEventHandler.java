package org.wso2.sample.defective.password.validator.handler;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.wso2.carbon.identity.core.bean.context.MessageContext;
import org.wso2.carbon.identity.event.IdentityEventConstants;
import org.wso2.carbon.identity.event.IdentityEventException;
import org.wso2.carbon.identity.event.event.Event;
import org.wso2.carbon.identity.event.handler.AbstractEventHandler;
import org.wso2.sample.defective.password.validator.constants.DefectivePasswordValidatorConstants;
import org.wso2.sample.defective.password.validator.util.Utils;
import org.wso2.sample.defective.password.validator.validate.DefectivePasswordValidator;
import org.wso2.sample.defective.password.validator.validate.impl.DefaultDefectivePasswordValidator;

/**
 * custom event handler implementation for defective password validation.
 */
public class DefectivePasswordValidatorEventHandler extends AbstractEventHandler {

    private static final Log log = LogFactory.getLog(DefectivePasswordValidator.class);

    @Override
    public void handleEvent(Event event) throws IdentityEventException {

        if (log.isDebugEnabled()) {
            log.debug("handle event invoked");
        }

        if (IdentityEventConstants.Event.PRE_UPDATE_CREDENTIAL.equals(event.getEventName())
                || IdentityEventConstants.Event.PRE_UPDATE_CREDENTIAL_BY_ADMIN.equals(event.getEventName())
                || IdentityEventConstants.Event.PRE_ADD_USER.equals(event.getEventName())) {

            if (log.isDebugEnabled()) {
                log.debug("entered event on updating password credentials");
            }

            Object credential = event.getEventProperties().get(IdentityEventConstants.EventProperty.CREDENTIAL);
            if (!DefaultDefectivePasswordValidator.getInstance().validate(credential)) {

                if (log.isDebugEnabled()) {
                    log.debug("entered password is defective");
                }

                throw Utils.handleEventException(
                        DefectivePasswordValidatorConstants.ErrorMessages.ERROR_CODE_CRACKED_PASSWORD_VALIDATION, null);
            }
        }
    }

    @Override
    public String getName() {
        return "defectivePasswordValidator";
    }

    @Override
    public int getPriority(MessageContext messageContext) {
        return 50;
    }
}
