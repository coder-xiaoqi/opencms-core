/*
 * File   : $Source: /alkacon/cvs/opencms/src/com/opencms/file/Attic/CmsResourceType.java,v $
 * Date   : $Date: 2000/06/05 13:37:56 $
 * Version: $Revision: 1.5 $
 *
 * Copyright (C) 2000  The OpenCms Group 
 * 
 * This File is part of OpenCms -
 * the Open Source Content Mananagement System
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * For further information about OpenCms, please see the
 * OpenCms Website: http://www.opencms.com
 * 
 * You should have received a copy of the GNU General Public License
 * long with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */

package com.opencms.file;

import java.util.*;
import com.opencms.core.*;

/**
 * This class describes a resource-type. To determine the special launcher 
 * for a resource this resource-type is needed.
 * 
 * @author Michael Emmerich
 * @version $Revision: 1.5 $ $Date: 2000/06/05 13:37:56 $
 */
public class CmsResourceType implements I_CmsConstants {	
	
     /**
      * The id of resource type.
      */
 	private int m_resourceType;
    
    /**
     * The id of the launcher used by this resource.
     */
    private int m_launcherType;
    
    /**
     * The resource type name.
     */
    private String m_resourceTypeName;
    
    /**
     * The class name of the Java class launched by the launcher.
     */
    private String m_launcherClass;
     
    
    /**
     * Constructor, creates a new CmsResourceType object.
     * 
     * @param resourceType The id of the resource type.
     * @param launcherType The id of the required launcher.
     * @param resourceTypeName The printable name of the resource type.
     * @param launcherClass The Java class that should be invoked by the launcher. 
     * This value is <b> null </b> if the default invokation class should be used.
     */
    public CmsResourceType(int resourceType, int launcherType,
                           String resourceTypeName, String launcherClass){
        
        m_resourceType=resourceType;
        m_launcherType=launcherType;
        m_resourceTypeName=resourceTypeName;
        m_launcherClass=launcherClass;
    }
    
	/**
	 * Returns the type of this resource-type.
	 * 
	 * @return the type of this resource-type.
	 */
    int getResourceType() {
         return m_resourceType;
     }
    
     /**
	 * Returns the launcher type needed for this resource-type.
	 * 
	 * @return the launcher type for this resource-type.
	 */
     int getLauncherType() {
         return m_launcherType;
     }
	
	/**
	 * Returns the name for this resource-type.
	 * 
	 * @return the name for this resource-type.
	 */
     public String getResourceName() {
         return m_resourceTypeName;
     }
    
     /**
	 * Returns the name of the Java class loaded by the launcher.
	 * This method returns <b>null</b> if the default class for this type is used.
	 * 
	 * @return the name of the Java class.
	 */
     public String getLauncherClass() {
         return m_launcherClass;
     }

	/**
	 * Returns a string-representation for this object.
	 * This can be used for debugging.
	 * 
	 * @return string-representation for this object.
	 */
     public String toString() {
        StringBuffer output=new StringBuffer();
        output.append("[ResourceType]:");
        output.append(m_resourceTypeName);
        output.append(" , Id=");
        output.append(m_resourceType);
        output.append(" , launcherType=");
        output.append(m_launcherType);
        output.append(" , launcherClass=");
        output.append(m_launcherClass);
        return output.toString();
      }
}
