package ea.kolokolov.config;

import com.google.inject.AbstractModule;
import ea.kolokolov.service.MoneyTransferService;
import ea.kolokolov.service.MoneyTransferServiceImpl;
import ea.kolokolov.service.UserInfoService;
import ea.kolokolov.service.UserInfoServiceImpl;

public class ServiceModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(UserInfoService.class).to(UserInfoServiceImpl.class);
        bind(MoneyTransferService.class).to(MoneyTransferServiceImpl.class);
    }
}
