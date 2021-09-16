package by.dergachev.service.impl;

import by.dergachev.annotations.Inject;
import by.dergachev.dao.MessageDao;
import by.dergachev.service.MailSender;

public class MailSenderImpl implements MailSender {

    private MessageDao messageDao;

    @Inject
    public MailSenderImpl(MessageDao messageDao) {
        this.messageDao = messageDao;
    }
}
