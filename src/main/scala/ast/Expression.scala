package cl.ravenhill.scum
package ast

/** Represents the base trait for an expression in an abstract syntax tree (AST).
  *
  * This trait is the foundation for a polymorphic abstract syntax tree structure, where each expression in the tree can
  * carry an additional type parameter `A`. This parameter can be used to attach metadata to each expression, which can
  * be used for various purposes, like attaching source code locations to expressions, or attaching type information to
  * expressions.
  *
  * Implementations of this trait would represent specific kinds of expressions, like numeric literals, identifiers,
  * let-bindings, or primitive operations.
  *
  * @tparam A
  *   The type of the metadata associated with each expression. This allows for attaching additional information to
  *   expressions in a flexible manner.
  */
sealed trait Expression[A] {

  /** The metadata associated with this expression. */
  val metadata: Metadata[A]
}

/** Represents a numeric literal expression in an abstract syntax tree (AST).
  *
  * This case class extends the `Expression` trait, encapsulating a numeric literal. It is a part of the polymorphic AST
  * structure where each expression can carry additional metadata of type `A`. The `Num` class specifically represents
  * numeric literals in the AST.
  *
  * @tparam A
  *   The type of the metadata associated with this numeric literal expression.
  * @param n
  *   The integer value of the numeric literal.
  * @param metadata
  *   The metadata associated with this numeric literal expression, provided implicitly.
  */
case class Num[A](n: Int)(using override val metadata: Metadata[A]) extends Expression[A] with terminal.NumImpl(n)

/** Represents a variable expression in an abstract syntax tree (AST).
  *
  * This case class extends the `Expression` trait, encapsulating a variable. It is part of the polymorphic AST
  * structure where each expression can carry additional metadata of type `A`. The `Var` class specifically represents
  * variable expressions in the AST, identified by a symbol.
  *
  * @tparam A
  *   The type of the metadata associated with this variable expression.
  * @param sym
  *   The symbol representing the variable.
  * @param metadata
  *   The metadata associated with this variable expression, provided implicitly.
  */
case class Var[A](sym: String)(using override val metadata: Metadata[A])
    extends Expression[A]
    with terminal.VarImpl(sym)

/** Represents the base trait for unary operations in an abstract syntax tree (AST).
  *
  * The `UnaryOperation` trait serves as a foundational component in an AST for representing unary operations. Unary
  * operations are operations that operate on a single operand. This trait extends the `Expression` trait, emphasizing
  * that unary operations themselves are expressions. It defines a common structure for all unary operations by
  * including a single expression (`expr`) as a component. This approach allows for a consistent and extensible way to
  * represent various unary operations such as negation, increment, or logical not.
  *
  * @tparam A
  *   The type of the metadata associated with the unary operation expression.
  */
sealed trait UnaryOperation[A] extends Expression[A] {

  /** The expression that the unary operation is applied to. */
  val expr: Expression[A]
}

/** Represents a decrement operation expression (`--expr`) in an abstract syntax tree (AST).
  *
  * `Decrement` is a case class that extends the `UnaryOperation` trait, specifically to represent a decrement operation
  * in an AST. As a unary operation, it encapsulates a single expression (`expr`) to be decremented. This class is a
  * part of the polymorphic AST structure where each expression, including unary operations like decrement, can carry
  * additional metadata of type `A`.
  *
  * The class also implements the `unary.DecrementImpl` trait, providing the implementation details for the decrement
  * operation, including a custom `toString` method that represents the operation.
  *
  * @tparam A
  *   The type of the metadata associated with this decrement operation expression.
  * @param expr
  *   The expression that is to be decremented.
  * @param metadata
  *   The metadata associated with this decrement operation expression, provided implicitly.
  */
case class Decrement[A](override val expr: Expression[A])(using override val metadata: Metadata[A])
    extends UnaryOperation[A]
    with unary.DecrementImpl(expr)

/** Represents an increment operation expression (`++expr`) in an abstract syntax tree (AST).
  *
  * `Increment` is a case class that extends the `ast.UnaryOperation` trait, specifically to represent an increment
  * operation in an AST. As a unary operation, it encapsulates a single expression (`expr`) to be incremented. This
  * class is a part of the polymorphic AST structure where each expression, including unary operations like increment,
  * can carry additional metadata of type `A`.
  *
  * The class also implements the `unary.IncrementImpl` trait, providing the implementation details for the increment
  * operation, including a custom `toString` method that represents the operation.
  *
  * @tparam A
  *   The type of the metadata associated with this increment operation expression.
  * @param expr
  *   The expression that is to be incremented.
  * @param metadata
  *   The metadata associated with this increment operation expression, provided implicitly.
  */
case class Increment[A](expr: ast.Expression[A])(using override val metadata: Metadata[A])
    extends ast.UnaryOperation[A]
    with unary.IncrementImpl(expr)

/** Represents a 'doubled' operation expression in an abstract syntax tree (AST).
  *
  * The `Doubled` case class extends the `UnaryOperation` trait, specifically to represent a 'doubled' operation on an
  * expression within an AST. As a unary operation, it encapsulates a single expression (`expr`) that is to be doubled.
  * This class is a part of the polymorphic AST structure, where each expression, including unary operations like
  * 'doubled', can carry additional metadata of type `A`.
  *
  * The class also implements the `unary.DoubledImpl` trait, providing implementation details for the 'doubled'
  * operation, including a custom `toString` method that represents the operation.
  *
  * @tparam A
  *   The type of the metadata associated with this 'doubled' operation expression.
  * @param expr
  *   The expression that is to be doubled.
  * @param metadata
  *   The metadata associated with this 'doubled' operation expression, provided implicitly.
  */
case class Doubled[A](expr: Expression[A])(using override val metadata: Metadata[A])
    extends UnaryOperation[A]
    with unary.DoubledImpl(expr)

/** Represents the base trait for binary operations in an abstract syntax tree (AST).
  *
  * The `BinaryOperation` sealed trait serves as a foundational component in an AST for representing binary operations.
  * Binary operations are operations that operate on two operands, namely `left` and `right`. This trait extends the
  * `Expression` trait, emphasizing that binary operations themselves are expressions. By defining a common structure
  * for all binary operations through its two components (left and right expressions), it allows for a consistent and
  * extensible way to represent various binary operations such as addition, subtraction, multiplication, or division.
  *
  * @tparam A
  *   The type of the metadata associated with the binary operation expression.
  * @param left
  *   The left-hand side expression of the binary operation.
  * @param right
  *   The right-hand side expression of the binary operation.
  */
sealed trait BinaryOperation[A](left: Expression[A], right: Expression[A]) extends Expression[A]

/** Represents a 'plus' (addition) operation expression in an abstract syntax tree (AST).
  *
  * The `Plus` case class extends the `BinaryOperation` trait to specifically represent an addition operation in an AST.
  * As a binary operation, it encapsulates two expressions (`left` and `right`) that are the operands of the addition.
  * This class is a part of the polymorphic AST structure, where each expression, including binary operations like
  * addition, can carry additional metadata of type `A`.
  *
  * The class also implements the `binary.PlusImpl` trait, providing implementation details for the addition operation.
  * This helps to maintain separation between the representation of the operation in the AST and the specific logic for
  * executing or processing the addition.
  *
  * @tparam A
  *   The type of the metadata associated with this addition operation expression.
  * @param left
  *   The left-hand side expression of the addition.
  * @param right
  *   The right-hand side expression of the addition.
  * @param metadata
  *   The metadata associated with this addition operation expression, provided implicitly.
  */
case class Plus[A](left: Expression[A], right: Expression[A])(using override val metadata: Metadata[A])
    extends BinaryOperation[A](left, right)
    with binary.PlusImpl(left, right)

/** Represents a 'minus' (subtraction) operation expression in an abstract syntax tree (AST).
  *
  * The `Minus` case class extends the `BinaryOperation` trait, specifically to represent a subtraction operation in an
  * AST. As a binary operation, it encapsulates two expressions (`left` and `right`) that are the operands of the
  * subtraction. This class is a part of the polymorphic AST structure, where each expression, including binary
  * operations like subtraction, can carry additional metadata of type `A`.
  *
  * The class also implements the `binary.MinusImpl` trait, providing implementation details for the subtraction
  * operation. This helps to maintain separation between the representation of the operation in the AST and the specific
  * logic for executing or processing the subtraction.
  *
  * @tparam A
  *   The type of the metadata associated with this subtraction operation expression.
  * @param left
  *   The left-hand side expression of the subtraction.
  * @param right
  *   The right-hand side expression of the subtraction.
  * @param metadata
  *   The metadata associated with this subtraction operation expression, provided implicitly.
  */
case class Minus[A](left: Expression[A], right: Expression[A])(using override val metadata: Metadata[A])
    extends BinaryOperation[A](left, right)
    with binary.MinusImpl(left, right)

/** Represents an 'if' conditional expression in an abstract syntax tree (AST).
  *
  * The `If` case class extends the `Expression` trait, specifically to represent an 'if' conditional expression in an
  * AST. It encapsulates three components: a predicate expression, and two expressions for the 'then' and 'else'
  * branches of the conditional. Additionally, this class includes metadata associated with the 'if' expression. The
  * `If` class is part of the polymorphic AST structure, where each expression can carry additional metadata of type
  * `A`.
  *
  * This class also implements the `IfImpl` trait, which provides the implementation details for the 'if' conditional,
  * including a custom `toString` method that represents the entire conditional expression.
  *
  * @tparam A
  *   The type of the metadata associated with this 'if' conditional expression.
  * @param predicate
  *   The expression representing the condition to be evaluated in the 'if' statement.
  * @param thenBranch
  *   The expression to be executed if the predicate evaluates to true.
  * @param elseBranch
  *   The expression to be executed if the predicate evaluates to false.
  * @param metadata
  *   The metadata associated with this 'if' conditional expression, provided implicitly.
  */
case class If[A](predicate: Expression[A], thenBranch: Expression[A], elseBranch: Expression[A])(using
    override val metadata: Metadata[A]
) extends Expression[A]
    with IfImpl(predicate, thenBranch, elseBranch)

/** Represents a 'let' binding expression in an abstract syntax tree (AST).
  *
  * The `Let` case class extends the `Expression` trait to specifically represent a 'let' binding expression in an AST.
  * This construct is common in functional programming languages and allows for binding an expression (`expr`) to a
  * symbol (`sym`), which is then utilized within another expression (`body`). This class includes metadata associated
  * with the 'let' binding. It forms a part of the polymorphic AST structure, where each expression can carry additional
  * metadata of type `A`.
  *
  * This class also implements the `LetImpl` trait, providing the implementation details for the 'let' binding,
  * including a custom `toString` method that represents the entire binding expression.
  *
  * @tparam A
  *   The type of the metadata associated with this 'let' binding expression.
  * @param sym
  *   The symbol to which the expression is bound.
  * @param expr
  *   The expression to be bound to the symbol.
  * @param body
  *   The body expression where the symbol binding is used.
  * @param metadata
  *   The metadata associated with this 'let' binding expression, provided implicitly.
  */
case class Let[A](sym: String, expr: Expression[A], body: Expression[A])(using val metadata: Metadata[A])
    extends Expression[A]
    with LetImpl(sym, expr, body)
