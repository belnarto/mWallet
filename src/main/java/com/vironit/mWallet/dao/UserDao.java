package com.vironit.mWallet.dao;

import com.vironit.mWallet.models.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao extends CrudDao<User> {

    User findByLogin(String login);

}
