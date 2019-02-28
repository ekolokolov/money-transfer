package ea.kolokolov;

import com.google.inject.servlet.GuiceFilter;
import ea.kolokolov.config.EmptyServlet;
import ea.kolokolov.config.GeneralConfig;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;

import javax.servlet.DispatcherType;
import java.util.EnumSet;

public class Main {

    static final int PORT = 8080;

    public static void main(String[] args) throws Exception {
        Server server = getServer();
        server.start();
    }

    static Server getServer() {
        Server server = new Server(PORT);
        ServletContextHandler root = new ServletContextHandler(server, "/", ServletContextHandler.SESSIONS);

        root.addEventListener(new GeneralConfig());
        root.addFilter(GuiceFilter.class, "/*", EnumSet.of(DispatcherType.REQUEST));
        root.addServlet(EmptyServlet.class, "/*");
        return server;
    }
}
