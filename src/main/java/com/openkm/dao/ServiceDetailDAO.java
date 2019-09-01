/**
 * OpenKM, Open Document Management System (http://www.openkm.com)
 * Copyright (c) 2006-2017  Paco Avila & Josep Llort
 * <p>
 * No bytes were intentionally harmed during the development of this application.
 * <p>
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 * <p>
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * <p>
 * You should have received a copy of the GNU General Public License along
 * with this program; if not, write to the Free Software Foundation, Inc.,
 * 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 */

package com.openkm.dao;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.openkm.core.DatabaseException;
import com.openkm.dao.bean.LeaveDetail;
import com.openkm.dao.bean.ServiceDetail;

public class ServiceDetailDAO {
	private static Logger log = LoggerFactory.getLogger(ServiceDetailDAO.class);

	private ServiceDetailDAO() {
	}

	/**
	 * Create activity
	 */
	public static void create(ServiceDetail serviceDetail) throws DatabaseException {
		Session session = null;
		Transaction tx = null;

		try {
			session = HibernateUtil.getSessionFactory().openSession();
			tx = session.beginTransaction();
			session.save(serviceDetail);
			HibernateUtil.commit(tx);
		} catch (HibernateException e) {
			HibernateUtil.rollback(tx);
			throw new DatabaseException(e.getMessage(), e);
		} finally {
			HibernateUtil.close(session);
		}
	}
	
	/**
	 * Update activity
	 */
	public static void update(ServiceDetail serviceDetail) throws DatabaseException {
		Session session = null;
		Transaction tx = null;

		try {
			session = HibernateUtil.getSessionFactory().openSession();
			tx = session.beginTransaction();
			session.update(serviceDetail);
			HibernateUtil.commit(tx);
		} catch (HibernateException e) {
			HibernateUtil.rollback(tx);
			throw new DatabaseException(e.getMessage(), e);
		} finally {
			HibernateUtil.close(session);
		}
	}

	/**
	 * Find by filter
	 */
	@SuppressWarnings("unchecked")
	public static List<ServiceDetail> findByUserName(String assignedUserName) throws DatabaseException {
		log.debug("findByUserName({})", assignedUserName);
		String qs = "from ServiceDetail a where a.assignedUserName=:assignedUserName";

		//qs += "order by a.date";
		Session session = null;

		try {
			session = HibernateUtil.getSessionFactory().openSession();
			Query q = session.createQuery(qs);
			q.setString("assignedUserName", assignedUserName);

			List<ServiceDetail> ret = q.list();
			log.debug("findByUserName: {}", ret);
			return ret;
		} catch (Exception e) {
			e.printStackTrace();
			throw new DatabaseException(e.getMessage(), e);
		} finally {
			HibernateUtil.close(session);
		}
	}
	
	/**
	 * Find by filter
	 */
	@SuppressWarnings("unchecked")
	public static List<ServiceDetail> findAll() throws DatabaseException {
		log.debug("findAll({})");
		String qs = "from ServiceDetail";

		//qs += "order by a.date";
		Session session = null;

		try {
			session = HibernateUtil.getSessionFactory().openSession();
			Query q = session.createQuery(qs);
			//q.setString("assignedUserName", assignedUserName);

			List<ServiceDetail> ret = q.list();
			log.debug("findByUserName: {}", ret);
			return ret;
		} catch (Exception e) {
			e.printStackTrace();
			throw new DatabaseException(e.getMessage(), e);
		} finally {
			HibernateUtil.close(session);
		}
	}
	
	/**
	 * Find by filter
	 */
	@SuppressWarnings("unchecked")
	public static List<ServiceDetail> findServiceByUserNameStatus(String assignedUserName, String status) throws DatabaseException {
		log.debug("findServiceByUserNameStatus({assignedUserName, status})", assignedUserName, status);
		
		String qs = "from ServiceDetail a where a.assignedUserName=:assignedUserName and a.status=:status";
		

		//qs += "order by a.date";
		Session session = null;

		try {
			session = HibernateUtil.getSessionFactory().openSession();
			Query q = session.createQuery(qs);
			q.setString("assignedUserName", assignedUserName);
			q.setString("status", status);

			List<ServiceDetail> ret = q.list();
			log.debug("findServiceByUserNameStatus: {assignedUserName, status}", ret);
			return ret;
		} catch (HibernateException e) {
			throw new DatabaseException(e.getMessage(), e);
		} finally {
			HibernateUtil.close(session);
		}
	}
	
	/**
	 * Find by filter
	 */
	@SuppressWarnings("unchecked")
	public static List<ServiceDetail> findServiceBytatus(String status) throws DatabaseException {
		log.debug("findServiceBytatus({status})", status);
		
		String qs = "from ServiceDetail a where a.status=:status'";

		//qs += "order by a.date";
		Session session = null;

		try {
			session = HibernateUtil.getSessionFactory().openSession();
			Query q = session.createQuery(qs);
			q.setString("status", status);

			List<ServiceDetail> ret = q.list();
			log.debug("findServiceBytatus: {status}", ret);
			return ret;
		} catch (HibernateException e) {
			throw new DatabaseException(e.getMessage(), e);
		} finally {
			HibernateUtil.close(session);
		}
	}
	
	/**
	 * Find by filter
	 */
	@SuppressWarnings("unchecked")
	public static ServiceDetail findByServiceId(int serviceId) throws DatabaseException {
		log.debug("findByServiceId({serviceId})", serviceId);
		String qs = "from ServiceDetail a where a.id=:id";

		//qs += "order by a.date";
		Session session = null;

		try {
			session = HibernateUtil.getSessionFactory().openSession();
			Query q = session.createQuery(qs);
			q.setInteger("id", serviceId);

			ServiceDetail ret = (ServiceDetail)q.setMaxResults(1).uniqueResult();
			log.debug("findByServiceId: {}", ret);
			return ret;
		} catch (HibernateException e) {
			throw new DatabaseException(e.getMessage(), e);
		} finally {
			HibernateUtil.close(session);
		}
	}

}
