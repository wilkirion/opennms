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
package org.opennms.systemreport.opennms;

import java.io.File;
import java.util.Map;
import java.util.TreeMap;

import org.opennms.systemreport.AbstractSystemReportPlugin;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;

public class LogReportPlugin extends AbstractSystemReportPlugin {

    public String getName() {
        return "Logs";
    }

    public String getDescription() {
        return "OpenNMS log files (full output only)";
    }

    public int getPriority() {
        return 21;
    }

    public TreeMap<String, Resource> getEntries() {
        final TreeMap<String,Resource> map = new TreeMap<String,Resource>();
        File f = new File(System.getProperty("opennms.home") + File.separator + "logs");
        processFile(f, map);
        return map;
    }

    public void processFile(final File file, final Map<String,Resource> map) {
        if (file.isDirectory()) {
            for (final File f : file.listFiles()) {
                processFile(f, map);
            }
        } else {
            String filename = file.getPath();
            filename = filename.replaceFirst("^" + System.getProperty("opennms.home") + File.separator + "logs" + File.separator + "?", "");
            if (filename.endsWith(".log") && file.length() > 0) {
                map.put(filename, new FileSystemResource(file));
            }
        }
    }
}
