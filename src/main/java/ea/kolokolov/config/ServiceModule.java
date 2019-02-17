package ea.kolokolov.config;

import com.google.inject.AbstractModule;
import ea.kolokolov.service.HelloService;
import ea.kolokolov.service.HelloServiceImpl;
import ea.kolokolov.service.MoneyTransferService;
import ea.kolokolov.service.MoneyTransferServiceImpl;

public class ServiceModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(HelloService.class).to(HelloServiceImpl.class);
        bind(MoneyTransferService.class).to(MoneyTransferServiceImpl.class);
    }
}
