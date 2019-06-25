package com.vironit.mWallet.dao.impl;

import com.vironit.mWallet.config.HibernateSessionFactoryUtil;
import com.vironit.mWallet.dao.RoleDao;
import com.vironit.mWallet.models.Role;
import com.vironit.mWallet.models.RoleEnum;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class RoleDaoImpl implements RoleDao {

    public Role findById(int id) {
        return HibernateSessionFactoryUtil.getSessionFactory().openSession().get(Role.class, id);
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

    @SuppressWarnings("unchecked")
    public List<Role> findAll() {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        List<Role> users = (List<Role>) session
                .createQuery("From Role order by id")
                .list();
        session.close();

        return users;
    }

}

