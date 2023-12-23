# S-Comp: A Simple Compiler in Scala

## Overview

S-Comp is a minimalistic compiler designed to demonstrate the fundamentals of compiler construction. It is implemented
in Scala, leveraging its functional programming capabilities and strong static type system. This project aims to
provide an educational tool for understanding how compilers work, from parsing source code to executing basic 
operations.

## Features

- **Expression Parsing**: Converts string representations of expressions into abstract syntax trees (ASTs).
- **Environment Handling**: Manages variable bindings within expressions.
- **Support for Basic Operations**: Implements basic arithmetic operations like addition and multiplication.
- **Prefix Notation**: Converts and evaluates expressions in prefix notation.

## Modules

- **Expression (Expr)**: The base trait for all expression types.
- **Num, Var, Plus, Times**: Concrete implementations of `Expr` representing numbers, variables, addition, and multiplication.
- **Environment**: Manages variable bindings.
- **Interpreter**: Evaluates expressions based on the `Environment`.

## Examples

Here are some example expressions and their usage:

```scala
// Addition
val expr1 = Plus(Num(1), Num(2))
// Evaluate
println(interpret(Environment.empty(), expr1)) // Output: 3

// Multiplication with variables
val expr2 = Times(Var("x"), Num(4))
// Define environment
val env = new Environment(Map("x" -> 2))
// Evaluate
println(interpret(env, expr2)) // Output: 8
```

## Acknowledgments

- Inspired by the lectures and assignments of [CC5116](https://users.dcc.uchile.cl/~etanter/CC5116/) at Universidad de Chile.