package cl.ravenhill.scum

/** Global variable representing the current mode for converting expressions to string representations.
  *
  * `toStringMode` determines how expressions are converted into their string representations. It can be set to
  * different modes defined in the `ToStringMode` enum. These modes dictate the level of detail or the type of
  * information included in the string representation of expressions, which can be useful for various purposes like
  * debugging or logging.
  */
var toStringMode = ToStringMode.NORMAL

/** Enumeration of possible modes for converting expressions to string representations.
  *
  * This enum defines the various modes that can be used to convert expressions into string representations. The mode
  * influences the verbosity and type of information included in the string representation.
  *
  *   - `NORMAL`: Represents the default mode, which provides a standard, concise string representation of expressions.
  *   - `DEBUG`: Represents a more detailed mode, intended for debugging purposes, providing additional information or a
  *     more verbose representation of expressions.
  */
enum ToStringMode {
  case NORMAL
  case DEBUG
}
