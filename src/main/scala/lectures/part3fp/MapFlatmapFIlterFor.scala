package lectures.part3fp

object MapFlatmapFIlterFor extends App {

  val list = List(1,2,3)
  println(list.head)
  println(list.tail)

  // map
  println(list.map(_ + 1))
  println(list.map(_ + " is a number"))

  // filter
  println(list.filter(_ % 2 == 0))

  val toPair = (x: Int) => List(x, x+1)
  println(list.flatMap(toPair))

  // print all combinations between two lists
  val numbers = List(1,2,3,4)
  val chars = List("a","b",'c', 'd')
  val colors = List("black", "white")

  // "iterations"
  // in FP instead of nested loops we use map and flatmap
  println(
    numbers.flatMap((x) => {
      chars.map((y) => {
        x.toString + y
      })
    })
  )

  // triple-nested loop -> flatMap => flatMap => map
  val combinations = numbers.flatMap(x => {
      chars.flatMap(y => {
        colors.map(z => x.toString+y+z)
      })
    })
  println(combinations)


  // foreach
  list.foreach(println)


  // for-comprehensions <- shorthands for all those flatMap => map chains
val forCombinations = for {
  n <- numbers
  c <- chars
  color <- colors
} yield n.toString + c + color

  // ^ this is exactly the same as combinations code, but better to read
  println(forCombinations)

  // guards
  val forCombinationsWithGuards = for {
    n <- numbers if n % 2 == 0  // this is GUARD for filtering
    c <- chars
    color <- colors
  } yield n.toString + c + color
  println(forCombinationsWithGuards)

  for {
    n <- numbers
  } yield println(n)
  
  // synthax overload
  val times2 = list.map { x =>
                x * 2
              }

  
  

}
