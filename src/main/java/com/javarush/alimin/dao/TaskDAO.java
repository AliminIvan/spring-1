package com.javarush.alimin.dao;

import com.javarush.alimin.domain.Task;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class TaskDAO {

    private final SessionFactory factory;

    public TaskDAO(SessionFactory factory) {
        this.factory = factory;
    }

    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    public List<Task> getAll(int offset, int limit) {
        Query<Task> tasks = getSession().createQuery("select t from Task t", Task.class);
        tasks.setFirstResult(offset);
        tasks.setMaxResults(limit);
        return tasks.getResultList();
    }

    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    public int getAllCount() {
        Query<Long> tasksCount = getSession().createQuery("select count(t) from Task t", Long.class);
        return Math.toIntExact(tasksCount.getSingleResult());
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public Task getById(int id) {
        Query<Task> tasks = getSession().createQuery("select t from Task t where t.id = :ID", Task.class);
        tasks.setParameter("ID", id);
        return tasks.getSingleResult();
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void saveOrUpdate(Task task) {
        getSession().persist(task);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void delete(Task task) {
        getSession().remove(task);
    }

    private Session getSession() {
        return factory.getCurrentSession();
    }
}
