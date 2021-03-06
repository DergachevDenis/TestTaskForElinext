package by.dergachev.service.impl;

import by.dergachev.annotations.Inject;
import by.dergachev.dao.UserDao;
import by.dergachev.service.MailSender;
import by.dergachev.service.UserService;

public class UserServiceImpl implements UserService {

    public final static String LOGIN_PHRASE = "User logged in";

    private MailSender mailSender;
    private UserDao userDao;

    @Inject
    public UserServiceImpl(MailSender mailSender, UserDao userDao) {
        this.mailSender = mailSender;
        this.userDao = userDao;
    }

    @Override
    public String loginAction() {
        return LOGIN_PHRASE;
    }
}
