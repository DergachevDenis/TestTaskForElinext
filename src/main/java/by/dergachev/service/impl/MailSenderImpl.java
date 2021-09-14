package by.dergachev.service.impl;

import by.dergachev.dao.MessageDao;
import by.dergachev.service.MailSender;

public class MailSenderImpl implements MailSender {
    private MessageDao messageDao;

    public MailSenderImpl() {
    }

    public MailSenderImpl(MessageDao messageDao) {
        this.messageDao = messageDao;
    }

    @Override
    public void print() {
        System.out.println(this.getClass().getSimpleName());
        System.out.println(this.messageDao.getClass().getSimpleName());
    }
}
