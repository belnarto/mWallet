package com.vironit.mwallet.services.impl;

import com.vironit.mwallet.dao.UserDao;
import com.vironit.mwallet.models.dto.UserDto;
import com.vironit.mwallet.models.entity.User;
import com.vironit.mwallet.services.UserService;
import com.vironit.mwallet.services.exception.LoginAlreadyDefinedException;
import com.vironit.mwallet.services.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@SuppressWarnings("SpringJavaAutowiredFieldsWarningInspection")
@Service
@Transactional(value = "hibernateTransactionManager")
public class UserServiceImpl implements UserDetailsService, UserService {

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private UserDao userDao;

    @Autowired
    private UserMapper userMapper;

    @Override
    public UserDto findById(int id) {
        User user = userDao.findById(id);
        return userMapper.toDto(user);
    }

    @Override
    public UserDto findByLogin(String login) {
        User user = userDao.findByLogin(login);
        return userMapper.toDto(user);
    }

    @Override
    public void save(UserDto userDto) throws LoginAlreadyDefinedException {
        if (findByLogin(userDto.getLogin()) != null) {
            throw new LoginAlreadyDefinedException();
        }
        userDto.setPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));
        userDao.save(userMapper.toEntity(userDto));
    }

    @Override
    public void delete(UserDto userDto) {
        userDao.delete(userMapper.toEntity(userDto));
    }

    @Override
    public void update(UserDto userDto) {
        User currentUser = userDao.findById(userDto.getId());

        String oldPass = currentUser.getPassword();
        if (!userDto.getPassword().equals(oldPass) && !userDto.getPassword().isEmpty()) {
            userDto.setPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));
        } else {
            userDto.setPassword(oldPass);
        }
        userDao.update(userMapper.toEntity(userDto));
    }

    @Override
    public List<UserDto> findAll() {
        return userDao.findAll().stream()
                .map(user -> userMapper.toDto(user))
                .collect(Collectors.toList());
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDto userDto = findByLogin(username);
        Set<GrantedAuthority> roles = new HashSet<>();
        roles.add(new SimpleGrantedAuthority(userDto.getRole().toString()));
        return new org.springframework.security.core.userdetails.User(userDto.getLogin(),
                userDto.getPassword(),
                roles);
    }
}
