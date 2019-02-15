package ea.kolokolov.config;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.servlet.GuiceServletContextListener;

/**
 * Example Guice Server configuration. Creates an Injector, and binds it to
 * whatever Modules we want. In this case, we use an anonymous Module, but other
 * modules are welcome as well.
 */
public class SampleConfig extends GuiceServletContextListener {
    @Override
    protected Injector getInjector() {
        return Guice.createInjector(
                new ServletBinding(),
                new ServiceModule()
        );
    }
}
