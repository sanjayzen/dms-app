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

import com.openkm.core.DatabaseException;
import com.openkm.dao.bean.Activity;
import com.openkm.dao.bean.ActivityFilter;
import com.openkm.dao.bean.LeaveDetail;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Calendar;
import java.util.List;

public class LeaveDetailDAO {
	private static Logger log = LoggerFactory.getLogger(LeaveDetailDAO.class);

	private LeaveDetailDAO() {
	}

	/**
	 * Create activity
	 */
	public static void create(LeaveDetail leaveDetail) throws DatabaseException {
		Session session = null;
		Transaction tx = null;

		try {
			session = HibernateUtil.getSessionFactory().openSession();
			tx = session.beginTransaction();
			session.save(leaveDetail);
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
	public static void update(LeaveDetail leaveDetail) throws DatabaseException {
		Session session = null;
		Transaction tx = null;

		try {
			session = HibernateUtil.getSessionFactory().openSession();
			tx = session.beginTransaction();
			session.update(leaveDetail);
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
	public static List<LeaveDetail> findByUserName(String userName) throws DatabaseException {
		log.debug("findByUserName({})", userName);
		String qs = "from LeaveDetail a where a.userName=:userName";

		//qs += "order by a.date";
		Session session = null;

		try {
			session = HibernateUtil.getSessionFactory().openSession();
			Query q = session.createQuery(qs);
			q.setString("userName", userName);

			List<LeaveDetail> ret = q.list();
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
	public static List<LeaveDetail> findPendingLeave(String approverName) throws DatabaseException {
		log.debug("findByUserId({})", approverName);
		String qs = "from LeaveDetail a where a.approverName=:approverName and a.isApproved = 'I'";

		//qs += "order by a.date";
		Session session = null;

		try {
			session = HibernateUtil.getSessionFactory().openSession();
			Query q = session.createQuery(qs);
			q.setString("approverName", approverName);

			List<LeaveDetail> ret = q.list();
			log.debug("findByUserId: {}", ret);
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
	public static LeaveDetail findByLeaveId(int leaveId) throws DatabaseException {
		log.debug("findByLeaveId({})", leaveId);
		String qs = "from LeaveDetail a where a.leaveId=:leaveId";

		//qs += "order by a.date";
		Session session = null;

		try {
			session = HibernateUtil.getSessionFactory().openSession();
			Query q = session.createQuery(qs);
			q.setInteger("leaveId", leaveId);

			LeaveDetail ret = (LeaveDetail)q.setMaxResults(1).uniqueResult();
			log.debug("findByLeaveId: {}", ret);
			return ret;
		} catch (HibernateException e) {
			throw new DatabaseException(e.getMessage(), e);
		} finally {
			HibernateUtil.close(session);
		}
	}

}
