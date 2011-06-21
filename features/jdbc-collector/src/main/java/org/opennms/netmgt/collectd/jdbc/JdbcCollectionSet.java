/*******************************************************************************
 * This file is part of the OpenNMS(R) Application.
 *
 * OpenNMS(R) is Copyright (C) 2011 The OpenNMS Group, Inc.  All rights reserved.
 * OpenNMS(R) is a registered trademark of The OpenNMS Group, Inc.
 *
 * OpenNMS(R) is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 2 of the License, or
 * (at your option) any later version.
 *
 * OpenNMS(R) is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 *     along with OpenNMS(R).  If not, see <http://www.gnu.org/licenses/>.
 *
 * For more information contact: 
 *     OpenNMS(R) Licensing <license@opennms.org>
 *     http://www.opennms.org/
 *     http://www.opennms.com/
 *******************************************************************************/
package org.opennms.netmgt.collectd.jdbc;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.opennms.netmgt.collectd.CollectionAgent;
import org.opennms.netmgt.collectd.CollectionResource;
import org.opennms.netmgt.collectd.CollectionSet;
import org.opennms.netmgt.collectd.CollectionSetVisitor;
import org.opennms.netmgt.collectd.ServiceCollector;

public class JdbcCollectionSet implements CollectionSet {
    private int m_status;
    private List<JdbcCollectionResource> m_collectionResources;
    private Date m_timestamp;
    
    public JdbcCollectionSet(CollectionAgent agent) {
        m_status = ServiceCollector.COLLECTION_FAILED;
        m_collectionResources = new ArrayList<JdbcCollectionResource>();
    }
    
    public int getStatus() {
        return m_status;
    }
    
    public void setStatus(int status) {
        m_status = status;
    }

    public List<JdbcCollectionResource> getCollectionResources() {
        return m_collectionResources;
    }

    public void setCollectionResources(List<JdbcCollectionResource> collectionResources) {
        m_collectionResources = collectionResources;
    }

    public void visit(CollectionSetVisitor visitor) {
        visitor.visitCollectionSet(this);

        for(CollectionResource resource : getCollectionResources())
                resource.visit(visitor);

        visitor.completeCollectionSet(this);
    }

    public boolean ignorePersist() {
        return false;
    }
    
	public Date getCollectionTimestamp() {
		return m_timestamp;
	}
    public void setCollectionTimestamp(Date timestamp) {
    	this.m_timestamp = timestamp;
	}

}
