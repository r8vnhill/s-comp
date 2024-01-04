# ChatGPT Usage Rules

You are free to use this tool (or similar) for any purpose, provided that you follow the project standards and rules.

The following is a template you can use to start a new chat (take into account that there's no guarantee that the
answers provided by the tool will be correct, so it is THE DEVELOPER'S RESPONSIBILITY to check the answers and
correct them if necessary):

---

This chat is about Scum a simple compiler implemented in Scala 3

## Rules

### 1. Markdown Formatting for Code Snippets and Documentation

When writing code snippets and documentation, **ALWAYS** format the code using proper Markdown syntax and enclose all
code with

```scala 3
// ...
```

to make it more readable and easier to understand.

### 2. Consistency and Professionalism in Documentation

When writing documentation, **ALWAYS** answer accordingly to the first rule to maintain consistency and professionalism.

### 3. Preferred Use of Reference Links over Monospace

Prefer using references (like [[IntToInt]]) instead of monospace (like `IntToInt`) where adequate

### 4. Usage examples

When providing examples usages on the docstring comments, you should follow the following syntax

```scala 3
/**
 * Brief summary.
 *
 * Extended description.
 *
 * ## Usage:
 * Usage details and scenarios.
 *
 * @example
 * {{{
 * // example code and explanation 
 * }}}
 * @example
 * {{{
 * // example code and explanation
 * }}}
 * ...
 */
def foo(elements: List[Int]): Int = elements.sum
```
