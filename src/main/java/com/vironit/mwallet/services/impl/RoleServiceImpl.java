package com.vironit.mwallet.services.impl;

import com.vironit.mwallet.dao.RoleDao;
import com.vironit.mwallet.models.entity.Role;
import com.vironit.mwallet.services.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SuppressWarnings("SpringJavaAutowiredFieldsWarningInspection")
@Service
@Transactional(value = "hibernateTransactionManager")
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleDao roleDao;

    @Override
    public Role findById(int id) {
        return roleDao.findById(id);
    }

    @Override
    public Role findByName(String roleName) {
        return roleDao.findByName(roleName);
    }

    @Override
    public void save(Role role) {
        roleDao.save(role);
    }

    @Override
    public void delete(Role role) {
        roleDao.delete(role);
    }

    @Override
    public void update(Role role) {
        roleDao.update(role);
    }

    @Override
    public List<Role> findAll() {
        return roleDao.findAll();
    }

}
