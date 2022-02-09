package lectures.part2oop
import scala.language.postfixOps

object MethodNotations extends App {

  class Person(val name: String, favoriteMovie: String, val age: Int = 0) {
    def likes(movie: String): Boolean = movie == this.favoriteMovie
    def hangOutWith(person: Person): String = s"${this.name} is hanigng out with ${person.name}"
    // operator plus definition (infix notation)
    def +(person: Person): String = s"${this.name} is with ${person.name}"
    // unary operator definition (prefix notation)
    def unary_! : String = s"$name, what the heck?!"
    // postfix notation
    def isAlive: Boolean = true
    // apply
    def apply(): String = s"Hi, me name is $name, and I like $favoriteMovie."

    // Ex 1.
    def +(nickname: String): Person = new Person(name= s"${this.name} ($nickname)", favoriteMovie=this.favoriteMovie)
    // Ex 2.
    def unary_+ : Person = new Person(name = this.name, favoriteMovie = this.favoriteMovie, age = this.age + 1)
    // Ex 3.
    def learns(str: String): String = s"${this.name} learns $str."
    def learnsScala: String = this learns "Scala"
    // Ex 4.
    def apply(n: Int): String = s"${this.name} watched ${this.favoriteMovie} $n times."
  }

  val mary = new Person("Mary", "Inception")
  println(mary.likes("Inception"))
  // INFIX NOTATION = OPERATOR NOTATION (example of syntactic sugar)
  // this is equivalent to the previous notation
  // this only works with methods with one parameter
  println(mary likes "Inception")

  // "operators" in scala
  val tom = new Person("Tom", "Fight Club")
  // this 'hangOutWith acts like a operator between mary and tom, so it's like a plus, minut and so one
  // we can define those operators as methods of a class and use infix notation like this
  println(mary hangOutWith tom)
  // ^ this is equivalent to println(mary.hangOutWith(tom))
  // we can define our own "+" operator for class person
  println(mary + tom)
  println(mary.+(tom))

  // mathematical operators are also methods and are defined in the same way
  println(1 + 2)
  println(1.+(2))

  // ALL OPERATORS ARE METHODS

  // PREFIX NOTATIONS
  val x = -1 // this minus before one is UNARY OPERATOR
  // equivalent to this
  val y = 1.unary_-
  // unary_prefix only works with - + ~ !
  println(!mary)
  // we can define what we want with prefix notation, but only with 4 operators (+, -, ~, !)

  // POSTFIX NOTATION
  // only methods with no parameters can be used as postfix notation
  println(mary isAlive)
  println(mary.isAlive)
  // to run postfix notation wee need to import:
  // import scala.language.postfixOps
  // postfix notation is not recommended to use - can cause ambiguity when reading a code

  // APPLY
  println(mary.apply())
  // the same as this
  println(mary()) // if compiler sees this notation, it looks for apply method
  // apply is extremely cool, because it breaks the barrier between OOP and functional programming - we will see that functions actually aref


  /*
  1. Overload the + operator
    mary + "the rockstar" => new Person "Mary (the rockstar)" (and the same movie
   2. Add an age to the Person class
    Add a unary + operator => new Person with the age +1
    +mary => mary with the age incremented
   3. Add a "learns" method in the Person class => "Mary learns Sacala"
     Add a learnScala method, calls learns method with "Scala"
     use it in postfix notation.
   4. Overload the apply method
    marry.apply(2) => "Mary watched Inception 2 time"
   */
  // 1.
  val newMary = mary + "the rockstar"
  println(newMary.name)
  println((mary + "the rockstar")()) // we are using here infix operator and run apply method on mary, which is like mary()
  // 2.
  val maryOlder = +mary
  println(maryOlder.age)
  // or like this
  println((+mary).age)
  // 3.
  println(mary learnsScala)
  // 4.
  println(mary(9))





}