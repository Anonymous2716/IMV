package aqh.ui;

import java.awt.Color;

public class AppVars {
    
    public static final Color FOREGROUND_COLOR = new Color(68, 44, 46);
    public static final Color BACKGROUND_COLOR = new Color(250, 255, 225);
    
    public static final Color COLOR_VALID = Color.GREEN;
    public static final Color COLOR_WARNING = Color.ORANGE;
    public static final Color COLOR_INVALID = Color.PINK;
    
    private static final String NOT_SET = "Not Set";

    private static AppVars appVarsInstance = null;
    private String mainPath;
    private String leftPath;
    private String rightPath;
    private String logFileDirStr;

    public AppVars() {
        this.mainPath = NOT_SET;
        this.leftPath = NOT_SET;
        this.rightPath = NOT_SET;
        this.logFileDirStr = NOT_SET;
    }

    public static AppVars getInstance() {
        if (appVarsInstance == null) {
            appVarsInstance = new AppVars();
        }
        return appVarsInstance;
    }

    //set
    public void setMainPathStr(String mainPath) {
        this.mainPath = mainPath;
    }

    public void setLeftPathStr(String rightPath) {
        this.leftPath = rightPath;
    }

    public void setRightPathStr(String leftPath) {
        this.rightPath = leftPath;
    }
    
    public void setLogFileDirStr(String logFileDirStr) {
        this.logFileDirStr = logFileDirStr;
    }

    //reset
    public void setMainPathUnknown() {
        this.mainPath = NOT_SET;
    }

    public void setLeftPathUnknown() {
        this.leftPath = NOT_SET;
    }

    public void setRightPathUnknown() {
        this.rightPath = NOT_SET;
    }
    
    public void setLogPathUnknown() {
        this.logFileDirStr = NOT_SET;
    }

    //get
    public String getMainPathStr() {
        return this.mainPath;
    }

    public String getLeftPathStr() {
        return this.leftPath;
    }

    public String getRightPathStr() {
        return this.rightPath;
    }
    
    public String getLogFileDirStr() {
        return this.logFileDirStr;
    }
}
