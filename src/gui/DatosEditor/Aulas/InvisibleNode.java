/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gui.DatosEditor.Aulas;

import java.util.Enumeration;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeNode;

/**
 *
 * @author David Gutiérrez Rubio <davidgutierrezrubio@gmail.com>
 */
public class InvisibleNode extends DefaultMutableTreeNode {

    /**
     *
     */
    protected boolean isVisible;

    /**
     *
     */
    public InvisibleNode() {
    this(null);
  }

    /**
     *
     * @param userObject
     */
    public InvisibleNode(Object userObject) {
    this(userObject, true, true);
  }

    /**
     *
     * @param userObject
     * @param allowsChildren
     * @param isVisible
     */
    public InvisibleNode(Object userObject, boolean allowsChildren,
      boolean isVisible) {
    super(userObject, allowsChildren);
    this.isVisible = isVisible;
  }

    /**
     *
     * @param index
     * @param filterIsActive
     * @return
     */
    public TreeNode getChildAt(int index, boolean filterIsActive) {
    if (!filterIsActive) {
      return super.getChildAt(index);
    }
    if (children == null) {
      throw new ArrayIndexOutOfBoundsException("node has no children");
    }

    int realIndex = -1;
    int visibleIndex = -1;
    Enumeration e = children.elements();
    while (e.hasMoreElements()) {
      InvisibleNode node = (InvisibleNode) e.nextElement();
      if (node.isVisible()) {
        visibleIndex++;
      }
      realIndex++;
      if (visibleIndex == index) {
        return (TreeNode) children.elementAt(realIndex);
      }
    }

    throw new ArrayIndexOutOfBoundsException("index unmatched");
    //return (TreeNode)children.elementAt(index);
  }

    /**
     *
     * @param filterIsActive
     * @return
     */
    public int getChildCount(boolean filterIsActive) {
    if (!filterIsActive) {
      return super.getChildCount();
    }
    if (children == null) {
      return 0;
    }

    int count = 0;
    Enumeration e = children.elements();
    while (e.hasMoreElements()) {
      InvisibleNode node = (InvisibleNode) e.nextElement();
      if (node.isVisible()) {
        count++;
      }
    }

    return count;
  }

    /**
     *
     * @param visible
     */
    public void setVisible(boolean visible) {
    this.isVisible = visible;
  }

    /**
     *
     * @return
     */
    public boolean isVisible() {
    return isVisible;
  }

}
