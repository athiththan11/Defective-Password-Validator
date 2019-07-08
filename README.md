# Defective Password Validator

A custom event handler implementation to validate password against a set of defective passwords stored in a text file.

## Prepare

### Defective Passwords Text File

Create a text file named `passwords.txt` and add the following lines. The following values will be used as a list of defective passwords to validate ...

* admin
* password
* helloWorld

Create a directory named `crackedPassword` inside `<IS_HOME>/repository/deployment/server/` path and place the above created `passwords.txt`.

### WSO2 Identity Server

Open the `<IS_HOME>/repository/conf/identity/identity-event.properties` file and add the following lines at the bottom of the properties file to subscribe for password-update events.

> Please update the digit value according to your `identity-event.properties` file (last used value + 1).

> The given `module.name` value is the same value which configured in the `getName()` method in `DefectivePasswordValidatorEventHandler` class.

```properties
module.name.13=defectivePasswordValidator
defectivePasswordValidator.subscription.1=PRE_UPDATE_CREDENTIAL
defectivePasswordValidator.subscription.2=PRE_UPDATE_CREDENTIAL_BY_ADMIN
defectivePasswordValidator.subscription.3=PRE_ADD_USER

```

## Build

Build the project by running ...

```shell
mvn clean package
```

## Deploy

After a successful build, copy the `org.wso2.sample.defective.password.validator-1.0.0.jar` artifact from the `target` folder and paste it inside `<IS HOME>/repository/components/dropins` folder.

## Run

Start your WSO2 Identity Server by executing the command from your `<IS HOME>/bin` folder

```shell
sh wso2server.sh
```
