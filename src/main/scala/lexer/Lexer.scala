package cl.ravenhill.scomp
package lexer

import scala.util.parsing.combinator.RegexParsers

/** Defines a lexer for a programming language using regular expression parsing.
  *
  * The `Lexer` object extends `RegexParsers` and provides a set of parser definitions to tokenize input strings into
  * specific `Token` instances. Each parser corresponds to a type of token like identifiers, numbers, and various syntax
  * symbols and keywords. Whitespace skipping is enabled by default.
  */
object Lexer extends RegexParsers {

  /** Indicates whether to skip whitespace between tokens. */
  override def skipWhitespace = true

  /** Parses identifiers and converts them to `IdentifierToken`. */
  def identifier: Parser[IdentifierToken] = """[a-zA-Z_][a-zA-Z0-9_]*""".r ^^ { IdentifierToken.apply }

  /** Parses numeric literals and converts them to `NumberToken`. */
  def number: Parser[NumberToken] = """[0-9]+""".r ^^ { str => NumberToken(str.toInt) }

  /** Parses open parenthesis '(' and converts it to `OpenParenthesisToken`. */
  def openParenthesis: Parser[OpenParenthesisToken.type] = "(" ^^ { _ => OpenParenthesisToken }

  /** Parses close parenthesis ')' and converts it to `CloseParenthesisToken`. */
  def closeParenthesis: Parser[CloseParenthesisToken.type] = ")" ^^ { _ => CloseParenthesisToken }

  /** Parses open brace '{' and converts it to `OpenBraceToken`. */
  def openBrace: Parser[OpenBraceToken.type] = "{" ^^ { _ => OpenBraceToken }

  /** Parses close brace '}' and converts it to `CloseBraceToken`. */
  def closeBrace: Parser[CloseBraceToken.type] = "}" ^^ { _ => CloseBraceToken }

  /** Parses decrement operator '--' and converts it to `DecrementToken`. */
  def decrement: Parser[DecrementToken.type] = "--" ^^ { _ => DecrementToken }

  /** Parses increment operator '++' and converts it to `IncrementToken`. */
  def increment: Parser[IncrementToken.type] = "++" ^^ { _ => IncrementToken }

  /** Parses doubling operator '==' and converts it to `DoubledToken`. */
  def doubled: Parser[DoubledToken.type] = "==" ^^ { _ => DoubledToken }

  /** Parses 'if' keyword and converts it to `IfToken`. */
  def ifExpr: Parser[IfToken.type] = "if" ^^ { _ => IfToken }

  /** Parses 'else' keyword and converts it to `ElseToken`. */
  def elseExpr: Parser[ElseToken.type] = "else" ^^ { _ => ElseToken }

  /** Parses 'let' keyword and converts it to `LetToken`. */
  def let: Parser[LetToken.type] = "let" ^^ { _ => LetToken }

  /** Parses dot '.' and converts it to `DotToken`. */
  def dot: Parser[DotToken.type] = "." ^^ { _ => DotToken }

  /** Parses arrow '->' and converts it to `ArrowToken`. */
  def arrow: Parser[ArrowToken.type] = "->" ^^ { _ => ArrowToken }
}
