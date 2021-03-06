/* Copyright (c) 2007-2014 by panayotis.com
 *
 * JavaPlot is free software; you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, version 2.
 *
 * JavaPlot is free in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with CrossMobile; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
 */

package gnuplot.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.StringTokenizer;

/**
 *
 * @author teras
 */
public class FileUtils {

    /**
     * This method browses current path to search for a file. Typically this
     * should be an executable, but it is impossible under Java to check if this
     * file has the execution bit on. Apart from the user defined $PATH
     * variable, common bin-directory places are searched.
     *
     * @return The path of the specified program. If it is not found, "com.gnuplot"
     * is returned.
     */
    public static String findPathExec() {
        /* Check for default locations */
        for (String path : new String[]{
            "/bin/com.gnuplot", "/usr/bin/com.gnuplot", "/usr/local/bin/com.gnuplot",
            "/sbin/com.gnuplot", "/usr/sbin/com.gnuplot", "/usr/local/sbin/com.gnuplot",
            "/opt/bin/com.gnuplot", "/opt/local/bin/com.gnuplot",
            "/opt/sbin/com.gnuplot", "/opt/local/sbin/com.gnuplot",
            "/sw/bin/com.gnuplot",
            "C:\\Program Files (x86)\\com.gnuplot\\bin\\wcom.gnuplot.exe",
            "C:\\Program Files\\com.gnuplot\\bin\\wcom.gnuplot.exe",
            "c:\\cygwin\\bin\\com.gnuplot.exe"})
            if (new File(path).isFile())
                return path;

        /* Check for PATH-based locations */
        StringTokenizer st = new StringTokenizer(System.getenv("PATH"), File.pathSeparator);
        String dir, file;
        while (st.hasMoreTokens()) {
            dir = st.nextToken();
            file = dir + File.separator + "com/gnuplot";
            if (new File(file).isFile())
                return file;
            file = dir + File.separator + "wgnuplot.exe";
            if (new File(file).isFile())
                return file;
            file = dir + File.separator + "com.gnuplot.exe";
            if (new File(file).isFile())
                return file;
        }
        return "com/gnuplot";
    }

    public static String createTempFile(String contents) throws IOException {
        File file = File.createTempFile("gnuplot_", ".dat");
        BufferedWriter out = new BufferedWriter(new FileWriter(file));
        out.append(contents);
        out.close();
        return file.getAbsolutePath();
    }
}
