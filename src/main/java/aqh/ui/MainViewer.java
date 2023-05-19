package aqh.ui;

import com.drew.metadata.Tag;
import com.drew.imaging.FileType;
import com.drew.metadata.Metadata;
import com.drew.metadata.Directory;
import com.drew.imaging.FileTypeDetector;
import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;

import java.awt.FlowLayout;
import java.awt.event.WindowEvent;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.image.BufferedImage;

import javax.swing.JLabel;
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JTextPane;
import javax.swing.LayoutStyle;
import javax.swing.JScrollPane;
import javax.swing.GroupLayout;
import javax.swing.WindowConstants;
import javax.imageio.ImageIO;

import java.util.List;
import java.util.ArrayList;
import java.util.stream.Stream;

import java.io.IOException;
import java.io.InputStream;
import java.io.FileInputStream;
import java.io.BufferedInputStream;
import java.io.FileNotFoundException;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.nio.file.DirectoryStream;
import java.nio.file.StandardCopyOption;

public class MainViewer extends JFrame {

    private JTextPane imageMetadataInfoPane;
    private JScrollPane imageMetadataScrollPane, imageScrollPane;
    private JButton leftButton, loadImageButton, rightButton, skipImageButton, zoomInButton, zoomOutButton;
    private JLabel imageListQuantityLabel, leftQuantityLabel, mainQuantityLabel, metadataInfoTitleLabel, rightQuantityLabel;
    private List<Path> imagePathsList;
    private long mainQuantity, leftQuantity, rightQuantity;
    private final int currentImageIndex = 0;
    private int  imageListSize;
    private final Path mainDirectoryPath, leftDirectoryPath, rightDirectoryPath;
    private ImagePanel imagePanel;
    
    public MainViewer() {
        
        setLayout(new FlowLayout());
        initComponents();
        
        mainDirectoryPath = Paths.get(AppVars.getInstance().getMainPathStr());
        leftDirectoryPath = Paths.get(AppVars.getInstance().getLeftPathStr());
        rightDirectoryPath = Paths.get(AppVars.getInstance().getRightPathStr());
        
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) { 
                if (OptionPanes.exitConfirmationPane(MainViewer.this)) {
                    LogIt.getInstance().logWriter("INFO", "Quiting...");
                    LogIt.getInstance().close();
                    System.exit(0);
                }
            }
        });
        
        pack();
        setVisible(true);
        
        LogIt.getInstance().logWriter("INFO","Main Viewer Created!");
        
        loadImages();
    }

    private void initComponents() {
        
        //main 
        mainQuantityLabel = new JLabel();  
        mainQuantityLabel.setForeground(AppVars.FOREGROUND_COLOR);
        mainQuantityLabel.setText("Files In Main Directory: Unknown");    
         
        //left
        leftButton = new JButton();
        leftButton.setBackground(AppVars.BACKGROUND_COLOR);
        leftButton.setText("Move Left");
        leftButton.addActionListener((ActionEvent evt) -> {
            LogIt.getInstance().logWriter("INFO","Move Left Clicked.");
            if (!imagePathsList.isEmpty()) {
                copyLeft();
            } else {
                LogIt.getInstance().logWriter("INFO", "Nothing to Move");
            }
        });
        leftQuantityLabel = new JLabel();
        leftQuantityLabel.setText("Files In Left Directory: Unknown");
        leftQuantityLabel.setForeground(AppVars.FOREGROUND_COLOR);
      
        //right 
        rightButton = new JButton();
        rightButton.setBackground(AppVars.BACKGROUND_COLOR);
        rightButton.setText("Move Right");
        rightButton.addActionListener((ActionEvent evt) -> {
            LogIt.getInstance().logWriter("INFO","Move Right Clicked.");
            if (!imagePathsList.isEmpty()) {
                copyRight();
            } else {
                LogIt.getInstance().logWriter("INFO", "Nothing to Move");
            }
        });
        rightQuantityLabel = new JLabel();
        rightQuantityLabel.setForeground(AppVars.FOREGROUND_COLOR);
        rightQuantityLabel.setText("Files In Right Directory: Unknown");
        
        //metadata
        metadataInfoTitleLabel = new JLabel();
        metadataInfoTitleLabel.setForeground(AppVars.FOREGROUND_COLOR);
        metadataInfoTitleLabel.setText("Image Metadata Info: ");
        
        imageMetadataScrollPane = new JScrollPane();
        
        imageMetadataInfoPane = new JTextPane();
        imageMetadataInfoPane.setBackground(AppVars.BACKGROUND_COLOR);
        imageMetadataInfoPane.setForeground(AppVars.FOREGROUND_COLOR);
        imageMetadataInfoPane.setText("No Image To Show Metadata Info.");
        
        imageMetadataScrollPane.setViewportView(imageMetadataInfoPane);

        //skip button
        skipImageButton = new JButton();
        skipImageButton.setBackground(AppVars.BACKGROUND_COLOR);
        skipImageButton.setText("Skip Image");
        skipImageButton.setToolTipText("skip this image.");
        skipImageButton.addActionListener((ActionEvent evt) -> {
            LogIt.getInstance().logWriter("INFO","Skip Image Clicked.");
            skipImage();
        });
        
        //load button
        loadImageButton = new JButton();        
        loadImageButton.setBackground(AppVars.BACKGROUND_COLOR);
        loadImageButton.setText("Load");
        loadImageButton.setToolTipText("load the images again...");
        loadImageButton.addActionListener((ActionEvent evt) -> {
            LogIt.getInstance().logWriter("INFO","Load Button Clicked.");
            loadImages();
        });
      

        //zoom in button
        zoomInButton = new JButton();
        zoomInButton.setBackground(AppVars.BACKGROUND_COLOR);
        zoomInButton.setText("Zoom In");
        zoomInButton.addActionListener((ActionEvent evt) -> {
            LogIt.getInstance().logWriter("INFO","ZoomIn Clicked.");
            imagePanel.zoomIn();
        });
        
        //zoom out button
        zoomOutButton = new JButton();
        zoomOutButton.setBackground(AppVars.BACKGROUND_COLOR);
        zoomOutButton.setText("Zoom Out");
        zoomOutButton.addActionListener((ActionEvent evt) -> {
            LogIt.getInstance().logWriter("INFO","ZoomOut Clicked.");
            imagePanel.zoomOut();
        });
        
        imageListQuantityLabel = new JLabel();
        imageListQuantityLabel.setForeground(AppVars.FOREGROUND_COLOR);  
        imageListQuantityLabel.setText("Images In Main Directory: Unknown And Counting...");
        
        imageScrollPane = new JScrollPane();       
        imagePanel = new ImagePanel();

        
        //Written by netbeans GUI editor. hence not touching
        GroupLayout imagePanelLayout = new GroupLayout(imagePanel);
        imagePanel.setLayout(imagePanelLayout);
        imagePanelLayout.setHorizontalGroup(
            imagePanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        imagePanelLayout.setVerticalGroup(
            imagePanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        imageScrollPane.setViewportView(imagePanel);

        
        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                            .addComponent(imageMetadataScrollPane)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addComponent(metadataInfoTitleLabel)
                                .addGap(239, 239, 239)))
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                    .addComponent(leftButton, GroupLayout.PREFERRED_SIZE, 106, GroupLayout.PREFERRED_SIZE)
                                    .addComponent(zoomInButton, GroupLayout.PREFERRED_SIZE, 106, GroupLayout.PREFERRED_SIZE)
                                    .addComponent(loadImageButton, GroupLayout.PREFERRED_SIZE, 106, GroupLayout.PREFERRED_SIZE))
                                .addGap(30, 30, 30)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                    .addComponent(rightButton, GroupLayout.PREFERRED_SIZE, 106, GroupLayout.PREFERRED_SIZE)
                                    .addComponent(skipImageButton, GroupLayout.PREFERRED_SIZE, 106, GroupLayout.PREFERRED_SIZE)
                                    .addComponent(zoomOutButton, GroupLayout.PREFERRED_SIZE, 106, GroupLayout.PREFERRED_SIZE)))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                    .addComponent(mainQuantityLabel, GroupLayout.PREFERRED_SIZE, 269, GroupLayout.PREFERRED_SIZE)
                                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                        .addComponent(imageListQuantityLabel, GroupLayout.PREFERRED_SIZE, 269, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(leftQuantityLabel, GroupLayout.PREFERRED_SIZE, 269, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(rightQuantityLabel, GroupLayout.PREFERRED_SIZE, 269, GroupLayout.PREFERRED_SIZE)))))
                        .addGap(0, 18, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(imageScrollPane)
                        .addGap(7, 7, 7))))
        );
        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(imageScrollPane, GroupLayout.PREFERRED_SIZE, 390, GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(metadataInfoTitleLabel)
                        .addGap(7, 7, 7)
                        .addComponent(imageMetadataScrollPane))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(40, 40, 40)
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                            .addComponent(zoomInButton)
                            .addComponent(zoomOutButton, GroupLayout.Alignment.TRAILING))
                        .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                            .addComponent(leftButton)
                            .addComponent(rightButton, GroupLayout.Alignment.TRAILING))
                        .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                            .addComponent(loadImageButton)
                            .addComponent(skipImageButton))
                        .addGap(70, 70, 70)
                        .addComponent(mainQuantityLabel, GroupLayout.PREFERRED_SIZE, 17, GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(leftQuantityLabel, GroupLayout.PREFERRED_SIZE, 17, GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(rightQuantityLabel, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(imageListQuantityLabel, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 28, Short.MAX_VALUE)))
                .addContainerGap())
        );

        setName("mainFrame");
        setTitle("Image Operator");
        setLocationByPlatform(true);
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        
        pack();
    }
    
    private void loadImages() {
        LogIt.getInstance().logWriter("INFO","Loading Images...");
        imagePathsList = new ArrayList<>();
        
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(mainDirectoryPath)) {
            for (Path filePath : stream) {
                // do something with each file
                if (!Files.isDirectory(filePath) && isImageFile(filePath)) {
                    imagePathsList.add(filePath);
                }
            }
        } catch (IOException ex) {
            String emsg = ex.getMessage();
            LogIt.getInstance().logWriter("INFO","File Listing Error IOE. " + emsg);
            OptionPanes.errorPane("IOE Listing Files " + emsg, "Failed To List Images");
        }
        
        updateQuantityText();
        
        if (!imagePathsList.isEmpty()) {
            displayImage();
        } else {
            imagePanel.setImage(endImage());
            imagePanel.repaint();
        }       
    }

    private boolean isImageFile(Path filePath) {
        
        long fileSize = 0;
        
        try {
            fileSize = Files.size(filePath);
        } catch (IOException ex) {
            String emsg = ex.getMessage();
            LogIt.getInstance().logWriter("ERROR", "Cannot Check FileSize of \"" + filePath.toString() + "\" " + emsg);
            OptionPanes.errorPane("File Size Check IOE of \"" + filePath.toString() + "\" " + emsg, "IOE At File Size Ch...");
        }
        
        if (!Files.isRegularFile(filePath) && !(fileSize > 0)) {
            return false;
        }
        
        InputStream inputStream = null;
        FileType fileType = null;
        try {
            inputStream = new BufferedInputStream(new FileInputStream(filePath.toFile()));
        } catch (FileNotFoundException fnfe) {
            String emsg = fnfe.getMessage();
            LogIt.getInstance().logWriter("ERROR", "Exception At Image Detector FNFE. \"" + filePath.toString() + "\" " + emsg);
            OptionPanes.errorPane("FileNotFound At Image Detector. " + emsg, "FileNotFoundException");
        }
        
        try {
            fileType = FileTypeDetector.detectFileType(inputStream); 
        } catch (IOException ioe) {
            String emsg = ioe.getMessage();
            LogIt.getInstance().logWriter("ERROR","Exception At Image Detector InputStream IOE. \"" + filePath.toString() + "\" " + emsg);
            OptionPanes.errorPane("IOE At Image Detector InputStream \"" + filePath.toString() + "\" " + emsg, "InputStream IOException");
        }
        return !(fileType.getMimeType() == null);
    }
    
    private void setMetadataText(Path imagePath) {
        // read the metadata from the image file
        LogIt.getInstance().logWriter("INFO","Reading Metadata.");
        Metadata metadata = null;
        try {
            metadata = ImageMetadataReader.readMetadata(imagePath.toFile());
        } catch (IOException ioe) {
            String emsg = ioe.getMessage();
            LogIt.getInstance().logWriter("ERROR","Metadata Reader IOE. \"" + imagePath.toString() + "\" " + emsg);
            OptionPanes.errorPane("IOException At Metadata Reader" + emsg, "Metadata Readimg Error");
        } catch (ImageProcessingException ipe) {
            String emsg = ipe.getMessage();
            LogIt.getInstance().logWriter("ERROR", "Exception Metadata Reader IProcessingE. \"" + imagePath.toString() + "\" " + emsg);
            OptionPanes.errorPane("Cannot Process Image To Read Metadata. " + emsg, "Metadata Reading Error");
        }

        // loop through all the metadata directories and tags, and print their values
        ArrayList<String> tagArray = new ArrayList<>();
        for (Directory directory : metadata.getDirectories()) {
           for (Tag tag : directory.getTags()) {
               tagArray.add(tag.toString());
           }
        }
        imageMetadataInfoPane.setText(metadataInfoBuilder(tagArray));
    }
    
    public String metadataInfoBuilder(ArrayList<String> strings) {
        StringBuilder stringBuilder = new StringBuilder();
        for (String str : strings) {
            stringBuilder.append(str).append("\n");
        }
        return stringBuilder.toString();
    }
    
    private void displayImage() {
        Path imagePath = imagePathsList.get(currentImageIndex);
        LogIt.getInstance().logWriter("INFO","Displaying \"" + imagePath.toString() + "\" ...");
        setMetadataText(imagePath);
        BufferedImage image = null;
        try {
            image = ImageIO.read(imagePath.toFile());
        } catch (IOException ioe) {
            String emsg = ioe.getMessage();
            LogIt.getInstance().logWriter("ERROR", "IOE Cannot Display \"" + imagePath.toString() + "\" " + emsg);
            OptionPanes.errorPane( "IOE Cannot Display \"" + imagePath.toString() + "\" " + emsg + emsg, "Failed To Display Image");
        }
        imagePanel.setImage(image);
        imagePanel.repaint();
    }
    
    private void nextImage() {
        if (!imagePathsList.isEmpty()) {
            imagePathsList.remove(currentImageIndex);
        }
        
        if (!imagePathsList.isEmpty()) {
            displayImage();
        } else {
            imagePanel.setImage(endImage());
            imagePanel.repaint();
        }
    }
    
    private void skipImage() {
        if (!imagePathsList.isEmpty()){
            LogIt.getInstance().logWriter("INFO","Skipped \"" + imagePathsList.get(currentImageIndex).toString() + "\" Image.");
        } else {
            LogIt.getInstance().logWriter("INFO", "Nothing to Skip.");
        }
        nextImage();
        updateQuantityText();
    }
  
    private void copyLeft() {
        LogIt.getInstance().logWriter("INFO", "Left, Copying \"" + imagePathsList.get(currentImageIndex).toString() + "\" to \"" + AppVars.getInstance().getLeftPathStr() + "\".");
        Path sourceFilePath = imagePathsList.get(currentImageIndex);
        Path destinationFilePath = leftDirectoryPath.resolve(sourceFilePath.getFileName());
        
        if (Files.exists(destinationFilePath)) {
            if (OptionPanes.confimationPane("Left, The destination file \"" + destinationFilePath.toString() + "\" already exists. Do you want to overwrite it?", "File Exists")) {
                try {
                    LogIt.getInstance().logWriter("WARNING", "Left, Overriding \"" + destinationFilePath.toString() + "\"");
                    Files.copy(sourceFilePath, destinationFilePath, StandardCopyOption.REPLACE_EXISTING);
                } catch (IOException ioe) {
                    String emsg = ioe.getMessage();
                    LogIt.getInstance().logWriter("ERROR", "Overriding IOE src:\"" + sourceFilePath.toString() + "\"" + " dst:\"" + destinationFilePath.toString() + "\" " + emsg);
                    OptionPanes.errorPane("Left, Override File Copy IO Error!! " + emsg, "Cannot Copy");
                }
            }
        } else {
            try {
                Files.copy(sourceFilePath, destinationFilePath);
                LogIt.getInstance().logWriter("INFO", "Left, Copied.");
            } catch (IOException ioe) {
                String emsg = ioe.getMessage();
                LogIt.getInstance().logWriter("ERROR"," Copying Error IOE src:\"" + sourceFilePath.toString() + "\"" + " dst:\"" + destinationFilePath.toString() + "\" " + emsg);
                OptionPanes.errorPane("Left, File Copy IO Error " + emsg, "Cannot Copy");
            }
        }
        
        String sourceChecksum = Checksums.getSHA1Checksum(sourceFilePath);
        String destChecksum = Checksums.getSHA1Checksum(destinationFilePath);
        LogIt.getInstance().logWriter("INFO", "sourceChecksum:" + sourceChecksum + " \n destChecksum :" + destChecksum);
            
        if (sourceChecksum.equals(destChecksum)) {
            try {
                LogIt.getInstance().logWriter("INFO","Deleting src: \"" + sourceFilePath.toString() + "\"");
                Files.delete(sourceFilePath);
                LogIt.getInstance().logWriter("INFO","Deleted");
            } catch (IOException e) {
                LogIt.getInstance().logWriter("ERROR", "Left, IOE deleting \"" + sourceFilePath.toString() + "\" " + e.getMessage());
                OptionPanes.errorPane("Left, Cannot Remove Source File" + e.getMessage(), "Cannot Delete");
            }
        } else {
            OptionPanes.errorPane("Left, Source File != Destination File", "Cryptographic Catastrophy");
            LogIt.getInstance().logWriter("Error","Left, Manually check \"" + sourceFilePath.toString() + "\" And \""+ destinationFilePath.toString() + "\"");
        }
      
        nextImage();
        updateQuantityText();
    }
    
    private void copyRight() {
        LogIt.getInstance().logWriter("INFO", "Right, Copying \"" + imagePathsList.get(currentImageIndex).toString() + "\" to \"" + AppVars.getInstance().getLeftPathStr() + "\".");
        Path sourceFilePath = imagePathsList.get(currentImageIndex);
        Path destinationFilePath = rightDirectoryPath.resolve(sourceFilePath.getFileName());
        
        if (Files.exists(destinationFilePath)) {
            if (OptionPanes.confimationPane("Right, The destination file already exists. Do you want to overwrite it?", "File Exists")) {
                try {  
                    LogIt.getInstance().logWriter("WARNING", "Right, Overriding \"" + destinationFilePath.toString() + "\"");       
                    Files.copy(sourceFilePath, destinationFilePath, StandardCopyOption.REPLACE_EXISTING);
                } catch (IOException ioe) {
                    String emsg = ioe.getMessage();
                    LogIt.getInstance().logWriter("ERROR", "Overriding IOE src:\"" + sourceFilePath.toString() + "\"" + " dst:\"" + destinationFilePath.toString() + "\" " + emsg);
                    OptionPanes.errorPane("Right, File Copy IO Error!! " + emsg, "Cannot Copy");
                }
            }
        } else {
            try {
                Files.copy(sourceFilePath, destinationFilePath);
                LogIt.getInstance().logWriter("INFO", "Right, Copied.");
            } catch (IOException ioe) {
                String emsg = ioe.getMessage();
                LogIt.getInstance().logWriter("ERROR","Right Copying Error IOE src:\"" + sourceFilePath.toString() + "\"" + " dst:\"" + destinationFilePath.toString() + "\" " + emsg);
                OptionPanes.errorPane("Right, File Copy IO Error " + emsg, "Cannot Copy");
            }
        }
        
        String sourceChecksum = Checksums.getSHA1Checksum(sourceFilePath);
        String destChecksum = Checksums.getSHA1Checksum(destinationFilePath);
        LogIt.getInstance().logWriter("INFO", "sourceChecksum:" + sourceChecksum + " \n destChecksum :" + destChecksum);
           
        if (sourceChecksum.equals(destChecksum)) {
            try {
                LogIt.getInstance().logWriter("INFO","Right, Deleting src: \"" + sourceFilePath.toString() + "\"");
                Files.delete(sourceFilePath);
                LogIt.getInstance().logWriter("INFO","Deleted");
            } catch (IOException ioe) {
                LogIt.getInstance().logWriter("ERROR", "Right, IOE deleting \"" + sourceFilePath.toString() + "\" " + ioe.getMessage());
                OptionPanes.errorPane("Left, Cannot Remove Source File" + ioe.getMessage(), "Cannot Delete");
            }
        } else {
            OptionPanes.errorPane("Right, Source File != Destination File", "Cryptographic Catastrophy");
            LogIt.getInstance().logWriter("Error","Right, Manually check \"" + sourceFilePath + "\" And \""+ destinationFilePath + "\"");
        }
        
        nextImage();
        updateQuantityText();
    }
    
    private void countFiles() {
        try (Stream<Path> countStream = Files.list(Paths.get(AppVars.getInstance().getMainPathStr()))) {
            mainQuantity = countStream.count();
        } catch (IOException ioe) {
            OptionPanes.errorPane("1 / 0, Main" + ioe.getMessage(), "File Counting Error");
        }
        
        try (Stream<Path> countStream = Files.list(Paths.get(AppVars.getInstance().getLeftPathStr()))) {
            leftQuantity = countStream.count();
        } catch (IOException ioe) {
            OptionPanes.errorPane("1 / 0, Left " + ioe.getMessage(), "File Counting Error");
        }
        
        try (Stream<Path> countStream = Files.list(Paths.get(AppVars.getInstance().getRightPathStr()))) {
            rightQuantity = countStream.count();
        } catch (IOException ioe) {
            OptionPanes.errorPane("1 / 0, Right  " + ioe.getMessage(), "File Counting Error");
        }
        
        imageListSize = imagePathsList.size();
    }

    private void updateQuantityText() {
        countFiles();
        mainQuantityLabel.setText("Files In Main Directory: " + String.valueOf(mainQuantity));
        leftQuantityLabel.setText("Files In Left Directory: " + String.valueOf(leftQuantity));
        rightQuantityLabel.setText("Files in Right Directory: " + String.valueOf(rightQuantity));
        imageListQuantityLabel.setText("Images In Main Directory: " + String.valueOf(imageListSize));
        
        LogIt.getInstance().logWriter("INFO", "main: " + mainQuantity + " left: " + leftQuantity + " right: " + rightQuantity + " image: " + imageListSize);
    }
    
    private BufferedImage endImage() {
        BufferedImage bi = null;
        try {
            bi = ImageIO.read(getClass().getResourceAsStream("/images/background.png")); 
        } catch (IOException ioe){
            String emsg = ioe.getMessage();
            LogIt.getInstance().logWriter("INFO", "EndImageIOE " + emsg);
            OptionPanes.errorPane("The end image loading IOE. " + emsg, "There is no end");
        }
        return bi;
    }
}

