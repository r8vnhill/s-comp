package cl.ravenhill.scomp
package ast

/** Represents boolean expressions in a simple expression language.
  *
  * The `Bool` trait extends the `Expr` trait, specializing in representing boolean values. It acts as a base for
  * concrete boolean value implementations. In an expression system, this trait can be used to handle boolean logic or
  * values distinctly from other types of expressions.
  */
sealed trait Bool extends Expr

/** Represents the boolean value `true`.
  *
  * `True` is a case object that extends the `Bool` trait. It represents the boolean value `true` in the context of an
  * expression language. This object can be used in expressions to represent the literal boolean value `true`.
  */
case object True extends Bool {

  /** Returns the prefix representation of the boolean value `true`. */
  override def toPrefix: String = "true"
}

/** Represents the boolean value `false`.
  *
  * `False` is a case object that extends the `Bool` trait. It represents the boolean value `false` in the context
  * of an expression language. This object can be utilized in expressions to denote the literal boolean value `false`.
  */
case object False extends Bool {

  /** Returns the prefix representation of the boolean value `false`. */
  override def toPrefix: String = "false"
}
