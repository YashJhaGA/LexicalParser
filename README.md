# LexicalParser
A custom made programming language called YashLang with a lexical analyzer and a parser.

In this language, you are able to create variables, perform mathmatical operations(BODMAS), use selection statements, and utilize loops.


# Similarities to Java

Here is some Java Comparisons to get you started on learning this language.

Java -> YashLang

byte -> int_1

short -> int_2
     
int  -> int_4

long -> int_8

for  -> flower

while -> water

if -> cond

else -> or

class { } -> START END

# Grammar of the Language

```
<Program> -> START <stmt_list> END

<stmt_list> -> <stmt>

<stmt> -> <declaration> | <cond_stmt> | <flower> | <water> | <initialization> 

<declaration> - <type> ‘id'
<type>  - 1nt_1 | int_2 | int_4 | int_8 

<cond_stmt> -> cond <bool_expr> <stmt> donc [ or <stmt> ro ]
<bool_expr> -> <value> <operator> <value>
<value> -> num | id
<operator> < | > | <= | >= | == | !=

<flower> -> flower <flower_expr> <stmt> rewolf
<flower_expr> -> num num num

<water> -> water <bool_expr> <stmt> retaw

<initialization> - ‘id’ = <expr>
<expr> - <term> + <term> |  <term> - <term>
<term> - <factor> * <factor> | <factor> / <factor>
<factor> - [<expr>] | num | id
```


# Syntax

To use a flower loop, it is 'flower startingValue finalValue increment/decrement'. So a valid flowerLoop is 'flower 5 10 1'.
You also need to terminate your flower loop with a rewolf.
So a valid code is 

```
START
int_4 purple
purple = 1
flower 5 10 1
purple += 10 + 5
rewolf
END
```

For a water loop, it is similar to a while loop. You need to use this syntax -> 'water variable opeartor number'
You also need to terminate your water loop with a 'retaw'.
A valid code is

```
START
int_2 yellow
yellow = 5
water yellow < 8
yellow --
retaw
END
```


For a conditional, it is similar to a if-else, except here we have cond-or. To use it, it is 'cond variable operator number'.
You need to terminate your cond with a 'donc'. After 'donc' you also have the option to start your 'else' with a 'or' statement. But just like before, 
you need to conclude that 'or' statement with a 'ro'.
Here is a valid code:

```
START
int_1 orange
orange = 15
cond orange > 20
orange --
donc
or
orange ++
ro
END
```
