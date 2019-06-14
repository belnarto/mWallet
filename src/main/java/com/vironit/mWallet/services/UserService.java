package com.vironit.mWallet.services;

import com.vironit.mWallet.dao.UserDao;
import com.vironit.mWallet.models.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserService implements UserDetailsService {

    public UserService() {
    }

    public static User findById(int id) {
        return UserDao.findById(id);
    }

    public static User findByLogin(String login) {
        return UserDao.findByLogin(login);
    }

    public static void save(User user) {
        UserDao.save(user);
    }

    public static void delete(User user) {
        UserDao.delete(user);
    }

    public static void update(User user) {
        UserDao.update(user);
    }

    public static List<User> findAll() {
        return UserDao.findAll();
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
