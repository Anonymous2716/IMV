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

# How Does It Work? 
This Application goes through all the files in a given directory. And loops through the files one by one to check if its an image file. 
The `isImageFile(Path filePath)`check if a file is an Image by passing it to *Metadata-Extractor* library. and if the file is not an Image then the library will return `null`. Which is not perfect but fast enough to load thousands of images in a second or two.
After loading the images in an `ArrayList<Path>` the application displays each image one after another and with metadata info . the given buttons to perform Actions on them. 

The Copy Options uses SHA-1 checksum verification after copying to `destinationFile` and before `sourcefile` deletion. and if they do not match the source file will be kept and an Error message will be shown. Also most of the information should be printed in stdout also a logfile in the given log directory.

This Application catches all the possible Exceptions at runtime and will show a `JOptionPane` with error message. 

I wrote this Application for personal Use. Its not perfect but usable.
