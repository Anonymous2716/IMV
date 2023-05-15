
package aqh.ui;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;
import java.nio.charset.Charset;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class LogIt {
    
    private static LogIt logItInstance = null;
    
    private static final File LOG_FILE = new File(AppVars.getInstance().getLogFileDirStr() , "AppLog_" + new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss.SSS").format(Calendar.getInstance().getTime()) + ".log" );
  
    PrintWriter logFileWriter, consoleWriter;
    Charset charset;
    
    public LogIt() {

        charset = Charset.isSupported("UTF-8")? Charset.forName("UTF-8"): Charset.defaultCharset();
        
    	try {
            LOG_FILE.createNewFile();
                    
            logFileWriter = new PrintWriter(new FileWriter(LOG_FILE, charset, true));
            consoleWriter = new PrintWriter(System.out, true);
        } catch (IOException ioe) {
            OptionPanes.errorPane("Log File IOE. " + ioe.getMessage(), "Logging IOE");
        }
    }
    
    public void close() {
        this.logFileWriter.close();
        this.consoleWriter.close();
    }
    
    public static LogIt getInstance() {
        if (logItInstance == null) {
            logItInstance = new LogIt();
        }
        return logItInstance;
    }
    
    
    public void logWriter(String logType, String logMessage) {
        String logText = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss.SSS").format(Calendar.getInstance().getTime()) + " " + logType + " " + logMessage;
        this.logFileWriter.println(logText);
        this.logFileWriter.flush();
        this.consoleWriter.println(logText);
        this.consoleWriter.flush();
    }
}
