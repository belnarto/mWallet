package com.vironit.mwallet.dao.impl;

import com.vironit.mwallet.dao.RoleDao;
import com.vironit.mwallet.model.entity.Role;
import com.vironit.mwallet.model.attribute.RoleEnum;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@SuppressWarnings({"SpringJavaAutowiredFieldsWarningInspection", "UnnecessaryLocalVariable"})
@Repository
public class RoleDaoImpl implements RoleDao {

    @Autowired
    private SessionFactory sessionFactory;

    public void save(Role role) {
        sessionFactory.getCurrentSession().save(role);
    }

    public void update(Role role) {
        sessionFactory.getCurrentSession().merge(role);
    }

    public void delete(Role role) {
        sessionFactory.getCurrentSession().delete(role);
    }

    public Role findById(int id) {
        return sessionFactory.getCurrentSession().get(Role.class, id);
    }

    @SuppressWarnings({"unchecked", "JpaQlInspection"})
    public List<Role> findAll() {
        List<Role> roles = (List<Role>) sessionFactory.getCurrentSession()
                .createQuery("From Role order by id")
                .list();
        return roles;
    }

    @SuppressWarnings("JpaQlInspection")
    public Role findByName(String roleName) {
        Role role = (Role) sessionFactory.getCurrentSession()
                .createQuery("From Role where roleEnum= :roleEnum ")
                .setParameter("roleEnum", RoleEnum.valueOf(roleName))
                .uniqueResult();
        if (role != null) {
            sessionFactory.getCurrentSession().evict(role); // detaching object from persistence session
        }
        return role;
    }

}

