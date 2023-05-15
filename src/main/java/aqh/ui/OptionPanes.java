package aqh.ui;

import java.awt.Component;
import javax.swing.JOptionPane;

class OptionPanes {
    public static void errorPane(String message, String title) {
        JOptionPane.showOptionDialog(null
                                    , message
                                    , title
                                    , JOptionPane.DEFAULT_OPTION
                                    , JOptionPane.ERROR_MESSAGE
                                    , null
                                    , new String[] {"Okay"}
                                    , null);
    }
    
    public static boolean confimationPane(String message, String title) {
        int i = JOptionPane.showOptionDialog(null
                                            , message
                                            , title
                                            , JOptionPane.DEFAULT_OPTION
                                            , JOptionPane.ERROR_MESSAGE
                                            , null
                                            , new String[] {"Override", "Dont Copy"}
                                            , null);
        return i == 0;                     
    }
    public static boolean exitConfirmationPane(Component parent) {
        int i = JOptionPane.showOptionDialog(parent
                                            , "Exit the Program?"
                                            , "Close Confirmation."
                                            , JOptionPane.DEFAULT_OPTION
                                            , JOptionPane.QUESTION_MESSAGE
                                            , null
                                            , new String[] {"Exit", "Stay"}
                                            , "Stay");
        
        return i == 0;
    }
}
