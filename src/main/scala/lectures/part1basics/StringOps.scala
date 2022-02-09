package lectures.part1basics

object StringOps extends App {

  val str: String = "Hello, I am learning Scala"

  // Java utilities
  println(str.charAt(2))
  println(str.substring(7, 11))
  println(str.split("\\s").toList)
  println(str.startsWith("Hello"))
  println(str.replace(" ", "-"))
  println(str.toLowerCase)
  println(str.length)

  // Scala utilities
  val aNumberString = "45"
  val aNumber = aNumberString.toInt
  println(aNumber)

  // prepending and appending
  println('a' +: aNumberString :+ 'z')

  println(str.reverse)
  // list like functions
  println(str.take(2))

  // Scala-specific: String interpolators
  // S-interpolate
  val name = "Paulinka"
  val age = 25
  val greeting = s"Hello, my name is $name and I am $age years old."
  val anotherGreeting = s"Hello, my name is $name and I will be turning ${age + 1} years old."
  println(anotherGreeting)

  // F-interpolators
  // they can be formatted
  val speed =  1.2f
  val myth = f"$name%s can run $speed%2.2fkm per minute."
  println(myth)

  // raw-interpolator
  // works as s-interpolator, but can print strings literally
  println(raw"This is a \n newline")

  // here escaped because declared earlier
  val escaped = "This is a \n newline"
  println(raw"$escaped")
}
