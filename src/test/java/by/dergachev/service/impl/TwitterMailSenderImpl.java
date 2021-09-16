package by.dergachev.service.impl;

import by.dergachev.annotations.Inject;
import by.dergachev.dao.MessageDao;
import by.dergachev.dao.UserDao;
import by.dergachev.service.MailSender;

public class TwitterMailSenderImpl implements MailSender {
    private MessageDao messageDao;
    private UserDao userDao;

    @Inject
    public TwitterMailSenderImpl(MessageDao messageDao, UserDao userDao) {
        this.messageDao = messageDao;
        this.userDao = userDao;
    }


}
