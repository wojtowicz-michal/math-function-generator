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
 *
 * Created on October 14, 2007, 1:42 PM
 */

package gnuplot;

import gnuplot.terminal.GNUPlotTerminal;
import gnuplot.utils.Debug;
import gnuplot.utils.FileUtils;

import java.io.*;

/**
 * Object representing the low level com.gnuplot executable. This object is used in
 * the plot() method of GNUPlot.
 *
 * @author teras
 */
class GNUPlotExec {

    private static final transient String DEFAULT_PATH = FileUtils.findPathExec();
    private transient String gnuplotexec;
    private boolean ispersist;
    private final static String[] persistcommand = {"path", "file", "-persist"};
    private final static String[] nopersist = {"path", "file"};

    /**
     * Create a new GNUPlotExec object with defaultΩ com.gnuplot path. Under POSIX
     * environment, it is able to automatically find com.gnuplot executable in the
     * $PATH
     *
     * @throws IOException if something went wrong and it is impossible
     *                     to continue without further notice
     */
    GNUPlotExec() throws IOException {
        this(null);
    }

    /**
     * Create a new GNUPlotExec object with a specific com.gnuplot path.
     *
     * @param path Path of com.gnuplot executable
     * @throws IOException if the com.gnuplot executable is not found
     */
    GNUPlotExec(String path) throws IOException {
        if (path == null)
            path = DEFAULT_PATH;
        setGNUPlotPath(path);
        ispersist = true;
    }

    /**
     * Set the desired path for com.gnuplot executable.
     *
     * @param path Filename of com.gnuplot executable
     * @throws IOException com.gnuplot is not found, or not valid
     */
    final void setGNUPlotPath(String path) throws IOException {
        if (new File(path).isFile())
            gnuplotexec = path;
        else
            throw new IOException("GnuPlot executable \"" + path + "\" not found.");
    }

    /**
     * Retrieve the file path of com.gnuplot
     *
     * @return The com.gnuplot file path
     */
    String getGNUPlotPath() {
        return gnuplotexec;
    }

    /**
     * Retrieves the command which will actually send to com.gnuplot, if we perform
     * a plot with the given parameters to the selected terminal. <br> This
     * method is used for debugging purposes.
     *
     * @return The commands to send to the com.gnuplot executable
     */
    public String getCommands(GNUPlotParameters par, GNUPlotTerminal terminal) {
        // Could cache these commands..
        return par.getPlotCommands(terminal);
    }

    /**
     * Plot using specific parameters and selected terminal.
     *
     * @param par      The parameters to use
     * @param terminal The terminal to use
     * @throws GNUPlotException throw if something goes
     *                          wrong
     */
    void plot(GNUPlotParameters par, GNUPlotTerminal terminal) throws GNUPlotException {
        try {
            final GNUPlotTerminal term = terminal;  // Use this thread-aware variable instead of "terminal"
            final String comms = getCommands(par, term); // Get the commands to send to com.gnuplot
            final Messages msg = new Messages();    // Where to store messages from output threads

            /*
             * Display plot commands to send to com.gnuplot
             */
            GNUPlot.getDebugger().msg("** Start of plot commands **", Debug.INFO);
            GNUPlot.getDebugger().msg(comms, Debug.INFO);
            GNUPlot.getDebugger().msg("** End of plot commands **", Debug.INFO);

            /*
             * It's time now to start the actual com.gnuplot application
             */
            String[] command;
            if (ispersist)
                command = persistcommand;
            else
                command = nopersist;
            command[0] = getGNUPlotPath();
            command[1] = FileUtils.createTempFile(comms);

            {
                String cmdStr = "";
                for (String cmd : command)
                    cmdStr += cmd + " ";
                GNUPlot.getDebugger().msg("exec(" + cmdStr + ")", Debug.INFO);
            }
            final Process proc = Runtime.getRuntime().exec(command);

            /*
             * Windows buffers DEMAND asynchronus read & write
             */

            /*
             * Thread to process the STDERR of com.gnuplot
             */
            Thread err_thread = new Thread() {

                @Override
                public void run() {
                    BufferedReader err = new BufferedReader(new InputStreamReader(proc.getErrorStream()));
                    StringBuilder buf = new StringBuilder();
                    String line;
                    try {
                        while ((line = err.readLine()) != null)
                            if (!line.trim().equals(""))
                                buf.append(line).append('\n');
                        err.close();
                        msg.output = buf.toString(); // Store output stream
                    } catch (IOException ex) {
                        ex.printStackTrace(new PrintStream(System.out));
                    }
                }
            };
            /*
             * Thread to process the STDOUT of com.gnuplot
             */
            err_thread.start();
            Thread out_thread = new Thread() {

                @Override
                public void run() {
                    msg.process = term.processOutput(proc.getInputStream());    // Execute terminal specific output parsing
                }
            };
            out_thread.start();

            try {
                proc.waitFor(); // wait for process to finish
                out_thread.join();  // wait for output (terminal related) thread to finish
                err_thread.join();  // wait for error (messages) output to finish
            } catch (InterruptedException ex) {
                throw new GNUPlotException("Interrupted execution of com.gnuplot");
            }
            new File(command[1]).delete();

            /*
             * Find the error message, if any, with precendence to the error
             * thread
             */
            String message = msg.error != null ? msg.error : msg.process;

            /*
             * Determine if error stream should be dumbed or not
             */
            int level = Debug.VERBOSE;
            if (message != null)
                level = Debug.ERROR;
            GNUPlot.getDebugger().msg("** Start of error stream **", level);
            GNUPlot.getDebugger().msg(msg.output, level);
            GNUPlot.getDebugger().msg("** End of error stream **", level);

            /*
             * Throw an exception if an error occured
             */
            if (message != null)
                throw new GNUPlotException(message);

        } catch (IOException ex) {
            throw new GNUPlotException("IOException while executing \"" + getGNUPlotPath() + "\":" + ex.getLocalizedMessage());
        }

    }

    void setPersist(boolean persist) {
        ispersist = persist;
    }

    private class Messages {

        String output = "";
        String error = null;
        String process = null;
    }
}
