package by.dergachev.dao.impl;

import by.dergachev.annotations.Inject;
import by.dergachev.dao.UserDao;
import by.dergachev.service.UserService;

public class AdminDaoImpl implements UserDao {
    UserService userService;

    @Inject
    public AdminDaoImpl(UserService userService) {
        this.userService = userService;
    }
}
