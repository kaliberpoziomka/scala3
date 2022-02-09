package lectures.part1basics

object Expressions extends App {
  val x = 1 + 2
  println(x)

  println(!(1 == x))

  var aVariable = 2
  aVariable += 3
  println(aVariable)

  // Instructions -> *DO SOMETHING* tell computer what to do, don't return nothing, only executes side effects
  // Expressions -> *REUTRN SOMETHIG* create a value

  // EVERYTHING in Scala is an EXPRESSION! (if, function calls, everything except definition word, class word etc.)
  // in other languages like Java, are instructions

  // Instructions are executed
  // Expressions are evaluated

  // in scala IF is expression

  // IF expression
  val aCondition = true
  val aConditionValue = if(aCondition) 5 else 3
  println(aConditionValue)

  // if returns expression by it's own
  println(if(aCondition) 10 else 3)

  // NEVER WRITE LOOPS IN SCALA
  var i = 10
  while (i < 10) {
    println(i)
    i += 1
  }


  // Instruction - excample
  val aWierdValue = (aVariable = 3)
  println(aWierdValue)
  // returned (), it is a value type Unit. Unit is void. This is example of instruction (assignment) returned nothig (Unit)

  // side effects: println(), whiles, reassigning - they are returning Unit, they are not from functional programming


  // Code Blocks - special kind of expressions
  val aCodeBlock = {
    val y = 2
    val z = y + 1

    if (z>2) "hello" else "goodbye" // this if statement returns value
  }
  // Code Blocks are expressions - they return value, returned by last line in block

  // In scala (and in other functional languages) we think in expressions

  // DO NOT USE WHILE LOOPS IN SCALA!

  // Excercies:
  // 1. difference between "hello world" and println("hello world")?
  // "hello world" is a string literal (value of type string), println is an instruction to print string object in console
  // 2. what is the value of:
  val someValue = {
    2 < 3
  }
  // true
  // 3. What is the value of:
  val someOtherVal = {
    if (someValue) 239 else 986
    42
  }
  // 42
}
