package com.rabia.dao.impl;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

public abstract class CustomHibernateDaoSupport extends HibernateDaoSupport{

	@Autowired
	public void setupSessionFactory (SessionFactory sessionFactory) {
		setSessionFactory(sessionFactory);
	}
}
