package com.vironit.mWallet.services;

import com.vironit.mWallet.dao.UserDao;
import com.vironit.mWallet.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserService implements UserDetailsService {

    private UserDao userDao;

    public UserService() {
    }

    @Autowired
    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    public User findById(int id) {
        return userDao.findById(id);
    }

    @SuppressWarnings("WeakerAccess")
    public User findByLogin(String login) {
        return userDao.findByLogin(login);
    }

    public void save(User user) {
        userDao.save(user);
    }

    public void delete(User user) {
        userDao.delete(user);
    }

    public void update(User user) {
        userDao.update(user);
    }

    public List<User> findAll() {
        return userDao.findAll();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = findByLogin(username);
        Set<GrantedAuthority> roles = new HashSet<>();
        //TODO       roles.add(new SimpleGrantedAuthority(user.getRole()));
        return new org.springframework.security.core.userdetails.User(user.getLogin(),
                user.getPassword(),
                roles);
    }
}
