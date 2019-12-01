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

package cc.chaos.mock;

import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JComboBox;

public class MockSelectionUI
{
    static final String LAB_SETTINGS    = "Settings...";
    static final String LAB_OPEN        = "Open";
    static final String LAB_CLOSE       = "Close";

    JLabel              _header;
    JComboBox<String>   _selector;
    JButton             _settings;
    JButton             _toggle;

    public MockSelectionUI(String title, String[] options) {
        _header   = new JLabel(title);
        _selector = new JComboBox<>(options);
        _settings = new JButton(LAB_SETTINGS);
        _toggle   = new JButton(LAB_OPEN);
    }

    public JLabel getHeader() { return _header; }
    public JComboBox<?> getSelector() { return _selector; }
    public JButton getSettingsButton() { return _settings; }
    public JButton getToggleButton() { return _toggle; }
}
