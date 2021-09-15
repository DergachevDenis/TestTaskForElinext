package by.dergachev.service.impl;

import by.dergachev.annotations.Inject;
import by.dergachev.dao.UserDao;
import by.dergachev.service.MailSender;
import by.dergachev.service.UserService;

public class UserServiceImpl implements UserService {

    private MailSender mailSender;
    private UserDao userDao;

    public UserServiceImpl() {
    }

    @Inject
    public UserServiceImpl(MailSender mailSender, UserDao userDao) {
        this.mailSender = mailSender;
        this.userDao = userDao;
    }

    @Override
    public void print() {
        System.out.println(this.getClass().getSimpleName());
        System.out.println(this.mailSender.getClass().getSimpleName());
        System.out.println(this.userDao.getClass().getSimpleName());
    }
}
