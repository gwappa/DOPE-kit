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

import java.nio.file.Path;
import java.nio.file.Files;
import java.nio.charset.Charset;
import java.io.IOException;
import java.io.Writer;
import java.util.Collections;
import java.util.List;
import java.util.LinkedList;
import java.util.logging.Logger;

import org.json.JSONObject;
import org.json.JSONArray;
import org.json.JSONTokener;
import org.json.JSONException;

import cc.chaos.util.Result;
import cc.chaos.opendata.FileSystemBackend;
import cc.chaos.opendata.dope.core.ManagedProject;

public class DOPESettings
    implements DOPE
{
    static final Logger         LOGGER   = Logger.getLogger("OpenData/DOPE");
    static final Charset        CHARSET  = Charset.forName("UTF-8");
    static final int            INDENT   = 4;

    static final String         KEY_PROJ = "projects";

    /**
     *  holds the path to the settings file.
     */
    static       Path           SOURCE   = null;

    /**
     *  holds the original JSON object
     */
    static       JSONObject     SETTINGS = null;

    /**
     *  load the settings: possibly from the source, or create a new one otherwise.
     */
    private static void load() {
        Result<Path> loading = FileSystemBackend.getDomainSettings(DOMAIN_NAME);

        SOURCE = loading.get();

        if (loading.isFailure()) {
            LOGGER.info("settings do not exist: creating a new one.");
            List<Object> items = Collections.emptyList();

            SETTINGS = new JSONObject().put(KEY_PROJ, items);
            writeBack();
        } else {
            // the settings file can be loaded
            try {
                SETTINGS = new JSONObject(new JSONTokener(Files.newBufferedReader(SOURCE, CHARSET)));
            } catch (JSONException e) {
                LOGGER.severe(String.format("error reading the settings: %s", e.getMessage()));
                e.printStackTrace();
            } catch (IOException e) {
                LOGGER.severe(String.format("cannot open settings file: %s", e.getMessage()));
                e.printStackTrace();
            }
        }
    }

    /**
     * write the settings back to the source.
     */
    private static void writeBack() {
        if (SOURCE == null) {
            throw new RuntimeException("attempted to write back before getting the source path");
        }
        if (SETTINGS == null) {
            throw new RuntimeException("attemted to write a settings before it is loaded");
        }
        try {
            Writer out = Files.newBufferedWriter(SOURCE, CHARSET);
            SETTINGS.write(out, INDENT, 0);
        } catch (JSONException e) {
            LOGGER.severe(String.format("error writing the settings: %s", e.getMessage()));
            e.printStackTrace();
        } catch (IOException e) {
            LOGGER.severe(String.format("cannot open settings file: %s", e.getMessage()));
            e.printStackTrace();
        }
    }

    public static ManagedProject[] getManagedProjects() {
        if (SETTINGS == null) {
            load();
        }
        JSONArray arr = SETTINGS.optJSONArray(KEY_PROJ);
        ManagedProject[] tmp = new ManagedProject[0];
        if (arr == null) {
            // project-key not found: create a new one
            List<Object> items = Collections.emptyList();
            SETTINGS.put(KEY_PROJ, items);
            writeBack();
            return tmp;
        } else {
            List<ManagedProject> ret = new LinkedList<>();
            for (int idx=0; idx<arr.length(); idx++) {
                try {
                    ret.add(ManagedProject.load(arr.getJSONObject(idx)));
                } catch (JSONException e) {
                    LOGGER.warning(String.format("managed project #%d does not seem to be an object: %s",
                                                idx+1, e.getMessage()));
                } catch (IOException e) {
                    LOGGER.warning(String.format("error parsing managed project #%d: %e",
                                                idx+1, e.getMessage()));
                }
            }
            return ret.toArray(tmp);
        }
    }
}
