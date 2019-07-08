# Defective Password Validation - Event Handler

A sample event handler implementation to validate an entered password against a set of defective passwords stored in a text file.

## Usage

The sample supports to validate the entered passwords when creating a user and when updating (renewing) passwords.

The subscribed events are

* PRE_UPDATE_CREDENTIAL
* PRE_UPDATE_CREDENTIAL_BY_ADMIN
* PRE_ADD_USER

If the entered password is not listed as a defective password, then the flow will continue on. But, if the validation fails, an error message `"Your password contains defective keywords"` will be displayed.

## Prepare

### Defective Passwords Folder & Text File

> **NOTE:**
> 
> If the directory name (crackedPassword) or the file name (passwords.txt) is different than the given (below) configurations, please do follow the [guide on changes in configuration](#changes-in-configuration) after following this to make necessary changes on the sample implementation to direct the paths.

Create a text file named `passwords.txt` and add all your defective passwords.
For example, the following values can be used as a list of defective passwords to validate ...

* admin
* password
* helloWorld

Create a directory named `crackedPassword` inside `<IS_HOME>/repository/deployment/server/` path and place the above created `passwords.txt`.

#### Sync

> **NOTE:**
> 
> WSO2 prefers to use SFS (Shared File System) than rsync to enable artifact synchronization on production

Enable sync mechanism on the `<IS_HOME>/repository/deployment/server` directory to sync the artifacts and the server folder with other working nodes. You can use either rsync or Shared File System to sync your artifacts.

### Changes in configuration

> *This section is refered only if there are any changes in the file name (passwords.txt) or the directory name (crackePassword) than default*

Open the sample implementation and navigate to `org.wso2.sample.defective.password.validator.constants` package and make the changes to the following constants inside the `DefectivePasswordValidatorConstants` class

* PASSWORD_FILE_NAME: this constant is used to define the file-name (default: passwords.txt)
* PASSWORD_DIR_NAME: this constant is used to define the directory-name (default: crackedPassword)

### WSO2 Identity Server

Open the `<IS_HOME>/repository/conf/identity/identity-event.properties` file and add the following lines at the bottom of the properties file to subscribe for password-update events.

> * Please update the module number value according to your `identity-event.properties` file (last used value + 1).
> * The given `module.name` value is the same value which configured in the `getName()` method in `DefectivePasswordValidatorEventHandler` class.

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
