package lectures.part2oop

import scala.annotation.tailrec

object OOPBasics  extends App{
  val person = new Person("Paulinka", 25)
//  println(person.name) in scala this is class parameter, to be it class member, you have to add 'val' before argument in class definition
  println(person.age) // this can be accessed, because it is class field
  println(person.x)
  person.greet("Krzyś")
  person.greet

  val author = new Writer("Charles", "Dickens", 1812)
  val novel = new Novel("Great Expectations", 1861, author)
  println(novel.authorAge)
  println(novel.isWrittenBy(author)) // true
  val impostor = new Writer("Charles", "Dickens", 1812)
  println(novel.isWrittenBy(impostor)) // false, which is a bit strange because author and impostor have the same data, later on the course


  // counter testing
  val counter = new Counter2
  counter.inc.print
  counter.inc.inc.inc.print
  // recursive counter
  counter.inc(5000).print
}


class Person(name: String, val age: Int = 0) { // constructor - every instance of person must be constructed given name and age
  // body
  val x = 2 // this is field

  println(1 + 3) // this is executed as well
  println(name) // this can be accessed from inside a class

  // METHODS
  def greet(name: String): Unit = println(s"${this.name} says: Hi, $name") // this is a referring to a class object

  // OVERLOADING - applying the methods with the same name, but different signatures
  // different signatures means different number of parameters or parameters of different types
  def greet: Unit = println(s"Hi, I am $name") // here you do not need this, because it uses this.name by default

  // OVERLOADING CONSTRUCTORS - multiple constructors
  def this(name: String) = this(name, 0) // auxilary constructor calls primary constructor with default age = 0. But we can create default parameter in primary constructor
  def this() = this("John Doe") // second auxilary constructor calls first auxilary constructor
}

// class parameters are NOT FIELDS

/*
* Novel and Writer
*
* Writer: first name, surname, year
*   - method fullname()
*
* Novel: name, year of release, author
*   - authorAge()
*   - isWrittenBy(author)
*   - copy(new year of release) = new instance of Novel
*
* Counter class
*   - receives an int value
*   - method current count
*   - method to increment/decrement => new Counter
*   - overload inc/dec to receive an amount => new Counter
* */

// Novel and Writer
class Writer(firstName: String, surname: String, val year: Int) {
  def fullName: String = {
    this.firstName + " " + this.surname
  }
}

class Novel(name: String, yearOfRelaese: Int, author: Writer) {
  def authorAge: Int = this.yearOfRelaese - this.author.year

  def isWrittenBy(author: Writer): Boolean = author == this.author

  def copy(newYearOfRelease: Int): Novel = new Novel(name, newYearOfRelease, author)
}

// Counter class
class Counter(i: Int) {
  def currentCount(): Int = this.i // this could be as well done by adding 'val' in the constructor - it would make field from 'i'

  def increment: Counter = new Counter(this.i + 1) // immutability - IMPORTANT IN FUNC PROGRAMMING, instances are fixed, cannot be modified
  def decrement: Counter = new  Counter(this.i - 1)

  // overloaded increment() and decrement()
  def increment(newNumber: Int): Counter = new Counter(this.i + newNumber)
  def decrement(newNumber: Int): Counter = new Counter(this.i - newNumber)
}

// Counter class second solution
class Counter2(count: Int = 0) {

  // we want this to be logging event of incrementing and decrementing
  def inc: Counter2 = {
    println("incrementing")
    new Counter2(this.count + 1)
  }

  def dec: Counter2 = {
    println("decrementing")
    new  Counter2(this.count - 1)
  }

  // overloaded increment() and decrement()
  def inc(n: Int): Counter2 = {
    if (n <= 0) this // if all numbers are done return this instance
    else inc.inc(n-1) // n is just a number of running inc method (the one without arguments)
    // so inc(n) does nothing more but calls inc method n-times
    // it can do this because of:
    // 1. inc returns Counter2 class, so we can do inc.inc(somenumber) <- inc returns an object and we can access method int(n) in this object
    // 2. inc is overloaded <- but this is not important, it could be named with another name
  }

  def dec(n: Int): Counter2 = {
    if (n <= 0) this
    else dec.dec(n - 1)
  }

  def print = println(count)
}