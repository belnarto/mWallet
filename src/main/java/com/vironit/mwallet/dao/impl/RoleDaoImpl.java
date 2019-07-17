package com.vironit.mwallet.dao.impl;

import com.vironit.mwallet.dao.RoleDao;
import com.vironit.mwallet.models.Role;
import com.vironit.mwallet.models.RoleEnum;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RoleDaoImpl implements RoleDao {

    @Autowired
    private SessionFactory sessionFactory;

    public void save(Role role) {
        sessionFactory.getCurrentSession().save(role);
    }

    public void update(Role role) {
        sessionFactory.getCurrentSession().update(role);
    }

    public void delete(Role role) {
        sessionFactory.getCurrentSession().delete(role);
    }

    public Role findById(int id) {
        Role role = sessionFactory.getCurrentSession().get(Role.class, id);
        sessionFactory.getCurrentSession().evict(role); // detaching object from persistence session
        return role;
    }

    @SuppressWarnings("unchecked")
    public List<Role> findAll() {
        List<Role> roles = (List<Role>) sessionFactory.getCurrentSession()
                .createQuery("From Role order by id")
                .list();
        return roles;
    }

    public Role findByName(String roleName) {
        Role role = (Role) sessionFactory.getCurrentSession()
                .createQuery("From Role where roleEnum= :roleEnum ")
                .setParameter("roleEnum", RoleEnum.valueOf(roleName))
                .uniqueResult();
        sessionFactory.getCurrentSession().evict(role); // detaching object from persistence session
        return role;
    }

}

