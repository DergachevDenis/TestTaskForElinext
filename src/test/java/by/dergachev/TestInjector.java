package by.dergachev;

import by.dergachev.dao.MessageDao;
import by.dergachev.dao.UserDao;
import by.dergachev.dao.impl.MessageDaoImpl;
import by.dergachev.dao.impl.UserDaoImpl;
import by.dergachev.injector.Injector;
import by.dergachev.injector.impl.InjectorImpl;
import by.dergachev.provider.Provider;
import by.dergachev.service.MailSender;
import by.dergachev.service.UserService;
import by.dergachev.service.impl.MailSenderImpl;
import by.dergachev.service.impl.UserServiceImpl;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class TestInjector {

    private Injector injector;

    @Before
    public void initInjector(){
        this.injector = new InjectorImpl();

        injector.bind(UserService.class, UserServiceImpl.class);
        injector.bind(MailSender.class, MailSenderImpl.class);
        injector.bind(MessageDao.class, MessageDaoImpl.class);
        injector.bind(UserDao.class, UserDaoImpl.class);
    }
    @Test
    public void testInjectorCreatesInstance() {

        Provider<UserService> provider = injector.getProvider(UserService.class);
        assertNotNull(provider);
        assertNotNull(provider.getInstance());
        assertSame(provider.getInstance().getClass(), UserServiceImpl.class);
    }

    @Test
    public void testInjectorCreatesPrototype() {

        Provider<UserService> providerOne = injector.getProvider(UserService.class);
        Provider<UserService> providerTwo = injector.getProvider(UserService.class);

        assertNotSame(providerOne.getInstance(), providerTwo.getInstance());
    }

    @Test
    public void testInjectorCreatesSingleton() {

        injector.bindSingleton(UserService.class, UserServiceImpl.class);

        Provider<UserService> providerOne = injector.getProvider(UserService.class);
        Provider<UserService> providerTwo = injector.getProvider(UserService.class);

        assertSame(providerOne.getInstance(), providerTwo.getInstance());
    }
}
