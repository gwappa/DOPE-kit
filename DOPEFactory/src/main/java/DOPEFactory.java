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

import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JSplitPane;
import javax.swing.JFrame;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.Box;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;

public class DOPEFactory extends JPanel {
    static final int    BORDER_SIDE     = 5;
    static final int    BORDER_TOP      = 5;
    static final int    BORDER_BOTTOM   = 15;
    static final int    BORDER_BETWEEN  = 2;
    static final String INITIAL_MSG     = "DOPE Factory, version 1.0.0a1";
    static final String CMD_LOAD        = "Load...";
    static final String CMD_SAVE        = "Save";

    JSplitPane              _body;
    JPanel                  _inspect;
    JTree                   _view;
    JLabel                  _status;
    JButton                 _load, _save;
    DefaultMutableTreeNode  _root;

    public DOPEFactory() {
        super();
        setLayout(new BorderLayout());
        _root = new DefaultMutableTreeNode("root");
        setupTestNodes();

        _inspect = new JPanel();

        _view = new JTree(_root);
        _view.setRootVisible(false);

        _body = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, _view, _inspect);
        _body.setResizeWeight(0.5);

        _load   = new JButton(CMD_LOAD);
        _save   = new JButton(CMD_SAVE);
        _status = new JLabel(INITIAL_MSG);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.LINE_AXIS));
        panel.add(_status);
        panel.add(Box.createHorizontalGlue());
        panel.add(_load);
        panel.add(_save);
        panel.setBorder(BorderFactory.createEmptyBorder(BORDER_BETWEEN,
                                                BORDER_BETWEEN,
                                                BORDER_BETWEEN,
                                                BORDER_BETWEEN));
        add(_body, BorderLayout.CENTER);
        add(panel, BorderLayout.PAGE_END);
        setBorder(BorderFactory.createEmptyBorder(0,
                                                0,
                                                BORDER_BOTTOM,
                                                0));
    }

    private void updateBorder(double proportional) {
        _body.setDividerLocation(proportional);
    }

    private void setupTestNodes() {
        DefaultMutableTreeNode raw          = new DefaultMutableTreeNode("raw");

        DefaultMutableTreeNode behav        = new DefaultMutableTreeNode("behav");
        behav.add(new DefaultMutableTreeNode("<all> (.txt)"));
        raw.add(behav);

        DefaultMutableTreeNode fastevent    = new DefaultMutableTreeNode("fastevent");
        fastevent.add(new DefaultMutableTreeNode("AER (.aedat)"));
        fastevent.add(new DefaultMutableTreeNode("FE (.fedat)"));
        raw.add(fastevent);

        DefaultMutableTreeNode video        = new DefaultMutableTreeNode("video");
        video.add(new DefaultMutableTreeNode("<all> (.mp4)"));
        raw.add(video);
        _root.add(raw);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("DOPE Factory");
        DOPEFactory factory = new DOPEFactory();
        frame.setContentPane(factory);
        frame.setSize(600, 400);
        frame.setLocation(120, 100);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        factory.updateBorder(0.3);
    }
}
