# Grammar Progressions for the Lexer and Parser

## 1. Program Structure

### Rule:
```
Program -> Procedure
Program -> Algorithm
```

---

## 2. Procedures

### Rule:
```
Procedure -> proc Digits { Algorithm ProcedureDefinitions }
```

---

## 3. Algorithm

### Rule:
```
Algorithm -> Instruction
Algorithm -> Algorithm ; Instruction
Algorithm -> ε
```

### Associativity:
```
Algorithm is left-associative:
Algorithm -> Algorithm ; Instruction
```

---

## 4. Procedure Definitions

### Rule:
```
ProcedureDefinitions -> , Procedure ProcedureDefinitions
ProcedureDefinitions -> ε
```

---

## 5. Instructions

### Rule:
```
Instruction -> Input
Instruction -> Output
Instruction -> Assignment
Instruction -> Loop
Instruction -> Branch
Instruction -> Halt
Instruction -> Call
Instruction -> Comment
```

---

## 6. Input

### Rule:
```
Input -> get Variable
```

---

## 7. Output

### Rule:
```
Output -> output Expression
Output -> response String
```

---

## 8. Assignment

### Rule:
```
Assignment -> Variable := Expression
```

---

## 9. Loops

### Rule:
```
Loop -> while ( BooleanExpression ) { Algorithm }
```

---

## 10. Branches

### Rule:
```
Branch -> if ( BooleanExpression ) then { Algorithm } else { Algorithm }
```

---

## 11. Halt

### Rule:
```
Halt -> halt
```

---

## 12. Call

### Rule:
```
Call -> call Procedure
```

---

## 13. Comments

### Rule:
```
Comment -> * Characters *
```

---

## 14. Expressions

### Rule:
```
Expression -> Expression + Term
Expression -> Expression - Term
Expression -> Term
Term -> Term * Factor
Term -> Term / Factor
Term -> Factor
Factor -> Variable
Factor -> Number
Factor -> ( Expression )
```

### Associativity and Precedence:
```
Addition/Subtraction are left-associative and have lower precedence than Multiplication/Division.
Parentheses override precedence.
```

---

## 15. Boolean Expressions

### Rule:
```
BooleanExpression -> true
BooleanExpression -> false
BooleanExpression -> not BooleanExpression
BooleanExpression -> BooleanExpression and BooleanExpression
BooleanExpression -> BooleanExpression or BooleanExpression
BooleanExpression -> Expression == Expression
BooleanExpression -> Expression > Expression
BooleanExpression -> Expression < Expression
```

### Associativity and Precedence:
```
not has the highest precedence.
and has higher precedence than or.
Expressions involving ==, >, < are evaluated last.
```

---

## 16. Variables

### Rule:
```
Variable -> numVar
Variable -> boolVar
Variable -> stringVar
```

---

## 17. Numbers

### Rule:
```
Number -> Digit
Number -> Digit . Digit
Digit -> 0 | 1 | 2 | 3 | 4 | 5 | 6 | 7 | 8 | 9
```

---

## 18. Digits

### Rule:
```
Digits -> Digit Digits
Digits -> Digit
```

---

## 19. Separators and Dividers

### Rule:
```
Separator -> ,
Divider -> ;
```

---

## 20. Strings

### Rule:
```
String -> " Characters "
Characters -> Character Characters
Characters -> ε
Character -> any printable character
```

---

## 21. Comments

### Rule:
```
Comment -> * Characters *
```

---

## 22. Brackets and Parentheses

### Rule:
```
Brackets -> { Algorithm }
Parentheses -> ( BooleanExpression )
```

---

This updated grammar includes explicit definitions for associativity and precedence to resolve ambiguities in `Expressions`, `Algorithm`, and `BooleanExpressions`. Let me know if more details are needed!
