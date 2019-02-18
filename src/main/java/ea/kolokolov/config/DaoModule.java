package ea.kolokolov.config;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import ea.kolokolov.service.AccountDao;
import ea.kolokolov.service.UserInfoDaoImpl;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.codegen.GenerationTool;
import org.jooq.impl.DSL;
import org.jooq.meta.jaxb.Configuration;

import javax.xml.bind.JAXB;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.StringJoiner;

public class DaoModule extends AbstractModule {

    private DSLContext context;

    @Override
    protected void configure() {
        bind(AccountDao.class).to(UserInfoDaoImpl.class);
    }

    @Provides
    public DSLContext getDslContext(Connection connection) {
        if (context == null) {
            context = DSL.using(connection, SQLDialect.H2);
        }
//        init();
        return context;
    }

    @Provides
    public Connection getDbConnection() throws SQLException {
        return DriverManager.getConnection(
                new StringJoiner(";")
                        .add("jdbc:h2:mem:default")
                        .add("INIT=RUNSCRIPT FROM 'classpath:sql/structure.sql'\\")
                        .add("RUNSCRIPT FROM 'classpath:sql/data.sql'")
                        .add("DB_CLOSE_ON_EXIT=FALSE").toString());
    }

    private void init() {
        Configuration configuration = JAXB.unmarshal(new File("jook/jooq.xml"), Configuration.class);
        try {
            GenerationTool.generate(configuration);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
