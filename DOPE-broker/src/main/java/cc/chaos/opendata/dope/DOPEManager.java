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
import java.util.logging.Logger;

import cc.chaos.util.Result;
import cc.chaos.opendata.dope.core.ProjectSpecification;
import cc.chaos.opendata.dope.ui.EntityListManager;

public class DOPEManager
    implements DOPE
{
    static final Logger LOGGER   = Logger.getLogger("OpenData/DOPE");

    static final EntityListManager<ProjectSpecification> NO_PROJECT_EDITOR
        = new EntityListManager<ProjectSpecification>() {
            public Result<List<ProjectSpecification>> run(ProjectSpecification[] projects) {
                return Result.<List<ProjectSpecification>>failure("project editor is not set properly: run setProjectEditor(ProjectEditor) to fix the problem.");
            }
        };

    static EntityListManager<ProjectSpecification> _projectEditor = NO_PROJECT_EDITOR;

    static {
        setProjectEditor(cc.chaos.opendata.dope.ui.DefaultProjectEditor.getInstance());
    }

    static final ProjectSpecification getCurrentProject() {
        return ProjectManager.getCurrentProject();
    }

    static final void setCurrentProject(ProjectSpecification project) {
        ProjectManager.setCurrentProject(project);
    }

    static final void setProjectEditor(EntityListManager<ProjectSpecification> editor) {
        if (editor != _projectEditor) {
            if (editor == null) {
                _projectEditor = NO_PROJECT_EDITOR;
            } else {
                _projectEditor = editor;
            }
        }
    }

    static final Result<List<ProjectSpecification>> editProjects(ProjectSpecification[] projects)
    {
        return _projectEditor.run(projects);
    }
}
