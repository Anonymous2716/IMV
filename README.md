![GitHub repo size](https://img.shields.io/github/repo-size/Anonymous2716/IMV?color=0bda51) 
![GitHub Release](https://img.shields.io/github/v/release/Anonymous2716/IMV?display_name=tag) 
![Github License](https://img.shields.io/github/license/Anonymous2716/IMV?color=0bda51)



This Java application, compatible with Java 8 and above, is designed to copy images from one directory to two separate directories.

## Downloads
get it from the [release page].

# How To Run
```
 java -jar IMV-x.x-x.jar 
```

## How To Build
Linux:

1. Clone the repository to your local machine:
```
git clone https://github.com/Anonymous2716/IMV
```
2. Navigate to the project directory:
```
cd IMV
```
3. Maven:
```
mvn clean package
```

Gradle:
```
./gradlew jar --no-daemon
```
in which case the final jar file would be in `build/libs` directory.

Or use your own commands if you know what you are doing.



# Requirements
To Run:

1. JDK(JRE) 8 or later.

To Build: 

2. maven or gradle.
3. Active Internet Connection for downloading dependencies and maven plugins.




# Screanshots
Screen Size: 1536x864 (80% WxH of 1920x1080).


Start Window:
![Start Window](https://github.com/Anonymous2716/IMV/raw/main/Screenshots/start.png)

Second Window:
![Second Window](https://github.com/Anonymous2716/IMV/raw/main/Screenshots/main-s.png)

## TODO
Further improvements will be done in the future if I have time and mind. such as:

1. Displaying destination directories.
2. Better scaling.
3. default zooming scale.
4. Theme management. 
5. Realtime text input validation. etc etc.




# How Does It Work? 
This Application goes through all the files in a given directory. And loops through the files one by one to check if its an image file. 
The `isImageFile(Path filePath)`check if a file is an Image by passing it to *Metadata-Extractor* library. and if the file is not an Image then the library will return `null`. Which is not perfect but fast enough to load thousands of images in a second or two.
After loading the images in an `ArrayList<Path>` the application displays each image one after another and with metadata info . the given buttons to perform Actions on them. 

The Copy Options uses SHA-1 checksum verification after copying to `destinationFile` and before `sourcefile` deletion. and if they do not match the source file will be kept and an Error message will be shown. Also most of the information should be printed in stdout also a logfile in the given log directory.

This application is designed to capture any potential exceptions that occur during runtime. In the event of an exception, it will display an error message using a `JOptionPane`.


## INFO
This is a personal public project which means it is free and open-source, even if I may accept feature requests I don't guarantee implementing them.

I may rewrite Git history from time to time because it stores few binary files which take noticeable disk space. Coding quality is very far from perfect. I know that but don't care, the app works and this is good.

The IMV application is distributed under GPL v3.0 license.

I don't care whether you use or not. As I said, this is a personal project and it does what I need. Think about that before sending me a blaming email regarding support. If you have lost your data, then only you are responsible for mistakes and misuses. Remember that [author] does not owe anything for you.

## Credits
 - [Termux] - Because I wrote this Application in Termux.


[Termux]: https://github.com/termux
[author]: https://github.com/Anonymous2716
[release page]: https://github.com/Anonymous2716/IMV/releases/latest
