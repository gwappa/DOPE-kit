/*
 * MIT License
 *
 * Copyright (c) 2019 Keisuke Sehara
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 */

package cc.chaos.opendata.dope;

import java.util.LinkedList;
import java.util.Iterator;
import java.util.logging.Logger;
import javax.swing.event.ListDataListener;

import cc.chaos.opendata.dope.core.ManagedProjectSpecification;

/**
 *  a class that manages DOPE projects
 */
public class ProjectManager
    implements javax.swing.ComboBoxModel<String>
{
    static final Logger         LOGGER     = Logger.getLogger("OpenData/DOPE");

    static final String         SEPARATOR  = "---------------------";
    static final String         LAB_MANAGE = "Manage projects...";

    static LinkedList<ListDataListener>            _listeners      = new LinkedList<>();
    static LinkedList<ManagedProjectSpecification> _projects       = new LinkedList<>();
    static ManagedProjectSpecification             _currentProject = null;

    public ProjectManager() {
        for (ManagedProjectSpecification spec: DOPESettings.getManagedProjectSpecifications())
            _projects.add(spec);
    }

    @Override
    public int getSize() {
        return _projects.size() + 2;
    }

    @Override
    public String getElementAt(int index) {
        if (index == _projects.size() + 1) {
            return LAB_MANAGE;
        } else if (index == _projects.size()) {
            return SEPARATOR;
        } else {
            return _projects.get(index).getName();
        }
    }

    @Override
    public String getSelectedItem() {
        return (_currentProject != null)? _currentProject.getName() : null;
    }

    @Override
    public void setSelectedItem(Object item) {
        LOGGER.warning("not implemented: ProjectManager.setSelectedItem(String)");
    }

    @Override
    public void addListDataListener(ListDataListener l) {
        _listeners.add(l);
    }

    @Override
    public void removeListDataListener(ListDataListener l) {
        _listeners.remove(l);
    }
}
