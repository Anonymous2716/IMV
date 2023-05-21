This Java application, compatible with Java 8 and above, is designed to copy images from one directory to two separate directories.

get it from the release page.


# How To Build
1. Clone the repository to your local machine:
```
git clone https://github.com/Anonymous2716/IMV
```
2. Navigate to the project directory:
```
cd IMV
```
3. Build using this command (or use your own if you know what you are doing):
```
mvn clean package
```

# How To Run
```
 java -jar IMV-x.x-x.jar 
```

# Requirements
To Run:
1. JDK(JRE) 8 or later.

To Build: 

2. maven.
3. Active Internet Connection for downloading dependencies and maven 
plugins.

# TODO
further improvements will be done in the future if I have time and mind. such as:

1. Displaying destination directories.
2. Better scaling.
3. default zooming scale.
4. Theme management. etc etc.


# Screanshots
Start Window:
![Start Window](https://github.com/Anonymous2716/IMV/raw/main/Screenshots/start.png)

Second Window:
![Second Window](https://github.com/Anonymous2716/IMV/raw/main/Screenshots/main-s.png)

Second Window Fullscreen:
![Second Window Fullscreen](https://github.com/Anonymous2716/IMV/raw/main/Screenshots/main-b.png)


# How Does It Work? 
This Application goes through all the files in a given directory. And loops through the files one by one to check if its an image file. 
The `isImageFile(Path filePath)`check if a file is an Image by passing it to *Metadata-Extractor* library. and if the file is not an Image then the library will return `null`. Which is not perfect but fast enough to load thousands of images in a second or two.
After loading the images in an `ArrayList<Path>` the application displays each image one after another and with metadata info . the given buttons to perform Actions on them. 

The Copy Options uses SHA-1 checksum verification after copying to `destinationFile` and before `sourcefile` deletion. and if they do not match the source file will be kept and an Error message will be shown. Also most of the information should be printed in stdout also a logfile in the given log directory.

This application is designed to capture any potential exceptions that occur during runtime. In the event of an exception, it will display an error message using a `JOptionPane`.

I wrote this Application for personal Use. Its not perfect but usable.
