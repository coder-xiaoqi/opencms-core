/*
 * File   : $Source: /alkacon/cvs/opencms/src-modules/org/opencms/ade/sitemap/client/hoverbar/Attic/CmsSitemapHoverbar.java,v $
 * Date   : $Date: 2010/06/14 12:52:21 $
 * Version: $Revision: 1.4 $
 *
 * This library is part of OpenCms -
 * the Open Source Content Management System
 *
 * Copyright (C) 2002 - 2009 Alkacon Software (http://www.alkacon.com)
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

package org.opencms.ade.sitemap.client.hoverbar;

import org.opencms.ade.sitemap.client.CmsSitemapTreeItem;
import org.opencms.ade.sitemap.client.control.CmsSitemapController;
import org.opencms.gwt.client.ui.A_CmsHoverHandler;
import org.opencms.gwt.client.ui.CmsDnDListHandler;
import org.opencms.gwt.client.ui.CmsListItemWidget;
import org.opencms.gwt.client.ui.css.I_CmsLayoutBundle;

import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.FlowPanel;

/**
 * Sitemap tree item hover-bar.<p>
 * 
 * @author Michael Moossen
 * 
 * @version $Revision: 1.4 $ 
 * 
 * @since 8.0.0
 */
public class CmsSitemapHoverbar extends FlowPanel {

    /** The handler manager. */
    protected HandlerManager m_handlerManager;

    /** The hovered sitemap entry site path. */
    protected String m_sitePath;

    /** The sitemap controller. */
    private CmsSitemapController m_controller;

    /**
     * Constructor.<p>
     * 
     * @param controller the controller
     */
    public CmsSitemapHoverbar(final CmsSitemapController controller) {

        m_controller = controller;
        m_handlerManager = new HandlerManager(this);

        setStyleName(I_CmsLayoutBundle.INSTANCE.listItemWidgetCss().buttonPanel());

        add(new CmsHoverbarGotoSubSitemapButton(this));
        add(new CmsHoverbarGotoButton(this));
        add(new CmsHoverbarSubsitemapButton(this));
        add(new CmsHoverbarDeleteButton(this));
        add(new CmsHoverbarEditButton(this));
        add(new CmsHoverbarNewButton(this));
        add(new CmsHoverbarMergeButton(this));
        add(new CmsHoverbarMoveButton(this));
        add(new CmsHoverbarParentButton(this));
    }

    /**
     * Adds a new attach event handler.<p>
     * 
     * @param handler the handler to add
     * 
     * @return the handler registration
     */
    public HandlerRegistration addAttachHandler(I_CmsHoverbarAttachHandler handler) {

        return m_handlerManager.addHandler(CmsHoverbarAttachEvent.getType(), handler);
    }

    /**
     * Adds a new detach event handler.<p>
     * 
     * @param handler the handler to add
     * 
     * @return the handler registration
     */
    public HandlerRegistration addDetachHandler(I_CmsHoverbarDetachHandler handler) {

        return m_handlerManager.addHandler(CmsHoverbarDetachEvent.getType(), handler);
    }

    /**
     * Detaches the hoverbar.<p>
     */
    public void deattach() {

        removeFromParent();
        m_handlerManager.fireEvent(new CmsHoverbarDetachEvent());
    }

    /**
     * Returns the controller.<p>
     *
     * @return the controller
     */
    public CmsSitemapController getController() {

        return m_controller;
    }

    /**
     * Returns the current entry's site path.<p>
     *
     * @return the current entry's site path
     */
    public String getSitePath() {

        return m_sitePath;
    }

    /** The drag'n drop handler. */
    protected CmsDnDListHandler m_handler;

    /**
     * Sets the drag'n drop handler.<p>
     *
     * @param handler the handler to set
     */
    public void setDnDHandler(CmsDnDListHandler handler) {

        m_handler = handler;
    }

    /**
     * Installs this hoverbar for the given item widget.<p>
     * 
     * @param controller the controller 
     * @param treeItem the item to hover
     */
    public void installOn(final CmsSitemapController controller, final CmsSitemapTreeItem treeItem) {

        final CmsListItemWidget widget = treeItem.getListItemWidget();
        A_CmsHoverHandler handler = new A_CmsHoverHandler() {

            /**
             * @see org.opencms.gwt.client.ui.A_CmsHoverHandler#onHoverIn(com.google.gwt.event.dom.client.MouseOverEvent)
             */
            @Override
            protected void onHoverIn(MouseOverEvent event) {

                m_sitePath = treeItem.getSitePath();
                widget.getContentPanel().add(CmsSitemapHoverbar.this);
                m_handlerManager.fireEvent(new CmsHoverbarAttachEvent());
            }

            /**
             * @see org.opencms.gwt.client.ui.A_CmsHoverHandler#onHoverOut(com.google.gwt.event.dom.client.MouseOutEvent)
             */
            @Override
            protected void onHoverOut(MouseOutEvent event) {

                deattach();
            }
        };
        widget.addMouseOutHandler(handler);
        widget.addMouseOverHandler(handler);
    }
}
