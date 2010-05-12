/*
 * File   : $Source: /alkacon/cvs/opencms/src-modules/org/opencms/ade/sitemap/client/Attic/CmsSitemapControllerHandler.java,v $
 * Date   : $Date: 2010/05/12 10:14:06 $
 * Version: $Revision: 1.8 $
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

package org.opencms.ade.sitemap.client;

import org.opencms.ade.sitemap.shared.CmsClientSitemapEntry;
import org.opencms.ade.sitemap.shared.CmsSitemapChangeDelete;
import org.opencms.ade.sitemap.shared.CmsSitemapChangeEdit;
import org.opencms.ade.sitemap.shared.CmsSitemapChangeMove;
import org.opencms.ade.sitemap.shared.CmsSitemapChangeNew;
import org.opencms.ade.sitemap.shared.I_CmsSitemapChange;
import org.opencms.file.CmsResource;
import org.opencms.gwt.client.ui.tree.CmsLazyTree;
import org.opencms.gwt.client.ui.tree.CmsTreeItem;
import org.opencms.util.CmsStringUtil;

/**
 * Sitemap controller handler.<p>
 * 
 * @author Michael Moossen
 * 
 * @version $Revision: 1.8 $ 
 * 
 * @since 8.0.0
 * 
 * @see org.opencms.ade.sitemap.client.CmsSitemapController
 */
public class CmsSitemapControllerHandler {

    /** The tree item factory. */
    private CmsSitemapTreeItemFactory m_factory;

    /** The sitemap toolbar. */
    private CmsSitemapToolbar m_toolbar;

    /** The displayed sitemap tree. */
    private CmsLazyTree<CmsSitemapTreeItem> m_tree;

    /**
     * Constructor.<p>
     * 
     * @param toolbar the toolbar
     * @param tree the displayed tree
     * @param factory the tree item factory
     */
    public CmsSitemapControllerHandler(
        CmsSitemapToolbar toolbar,
        CmsLazyTree<CmsSitemapTreeItem> tree,
        CmsSitemapTreeItemFactory factory) {

        m_toolbar = toolbar;
        m_tree = tree;
        m_factory = factory;
    }

    /**
     * Will be triggered when something has changed.<p>
     * 
     * @param change the specific change
     */
    public void onChange(I_CmsSitemapChange change) {

        switch (change.getType()) {
            case DELETE:
                CmsSitemapChangeDelete changeDelete = (CmsSitemapChangeDelete)change;
                CmsTreeItem deleteParent = getTreeItem(CmsResource.getParentFolder(changeDelete.getEntry().getSitePath()));
                deleteParent.removeChild(changeDelete.getEntry().getName());
                break;

            case EDIT:
                CmsSitemapChangeEdit changeEdit = (CmsSitemapChangeEdit)change;
                CmsSitemapTreeItem editEntry = (CmsSitemapTreeItem)getTreeItem(changeEdit.getOldEntry().getSitePath());
                editEntry.updateEntry(changeEdit.getNewEntry());
                break;

            case MOVE:
                CmsSitemapChangeMove changeMove = (CmsSitemapChangeMove)change;
                CmsTreeItem sourceParent = getTreeItem(CmsResource.getParentFolder(changeMove.getSourcePath()));
                CmsTreeItem moved = sourceParent.getChild(changeMove.getSourcePosition());
                sourceParent.removeChild(changeMove.getSourcePosition());
                CmsTreeItem destParent = getTreeItem(CmsResource.getParentFolder(changeMove.getDestinationPath()));
                destParent.insertChild(moved, changeMove.getDestinationPosition());
                break;

            case NEW:
                CmsSitemapChangeNew changeNew = (CmsSitemapChangeNew)change;
                CmsTreeItem newParent = getTreeItem(CmsResource.getParentFolder(changeNew.getEntry().getSitePath()));
                CmsSitemapTreeItem newChild = m_factory.create(changeNew.getEntry());
                if (changeNew.getEntry().getPosition() != -1) {
                    newParent.insertChild(newChild, changeNew.getEntry().getPosition());
                } else {
                    newParent.addChild(newChild);
                }
                break;

            default:
                break;
        }
    }

    /**
     * Will be triggered when the undo list is cleared.<p> 
     */
    public void onClearUndo() {

        m_toolbar.getRedoButton().disable(Messages.get().key(Messages.GUI_DISABLED_REDO_0));
    }

    /**
     * Will be triggered when starting to undo.<p>
     */
    public void onFirstUndo() {

        m_toolbar.getRedoButton().enable();
    }

    /**
     * Will be triggered when an entry gets its children.<p>
     * 
     * @param entry the entry that got its children
     */
    public void onGetChildren(CmsClientSitemapEntry entry) {

        CmsTreeItem target = getTreeItem(entry.getSitePath());
        target.clearChildren();
        for (CmsClientSitemapEntry child : entry.getChildren()) {
            target.addChild(m_factory.create(child));
        }
        ((CmsSitemapTreeItem)target).onFinishLoading();
    }

    /**
     * Will be triggered when redoing the last possible action.<p>
     */
    public void onLastRedo() {

        m_toolbar.getRedoButton().disable(Messages.get().key(Messages.GUI_DISABLED_REDO_0));
    }

    /**
     * Will be triggered when undoing the last possible action.<p>
     */
    public void onLastUndo() {

        m_toolbar.getSaveButton().disable(Messages.get().key(Messages.GUI_DISABLED_SAVE_0));
        m_toolbar.getResetButton().disable(Messages.get().key(Messages.GUI_DISABLED_RESET_0));
        m_toolbar.getUndoButton().disable(Messages.get().key(Messages.GUI_DISABLED_UNDO_0));
    }

    /**
     * Will be triggered on reset.<p>
     */
    public void onReset() {

        m_toolbar.getSaveButton().disable(Messages.get().key(Messages.GUI_DISABLED_SAVE_0));
        m_toolbar.getResetButton().disable(Messages.get().key(Messages.GUI_DISABLED_RESET_0));
        m_toolbar.getUndoButton().disable(Messages.get().key(Messages.GUI_DISABLED_UNDO_0));
    }

    /**
     * Will be triggered when the sitemap is changed in anyway for the first time.<p> 
     */
    public void onStartEdit() {

        m_toolbar.getSaveButton().enable();
        m_toolbar.getResetButton().enable();
        m_toolbar.getUndoButton().enable();
    }

    /**
     * Returns the tree entry with the given path.<p>
     * 
     * @param path the path to look for
     * 
     * @return the tree entry with the given path, or <code>null</code> if not found
     */
    private CmsTreeItem getTreeItem(String path) {

        String[] names = CmsStringUtil.splitAsArray(path, "/");
        CmsTreeItem result = null;
        for (String name : names) {
            if (CmsStringUtil.isEmptyOrWhitespaceOnly(name)) {
                // in case of leading slash
                continue;
            }
            if (result != null) {
                result = result.getChild(name);
            } else {
                // match the root node
                result = m_tree.getItem(name);
            }
            if (result == null) {
                // not found
                break;
            }
        }
        return result;
    }
}
