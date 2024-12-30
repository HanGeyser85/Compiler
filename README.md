# Grammar Description

## 1. Program Structure

### Rule:
```
Program -> Statements
Statements -> Statement ; Statements
Statements -> ε
```

### Progression Example:
Input: `p;`
```
Program -> Statements
Statements -> Statement ; Statements
Statement -> Procedure
Statements -> ε
```

---

## 2. Statements

### Rule:
```
Statement -> Assignment
Statement -> Conditional
Statement -> Loop
Statement -> Procedure
Statement -> Input
Statement -> Output
Statement -> Halt
```

### Progression Examples:

#### Procedure:
Input: `p`
```
Statement -> Procedure
Procedure -> p
```

#### Halt:
Input: `h`
```
Statement -> Halt
Halt -> h
```

#### Input:
Input: `g n`
```
Statement -> Input
Input -> get Variable
Variable -> n
```

#### Output:
Input: `o n`
```
Statement -> Output
Output -> output Expression
Expression -> Variable
Variable -> n
```

---

## 3. Assignments

### Rule:
```
Assignment -> Variable := Expression
```

### Progression Example:
Input: `n := 5`
```
Statement -> Assignment
Assignment -> Variable := Expression
Variable -> n
Expression -> Number
Number -> 5
```

---

## 4. Expressions

### Rule:
```
Expression -> Term + Expression
Expression -> Term - Expression
Expression -> Term
Term -> Factor * Term
Term -> Factor / Term
Term -> Factor
Factor -> Number
Factor -> Variable
Factor -> ( Expression )
Number -> Digit
Number -> Digit . Digit
Digit -> 0 | 1 | 2 | 3 | 4 | 5 | 6 | 7 | 8 | 9
```

### Progression Example:
Input: `5 + 3 * 2`
```
Expression -> Term + Expression
Term -> Factor
Factor -> Number
Number -> 5
Expression -> Term
Term -> Factor * Term
Factor -> Number
Number -> 3
Term -> Factor
Factor -> Number
Number -> 2
```

---

## 5. Conditionals

### Rule:
```
Conditional -> if Expression then Statements else Statements
```

### Progression Example:
Input: `if n < 5 then p; else h;`
```
Statement -> Conditional
Conditional -> if Expression then Statements else Statements
Expression -> Variable < Number
Variable -> n
Number -> 5
Statements -> Statement ; Statements
Statement -> Procedure
Procedure -> p
Statements -> ε
Statements -> Statement ; Statements
Statement -> Halt
Halt -> h
Statements -> ε
```

---

## 6. Loops

### Rule:
```
Loop -> while Expression Statements
```

### Progression Example:
Input: `while n < 10 p;`
```
Statement -> Loop
Loop -> while Expression Statements
Expression -> Variable < Number
Variable -> n
Number -> 10
Statements -> Statement ; Statements
Statement -> Procedure
Procedure -> p
Statements -> ε
```

---

## 7. Logical Expressions

### Rule:
```
LogicalExpression -> true
LogicalExpression -> false
LogicalExpression -> LogicalTerm or LogicalTerm
LogicalTerm -> LogicalFactor and LogicalFactor
LogicalFactor -> not LogicalFactor
LogicalFactor -> ( LogicalExpression )
```

### Progression Example:
Input: `true and false or not true`
```
LogicalExpression -> LogicalTerm or LogicalTerm
LogicalTerm -> LogicalFactor and LogicalFactor
LogicalFactor -> true
LogicalFactor -> false
LogicalTerm -> LogicalFactor
LogicalFactor -> not LogicalFactor
LogicalFactor -> true
```

---

## 8. Comparisons

### Rule:
```
Comparison -> Expression equals Expression
Comparison -> Expression < Expression
Comparison -> Expression > Expression
```

### Progression Example:
Input: `n equals 5`
```
Comparison -> Expression equals Expression
Expression -> Variable
Variable -> n
Expression -> Number
Number -> 5
```

---

## 9. Comments and Strings

### Rule:
```
String -> " Character* "
Comment -> * Character* *
```

### Progression Example for Strings:
Input: `"Hello World"`
```
String -> " Character* "
Character -> H Character
Character -> e Character
Character -> l Character
Character -> l Character
Character -> o Character
Character ->   Character
Character -> W Character
Character -> o Character
Character -> r Character
Character -> l Character
Character -> d Character
Character -> ε
```
