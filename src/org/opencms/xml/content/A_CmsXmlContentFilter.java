/*
 * File   : $Source: /alkacon/cvs/opencms/src/org/opencms/xml/content/Attic/A_CmsXmlContentFilter.java,v $
 * Date   : $Date: 2004/10/15 12:22:00 $
 * Version: $Revision: 1.1 $
 *
 * This library is part of OpenCms -
 * the Open Source Content Mananagement System
 *
 * Copyright (C) 2002 - 2004 Alkacon Software (http://www.alkacon.com)
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * For further information about Alkacon Software, please see the
 * company website: http://www.alkacon.com
 *
 * For further information about OpenCms, please see the
 * project website: http://www.opencms.org
 * 
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */
 
package org.opencms.xml.content;

/**
 * Provides some helpful base implementations for content filter classes.<p>
 */
public abstract class A_CmsXmlContentFilter implements I_CmsXmlContentFilter {

    /** The filter order of this filter. */
    protected int m_order;

    /** The hash code of this filter. */
    private int m_hashcode;
    
    /**
     * Constructor to initialize some default values.<p>
     */
    public A_CmsXmlContentFilter() {
        
        m_hashcode = getClass().getName().hashCode();
    }
    
    /**
     * @see org.opencms.xml.content.I_CmsXmlContentFilter#getOrder()
     */
    public int getOrder() {
    
        return m_order;
    }

    /**
     * @see org.opencms.xml.content.I_CmsXmlContentFilter#setOrder(int)
     */
    public void setOrder(int order) {

        m_order = order;
    }
    
    /**
     * @see java.lang.Comparable#compareTo(java.lang.Object)
     */
    public int compareTo(Object o) {
    
        if (o == null) {
            return 0;
        }
        
        if (! (o instanceof I_CmsXmlContentFilter)) {
            return 0;
        }
        
        return (getOrder() - ((I_CmsXmlContentFilter)o).getOrder());
    }    
    
    /**
     * Two filters are considered to be equal if they are sharing the same
     * implementation class.<p> 
     * 
     * @see java.lang.Object#equals(java.lang.Object)
     */
    public boolean equals(Object o) {

        if (o == null) {
            return false;
        }
        
        if (! (o instanceof I_CmsXmlContentFilter)) {
            return false;
        }
        
        return getClass().getName().equals(o.getClass().getName());
    }    
    
    /**
     * @see java.lang.Object#hashCode()
     */
    public int hashCode() {
        
        return m_hashcode;
    }    
}
