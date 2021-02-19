# Comics-RDBMS-and-JAVA-Project

Steps for compiling and running the code:
============================================

Step 1: Download JDK
--------------------------
1. Goto Java SE download site -
http://www.oracle.com/technetwork/java/javase/downloads/index.html.
2. Under "Java Platform, Standard Edition" "Java SE 13.0. ⇒ {x}", where{x} denotes a fast
running security-update number Click the "Oracle JDK" "Download" button. ⇒
3. Under "Java SE Development Kit 13.0.{x}" Check "Accept License Agreement". ⇒
4. Choose the JDK for your operating system, i.e., "Windows". Download the "exe" installer (e.g.,
"jdk-13.0.{x}_windows-x64_bin.exe" - about 150MB).

Step 2: Install JDK
--------------------------------
1. Run the downloaded installer (e.g., "jdk-13.0.{x}_windows-x64_bin.exe"), which installs both
the JDK and JRE.
2. By default, JDK is installed in directory "C:\Program Files\Java\jdk-13.0.{x}", where {x}
denotes the update number. Accept the defaults and follow the screen instructions to install
JDK.
3. Use your "File Explorer", navigate to "C:\Program Files\Java" to inspect the sub-directories.
Take note of your JDK installed directory, in particular, the update number{x}, which you will
need in the next step
Step 3: Include JDK's "bin" Directory in the PATH

To edit the PATH environment variable in Windows 10:
-----------------------------------------------------
Launch "Control Panel" (Optional) "System and Security" "System" Click "Advanced system ⇒ ⇒ ⇒
settings" on the left pane.
1. Switch to "Advanced" tab Click "Environment Variables" button. ⇒
2. Under "System Variables" (the bottom pane), scroll down to select variable "Path" Click ⇒
"Edit...".
3. For Newer Windows 10:
You shall see a TABLE listing all the existing PATH entries (if not, goto next step). Click
"New" Click "Browse" and navigate to your JDK's "bin" directory, i.e., "c:\Program Files\ ⇒
Java\jdk-13.0.{x}\bin", where {x} is your installation update number Select "Move Up" to ⇒
move this entry all the way to the TOP.

Step 4: Verify the JDK Installation
----------------------------------------
1. Launch a CMD via one of the following means:
➢ Click "Search" button Enter "cmd" Choose "Command Prompt", or
➢ Right-click "Start" button run... enter "cmd", or
➢ Click "Start" button Windows System Command Prompt
2. Issue the following commands to verify your JDK installation:
➢ Issue "path" command to list the contents of the PATH environment variable. Check to make
sure that your JDK's "bin" is listed in the PATH.
➢ Issue javac –version which displays JDK version as “javac 11.0.1”
➢ Issue java -version which displays JRE version.

Step 5: Download sqlite and install it on windows:
----------------------------------------------------------
1. Open the download page https://www.sqlite.org/download.html and download command-line
shell program from precompiled binaries for windows.
2. Create a new folder sqlite in C drive
3. Extract the content of the file that you downloaded in the previous section to the c: \ sqlite folder
4. In command prompt, navigate to sqlite folder and type ‘sqlite3’ and verify the version

Step 6: Compile and Run the SaiMadhuriYerramsetti_Lab5_Program Java Program
------------------------------------------------------------------------------
1. Copy ComicRecommendation.db file to c: \sqlite path
2. Create new folder ‘java’ in the above mentioned path
3. Download latest version of SQLite JDBC driver(sqlite-jdbc-3.27.2.1.jar) from
https://bitbucket.org/xerial/sqlite-jdbc/downloads/ for windows and copy to c:\sqlite\java
4. Copy SaiMadhuriYerramsetti_Lab5_Program.java file in c:\sqlite\java
5. Open command prompt and type: cd c:\sqlite\java
6. Compile the java file using: javac SaiMadhuriYerramsetti_Lab5_Program.java
7. Type ‘dir’ command and check that SaiMadhuriYerramsetti_Lab5_Program.class
is created in c:\sqlite\java path
8. Type the following command to run the program:
 java -classpath ".;sqlite-jdbc-3.27.2.1.jar" SaiMadhuriYerramsetti_Lab5_Program
 
Step 7: Execute the sql queries required:
-------------------------------------------
Program will prompt with 11 options, out of which any option can be selected and executed.

Known Issues:
------------------
1. The query number in the ‘Main menu’of the program should always be given as a number, if any
other format such as strings are given, it will throw a ‘java.util.InputMismatchException’
2. The input values are case sensitive, so it is required to make sure that valid input is given as
prompted by the program to run a query. In case of invalid input, program will prompt to enter valid
input again.
