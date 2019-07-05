package com.athiththan.sample.handler;

import com.athiththan.sample.constants.DefectivePasswordValidatorConstants;
import com.athiththan.sample.util.Utils;
import com.athiththan.sample.validate.impl.DefaultDefectivePasswordValidator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wso2.carbon.identity.core.bean.context.MessageContext;
import org.wso2.carbon.identity.event.IdentityEventConstants;
import org.wso2.carbon.identity.event.IdentityEventException;
import org.wso2.carbon.identity.event.event.Event;
import org.wso2.carbon.identity.event.handler.AbstractEventHandler;

/**
 * event handler implementation to take action whenever an event is published
 */
public class DefectivePasswordValidatorEventHandler extends AbstractEventHandler {

    private static final Logger log = LoggerFactory.getLogger(DefectivePasswordValidatorEventHandler.class);

    /**
     * events are handled inside this method. the current implementation is
     * subscribed to PRE_UPDATE_CREDENTIALS and PRE_UPDATE_CREDENTIALS_BY_ADMIN
     * events to validate the entered password against a list of defective passwords
     */
    @Override
    public void handleEvent(Event event) throws IdentityEventException {

        if (log.isDebugEnabled()) {
            log.debug("handleEvent() invoked");
        }

        if (IdentityEventConstants.Event.PRE_UPDATE_CREDENTIAL.equals(event.getEventName())
                || IdentityEventConstants.Event.PRE_UPDATE_CREDENTIAL_BY_ADMIN.equals(event.getEventName())) {

            Object credential = event.getEventProperties().get(IdentityEventConstants.EventProperty.CREDENTIAL);
            if (!DefaultDefectivePasswordValidator.getInstance().validate(credential)) {

                if (log.isDebugEnabled()) {
                    log.debug("given password is defective");
                }

                throw Utils.handleEventException(
                        DefectivePasswordValidatorConstants.ErrorMessages.ERROR_CODE_CRACKED_PASSWORD_VALIDATION, null);
            }
        }
    }

    /**
     * configure a name for the custom event handler implementation
     */
    @Override
    public String getName() {
        return "defectivePasswordValidator";
    }

    @Override
    public int getPriority(MessageContext messageContext) {
        return 50;
    }
}
