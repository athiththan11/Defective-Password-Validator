package com.athiththan.sample.internal;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;

import com.athiththan.sample.constants.DefectivePasswordValidatorConstants;
import com.athiththan.sample.handler.DefectivePasswordValidatorEventHandler;
import com.athiththan.sample.validate.impl.DefaultDefectivePasswordValidator;

import org.osgi.service.component.ComponentContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wso2.carbon.identity.event.handler.AbstractEventHandler;

/**
 * osgi service component implementation to register the custom event handler
 * with the bundle context.
 */
@Component(name = "com.athiththan.sample.internal.component", service = DefectivePasswordValidatorServiceComponent.class, immediate = true)
public class DefectivePasswordValidatorServiceComponent {

    private static final Logger log = LoggerFactory.getLogger(DefectivePasswordValidatorServiceComponent.class);

    /**
     * osgi bundle activation method to activate and register the custom event
     * handler bundle implementation with the context
     * 
     * @param context
     */
    @Activate
    protected void activate(ComponentContext context) {

        try {

            context.getBundleContext().registerService(AbstractEventHandler.class.getName(),
                    new DefectivePasswordValidatorEventHandler(), null);

            // initialize all defective passwords from the text file
            DefaultDefectivePasswordValidator.getInstance().initValues();

            // initialize watchservice on /defective directory
            initializeWatchService();

        } catch (Throwable e) {
            log.error("Error occured while initializing service component", e);
        }
    }

    @Deactivate
    protected void deactivate(ComponentContext context) {

    }

    /**
     * Java WatchService implementation to watch for entry modifications inside a
     * folder. in this sample, the watch service will be watching the /defective
     * directory and whenever an update is made on the "password.txt", it will
     * trigger and initialize the new values
     */
    private void initializeWatchService() {

        WatchService watchService = null;
        try {
            watchService = FileSystems.getDefault().newWatchService();
            Path path = Paths.get(DefectivePasswordValidatorConstants.PASSWORD_FILE_DIR);
            path.register(watchService, StandardWatchEventKinds.ENTRY_MODIFY);
            WatchKey key;
            while ((key = watchService.take()) != null) {
                for (WatchEvent<?> event : key.pollEvents()) {

                    if (log.isDebugEnabled()) {
                        log.debug("watch service on " + event.context() + " :: " + event.kind());
                    }

                    // initialize new values
                    DefaultDefectivePasswordValidator.getInstance().initValues();
                }
                key.reset();
            }

        } catch (IOException e) {
            log.error("Exception occured while initializing watch-service on "
                    + DefectivePasswordValidatorConstants.PASSWORD_FILE_NAME, e);
        } catch (InterruptedException e) {
            log.error("Interrupted exception occured while watching the directory", e);
        }
    }
}
