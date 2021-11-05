package com.hnzs.dao.common;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.hibernate.jpa.HibernateEntityManager;
import org.hibernate.query.Query;
import org.hibernate.transform.Transformers;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@SuppressWarnings("all")
@Service
public class CommonDaoImpl implements CommonDao {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public ArrayList<Map<Object, Object>> selectExecute(String sql) {
		HibernateEntityManager hEntityManager = (HibernateEntityManager)entityManager;
		Session session = hEntityManager.getSession();
		//ArrayList<Map<Object, Object>> alist = (ArrayList<Map<Object, Object>>) session.createSQLQuery(sql).list();
        Query query =session.createSQLQuery(sql);
        query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        ArrayList<Map<Object, Object>> alist=(ArrayList<Map<Object, Object>>)query.list();
		return alist;
	}


//	public List<?> selectExecute(String sql, int offset, int length) {
//		HibernateEntityManager hEntityManager = (HibernateEntityManager)entityManager;
//		Session session = hEntityManager.getSession();
//		Query query =session.createSQLQuery(sql);
//		query.setFirstResult(offset);
//		query.setMaxResults(length);
//		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
//		return query.list();
//	}

    public ArrayList<?> selectExecute(String sql, int offset, int length) {
        HibernateEntityManager hEntityManager = (HibernateEntityManager)entityManager;
        Session session = hEntityManager.getSession();
        //query.setFirstResult(0);//从第一条记录开始
        //    query.setMaxResults(4);//取出四条记录
        Query query =session.createSQLQuery(sql);
        query.setFirstResult(offset);
        query.setMaxResults(length);
        query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        ArrayList alist=(ArrayList) query.list();
        return alist;
    }

	@Override
    @Modifying
    @Transactional
	public int addUpdateDeleteExecute(ArrayList<String> alist) {
		int result = 0;
		HibernateEntityManager hEntityManager = (HibernateEntityManager)entityManager;
		Session session = hEntityManager.getSession();
		for(String sql : alist){
			int res = session.createSQLQuery(sql).executeUpdate();
			result += res;
		}
		return result;
	}

}
