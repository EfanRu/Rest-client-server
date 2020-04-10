package com.example.springcucumber.spring_cucumber.dao;

import com.example.springcucumber.spring_cucumber.model.Role;
import com.example.springcucumber.spring_cucumber.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.util.List;

@Repository
public class UserDaoImpl implements UserDao {
    @Autowired
    private EntityManagerFactory emf;
    @PersistenceContext
    private EntityManager entityManager;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    @Override
    public List<User> getAllUsers() {
        Query query = entityManager.createQuery("FROM User u");
        return (List<User>) query.getResultList();
    }

    @Transactional
    @Override
    public boolean addUser(User user) {
        try {
            if (!isRoleContains(user)) {
                entityManager.persist(user.getRole());
            }

            user.setRole(getRoleByName(user.getRole().getName()));

            entityManager.persist(user);
            return true;
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Transactional
    @Override
    public boolean deleteUser(String id) {
        try {
            Query query = entityManager.createQuery("DELETE FROM User u WHERE u.id = :id");
            query.setParameter("id", Long.parseLong(id));
            query.executeUpdate();
            return true;
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Transactional
    @Override
    public boolean updateUser(String id, String firstName, String lastName, String phoneNumber, String role, String login, String password) {
        try {
            entityManager.merge(new User(Long.parseLong(id), firstName, lastName, login, password, Long.parseLong(phoneNumber), getRoleByName(role)));
            return true;
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Transactional
    @Override
    public boolean updateUser(String id, String firstName, String lastName, String phoneNumber, String role, String login) {
        try {
            entityManager.merge(new User(Long.parseLong(id), firstName, lastName, login, getUserById(id).getPassword(), Long.parseLong(phoneNumber), getRoleByName(role)));
            return true;
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
        return false;
    }


    @Transactional
    @Override
    public boolean updateUser(User user) {
        user.setRole(getRoleByName(user.getRole().getName()));
        try {
            entityManager.merge(user);
            return true;
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean checkAuth(String login, String password) {
        try {
            Query query = entityManager.createQuery("FROM User u WHERE u.login = :login AND u.password = :password");
            query.setParameter("login", login);
            query.setParameter("password", password);
            return query.getSingleResult() != null;
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public User getUserByLogin(String login) {
        User user = new User();
        user.setLogin("");
        user.setRole(new Role("user"));
        try {
            Query query = entityManager.createQuery("FROM User u WHERE login = :login");
            query.setParameter("login", login);
            return (User) query.getSingleResult();
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
        return user;
    }

    @Override
    public User getUserById(String id) {
        try {
            Query query = entityManager.createQuery("FROM User u WHERE u.id = :id");
            query.setParameter("id", Long.parseLong(id));
            return (User) query.getSingleResult();
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
        return new User();
    }


    private boolean isRoleContains(User user) {
        Query query = entityManager.createQuery("FROM Role r");
        List<Role> list = query.getResultList();
        for (Role r : list) {
            if (r.getName().toUpperCase().equals(user.getRole().getName().toUpperCase())) {
                return true;
            }
        }
        return false;
    }


    @Transactional
    @Override
    public boolean addRole(Role role) {
        try {
            entityManager.persist(role);
            return true;
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
        return false;
    }

    public Role getRoleByName(String name) {
        try {
            Query query = entityManager.createQuery("FROM Role r WHERE r.name = :name");
            query.setParameter("name", name);
            return (Role) query.getSingleResult();
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
        Role newRole = new Role(name);
        addRole(newRole);
        name = newRole.getName();
        try {
            Query query = entityManager.createQuery("FROM Role r WHERE r.name = :name");
            query.setParameter("name", name);
            return (Role) query.getSingleResult();
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
        return null;
    }
}
