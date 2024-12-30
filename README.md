# SPL Grammar in CFG

Below is a **Context-Free Grammar (CFG)** for your SPL language, reflecting how your **lexer** (single-character tokens) and **parser** (recursive-descent) currently behave. The terminals match the single-letter tokens from the lexer (e.g., `'p'` for `PROC`, `'n'` for `NUMVAR`, etc.). Multi-character tokens like `":="`, string literals, and comments are also represented as single terminals (since the lexer generates them as single tokens).

---

## 1. Start Rule

```
<S> → <PROGR>
```

---

## 2. Program & Procedures

```
<PROGR> → <ALGO> <PROCDEFS>
```

```
<PROCDEFS> → ',' <PROC> <PROCDEFS>
           | ε
```
- `','` corresponds to `_TokenType.SEPARATOR`.

```
<PROC> → 'p' <DIGITS> '{' <PROGR> '}'
```
- `'p'` = `_TokenType.PROC`.
- `'{'` = `_TokenType.OPENBRACKET`, `'}'` = `_TokenType.CLOSEBRACKET`.

---

## 3. ALGO, COMMENT, SEQ

```
<ALGO> → <INSTR> <COMMENT> <SEQ>
       | ε
```

```
<COMMENT> → COMMENT_TOKEN
          | ε
```
- `COMMENT_TOKEN` is a single token (e.g., `*...*` in your lexer).

```
<SEQ> → ';' <ALGO>
      | ε
```
- `';'` = `_TokenType.DIVIDER`.

---

## 4. Instructions

```
<INSTR> → <INPUT>
        | <OUTPUT>
        | <ASSIGN>
        | <CALL>
        | <LOOP>
        | <BRANCH>
        | <HALT>
```

### 4.1 `<INPUT>`

```
<INPUT> → 'g' <NUMVAR>
```
- `'g'` = `_TokenType.INPUT_GET`.

### 4.2 `<OUTPUT>`

```
<OUTPUT> → 'o' <NUMVAR>
         | 'r' <STRINGV>
```
- `'o'` = `_TokenType.OUTPUT`.
- `'r'` = `_TokenType.TEXT_RESPONSE`.

### 4.3 `<ASSIGN>`

```
<ASSIGN> → <NUMVAR> '":="' <NUMEXPR>
         | <BOOLVAR> '":="' <BOOLEXPR>
         | <STRINGV> '":="' <STRI>
```
- `":="` = `_TokenType.ASSIGN`.

### 4.4 `<CALL>`

```
<CALL> → 'c' 'p' <DIGITS>
```
- `'c'` = `_TokenType.CALL`.
- `'p'` = `_TokenType.PROC`.

### 4.5 `<LOOP>` (While)

```
<LOOP> → 'w' '(' <BOOLEXPR> ')' '{' <ALGO> '}'
```
- `'w'` = `_TokenType.WHILE`.
- `'('` = `_TokenType.OPENPARENTHESIS`, `')'` = `_TokenType.CLOSEPARENTHESIS`.

### 4.6 `<BRANCH>` (If / Else)

```
<BRANCH> → 'i' '(' <BOOLEXPR> ')' 't' '{' <ALGO> '}' <ELSE_PART>
```
- `'i'` = `_TokenType.IF`.
- `'t'` = `_TokenType.THEN`.

```
<ELSE_PART> → 'e' '{' <ALGO> '}'
            | ε
```
- `'e'` = `_TokenType.ELSE`.

### 4.7 `<HALT>`

```
<HALT> → 'h'
```
- `'h'` = `_TokenType.HALT`.

---

## 5. Boolean Expressions

```
<BOOLEXPR> → <BOOLVAR>
           | 'T'
           | 'F'
           | '!' '(' <BOOLEXPR> ')'
           | '^' '(' <BOOLEXPR> ',' <BOOLEXPR> ')'
           | 'v' '(' <BOOLEXPR> ',' <BOOLEXPR> ')'
           | 'E' '(' <NUMEXPR> ',' <NUMEXPR> ')'
           | '>' '(' <NUMEXPR> ',' <NUMEXPR> ')'
           | '<' '(' <NUMEXPR> ',' <NUMEXPR> ')'
```
- `'T'` = `_TokenType.LOGIC_TRUE`  
- `'F'` = `_TokenType.LOGIC_FALSE`  
- `'!'` = `_TokenType.LOGIC_NOT`  
- `'^'` = `_TokenType.LOGIC_AND`  
- `'v'` = `_TokenType.LOGIC_OR`  
- `'E'` = `_TokenType.CMPR_EQUAL`  
- `'>'` = `_TokenType.CMPR_GREATERTHAN`  
- `'<'` = `_TokenType.CMPR_LESSTHAN`

### 5.1 `<BOOLVAR>`

```
<BOOLVAR> → 'b' <DIGITS>
```
- `'b'` = `_TokenType.BOOLVAR`.

---

## 6. Numeric Expressions

```
<NUMEXPR> → 'a' '(' <NUMEXPR> ',' <NUMEXPR> ')'
          | 'd' '(' <NUMEXPR> ',' <NUMEXPR> ')'
          | 'm' '(' <NUMEXPR> ',' <NUMEXPR> ')'
          | <NUMVAR>
          | <DECNUM>
```
- `'a'` = `_TokenType.NUMEXPR_ADDITION`  
- `'d'` = `_TokenType.NUMEXPR_DIVISION`  
- `'m'` = `_TokenType.NUMEXPR_MULTIPLICATION`

### 6.1 `<NUMVAR>`

```
<NUMVAR> → 'n' <DIGITS>
```
- `'n'` = `_TokenType.NUMVAR`.

### 6.2 `<DECNUM>`

```
<DECNUM> → '-' <POS>
         | <POS>
         | "0.00"
```
- `'-'` = `_TokenType.NEG_MINUS`.
- `"0.00"` is a special case your parser checks explicitly.

#### `<POS>` (Positive decimal)

```
<POS> → <INT> '.' <D> <D>
```
- `'.'` = `_TokenType.POS_FLOATPOINT`.

#### `<INT>` (Integer part)

```
<INT> → <D> <MORE>
```

#### `<MORE>`

```
<MORE> → <D> <MORE>
       | ε
```

#### `<D>` (Digit)

```
<D> → '0'
    | '1'
    | '2'
    | '3'
    | '4'
    | '5'
    | '6'
    | '7'
    | '8'
    | '9'
```
- In the lexer, each digit is a separate `_TokenType.D`.

---

## 7. String Variables & Literals

```
<STRINGV> → 's' <DIGITS>
```
- `'s'` = `_TokenType.STRINGV`.

```
<STRI> → STRI
```
- `STRI` is a single token produced by the lexer for `"..."` (16 chars in your code).

---

## 8. Digits (for Variable Names)

```
<DIGITS> → <D> <DIGITS>
         | <D>
```

---

## 9. Final Notes

- The grammar matches the **single-character tokens** your lexer produces (e.g., `'p'` for `PROC`, `'n'` for `NUMVAR`, etc.).  
- Multi-character tokens (`":="`, `STRI`, `COMMENT_TOKEN`) appear as single terminals in the grammar.  
- In a more typical language, `proc` would be a multi-letter keyword, numeric literals would be scanned as single tokens (e.g., `123.45`), and string literals would be variable-length. However, this grammar accurately reflects your current lexer + parser approach.
