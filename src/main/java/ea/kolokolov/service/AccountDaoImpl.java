package ea.kolokolov.service;

import ea.kolokolov.adapter.AccountAdapter;
import ea.kolokolov.data.Account;
import ea.kolokolov.jooq.tables.records.AccountRecord;
import org.jooq.DSLContext;
import org.jooq.codegen.GenerationTool;
import org.jooq.meta.jaxb.Configuration;

import javax.inject.Inject;
import javax.xml.bind.JAXB;
import java.io.File;

import static ea.kolokolov.jooq.Tables.ACCOUNT;


public class AccountDaoImpl implements AccountDao {

    private DSLContext context;
    private AccountAdapter adapter = new AccountAdapter();

    @Inject
    public AccountDaoImpl(DSLContext context) {
        this.context = context;
//        init();
    }

    @Override
    public Account getAccount(Integer id) {
        AccountRecord accountRecord = context.selectFrom(ACCOUNT)
                .where(ACCOUNT.ID.eq(id))
                .fetchAny();
        return adapter.createAccount(accountRecord);
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
