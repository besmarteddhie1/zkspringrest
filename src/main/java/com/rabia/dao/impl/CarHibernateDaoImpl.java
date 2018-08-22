package com.rabia.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.rabia.dao.CarDao;
import com.rabia.model.Car;

@Repository("carDao")
public class CarHibernateDaoImpl extends CustomHibernateDaoSupport implements CarDao{

	@SuppressWarnings("unchecked")
	@Override
	public List<Car> findAll() {
		return (List<Car>) getHibernateTemplate().find("from Car");
	}

	@SuppressWarnings({ "deprecation", "unchecked" })
	@Override
	public List<Car> search(String keyword) {
		SessionFactory sessionFactory = getHibernateTemplate().getSessionFactory();
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(Car.class);
		criteria.add(Restrictions.or(Restrictions
				.sqlRestriction("lower({alias}.make) like lower(?)"
						,"%"+keyword+"%",Hibernate.STRING),
				Restrictions.sqlRestriction("lower({alias}.model) like lower(?)"
						,"%"+keyword+"%",Hibernate.STRING)));
		List<Car> result = (List<Car>)criteria.list();
		return result;
	}

	@Override
	public void deleteCar(Car car) {
		getHibernateTemplate().delete(car);
	}

	@Override
	public Integer save(Car car) {
		return (Integer) getHibernateTemplate().save(car);
	}

	@Override
	public void update(Car car) {
		getHibernateTemplate().update(car);
	}

	@SuppressWarnings({ "deprecation", "unchecked" })
	@Override
	public List<Car> search(String keyword, int offset, int limit) {
		SessionFactory sessionFactory = getHibernateTemplate().getSessionFactory();
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(Car.class);
		criteria.setFirstResult(offset);
		criteria.setMaxResults(limit);
		criteria.add(Restrictions.or(Restrictions
				.sqlRestriction("lower({alias}.make) like lower(?)"
						,"%"+keyword+"%",Hibernate.STRING),
				Restrictions.sqlRestriction("lower({alias}.model) like lower(?)"
						,"%"+keyword+"%",Hibernate.STRING)));
		List<Car> result = (List<Car>)criteria.list();
		return result;
	}

	@SuppressWarnings("deprecation")
	@Override
	public Integer countCarByKeyword(String keyword) {
		int result = 0;
		Session session = getSession(true);
		session.beginTransaction();
		Criteria criteria = session.createCriteria(Car.class);
		criteria.add(Restrictions.or(Restrictions
				.sqlRestriction("lower({alias}.make) like lower(?)"
						,"%"+keyword+"%",Hibernate.STRING),
				Restrictions.sqlRestriction("lower({alias}.model) like lower(?)"
						,"%"+keyword+"%",Hibernate.STRING)));
		result = criteria.list().size();
		return result;
	}

}
