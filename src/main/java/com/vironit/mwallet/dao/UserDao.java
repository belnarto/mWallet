package com.vironit.mwallet.dao;

import com.vironit.mwallet.model.entity.User;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserDao extends CrudDao<User> {

    User findByLogin(String login);

    List<User> findAllByNamePart(String namePart);

}
