## Input and Output

The JAR is prebuilt and works.
- Input your SPL in the `INPUT.txt` file.
- In the `OUTPUT_PARSE_TREE.xml` file will be rudimentary parsing with a structured parse tree in XML format, **This is a fall back if the AST does not output**
- If all is good and well, the `OUTPUT_AST.xml` will contain the fully complete and in detail AST without any useless tokens _pruned_.
- For vTable building, all error and warning messages relating to it will be written to `OUTPUT_SYMBOL_TABLE.html` as well as the complete symbol table in the format of:
    Global vTable. _WITH A HAS VALUE_ to show all the 
    All procedure scopes in the order of hierarchy ie. MAIN first, then the first proc in MAIN etc.
    WARNING messages.
    ERROR messages.
    SEMANTIC ERROR messages.
    **ALL ABLE TO VIEW IN BROWSER**
- For Intermediate Language Generation, the BASIC will be in the `OUTPUT.txt`, this is to be copied and pasted in **http://www.quitebasic.com/**. `SCOPE TABLES ARE NOT NEEDED AND ARE DISABLED!!!!!`
    Line number follow on each other except in the cases of loops, gotos and branches as you will se in the output.
    Loop GOTO and accompanying commands have the range of 2999 to 4000. _The inner lines follow the normal numbering scheme._
    Branch GOTO and accompanying commands have the range of 6999 to 9000. _The inner lines follow the normal numbering scheme._
    Proc and GOSUB and accompanying commands have a range of 5000 to 6998, _The inner lines follow the normal numbering scheme._

## Structure

- In `.vscode` is launch settings and is only to be used if the jar fails. **BACK UP PLAN**
- In `bin` is all the class files.
- In `lib` is nothing.
- In `src` is all the source code.

## How to Use

1. Input your SPL in the `INPUT.txt` file.
2. Open a terminal in the project `COMPDI_V1` directory and run the `COMPDI_V1.jar` file using `java -jar COMPDI_V1.jar`.
    1. If the jar fails, open the Java Project in VSCode and run with debugger.
    2. Open the debugger tab in VS Code.
    3. At the top dropdown select COS341_P2.
    4. Click the green arrow to run.
3. Check the `OUTPUT.txt` file for output.
    1. Errors will be displayed in the `.xml` files.

