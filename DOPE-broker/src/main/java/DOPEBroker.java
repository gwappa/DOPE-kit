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

import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JLabel;
import javax.swing.JFrame;
import javax.swing.BorderFactory;

import java.awt.BorderLayout;
import javax.swing.BoxLayout;
import javax.swing.Box;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;

import cc.chaos.opendata.dope.ProjectManager;
import cc.chaos.opendata.mock.MockSelectorUI;
import cc.chaos.opendata.gui.OpenCloseSelectorUI;
import cc.chaos.opendata.gui.SelectionOpenCloseUI;

public class DOPEBroker extends JPanel
    implements cc.chaos.gui.GUIConstants
{
    static final String     INITIAL_MSG     = "DOPE broker, version 1.0.0a1";

    static final String []  SAMPLE_PROJECTS = new String[] { "AirTrack_Pupil", "FastEvent_Adapt" };
    static final String []  SAMPLE_SUBJECTS = new String[] { "S005", "S006", "A22" };

    JLabel  _status;

    OpenCloseSelectorUI _projects = new SelectionOpenCloseUI("Project: ", ProjectManager.getInstance());
    OpenCloseSelectorUI _subjects = new MockSelectorUI("Subject: ", SAMPLE_SUBJECTS);

    public DOPEBroker() {
        super();
        setLayout(new BorderLayout());

        _status = new JLabel(INITIAL_MSG);

        add(createMainPanel(), BorderLayout.CENTER);
        add(createStatusPanel(), BorderLayout.PAGE_END);
        setBorder(BorderFactory.createEmptyBorder(0,
                                                0,
                                                BORDER_BOTTOM,
                                                0));
    }

    /**
    *   a routine for creation of the "main" panel,
    *   including the project/subject selectors and the tabbed panes.
    *
    *   this method is called during the initialization.
    *   @return     a JPanel object representing the "main" panel.
    */
    private JPanel createMainPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
        panel.add(createSelectorPanel());
        panel.add(createSubjectPanel());
        return panel;
    }

    /**
    *   a routine for creation of the selectors panel in a GridBagLayout.
    *
    *   this method is called during the initialization.
    *   @return     a JPanel object representing the "selectors" panel.
    */
    private JPanel createSelectorPanel() {
        JPanel          panel   = new JPanel();
        GridBagLayout   grid    = new GridBagLayout();
        GridBagConstraints con  = new GridBagConstraints();
        panel.setLayout(grid);

        con.gridy = 0;
        addSelectorForUI(panel, grid, _projects, con);
        con.gridy = 1;
        addSelectorForUI(panel, grid, _subjects, con);
        return panel;
    }

    /**
     *  a supportive subroutine for adding form components for a selector UI.
     */
    private void addSelectorForUI(JPanel panel,
                                    GridBagLayout grid,
                                    OpenCloseSelectorUI ui,
                                    GridBagConstraints con)
    {
        con.gridx = GridBagConstraints.RELATIVE;
        grid.setConstraints(ui.getHeader(), con);
        panel.add(ui.getHeader());
        grid.setConstraints(ui.getSelector(), con);
        panel.add(ui.getSelector());
        grid.setConstraints(ui.getToggleButton(), con);
        panel.add(ui.getToggleButton());
    }


    /**
    *   a routine for creation of the subject panel.
    *
    *   this method is called during the initialization.
    *   @return     a JTabbedPane object representing the "subject" panel.
    */
    private JTabbedPane createSubjectPanel() {
        JTabbedPane panel = new JTabbedPane();
        panel.addTab("Log", new JPanel());
        panel.addTab("Session", new JPanel());
        panel.addTab("Analysis", new JPanel());
        return panel;
    }

    /**
    *   a routine for creation of the status panel in a BoxLayout.
    *
    *   this method is called during the initialization.
    *   @return     a JPanel object representing the "status" panel.
    */
    private JPanel createStatusPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.LINE_AXIS));
        panel.add(_status);
        panel.add(Box.createHorizontalGlue());
        panel.setBorder(BorderFactory.createEmptyBorder(BORDER_BETWEEN,
                                                BORDER_BETWEEN,
                                                BORDER_BETWEEN,
                                                BORDER_BETWEEN));
        return panel;
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("DOPE broker");
        DOPEBroker broker = new DOPEBroker();
        frame.setContentPane(broker);
        frame.setSize(600, 250);
        frame.setLocation(120, 100);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
