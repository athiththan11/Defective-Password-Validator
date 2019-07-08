package org.wso2.sample.defective.password.validator.validate;

/**
 * interface for defective password validator implementations.
 */
public interface DefectivePasswordValidator {

    /**
     * init() method to initialize defective passwords.
     */
    void initValues();

    /**
     * password validation method against defective passwords.
     * 
     * @param credential password credential
     * @return
     */
    boolean validate(Object credential);
}
