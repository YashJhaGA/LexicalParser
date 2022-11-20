This is a standalone lexical analyzer for my language.
Here I have YashLexer.java which is the lexical analyzer and 3 test files.

YashTest1.txt is a correct file and will return an expected list of [0, 33, 48, 48, 21, 34, 44, 34, 34, 34, 48, 21, 48, 7, 34, 45, 99].
YashTest2.txt is a incorrect file and will throw a not valid token error.
YashTest3.txt is a incorrect file and will throw a not declared error.

This program requires passing of arguments through Command Line. In your file explorer, click on the top at the file explorer where it says "Lexer" in the path. Clicking on it should
show the full path to folder. Erase it all and type 'cmd' and hit enter.

Command prompt will open for the folder path.

After that here are the commands for all 3 test files(Run each separtely).

java YashLexer.java YashTest1.txt
java YashLexer.java YashTest2.txt
java YashLexer.java YashTest3.txt