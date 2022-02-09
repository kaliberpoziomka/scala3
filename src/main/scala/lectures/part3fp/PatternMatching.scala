package lectures.part3fp

import scala.util.Random

object PatternMatching extends App {

  // switch on steroids
  val random = new Random
  val x = random.nextInt(10)

  val description = x match {
    case 1 => "the ONE"
    case 2 => "double or nothing"
    case 3 => "third time is the charm"
    case _ => "something else" // _ == WILDCARD
  }

  println(description)

  // 1. Decompose values in CASE CLASSES
  case class Person(name: String, age: Int)
  val bob = Person("Bob", 20)

  val greeting = bob match {
    // we are able to deconstruct class and use them
    // we can use deconstructed fields in guards
    case Person(n, a) if a < 21 => s"Hi, my name is $n and I am $a years old and I can't drink in the US"
    case Person(n, a) => s"Hi, my name is $n and I am $a years old"
    case _ => "I don't know who I am"
  }
  println(greeting)

  /*
    1. case are matched in order (first one matched is returned
    2. MatchError if don't match nothing -> use wildcard
    3. type of expression is unified type of all types ins all the cases
    4. Pattern Matching works great out of the box with case classes
  */

  // Pattern Marching on sealed hierarchies
  sealed class Animal
  case class Dog(breed: String) extends Animal
  case class Parrot(greeting: String) extends Animal

  val animal: Animal = Dog("Terra Nova")
  animal match {
    case Dog(someBreed) => println(s"Matched a dog of the $someBreed breed")
  }

  // Exercise
  trait Expr
  case class Number(n: Int) extends Expr
  case class Sum(e1: Expr, e2: Expr) extends Expr
  case class Prod(e1: Expr, e2: Expr) extends Expr
  // wirte simple func that uses pattern matching and takes expr as an argument and returns human readable form
  // Sum(Number(2), Number(3)) => 2 + 3
  // Prod(Sum(Number(2), Number(1)), Number(3)) = (2 + 1) * 3
  // Sum(Prod(Number(2), Number(1)), Number(3)) = 2 * 1 + 3

  def show(expr: Expr): String = {
    expr match {
      case Number(n) => n.toString
      case Sum(e1, e2): Expr => s"${show(e1)} + ${show(e2)}"
      case Prod(e1, e2): Expr => {
        def maybeShowParenthesess(e: Expr) = e match {
          case Prod(_, _) => show(e)
          case Number(_) => show(e)
          case _ => "(" + show(e) + ")"
        }
        maybeShowParenthesess(e1) + " * " + maybeShowParenthesess(e2)
      }
    }
  }
  println(show(Sum(Prod(Number(2), Number(1)), Number(3))))
  println(show(Prod(Sum(Number(2), Sum(Number(1), Number(6))), Number(3))))

}
