package ea.kolokolov.config;

import com.google.inject.servlet.ServletModule;
import com.sun.jersey.guice.spi.container.servlet.GuiceContainer;
import ea.kolokolov.endpoint.AccountEndpoint;
import ea.kolokolov.endpoint.TransactionEndpoint;
import ea.kolokolov.endpoint.UserEndpoint;
import org.codehaus.jackson.jaxrs.JacksonJsonProvider;

import javax.ws.rs.ext.MessageBodyReader;
import javax.ws.rs.ext.MessageBodyWriter;
import java.util.HashMap;
import java.util.Map;

public class ServletBinding extends ServletModule {

    @Override
    protected void configureServlets() {
        /* bind the REST resources */
        bind(UserEndpoint.class);
        bind(TransactionEndpoint.class);
        bind(AccountEndpoint.class);

        bind(MessageBodyReader.class).to(JacksonJsonProvider.class);
        bind(MessageBodyWriter.class).to(JacksonJsonProvider.class);
        Map<String, String> initParams = new HashMap<String, String>();
//        initParams.put("com.sun.jersey`.config.feature.Trace", "true");
        initParams.put("com.sun.jersey.config.properties.packages",
                "ausdbsoccer.server.resources");
        serve("*").with(
                GuiceContainer.class,
                initParams);
    }
}
