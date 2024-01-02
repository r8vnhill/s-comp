package cl.ravenhill.scum

/** Represents a generic trait for encapsulating metadata associated with compiled expressions.
  *
  * This trait `Metadata` serves as a generic interface for handling metadata in the context of compiled expressions or
  * similar constructs. The metadata can be of any type `T`, allowing for flexible representation of additional
  * information such as compilation details, optimization flags, runtime characteristics, or other relevant data that
  * may be associated with a compiled expression.
  *
  * Implementors of this trait will provide a concrete type for `T` and a means to access the metadata through the
  * `data` method.
  *
  * @tparam T
  *   The type of the metadata. This type parameter allows for flexibility in what kind of metadata can be associated
  *   with an expression, making the trait adaptable to various needs.
  */
trait Metadata[T] {

  /** Provides access to the metadata.
    *
    * This method should be implemented to return the metadata of type `T`. The nature of the metadata is determined by
    * the specific implementation of this trait and can vary widely depending on the use case.
    *
    * @return
    *   The metadata associated with a compiled expression, of type `T`.
    */
  def data: T
}
