package com.vironit.mWallet.dao;

import java.util.List;

public interface CrudDao<E> {

    void save(E entity);

    void update(E entity);

    void delete(E entity);

    E findById(int id);

    List<E> findAll();

}
