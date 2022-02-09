package lectures.part1basics

object ValuesVariablesTypes extends App {
  // val is immutable
  val x: Int = 42
  // compiler can infer types
  val y = 35

  val aString: String = "henlo"

  val aBoolean: Boolean = true

  val aShort: Short = 23893
  val aLong: Long = 34823908230953280L
  val aFloat: Float = 2.0f
  val aDouble: Double = 3.14

  // variables can be reassigned
  var aVariable: Int = 4
  aVariable = 5
  println(aVariable)

}
