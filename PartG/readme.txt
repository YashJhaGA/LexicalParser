This is my full program- Lexer + Parser

Here we have 4 Test files all with 30+ Lexemes(if we include spaces as a lexeme).

YashLexTest.txt is the file with 5+ lexical errors.
YashSyntaxTest.txt is the file with 5+ syntax errors.
YashPassTest1.txt is the 1st successful file.
YashPassTest2.txt is the 2nd successful file.

To run these programs, it requires passing of arguments through Command Line. In your file explorer, click on the top at the file explorer where it says "LexerAndParser" in the path. Clicking on it should
show the full path to folder. Erase it all and type 'cmd' and hit enter.

Command prompt will open for the folder path.

Here is the commands to run all 4 test files(Run each separately):
java YashLexical.java YashLexTest.txt
java YashLexical.java YashSyntaxTest.txt
java YashLexical.java YashPassTest1.txt
java YashLexical.java YashPassTest2.txt

If you get some errors, try recompiling the program with the line: "javac YashLexical.java", but that shouldn't be the case since I provided the .class files.