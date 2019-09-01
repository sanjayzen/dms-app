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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.openkm.core.DatabaseException;
import com.openkm.dao.bean.LeaveType;
import com.openkm.dao.bean.ServiceType;

public class ServiceTypeDAO {
	private static Logger log = LoggerFactory.getLogger(ServiceTypeDAO.class);

	private ServiceTypeDAO() {
	}

	/**
	 * Find by filter
	 */
	@SuppressWarnings("unchecked")
	public static List<ServiceType> findServiceType() throws DatabaseException {
		log.debug("findServiceType({})");
		String qs = "from ServiceType";

		//qs += "order by a.date";
		Session session = null;

		try {
			session = HibernateUtil.getSessionFactory().openSession();
			Query q = session.createQuery(qs);
			//q.setInteger("userId", userId);
			List<ServiceType> ret = q.list();
			log.debug("findServiceType: {}", ret);
			return ret;
		} catch (HibernateException e) {
			throw new DatabaseException(e.getMessage(), e);
		} finally {
			HibernateUtil.close(session);
		}
	}

}
