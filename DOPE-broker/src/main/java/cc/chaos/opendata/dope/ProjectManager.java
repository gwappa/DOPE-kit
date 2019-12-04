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

import java.util.List;
import java.util.LinkedList;
import java.util.Iterator;
import java.util.logging.Logger;
import java.nio.file.Path;
import javax.swing.event.ListDataListener;
import javax.swing.event.ListDataEvent;

import cc.chaos.util.Result;
import cc.chaos.opendata.dope.core.ProjectSpecification;
import cc.chaos.opendata.dope.core.ManagedProjectSpecification;

/**
 *  a class that manages DOPE projects
 */
public class ProjectManager
    implements cc.chaos.opendata.ui.OpenCloseSelectionModel<ProjectSpecification>
{
    static final Logger         LOGGER     = Logger.getLogger("OpenData/DOPE");

    static final int            EXTRA_ROWS = 2; // additional rows to combo box; separator and manage menu

    protected static class MetaSpecification
        implements ProjectSpecification
    {
        final String LABEL;

        public MetaSpecification(String label) {
            LABEL = label;
        }

        @Override public String getName()          { return LABEL; }
        @Override public Path   getSettingsFile()  { return null;  }
        @Override public String toString()         { return LABEL; }
    }

    protected static final ProjectSpecification SELECT_PROJECT
        = new MetaSpecification("Select a project...");

    protected static final ProjectSpecification SEPARATOR
        = new MetaSpecification("---------------------");

    protected static final ProjectSpecification MANAGE_PROJECTS
        = new MetaSpecification("Manage projects...");

    static LinkedList<ListDataListener>            _listeners      = new LinkedList<>();
    static LinkedList<ManagedProjectSpecification> _projects       = new LinkedList<>();
    static ManagedProjectSpecification             _currentProject = null;

    static ProjectManager                          _instance       = null;

    private ProjectManager() {
        for (ManagedProjectSpecification spec: DOPESettings.getManagedProjectSpecifications())
            _projects.add(spec);
    }

    public static synchronized ProjectManager getInstance() {
        if (_instance == null) {
            _instance = new ProjectManager();
        }
        return _instance;
    }

    public static ProjectSpecification getCurrentProject() {
        return _currentProject;
    }

    public static void setCurrentProject(ProjectSpecification spec) {
        LOGGER.warning("not implemented: <static> ProjectManager.setSelectedItem(ProjectSpecification)");
    }

    protected static void manageProjects() {
        Result<List<ProjectSpecification>> ret = DOPEManager.editProjects(
                _projects.toArray(new ProjectSpecification[0]));
        if (ret.isSuccessful()) {
            // refresh projects
            final int prevsize = _projects.size();
            _projects.clear();
            Iterator<ProjectSpecification> it = ret.get().iterator();
            while(it.hasNext()) {
                _projects.add(ManagedProjectSpecification.load(it.next()));
            }
            final int newsize  = _projects.size();
            getInstance().fireContentsChanged(Math.max(prevsize, newsize));
        } else {
            LOGGER.info(ret.getMessage());
        }
    }

    protected void fireContentsChanged(int size) {
        ListDataEvent evt = new ListDataEvent(this, ListDataEvent.CONTENTS_CHANGED, 0, size + EXTRA_ROWS);
        Iterator<ListDataListener> it = _listeners.iterator();
        while(it.hasNext()) {
            it.next().contentsChanged(evt);
        }
    }

    @Override
    public int getSize() {
        return _projects.size() + EXTRA_ROWS;
    }

    @Override
    public ProjectSpecification getElementAt(int index) {
        if (index == _projects.size() + 1) {
            return MANAGE_PROJECTS;
        } else if (index == _projects.size()) {
            return SEPARATOR;
        } else {
            return _projects.get(index);
        }
    }

    @Override
    public boolean isSelected() {
        return (getCurrentProject() != null);
    }

    @Override
    public ProjectSpecification getSelectedItem() {
        final ProjectSpecification selected = getCurrentProject();
        return (selected == null)? SELECT_PROJECT : selected;
    }

    @Override
    public void setSelectedItem(Object item) {
        if (item instanceof ProjectSpecification){
            if (item == SEPARATOR || item == SELECT_PROJECT) {
                setCurrentProject(null);
            } else if (item == MANAGE_PROJECTS) {
                manageProjects();
            } else {
                setCurrentProject((ProjectSpecification)item);
            }
        } else {
            LOGGER.warning("not a ProjectSpecification instance: "+item.toString());
        }
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
