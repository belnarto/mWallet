package com.vironit.mWallet.services;

import com.vironit.mWallet.dao.UserDao;
import com.vironit.mWallet.models.User;
import com.vironit.mWallet.services.exception.LoginAlreadyDefinedException;
import com.vironit.mWallet.services.exception.UserServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private UserDao userDao;

    public User findById(int id) {
        return userDao.findById(id);
    }

    public User findByLogin(String login) {
        return userDao.findByLogin(login);
    }

    public void save(User user) throws LoginAlreadyDefinedException {
        if (findByLogin(user.getLogin()) != null) {
            throw new LoginAlreadyDefinedException();
        }
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userDao.save(user);
    }

    public void delete(User user) {
        userDao.delete(user);
    }

    public void update(User user) {
        User currentUser = userDao.findById(user.getId());
        String oldPass = currentUser.getPassword();
        if (!user.getPassword().equals(oldPass) && !user.getPassword().isEmpty()) {
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        } else {
            user.setPassword(oldPass);
        }
        userDao.update(user);
    }

    public List<User> findAll() {
        return userDao.findAll();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = findByLogin(username);
        Set<GrantedAuthority> roles = new HashSet<>();
        roles.add(new SimpleGrantedAuthority(user.getRole().toString()));
        return new org.springframework.security.core.userdetails.User(user.getLogin(),
                user.getPassword(),
                roles);
    }
}
