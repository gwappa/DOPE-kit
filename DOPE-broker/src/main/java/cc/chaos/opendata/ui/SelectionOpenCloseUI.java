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

package cc.chaos.opendata.ui;

import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.ComboBoxModel;

public class SelectionOpenCloseUI
    implements OpenCloseSelectorUI
{

    JLabel                  _header;
    OpenCloseSelectionModel _model;
    JComboBox<?>            _selector;
    JButton                 _toggle;

    public SelectionOpenCloseUI(String title, OpenCloseSelectionModel<?> model) {
        _header   = new JLabel(title);
        _model    = model;
        _selector = new JComboBox<>(model);
        _toggle   = new JButton(LAB_OPEN);

        updateWithSelection();
    }

    protected void updateWithSelection() {
        _toggle.setEnabled(_model.isSelected());
    }

    @Override public JLabel getHeader() { return _header; }
    @Override public JComboBox<?> getSelector() { return _selector; }
    @Override public JButton getToggleButton() { return _toggle; }
}
