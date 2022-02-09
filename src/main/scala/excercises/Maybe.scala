package excercises

// Maybe is 'collection' that is either empty or has one element
// it is important structure, it denotes a possibly absence of a value
abstract class Maybe[+T] {
  def map[B](f: T => B): Maybe[B]
  def flatMap[B](f: T => Maybe[B]): Maybe[B]
  def filter(p: T => Boolean): Maybe[T]
}

case object MaybeNot extends Maybe[Nothing] { // empty 'collection' that is an case object - only one instance
  override def map[B](f: Nothing => B): Maybe[B] = MaybeNot
  override def flatMap[B](f: Nothing => Maybe[B]): Maybe[B] = MaybeNot
  override def filter(p: Nothing => Boolean): Maybe[Nothing] = MaybeNot
}
case class Just[+T](value: T) extends Maybe[T] { // full 'collection' that holds just one value
  override def map[B](f: T => B): Maybe[B] = Just(f(value)) // case classes do not need a 'new' keyword

  override def flatMap[B](f: T => Maybe[B]): Maybe[B] = f(value)

  override def filter(p: T => Boolean): Maybe[T] = {
    if (p(value)) this
    else MaybeNot
  }

}

object MaybeTest extends App {
  val just3 = Just(3) // XD
  println(just3)
  println(just3.map(_*2))
  println(just3.flatMap(x => Just(x % 2 == 0)))
  println(just3.filter(x => x % 2 == 0))
}
