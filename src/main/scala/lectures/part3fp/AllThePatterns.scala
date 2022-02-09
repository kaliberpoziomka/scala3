package lectures.part3fp
import excercises.{CustomList1, Empty1, MyList1}

object AllThePatterns extends App {

  // 1- constants
  val x: Any = "Scala"
  val constants = x match {
    case 1 => "a number"
    case "Scala" => "THE Scala"
    case true => "The Truth"
    case AllThePatterns => "A singleton object"
  }

  // 2 - match anything
  // 2.1 wildcard
  val matchAnything = x match {
    case _ =>
  }

  // 2.1 variable
  val matchAVariable = x match {
    case something => s"I've found $something"
  }

  // 3 - tuples
  val aTuple = (1, 2)
  val matchATuple = aTuple match {
    //tuple of literals
    case (1, 2) =>
    // nested pattern in tuple - may be e.g. another tuple
    case (something, 2) => s"$something"
  }

  val nestedTuple = (1, (2, 3))
  val matchANestedTuple = nestedTuple match {
    case (_, (2, v)) => s"I can match a $v in nested tuple that has number two in inside tuple!"
  }

  // 4 - case classes - constructor pattern
  val aList: MyList1[Int] = CustomList1(1, CustomList1(2, Empty1))
  val matchAList = aList match {
    case Empty1 => ???
    case CustomList1(head, tail) => ???
    case CustomList1(head, CustomList1(subhead, subtail)) => ??? // can also be nested
  }

  // 5 - list Patterns
  val aStandardList = List(1,2,3,42)
  val standardListMatching = aStandardList match {
    case List(1, _, _, _) => // extractor - advanced
    case List(1, _*) => // list of arbitrary length - advanced
    case 1:: List(_) => // infix pattern
    case List(1,2,3) :+ 42 => // infix pattern
  }

  // 6 - type specifiers
  val unknown: Any = 2
  val unknownMatch = unknown match {
    case list: List[Int] => // explicit type specifier
    case _ =>
  }

  // 7 - name binding
  val nameBindingMatch = aList match {
    // this @ sign lets you name entire patterns, not only variables
    case nonEmptyList @ CustomList1(_, _) => // name binding <- lets you use the name later (or here)
    case CustomList1(1, rest @ CustomList1(2, _)) => // name binding inside nested patterns
    // we can use those nonEmptyList and rest objects returned later in the expression
  }

  // 8 - multi-patterns
  val multiPattern = aList match {
    // compound pattern (multi-pattern)
    case Empty1 | CustomList1(0, _) => // with pipe we can chain as many patterns we want. This is OR
  }

  // 9 - if guards
  val secondElementSpecial = aList match {
    case CustomList1(_, CustomList1(specialElement, _)) if specialElement % 2 == 0 => // we can match with guards
  }

  // Question
  val numbers = List(1,2,3)
  val numbersMatch = numbers match {
    case listOfStrings: List[String] => "a list of strings"
    case listOfNumbers: List[Int] => "a list of numbers"
    case _ => ""
  }

  println(numbersMatch) // "a list of strings"
  // this is JVM trick question
  // because of JVM backward-compatibility, JVM is oblivious to generic types
  // so it looks only for List (without types)
}
