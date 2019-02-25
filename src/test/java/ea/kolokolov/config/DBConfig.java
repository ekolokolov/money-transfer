package ea.kolokolov.config;

import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.StringJoiner;

public class DBConfig {

    private static Connection connection;

    public static DSLContext context() throws SQLException {
        Connection connection = getConnection();
        return DSL.using(connection, SQLDialect.H2);
    }

    private static Connection getConnection() throws SQLException {
        if (connection == null) {
            connection = DriverManager.getConnection(
                    new StringJoiner(";")
                            .add("jdbc:h2:mem:default")
                            .add("DB_CLOSE_ON_EXIT=FALSE")
                            .add("INIT=RUNSCRIPT FROM 'classpath:sql/structure.sql'\\")
                            .add("RUNSCRIPT FROM 'classpath:sql/data.sql'").toString());
        }
        return connection;
    }

}
