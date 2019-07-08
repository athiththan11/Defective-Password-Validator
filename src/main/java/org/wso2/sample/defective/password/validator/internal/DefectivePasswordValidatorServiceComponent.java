package org.wso2.sample.defective.password.validator.internal;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.osgi.service.component.ComponentContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.wso2.carbon.identity.event.handler.AbstractEventHandler;
import org.wso2.sample.defective.password.validator.constants.DefectivePasswordValidatorConstants;
import org.wso2.sample.defective.password.validator.handler.DefectivePasswordValidatorEventHandler;
import org.wso2.sample.defective.password.validator.validate.DefectivePasswordValidator;
import org.wso2.sample.defective.password.validator.validate.impl.DefaultDefectivePasswordValidator;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;

/**
 * defective password validator service component. used to register and activate
 * the service component with osgi bundles
 */
@Component(
        name = "org.wso2.sample.defective.password.validator.internal.component",
        service = DefectivePasswordValidatorServiceComponent.class,
        immediate = true
)
public class DefectivePasswordValidatorServiceComponent {

    private static final Log log = LogFactory.getLog(DefectivePasswordValidator.class);

    @Activate
    protected void activate(ComponentContext context) {

        if (log.isDebugEnabled()) {
            log.debug("activating service component");
        }

        try {

            context.getBundleContext().registerService(AbstractEventHandler.class.getName(),
                    new DefectivePasswordValidatorEventHandler(), null);

            // initialize cracked password values
            DefaultDefectivePasswordValidator.getInstance().initValues();

            // initialize watchservice on directory
            initializeWatchService();

        } catch (Throwable e) {
            log.error("Error occured while initializing service component : DefectivePasswordValidator.", e);
        }
    }

    @Deactivate
    protected void deactivate(ComponentContext context) {
    }

    private void initializeWatchService() {

        if (log.isDebugEnabled()) {
            log.debug("intializing watch service");
        }

        WatchService watchService = null;
        try {
            watchService = FileSystems.getDefault().newWatchService();
            Path path = Paths.get(DefectivePasswordValidatorConstants.PASSWORD_FILE_DIR);
            path.register(watchService, StandardWatchEventKinds.ENTRY_MODIFY);
            WatchKey key;
            while ((key = watchService.take()) != null) {

                if (log.isDebugEnabled()) {
                    log.debug("Watch Service :: " + key.pollEvents().get(0).context()
                            + " :: " + key.pollEvents().get(0).kind());
                }

                // initialize new values
                DefaultDefectivePasswordValidator.getInstance().initValues();

                key.reset();
            }

        } catch (IOException e) {
            log.error("Exception occured while initializing watch service", e);
        } catch (InterruptedException e) {
            log.error("Interruption exception occured while operating watch service", e);
        }
    }
}
