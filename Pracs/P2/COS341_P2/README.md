# Johannes Gerhardus Geyser
# u20450932

COS 341 Practical 2

## Input and Output

The JAR is prebuilt and works.
- Input your SPL in the `INPUT.txt` file.
- In the `OUTPUT_PARSE_TREE.xml` file will be rudimentary parsing with a structured parse tree in XML format, **This is a fall back if the AST does not output**
- If all is good and well, the `OUTPUT_AST.xml` will contain the fully complete and in detail AST without any useless tokens _pruned_.

## Structure

- In `.vscode` is launch settings and is only to be used if the jar fails. **BACK UP PLAN**
- In `bin` is all the class files.
- In `lib` is nothing.
- In `src` is all the source code.

## How to Use

1. Input your SPL in the `INPUT.txt` file.
2. Open a terminal in the project `COS341_P2` directory and run the `COS341_P2.jar` file using `java -jar COS341_P2.jar`.
    1. If the jar fails, open the Java Project in VSCode and run with debugger.
    2. Open the debugger tab in VS Code.
    3. At the top dropdown select COS341_P2.
    4. Click the green arrow to run.
3. Check the `OUTPUT_AST.xml` file for output.
    1. If `OUTPUT_AST.xml` is empty, check in `OUTPUT_PARSE_TREE.xml`.
