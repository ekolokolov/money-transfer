package ea.kolokolov.config;

import com.google.inject.servlet.ServletModule;
import com.sun.jersey.guice.spi.container.servlet.GuiceContainer;
import ea.kolokolov.endpoint.AccountEndpoint;
import org.codehaus.jackson.jaxrs.JacksonJsonProvider;

import javax.ws.rs.ext.MessageBodyReader;
import javax.ws.rs.ext.MessageBodyWriter;
import java.util.HashMap;
import java.util.Map;

public class ServletBinding extends ServletModule {

    @Override
    protected void configureServlets() {
        /* bind the REST resources */
        bind(AccountEndpoint.class);

        /* bind jackson converters for JAXB/JSON serialization */
        bind(MessageBodyReader.class).to(JacksonJsonProvider.class);
        bind(MessageBodyWriter.class).to(JacksonJsonProvider.class);
        Map<String, String> initParams = new HashMap<String, String>();
        serve("*").with(
                GuiceContainer.class,
                initParams);
    }
}
