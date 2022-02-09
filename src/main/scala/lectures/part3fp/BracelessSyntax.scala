package lectures.part3fp

object BracelessSyntax extends App{
  // if - expressions
  val anIfExpression = if (2 > 3) "bigger" else "smaller"
  // java-style
  val anIfExpression_v2 =
    if (2 > 3) {
      "bigger"
    } else {
      "smaller"
    }

  // compact
  val anIfExpression_v3 =
    if (2 > 3) "bigger"
    else "smaller"

  // scala 3
  val anIfExpression_v4 =
    if 2 > 3 then
      "bigger" // those must be indented related to the if
    else
      "smaller"

  val anIfExpression_v5 =
    if 2 > 3 then
      val result = "bigger" // this indented block is code block, so we can define values here and all stuff
      result
    else
      val result = "smaller"
      result

  println(anIfExpression_v5)

  // one-liner if expressions
  val anIfExpression_v6 = if 2 > 3 then "bigger" else "smaller"

  // for comprehensions
  val aForComprehension = for {
    n <- List(1,2,3)
    s <- List("black", "white")
  } yield s"$n, $s"

  //scala3 style
  val aForComprehension_v2 =
    for
      n <- List(1,2,3)
      s <- List("black", "white")
    yield s"$s, $n"

  // pattern matching
  val meaningOfLife = 42
  val aPatternMatch = meaningOfLife match {
    case 1 => "the one"
    case 2 => "double or nothing"
    case _ => "smth else"
  }

  // scala3
  val meaningOfLife_v2 = 42
  val aPatternMatch_v2 = meaningOfLife_v2 match
    case 1 => "the one"
    case 2 => "double or nothing"
    case _ => "smth else"

  // method without braces
  def computeMeaningOfLife(arg: Int): Int =
    val partialResult = 40


    partialResult + 2


  // class definition with significant indentation (same for traits, obj, enums, etc)
  class Animal: // compiler with : expects the body of Animal
    def eat(): Unit =
      println("I'm eating")

    def grow(): Unit =
      println("I'm growing")

  // with big classes you can add end token
  end Animal // end token can be used in for, ir, match, methods, classes, traits, enums, objects

  // anonymous classes
  val aSpecialAnimal = new Animal:
    override def eat(): Unit = println("I'm special")


  // indentation = strictly larger indentation
  // 3 spaces + 2 tabs > 2 spaces + 2 tabs
  // 3 spaces + 2 tabs > 3 spaces + 1 tab
  // 3 tabs + 2 spaces ??? 2 tabs + 3 spaces
  // what is visible on the screen will be visible to the compiler


}
