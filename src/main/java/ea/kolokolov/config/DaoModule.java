package ea.kolokolov.config;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import ea.kolokolov.service.AccountDao;
import ea.kolokolov.service.AccountDaoImpl;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;

import java.sql.DriverManager;
import java.sql.SQLException;

public class DaoModule extends AbstractModule {

    private DSLContext context;

    @Override
    protected void configure() {
        bind(AccountDao.class).to(AccountDaoImpl.class);
    }

    @Provides
    public DSLContext getDslContext() throws SQLException {
        if (context == null) {
            context = DSL.using(DriverManager.getConnection("jdbc:h2:mem:default;" +
                    "INIT=runscript from '/home/evgeny/test/maneytransfer/src/main/resources/sql/structure.sql'\\;" +
                    "runscript from '/home/evgeny/test/maneytransfer/src/main/resources/sql/data.sql';" +
                    "DB_CLOSE_ON_EXIT=FALSE"), SQLDialect.H2);
        }
        return context;
    }
}
