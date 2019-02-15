package ea.kolokolov.service;

import javax.inject.Singleton;

@Singleton
public class HelloServiceImpl implements HelloService {
    @Override
    public String sayHello() {
        return "i say hello";
    }
}
