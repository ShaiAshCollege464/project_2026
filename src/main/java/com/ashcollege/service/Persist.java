package com.ashcollege.service;


import com.ashcollege.entities.*;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;


@Transactional
@Component
@SuppressWarnings("unchecked")
public class Persist {

    private static final Logger LOGGER = LoggerFactory.getLogger(Persist.class);

    private final SessionFactory sessionFactory;


    @Autowired
    public Persist(SessionFactory sf) {
        this.sessionFactory = sf;
    }

    public <T> void saveAll(List<T> objects) {
        for (T object : objects) {
            sessionFactory.getCurrentSession().saveOrUpdate(object);
        }
    }

    public <T> void remove(Object o){
        sessionFactory.getCurrentSession().remove(o);
    }

    public Session getQuerySession() {
        return sessionFactory.getCurrentSession();
    }

    public void save(Object object) {
        this.sessionFactory.getCurrentSession().saveOrUpdate(object);
    }

    public <T> T loadObject(Class<T> clazz, int oid) {
        return this.getQuerySession().get(clazz, oid);
    }

    public <T> List<T> loadList(Class<T> clazz)
    {
        return this.sessionFactory.getCurrentSession()
                .createQuery("FROM " + clazz.getSimpleName()).list();
    }

    public UserEntity getUserByEmail(String email) {
        return this.sessionFactory.getCurrentSession()
                .createQuery("FROM UserEntity WHERE email = :email ", UserEntity.class)
                .setParameter("email", email)
                .uniqueResult();
    }

    public List<UserEntity> getUsersByCity(String city) {
        return null;
    }

    public ClientEntity getUserByUsernameAndPassword(String username, String password) {
        return this.sessionFactory.getCurrentSession()
                .createQuery("FROM ClientEntity " +
                        "WHERE username = :username " +
                        "AND password = :password", ClientEntity.class)
                .setParameter("username", username)
                .setParameter("password", password)
                .uniqueResult();
    }

    public List<PostEntity> getPostsByClientId(int clientId) {
        return this.sessionFactory.getCurrentSession()
                .createQuery("FROM PostEntity " +
                        "WHERE clientEntity.id = :clientId", PostEntity.class)
                .setParameter("clientId", clientId)
                .list();
    }

    public ClientEntity getClientByToken(String token) {
        return this.sessionFactory.getCurrentSession()
                .createQuery("FROM ClientEntity " +
                        "WHERE token = :token", ClientEntity.class)
                .setParameter("token", token)
                .uniqueResult();
    }

    public ProffesionalEntity getProfessionalByToken(String token) {
        return this.sessionFactory.getCurrentSession()
                .createQuery("FROM ProffesionalEntity " +
                        "WHERE token = :token", ProffesionalEntity.class)
                .setParameter("token", token)
                .uniqueResult();
    }


    public ProffesionalEntity getProffesionalByUsernameAndPassword(String username, String password) {
        return this.sessionFactory.getCurrentSession()
                .createQuery("FROM ProffesionalEntity " +
                        "WHERE username = :username " +
                        "AND password = :password", ProffesionalEntity.class)
                .setParameter("username", username)
                .setParameter("password", password)
                .uniqueResult();
    }

    public BasicUser getUserByToken(String token) {
        BasicUser user = getClientByToken(token);
        if (user == null) {
            user = getProfessionalByToken(token);
        }
        return user;
    }





}