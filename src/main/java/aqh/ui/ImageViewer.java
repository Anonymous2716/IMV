package aqh.ui;

import java.awt.Label;
import java.awt.Cursor;
import java.awt.event.ActionEvent;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.GroupLayout;
import javax.swing.LayoutStyle;
import javax.swing.JFileChooser;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;

import static aqh.ui.AppVars.COLOR_VALID;
import static aqh.ui.AppVars.COLOR_INVALID;
import static aqh.ui.AppVars.COLOR_WARNING;
/*import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;*/

public class ImageViewer extends JFrame {
    
    private boolean isRightPathValid = false;
    private boolean isLeftPathValid = false;
    private boolean isMainPathValid = false;
    private boolean isLogPathValid = false;

    private final Label mainMessageLabel,mainPathLabel,
                        leftPathLabel, leftMessageLabel,
                        rightPathLabel, rightMessageLabel,
                        logPathLabel, logMessageLabel;
    
    private final JTextField mainPathField, leftPathField, rightPathField, logPathField ;
    private final JButton mainBrowseButton, leftBrowseButton, rightBrowseButton, logDirBrowseButton, checkButton, startButton, clearButton; 

    public ImageViewer() {
        

       /* if (System.getProperty("os.name").toLowerCase().contains("linux")) {
            // Get the name of the current look and feel
            String lookAndFeelName = UIManager.getLookAndFeel().getName();
            System.out.println(lookAndFeelName);
            // Check if the look and feel is GTK+
            if (lookAndFeelName.equals("GTK+")) {
                // The system is using the GTK+ look and feel
                if (OptionPanes.confimationPane("Use GTK Theme?", "GTK Available")) {
                    System.out.println("System is using GTK+");

                    try {              
                        UIManager.setLookAndFeel("com.sun.java.swing.plaf.gtk.GTKLookAndFeel");           
                    } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | UnsupportedLookAndFeelException e) {
                        OptionPanes.errorPane("Cannot Set UIManager " + e.getMessage(), "Cannot Set GTK Theme");
                    }       
            
                    System.setProperty("awt.useSystemAAFontSettings", "on");
                    System.setProperty("swing.useSystemFontSettings", "true");  
                }
            }
        }*/

        mainPathLabel = new Label();
        mainPathLabel.setForeground(AppVars.FOREGROUND_COLOR);
        mainPathLabel.setText("Main Path: ");

        mainPathField = new JTextField();
        mainPathField.setBackground(AppVars.BACKGROUND_COLOR);
        mainPathField.setForeground(AppVars.FOREGROUND_COLOR);

        mainBrowseButton = new JButton();
        mainBrowseButton.setBackground(AppVars.BACKGROUND_COLOR);
        mainBrowseButton.setForeground(AppVars.FOREGROUND_COLOR);
        mainBrowseButton.setText("Open");
        mainBrowseButton.setToolTipText("Click to open FileChooser.");
        mainBrowseButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        mainBrowseButton.addActionListener((ActionEvent evt) -> {
            chooseFilePath(0);
        });

        mainMessageLabel = new Label();
        

        //second (left) row components
        leftPathLabel = new Label();
        leftPathLabel.setForeground(AppVars.FOREGROUND_COLOR);
        leftPathLabel.setText("Left Path:");

        leftPathField = new JTextField();
        leftPathField.setBackground(AppVars.BACKGROUND_COLOR);
        leftPathField.setForeground(AppVars.FOREGROUND_COLOR);

        leftBrowseButton = new JButton();
        leftBrowseButton.setBackground(AppVars.BACKGROUND_COLOR);
        leftBrowseButton.setForeground(AppVars.FOREGROUND_COLOR);
        leftBrowseButton.setText("Open");
        leftBrowseButton.setToolTipText("Click to open FileChooser.");
        leftBrowseButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        leftBrowseButton.addActionListener((ActionEvent evt) -> {
            chooseFilePath(1);
        });

        leftMessageLabel = new Label();
        
        
        //third (right) riw components
        rightPathLabel = new Label();
        rightPathLabel.setForeground(AppVars.FOREGROUND_COLOR);
        rightPathLabel.setText("Right Path: ");

        rightPathField = new JTextField();
        rightPathField.setBackground(AppVars.BACKGROUND_COLOR);
        rightPathField.setForeground(AppVars.FOREGROUND_COLOR);

        rightBrowseButton = new JButton();
        rightBrowseButton.setBackground(AppVars.BACKGROUND_COLOR);
        rightBrowseButton.setForeground(AppVars.FOREGROUND_COLOR);
        rightBrowseButton.setText("Open");
        rightBrowseButton.setToolTipText("Click to open FileChooser.");
        rightBrowseButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        rightBrowseButton.addActionListener((ActionEvent evt) -> {
            chooseFilePath(2);
        });

        rightMessageLabel = new Label();
        

        //fourth (log) row components
        logPathLabel = new Label();
        logPathLabel.setForeground(AppVars.FOREGROUND_COLOR);
        logPathLabel.setText("Log Path:");

        logPathField = new JTextField();
        logPathField.setBackground(AppVars.BACKGROUND_COLOR);
        logPathField.setForeground(AppVars.FOREGROUND_COLOR);

        logDirBrowseButton = new JButton();
        logDirBrowseButton.setBackground(AppVars.BACKGROUND_COLOR);                                   
        logDirBrowseButton.setForeground(AppVars.FOREGROUND_COLOR);
        logDirBrowseButton.setText("Open");          
        logDirBrowseButton.setToolTipText("Click to open FileChooser.");
        logDirBrowseButton.setCursor(new Cursor(Cursor.HAND_CURSOR));   
        logDirBrowseButton.addActionListener((ActionEvent evt) -> {
            chooseFilePath(3);
        });

        logMessageLabel = new Label();
        

        //start button
        startButton = new JButton();
        startButton.setBackground(AppVars.BACKGROUND_COLOR);
        startButton.setForeground(AppVars.FOREGROUND_COLOR);
        startButton.setText("Start");                 
        startButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        startButton.addActionListener((ActionEvent evt) -> {
            checkPaths();                  
            if (isMainPathValid && isLeftPathValid && isRightPathValid && isLogPathValid) {
                startApplication();
                dispose();
            }
        });


        //check button
        checkButton = new JButton();
        checkButton.setBackground(AppVars.BACKGROUND_COLOR);
        checkButton.setForeground(AppVars.FOREGROUND_COLOR);
        checkButton.setText("Check");            
        checkButton.setToolTipText("Check Validity.");
        checkButton.setCursor(new Cursor(Cursor.HAND_CURSOR));     
        checkButton.addActionListener((ActionEvent evt) -> {
            checkPaths();
        });


        //clear button
        clearButton = new JButton();
        clearButton.setBackground(AppVars.BACKGROUND_COLOR);
        clearButton.setForeground(AppVars.FOREGROUND_COLOR);
        clearButton.setText("Clear");
        clearButton.setToolTipText("Clear All Entry.");
        clearButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        clearButton.addActionListener((ActionEvent evt) -> {
            clearPathAndMessages();
        });
        

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationByPlatform(true);
        setName("Image Viewer");


        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(rightPathLabel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(90, 90, 90)
                                        .addComponent(clearButton)
                                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(checkButton, GroupLayout.PREFERRED_SIZE, 89, GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(startButton))
                                    .addComponent(rightMessageLabel, GroupLayout.Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(logPathLabel)
                                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(logPathField, GroupLayout.PREFERRED_SIZE, 272, GroupLayout.PREFERRED_SIZE))
                                    .addComponent(logMessageLabel, GroupLayout.Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)))
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                            .addComponent(rightBrowseButton)
                            .addComponent(logDirBrowseButton)))
                    .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                            .addComponent(leftMessageLabel, GroupLayout.Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(mainMessageLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(mainPathLabel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 19, Short.MAX_VALUE)
                                .addComponent(mainPathField, GroupLayout.PREFERRED_SIZE, 269, GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(leftPathLabel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(leftPathField, GroupLayout.PREFERRED_SIZE, 269, GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(rightPathField, GroupLayout.PREFERRED_SIZE, 269, GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                            .addComponent(mainBrowseButton)
                            .addComponent(leftBrowseButton))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(mainPathLabel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(mainBrowseButton)
                        .addComponent(mainPathField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(mainMessageLabel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(leftPathLabel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(leftPathField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addComponent(leftBrowseButton)))
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(leftMessageLabel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(rightBrowseButton)
                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                        .addComponent(rightPathField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addComponent(rightPathLabel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(rightMessageLabel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(logPathField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addComponent(logDirBrowseButton)
                    .addComponent(logPathLabel))
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(logMessageLabel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(clearButton)
                    .addComponent(checkButton)
                    .addComponent(startButton))
                .addContainerGap(22, Short.MAX_VALUE))
        );


        pack();
    }

    private void checkPaths() {
        String mainPath = mainPathField.getText();
        String leftPath = leftPathField.getText();
        String rightPath = rightPathField.getText();
        String logPath = logPathField.getText();

        Path[] paths = {Paths.get(mainPath), Paths.get(leftPath), Paths.get(rightPath), Paths.get(logPath)};

        
        for (int i = 0; i < 4; i++) {
            Path path = paths[i];
            String pathStr = path.toString();
            System.out.println();
            
            if (!pathStr.trim().isEmpty()) {
                if (Files.exists(path)) {
                    pathExistsLogger(i, pathStr);
                    if (Files.isDirectory(path)) {
                        pathIsDirectoryLogger(i, pathStr);
                        if (Files.isReadable(path)) {
                            pathReadableLogger(i, pathStr);
                            if (Files.isWritable(path)) {
                                pathWritableLogger(i, pathStr);
                                if (preOkay(paths.clone())) {
                                    allOkay(i, pathStr);
                                }
                            } else {
                                notWritableLogger(i, pathStr);
                                notWritableButReadableDirectory(i, pathStr);
                            }
                        } else {
                            pathNotReadableLogger(i, pathStr);
                            if (Files.isWritable(path)) {
                                pathWritableLogger(i, pathStr);
                                notReadableButWriteableDirectory(i, pathStr);
                            } else {
                                notWritableLogger(i, pathStr);
                                notReadableNotWritableMessageSetter(i);
                                invalidPath(i);
                            }
                        }
                    } else {
                        notADirectoryLoggerAndMessageSetter(i, pathStr);
                        if (Files.isReadable(path)) {
                            pathReadableLogger(i, pathStr);
                            if (Files.isWritable(path)) {
                                pathWritableLogger(i, pathStr);
                            } else {
                                notWritableLogger(i, pathStr);
                            }
                        } else {
                            pathNotReadableLogger(i, pathStr);
                            if (Files.isWritable(path)) {
                                pathWritableLogger(i, pathStr);
                            } else {
                                notWritableLogger(i, pathStr);
                            }
                        }
                        invalidPath(i);
                    }
                } else {
                     doesNotExistLoggerAndMessageSetter(i, pathStr);
                     invalidPath(i);
                }
            } else {
                pathEmptyLoggerAndSetErrorMessage(i);
                invalidPath(i);
            }
        }
    }
    
    private void chooseFilePath(int button) {
        // Create a file chooser dialog
        JFileChooser chooser = new JFileChooser();
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        
        
        int returnVal = chooser.showOpenDialog(this);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            // Get the selected file or directory
            File selectedFile = chooser.getSelectedFile();
            String selectedAbsolutePathString = selectedFile.getAbsolutePath();

            // Set the path to the corresponding text field
            switch (button) {
                case 0:
                    mainPathField.setText(selectedAbsolutePathString);
                    break;
                case 1:
                    leftPathField.setText(selectedAbsolutePathString);
                    break;
                case 2:
                    rightPathField.setText(selectedAbsolutePathString);
                    break;
                case 3:
                    logPathField.setText(selectedAbsolutePathString);
            }
        }
    }
    
    private void notWritableButReadableDirectory(int i, String pathStr) {
        switch (i) {
            case 0:
                AppVars.getInstance().setMainPathStr(pathStr);
                mainMessageLabel.setForeground(COLOR_WARNING);
                mainMessageLabel.setText("Okay But Read Only FileSystem");
                isMainPathValid = true;
                break;
            case 1:
                AppVars.getInstance().setLeftPathUnknown();
                leftMessageLabel.setForeground(COLOR_INVALID);
                leftMessageLabel.setText("Read Only FileSystem");
                isLeftPathValid = false;
                break;
            case 2:
                AppVars.getInstance().setRightPathUnknown();
                rightMessageLabel.setForeground(COLOR_INVALID);
                rightMessageLabel.setText("Read Only FileSystem");
                isRightPathValid = false;
                break;
            case 3:
                AppVars.getInstance().setLogPathUnknown();
                logMessageLabel.setForeground(COLOR_INVALID);
                logMessageLabel.setText("Read Only FileSystem");
                isLogPathValid = false;
                break;
        }
    }
    
    private void notReadableButWriteableDirectory(int i, String pathStr) {
        switch(i) {
            case 0:
                AppVars.getInstance().setMainPathUnknown();
                mainMessageLabel.setForeground(COLOR_INVALID);
                mainMessageLabel.setText("Not Readable");
                isMainPathValid = false;
                break;
            case 1:
                AppVars.getInstance().setLeftPathStr(pathStr);
                leftMessageLabel.setForeground(COLOR_WARNING);
                leftMessageLabel.setText("Okay But FileSystem Not Readable");
                isLeftPathValid = true;
                break;
            case 2:
                AppVars.getInstance().setRightPathStr(pathStr);
                rightMessageLabel.setForeground(COLOR_WARNING);
                rightMessageLabel.setText("Okay But FileSystem Not Readable");
                isRightPathValid = true;
                break;
            case 3:
                AppVars.getInstance().setRightPathStr(pathStr);
                logMessageLabel.setForeground(COLOR_WARNING);
                logMessageLabel.setText("Okay But FileSystem Not Readable");
                isLogPathValid = true;
                break;
        }
    }
    
    private void invalidPath(int i) {
        switch (i) {
            case 0 :
                AppVars.getInstance().setMainPathUnknown();
                isMainPathValid = false;
                break;
            case 1 :
                AppVars.getInstance().setLeftPathUnknown();
                isLeftPathValid = false;
                break;
            case 2 :
                AppVars.getInstance().setRightPathUnknown();
                isRightPathValid = false;
                break;
            case 3:
                AppVars.getInstance().setLogPathUnknown();
                isLogPathValid = false;
                break;
        }
    }
    
    private boolean preOkay(Path[] paths) {
        
        boolean mnL;
        boolean mnR;
     
        if (paths[1].equals(paths[2])) {
            isLeftPathValid = true;
            leftMessageLabel.setForeground(COLOR_WARNING);
            leftMessageLabel.setText("Same As Right Path");
            
            isRightPathValid = true;
            rightMessageLabel.setForeground(COLOR_WARNING);
            rightMessageLabel.setText("Same As Leftt Path");
        }
        
        if (paths[0].equals(paths[1])) {
            mnL = false;
            isLeftPathValid = false;
            AppVars.getInstance().setLeftPathUnknown();
            leftMessageLabel.setForeground(COLOR_INVALID);
            leftMessageLabel.setText("Cannot Be The Same As Main Path");
        } else {
            mnL = true;
        }
        
        if (paths[0].equals(paths[2])) {
            mnR = false;
            isRightPathValid = false;
            AppVars.getInstance().setRightPathUnknown();
            rightMessageLabel.setForeground(COLOR_INVALID);
            rightMessageLabel.setText("Cannot Be The Same As Main Path");
        } else {
            mnR = true;
        }
        
        return mnL && mnR;
    }
    
    private void allOkay(int i, String pathStr) {

        switch (i) {
            case 0 :
                isMainPathValid = true;
                AppVars.getInstance().setMainPathStr(pathStr);
                mainMessageLabel.setForeground(COLOR_VALID);
                mainMessageLabel.setText("Okay");
                break;
            case 1 :
                isLeftPathValid = true;
                AppVars.getInstance().setLeftPathStr(pathStr);
                leftMessageLabel.setForeground(COLOR_VALID);
                leftMessageLabel.setText("Okay");
                break;
            case 2 :
                isRightPathValid = true;
                AppVars.getInstance().setRightPathStr(pathStr);
                rightMessageLabel.setForeground(COLOR_VALID);
                rightMessageLabel.setText("Okay");
                break;
            case 3 :
                isLogPathValid = true;
                AppVars.getInstance().setLogFileDirStr(pathStr);
                logMessageLabel.setForeground(COLOR_VALID);
                logMessageLabel.setText("Okay");
                break;
        }
    }
    
    private void pathExistsLogger(int i, String pathStr) {
        switch (i) {
            case 0 : 
                System.err.println("Main Path \"" + pathStr + "\" Exists");
                break;
            case 1 : 
                System.out.println("Left Path \"" + pathStr + "\" Exists");
                break;
            case 2 : 
                System.out.println("Right Path \"" + pathStr + "\" Exists");
                break;
            case 3 : 
                System.out.println("Log Dir Path \"" + pathStr + "\" Exists");
                break;
        }
    }
    
    private void pathReadableLogger(int i, String pathStr) {
        switch (i) {
            case 0 : 
                System.out.println("Main Path \"" + pathStr + "\" is Readable");
                break;
            case 1 : 
                System.out.println("Left Path \"" + pathStr + "\" is Readable");
                break;
            case 2 : 
                System.out.println("Right Path \"" + pathStr + "\" is Readable");
                break;
            case 3 : 
                System.out.println("Log Dir Path \"" + pathStr + "\" is Readable");
                break;
        }
    }
    
    private void pathNotReadableLogger(int i, String pathStr) {
        switch (i) {
            case 0 : 
                System.out.println( "Main Path \"" + pathStr + "\" is Not Readable");
                break;
            case 1 : 
                System.out.println( "Left Path \"" + pathStr + "\" is Not Readable");
                break;
            case 2 : 
                System.out.println( "Right Path \"" + pathStr + "\" is Not Readable");
                break;
            case 3 : 
                System.out.println( "Log Dir Path \"" + pathStr + "\" is Not Readable");
                break;
        }
    }
    
    private void pathWritableLogger(int i, String pathStr) {
        switch (i) {
            case 0 :
                System.out.println( "Main Path \"" + pathStr + "\" is Writable");
                break;
            case 1 :
                System.out.println( "Left Path \"" + pathStr + "\" is Writable");
                break;
            case 2 :
                System.out.println( "Right Path \"" + pathStr + "\" is Writable");
                break;
            case 3 :
                System.out.println( "Log Dir Path \"" + pathStr + "\" is Writable");
                break;
        }
    }
    
    private void notWritableLogger(int i, String pathStr) {
        switch (i) {
            case 0 :
                System.out.println( "Main Path \"" + pathStr + "\" is not Writable");
                break;
            case 1 :
                System.out.println( "Left Path \"" + pathStr + "\" is not Writable");
                break;
            case 2 :
                System.out.println( "Right Path \"" + pathStr + "\" is not Writable");
                break;
            case 3 :
                System.out.println( "Log Dir Path \"" + pathStr + "\" is not Writable");
                break;
        }
    }
    
    private void pathIsDirectoryLogger(int i, String pathStr) {
        switch (i) {
            case 0 : 
                System.out.println( "Main Path \"" + pathStr + "\" Is A Directory");
                break;
            case 1 :
                System.out.println( "Left Path \"" + pathStr + "\" Is A Directory");
                break;
            case 2 :
                System.out.println( "Right Path \"" + pathStr + "\" Is A Directory");
                break;
            case 3 :
                System.out.println( "Log Dir Path \"" + pathStr + "\" Is A Directory");
                break;
        }
    
    }
    
    private void notADirectoryLoggerAndMessageSetter(int i, String pathStr) {
        switch (i) {
            case 0 :
                System.out.println( "Main Path \"" + pathStr + "\" is Not a Directory");
                mainMessageLabel.setForeground(COLOR_INVALID);
                mainMessageLabel.setText("Not A Directory");
                break;
            case 1 : 
                System.out.println( "Left Path \"" + pathStr + "\" is Not a Directory");
                leftMessageLabel.setForeground(COLOR_INVALID);
                leftMessageLabel.setText("Not A Directory");
                break;
            case 2 :
                System.out.println( "Right Path \"" + pathStr + "\" is Not a Directory");
                rightMessageLabel.setForeground(COLOR_INVALID);
                rightMessageLabel.setText("Not A Directory");
                break;
            case 3 :
                System.out.println( "Log Path \"" + pathStr + "\" is Not a Directory");
                logMessageLabel.setForeground(COLOR_INVALID);
                logMessageLabel.setText("Not A Directory");
                break;
        }
    }
     
    private void doesNotExistLoggerAndMessageSetter(int i, String pathStr) {
         switch (i) {
            case 0 :
                System.out.println( "Main Path \"" + pathStr + "\" Does Not Exist or Inaccessible");
                mainMessageLabel.setForeground(COLOR_INVALID);
                mainMessageLabel.setText("Does Not Exist");
                break;
            case 1 :
                System.out.println( "Left Path \"" + pathStr + "\" Does Not Exist or Inaccessible");
                leftMessageLabel.setForeground(COLOR_INVALID);
                leftMessageLabel.setText("Does Not Exist");
                break;
            case 2 :
                System.out.println( "Right Path \"" + pathStr + "\" Does Not Exist or Inaccessible");
                rightMessageLabel.setForeground(COLOR_INVALID);
                rightMessageLabel.setText("Does Not Exist");
                break;
            case 3 :
                System.out.println( "Log Path \"" + pathStr + "\" Does Not Exist or Inaccessible");
                logMessageLabel.setForeground(COLOR_INVALID);
                logMessageLabel.setText("Does Not Exist");
                break;
        }
    }
    
    private void pathEmptyLoggerAndSetErrorMessage(int i) {
        switch (i) {
            case 0 :
                System.out.println( "Main Path Empty Input");
                mainMessageLabel.setForeground(COLOR_INVALID);
                mainMessageLabel.setText("Cannot Be Empty");
                break;
            case 1 :
                System.out.println( "Left Path Empty Input");
                leftMessageLabel.setForeground(COLOR_INVALID);
                leftMessageLabel.setText("Cannot Be Empty");
                break;
            case 2 :
                System.out.println( "Right Path Empty Input");
                rightMessageLabel.setForeground(COLOR_INVALID);
                rightMessageLabel.setText("Cannot Be Empty");
                break;
            case 3 :
                System.out.println( "Log Path Empty Input");
                logMessageLabel.setForeground(COLOR_INVALID);
                logMessageLabel.setText("Cannot Be Empty");
                break;
        }
    }
    
    private void notReadableNotWritableMessageSetter(int i) {
        switch (i) {
            case 0:
                mainMessageLabel.setForeground(COLOR_INVALID);
                mainMessageLabel.setText("Directory But Not Readable Or Writable");
                break ;
            case 1:
                leftMessageLabel.setForeground(COLOR_INVALID);
                leftMessageLabel.setText("Directory But Not Readable Or Writable");
                break;
            case 2:
                rightMessageLabel.setForeground(COLOR_INVALID);
                rightMessageLabel.setText("Directory But Not Readable Or Writable");
                break;
            case 3:
                logMessageLabel.setForeground(COLOR_INVALID);
                logMessageLabel.setText("Directory But Not Readable Or Writable");
                break;
        }
    }
    
    private void clearPathAndMessages() {
        for (int i = 0; i < 3; i++) {
            invalidPath(i);
        }
        
        mainPathField.setText("");
        leftPathField.setText("");
        rightPathField.setText("");
        logPathField.setText("");
        
        mainMessageLabel.setText("");
        leftMessageLabel.setText("");
        rightMessageLabel.setText("");
        logMessageLabel.setText("");
        System.out.println( "Path Field , MessageField Reset.");
    }

    private void startApplication() {
        
        System.out.println("Starting Main Viewer...");
        
        java.awt.EventQueue.invokeLater(() -> {
            new MainViewer();
        });
        
        System.out.println("Main Viewer Started.");
    }
    
    public static void main(String args[]) {
        SwingUtilities.invokeLater(() -> {
            new ImageViewer().setVisible(true);
        });
    }
}
