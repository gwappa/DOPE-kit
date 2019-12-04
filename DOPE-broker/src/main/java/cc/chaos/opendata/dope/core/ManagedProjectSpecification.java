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
package cc.chaos.opendata.dope.core;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.json.JSONObject;

/**
 *  configurations for a managed project
 */
public class ManagedProjectSpecification
    implements ProjectSpecification
{
    final String _name;
    final Path   _settings;

    public ManagedProjectSpecification(final String name,
                          final Path settings)
    {
        _name       = name;
        _settings   = settings;
        // _datadir    = datadir;
    }

    @Override public String getName()          { return _name;     }
    @Override public Path   getSettingsFile()  { return _settings; }
    // @Override public Path   getDataDirectory() { return _datadir;  }
    @Override public String toString()         { return _name; }

    /**
     *  initializes a ManagedProject from a JSON entry.
     */
    public static ManagedProjectSpecification load(JSONObject src)
        throws IOException
    {
        String name     = src.optString(KEY_PROJ_NAME);
        if (name == null) {
            throw new IOException("'name' not specified for this project");
        }
        String settings = src.optString(KEY_PROJ_SETTINGS);
        if (settings == null) {
            throw new IOException(String.format("'settings' path not specified for '%s'", name));
        }
        // String datadir  = src.optString(KEY_PROJ_DATADIR);
        // if (datadir == null) {
        //     throw new IOException(String.format("'datadir' path not specified for '%s'", name));
        // }
        return new ManagedProjectSpecification(name, Paths.get(settings));
    }

    public static ManagedProjectSpecification load(ProjectSpecification other)
    {
        return new ManagedProjectSpecification(other.getName(), other.getSettingsFile());
    }
}
