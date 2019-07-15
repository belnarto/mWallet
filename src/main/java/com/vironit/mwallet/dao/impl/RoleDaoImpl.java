package com.vironit.mwallet.dao.impl;

import com.vironit.mwallet.config.HibernateSessionFactoryUtil;
import com.vironit.mwallet.dao.RoleDao;
import com.vironit.mwallet.models.Role;
import com.vironit.mwallet.models.RoleEnum;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RoleDaoImpl implements RoleDao {

    public void save(Role role) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.save(role);
        tx1.commit();
        session.close();
    }

    public void update(Role role) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.update(role);
        tx1.commit();
        session.close();
    }

    public void delete(Role role) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.delete(role);
        tx1.commit();
        session.close();
    }

    public Role findById(int id) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        Role role = session.get(Role.class, id);
        tx1.commit();
        session.close();
        return role;
    }

    @SuppressWarnings("unchecked")
    public List<Role> findAll() {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        List<Role> users = (List<Role>) session
                .createQuery("From Role order by id")
                .list();
        session.close();

        return users;
    }

    public Role findByName(String roleName) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Role role = (Role) session
                .createQuery("From Role where roleEnum= :roleEnum ")
                .setParameter("roleEnum", RoleEnum.valueOf(roleName))
                .uniqueResult();
        session.close();
        return role;
    }

}

