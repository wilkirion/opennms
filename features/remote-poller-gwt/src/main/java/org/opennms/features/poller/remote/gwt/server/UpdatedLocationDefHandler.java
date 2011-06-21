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
package org.opennms.features.poller.remote.gwt.server;

import org.opennms.features.poller.remote.gwt.client.remoteevents.MapRemoteEventHandler;

import de.novanic.eventservice.client.event.Event;
import de.novanic.eventservice.service.EventExecutorService;

/**
 * <p>UpdatedLocationDefHandler class.</p>
 *
 * @author ranger
 * @version $Id: $
 * @since 1.8.1
 */
public class UpdatedLocationDefHandler extends DefaultLocationDefHandler {

	/**
	 * <p>Constructor for UpdatedLocationDefHandler.</p>
	 *
	 * @param locationDataService a {@link org.opennms.features.poller.remote.gwt.server.LocationDataService} object.
	 * @param eventService a {@link de.novanic.eventservice.service.EventExecutorService} object.
	 */
	public UpdatedLocationDefHandler(final LocationDataService locationDataService, final EventExecutorService eventService) {
		super(locationDataService, eventService);
	}

	/** {@inheritDoc} */
	@Override
	protected void sendEvent(final Event event) {
		getEventService().addEvent(MapRemoteEventHandler.LOCATION_EVENT_DOMAIN, event);
	}
}
