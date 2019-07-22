package com.vironit.mwallet.services.impl;

import com.vironit.mwallet.dao.UserDao;
import com.vironit.mwallet.models.entity.User;
import com.vironit.mwallet.services.UserService;
import com.vironit.mwallet.services.exception.LoginAlreadyDefinedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@SuppressWarnings("SpringJavaAutowiredFieldsWarningInspection")
@Service
@Transactional(value = "hibernateTransactionManager")
public class UserServiceImpl implements UserDetailsService, UserService {

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private UserDao userDao;

    @Override
    public User findById(int id) {
        return userDao.findById(id);
    }

    @Override
    public User findByLogin(String login) {
        return userDao.findByLogin(login);
    }

    @Override
    public void save(User user) throws LoginAlreadyDefinedException {
        if (findByLogin(user.getLogin()) != null) {
            throw new LoginAlreadyDefinedException();
        }
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userDao.save(user);
    }

    @Override
    public void delete(User user) {
        userDao.delete(user);
    }

    @Override
    public void update(User user) throws LoginAlreadyDefinedException {
        User currentUser = userDao.findById(user.getId());
        String oldPass = currentUser.getPassword();
        String oldLogin = currentUser.getLogin();
        if (!user.getLogin().equals(oldLogin)
                && findByLogin(user.getLogin()) != null) {
            throw new LoginAlreadyDefinedException();
        }
        if (!user.getPassword().equals(oldPass) && !user.getPassword().isEmpty()) {
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        } else {
            user.setPassword(oldPass);
        }
        userDao.update(user);
    }

    @Override
    public List<User> findAll() {
        return userDao.findAll();
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        User user = findByLogin(username);
        Set<GrantedAuthority> roles = new HashSet<>();
        roles.add(new SimpleGrantedAuthority(user.getRole().toString()));
        return new org.springframework.security.core.userdetails.User(user.getLogin(),
                user.getPassword(),
                roles);
    }
}
