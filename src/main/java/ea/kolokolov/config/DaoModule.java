package ea.kolokolov.config;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import ea.kolokolov.dao.*;
import org.eclipse.jetty.util.Loader;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.codegen.GenerationTool;
import org.jooq.impl.DSL;
import org.jooq.meta.jaxb.Configuration;

import javax.xml.bind.JAXB;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.StringJoiner;

public class DaoModule extends AbstractModule {

    private DSLContext context;

    @Override
    protected void configure() {
        bind(UserDao.class).to(UserDaoImpl.class);
        bind(AccountDao.class).to(AccountDaoImpl.class);
        bind(TransactionDao.class).to(TransactionDaoImpl.class);
        initDb();
//        jooQInit();
    }

    @Provides
    public DSLContext getDslContext(Connection connection, Properties properties) {
        if (context == null) {
            context = DSL.using(connection, SQLDialect.valueOf(properties.getProperty("db.dialect")));
        }
        return context;
    }

    @Provides
    @Singleton
    public Properties getProperties() throws IOException {
        URL resource = Loader.getResource("app.properties");
        FileInputStream fileInputStream = new FileInputStream(resource.getPath());
        Properties properties = new Properties();
        properties.load(fileInputStream);
        return properties;
    }

    @Provides
    public Connection getDbConnection(Properties properties) throws SQLException {
        return DriverManager.getConnection(
                new StringJoiner(";")
                        .add(properties.getProperty("db.connectionString"))
                        .add("DB_CLOSE_ON_EXIT=" + properties.getProperty("db.closeOnExit")).toString());
    }


    private void initDb() {
        try {
            Properties properties = getProperties();
            DriverManager.getConnection(
                    new StringJoiner(";")
                            .add(properties.getProperty("db.connectionString"))
                            .add("INIT=RUNSCRIPT FROM 'classpath:" + properties.getProperty("db.script.schema") + "'\\")
                            .add("RUNSCRIPT FROM 'classpath:" + properties.getProperty("db.script.data") + "'").toString());
        } catch (SQLException | IOException e) {
            System.exit(1);
        }
    }

    private void jooQInit() {
        Configuration configuration = JAXB.unmarshal(new File("jook/jooq.xml"), Configuration.class);
        try {
            GenerationTool.generate(configuration);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
