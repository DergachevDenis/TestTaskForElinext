package by.dergachev;

import by.dergachev.dao.MessageDao;
import by.dergachev.dao.UserDao;
import by.dergachev.dao.impl.MessageDaoImpl;
import by.dergachev.dao.impl.UserDaoImpl;
import by.dergachev.injector.Injector;
import by.dergachev.injector.impl.InjectorImpl;
import by.dergachev.service.MailSender;
import by.dergachev.service.UserService;
import by.dergachev.service.impl.MailSenderImpl;
import by.dergachev.service.impl.UserServiceImpl;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class App {
    public static void main(String[] args) {

        MessageDaoImpl messageDao = new MessageDaoImpl();
        List<Object> list = new ArrayList<>();
        list.add(messageDao);
        try {
            MailSender mailSender = (MailSender) MailSenderImpl.class.getConstructors()[1].newInstance(list.toArray());
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }


        InjectorImpl injector = new InjectorImpl();
        injector.bind(UserService.class, UserServiceImpl.class);
        injector.bind(UserDao.class, UserDaoImpl.class);
        injector.bind(MessageDao.class, MessageDaoImpl.class);
        injector.bind(MailSender.class, MailSenderImpl.class);
        try {
         UserService userService = injector.getBean(UserService.class);
         userService.print();
        } catch (InvocationTargetException | InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        System.out.println("test");
    }
}
