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
package cc.chaos.opendata;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Logger;

import cc.chaos.util.Result;

public class FileSystemBackend
{
    static final String ROOT   = ".opendata";
    static final Logger LOGGER = Logger.getLogger("OpenData/Backend");

    /**
     *  finds the directory related to root opendata directory.
     *  if not found, it tries to create one.
     *
     *  @return the Path object corresponding to the opendata directory.
     */
    public static Path getOpenDataDirectory() {
        return ensureDirectory(Paths.get(System.getProperty("user.home"), ROOT));
    }

    /**
     *  finds the directory related to domain-specific metadata.
     *  if not found, it tries to create one.
     *
     *  @return the Path object corresponding to the domain-specific metadata directory.
     */
    public static Path getDomainDirectory(final String name) {
        return ensureDirectory(Paths.get(getOpenDataDirectory().toString(), name));
    }

    /**
     *  returns the Result object for a Path to domain-specific settings JSON file.
     *
     *  @param name the name of the domain.
     *  @return a Result object, always containing the Path object to the settings file.
     *          if the Path does not exist, calling isSuccessful() to this Result object
     *          returns false.
     */
    public static Result<Path> getDomainSettings(final String name) {
        Path settingspath = Paths.get(getDomainDirectory(name).toString(), "settings.json");
        File settingsfile = settingspath.toFile();
        if (!settingsfile.exists()) {
            return Result.failure(settingspath, "file does not exist");
        } else {
            return Result.success(settingspath);
        }
    }

    /**
     *  checks if the specified directory exists, and attempts to create it if not.
     *
     *  @param  dir the Path object to be chcked, and is supposed to point to a directory. 
     *  @throws RuntimeException if the specified Path points to a file, or if directory
     *                           creation failed.
     */
    public static Path ensureDirectory(final Path dir) {
        File fileobj = dir.toFile();
        if (fileobj.exists()) {
            if (fileobj.isDirectory()) {
                // do nothing
            } else {
                throw new RuntimeException(String.format("is a file: %s", dir.toString()));
            }
        } else {
            if (fileobj.mkdirs()) {
                LOGGER.info(String.format("created: %s", dir.toString()));
            } else {
                throw new RuntimeException(String.format("cannot create: %s", dir.toString()));
            }
        }
        return dir;
    }
}
