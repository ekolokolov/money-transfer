package ea.kolokolov.config;

import com.google.inject.AbstractModule;
import ea.kolokolov.service.*;

public class ServiceModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(UserInfoService.class).to(UserInfoServiceImpl.class);
        bind(AccountService.class).to(AccountServiceImpl.class);
        bind(TransactionService.class).to(TransactionServiceImpl.class);
    }
}
