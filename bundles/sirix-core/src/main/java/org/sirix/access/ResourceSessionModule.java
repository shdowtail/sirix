package org.sirix.access;

import dagger.Module;
import dagger.Provides;
import org.sirix.dagger.ResourceSessionScope;
import org.sirix.io.IOStorage;
import org.sirix.io.Reader;
import org.sirix.io.StorageType;
import org.sirix.page.PageReference;
import org.sirix.page.UberPage;

import java.util.concurrent.Semaphore;

/**
 * A module with all the common bindings between resource sessions.
 *
 * @author Joao Sousa
 */
@Module
public interface ResourceSessionModule {

    @Provides
    @ResourceSessionScope
    static IOStorage ioStorage(final ResourceConfiguration resourceConfiguration) {
        return StorageType.getStorage(resourceConfiguration);
    }

    @Provides
    @ResourceSessionScope
    static Semaphore writeLock(final WriteLocksRegistry registry, final ResourceConfiguration resourceConfiguration) {
        return registry.getWriteLock(resourceConfiguration.getResource());
    }

    @Provides
    @ResourceSessionScope
    static UberPage rootPage(final IOStorage storage) {
        final UberPage uberPage;
        if (storage.exists()) {
            try (final Reader reader = storage.createReader()) {
                final PageReference firstRef = reader.readUberPageReference();
                if (firstRef.getPage() == null) {
                    uberPage = (UberPage) reader.read(firstRef, null);
                } else {
                    uberPage = (UberPage) firstRef.getPage();
                }
            }
        } else {
            // Bootstrap uber page and make sure there already is a root node.
            uberPage = new UberPage();
        }
        return uberPage;
    }

}
