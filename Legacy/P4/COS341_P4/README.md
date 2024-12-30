# Johannes Gerhardus Geyser
# u20450932

COS 341 Practical 4

## Input and Output

The JAR is prebuilt and works.
- Input your SPL in the `INPUT.txt` file.
- In the `OUTPUT_PARSE_TREE.xml` file will be rudimentary parsing with a structured parse tree in XML format, **This is a fall back if the AST does not output**
- If all is good and well, the `OUTPUT_AST.xml` will contain the fully complete and in detail AST without any useless tokens _pruned_.
- For practical 4, all error and warning messages relating to it will be written to `OUTPUT_SYMBOL_TABLE.html` as well as the complete symbol table in the format of:
    Global vTable. _WITH A HAS VALUE_ to show all the 
    All procedure scopes in the order of hierarchy ie. MAIN first, then the first proc in MAIN etc.
    WARNING messages.
    ERROR messages.
    SEMANTIC ERROR messages.
    **ALL ABLE TO VIEW IN BROWSER**

## Structure

- In `.vscode` is launch settings and is only to be used if the jar fails. **BACK UP PLAN**
- In `bin` is all the class files.
- In `lib` is nothing.
- In `src` is all the source code.

## How to Use

1. Input your SPL in the `INPUT.txt` file.
2. Open a terminal in the project `COS341_P4` directory and run the `COS341_P4.jar` file using `java -jar COS341_P4.jar`.
    1. If the jar fails, open the Java Project in VSCode and run with debugger.
    2. Open the debugger tab in VS Code.
    3. At the top dropdown select COS341_P2.
    4. Click the green arrow to run.
3. Check the `OUTPUT_SYMBOL_TABLE.html` file for output.
    1. If `OUTPUT_SYMBOL_TABLE.html` contains null errors it will be in the console, else all errors will be in the above mentioned format and as per specification.
4. Open the `OUTPUT_SYMBOL_TABLE.html` file in the browser for formatting.
