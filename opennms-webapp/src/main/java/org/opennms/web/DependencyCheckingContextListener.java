/*
 * This file is part of the OpenNMS(R) Application.
 *
 * OpenNMS(R) is Copyright (C) 2006 The OpenNMS Group, Inc.  All rights reserved.
 * OpenNMS(R) is a derivative work, containing both original code, included code and modified
 * code that was published under the GNU General Public License. Copyrights for modified
 * and included code are below.
 *
 * OpenNMS(R) is a registered trademark of The OpenNMS Group, Inc.
 * 
 * Copyright (C) 2007 Daniel J. Gregor, Jr.  All rights reserved.
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA 02111-1307, USA.
 *
 * For more information contact:
 *      OpenNMS Licensing       <license@opennms.org>
 *      http://www.opennms.org/
 *      http://www.opennms.com/
 */
package org.opennms.web;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Category;
import org.opennms.core.utils.ThreadCategory;

/**
 * 
 * @author <a href="dj@opennms.org">DJ Gregor</a>
 */
public class DependencyCheckingContextListener implements ServletContextListener {
    private static final String IGNORE_ERRORS_PROPERTY = "dontBlameOpenNMS";
    private static final String IGNORE_ERRORS_MESSAGE = "but don't blame OpenNMS for any errors that occur without switching back to a supported JVM and setting the property back to 'false', first.";
    
    public void contextDestroyed(ServletContextEvent event) {
    }

    public void contextInitialized(ServletContextEvent event) {
        checkJvmName(event.getServletContext());
    }

    private void checkJvmName(ServletContext context) {
        final String systemProperty = "java.vm.name";
        final String systemPropertyMatch = "HotSpot(TM)";

        String vmName = System.getProperty(systemProperty);
        if (vmName == null) {
            logAndOrDie(context, "System property '" + systemProperty + "' is not set so we can't figure out if this version of Java is supported");
        } else if (!vmName.contains(systemPropertyMatch)) {
            logAndOrDie(context, "System property '" + systemProperty + "' does not contain '" + systemPropertyMatch + "' (it is '" + vmName + "'), so it does not appear to be a Sun JVM.  OpenNMS is only supported on Sun JVMs.");
        } else {
            log().info("System property '" + systemProperty + "' does contain '" + systemPropertyMatch + "', so it looks like you are running a Sun JVM.  Congratulations!");
        }
    }

    private void logAndOrDie(ServletContext context, String message) {
        String webXmlPath = context.getRealPath("/WEB-INF/web.xml");
        
        if (Boolean.parseBoolean(context.getInitParameter(IGNORE_ERRORS_PROPERTY))) {
            log().warn(message);
            log().warn("Context parameter '" + IGNORE_ERRORS_PROPERTY + "' is set in " + webXmlPath + ", so the above warning is not fatal,  " + IGNORE_ERRORS_MESSAGE);
        } else {
            log().fatal(message);
            log().fatal("You can edit " + webXmlPath + " and change the value for the '" + IGNORE_ERRORS_PROPERTY + "' context parameter from 'false' to 'true', " + IGNORE_ERRORS_MESSAGE);
            throw new RuntimeException(message);
        }
    }

    private Category log() {
        return ThreadCategory.getInstance(getClass());
    }
}
