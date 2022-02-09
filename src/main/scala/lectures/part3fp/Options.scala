package lectures.part3fp

import scala.util.Random

// OPTION SOLVE NULL POINTER PROBLEM
// option is a wrapper for a value that might be present or not <- like a shroedringer's cat
object Options extends App {

  // two options to create Option
  val myFirstOption: Option[Int] = Some(4)
  val noOption: Option[Int] = None

  println(myFirstOption)

  // they were created for unsafe AIPs
  def unsafeMethod(): String = null
//  val result = Some(unsafeMethod()) // wrong!!! Some should always have a valid value inside, NEVER null
  //valid option
  val result = Option(unsafeMethod()) // Some or None
// WE SHOULD NEVER DO NULL CHECKS FOR OURSELVES -> Option will do it for us

  // use Options in chain methods
  def backupMethod(): String = "A valid result"
  //if option is None, then use method orElse to chain result with another function
  val chainedResult = Option(unsafeMethod()).orElse(Option(backupMethod()))

  // DESIGN unsafe APIs
  def betterUnsafeMethod(): Option[String] = None
  def betterBackupMethod(): Option[String] = Some("A valid result")

  val betterChainResult = betterUnsafeMethod() orElse betterBackupMethod()

  //functions on Options
  println(myFirstOption.isEmpty)
//  println(myFirstOption.get) // DON'T USE THIS: UNSAFE - getting a value from option that is None returns null pointer exception

  // map, flatMap, filter
  println(myFirstOption.map(_ * 2))
  println(myFirstOption.filter(_ > 10))
  println(myFirstOption.flatMap(x => Option(x*10)))

  // for-comprehensions

  // Excercise
  val config: Map[String, String] = Map(
    // those values are fetched from elsewhere - so they may or may not contain values
    "host" -> "176.45.36.1",
    "port" -> "80"
  )

  class Connection {
    def connect = "Connect" // connect to some server
  }
  object Connection {
    val rand = new Random(System.nanoTime())
    // this function is written by someone else
    def apply(host: String, port: String): Option[Connection] = {
      if(rand.nextBoolean()) Some(new Connection)
      else None
    }
  }

  // try to establish a connection, if so - print the connect method

  // my solution
//  def manitainConnection(config: Map[String, String]): Option[String] = {
//    val connection = Connection(config("host"), config(""))
//    if (connection.isEmpty) manitainConnection(config)
//    else Option(connection)
//  }

  //Daniels' solution
  val host = config.get("host") // Option[String], Map.get returns an Option
  val port = config.get("port") // Option[String]
  val connection = host.flatMap(h => port.flatMap(p => Connection(h, p))) // this allows as securely use Connection
  /*
  if (h != null)
    if (p != null)
      return Connection.apply(h, p)
  return null

   */
  val connectionStatus = connection.map(c => c.connect) // this allows us securely get value of connection, because it might not be there
  /*
    if (c != null)
      return c.connect
    return null
  */
  println(connectionStatus)
  // if (connectionStatus == null) println(None) else println(Some(connectionStatus))
  connectionStatus.foreach(println)
  /*
   if (connectionStatus != null)
      println(Some(connectionStatus).get)
  */

  // it look strange because we iterate over one value
  // but it's iteration over Option, so it can have a Some(value) or None

  // chained calls version
  println("chained calls version:")
  config.get("host")
    .flatMap(host => config.get("port").flatMap(port => Connection(host, port))) // getting values and using Connection obj
    // without this map below, we would get this: Some(lectures.part3fp.Options$Connection@5d624da6
    .map(connection => connection.connect) // secure accessing value from option that connection returned
    .foreach(println) // just printing to console

  // for-comprehension version
  println("for-comprehension:")
  val forConnectionStatus = for { // it's all just one iteration, but it's secure
    // it does all flatMap and map chained calls for us
    /*
      we could read this like that:
      given a host, given a port and given a connection,
      assuming that host, port and connection are not null
      then give me connection.connect
      otherwise give me None
    */
    host <- config.get("host") // .get returns Option
    port <- config.get("port")
    connection <- Connection(host, port)
  } yield {
    connection.connect
    // it is a chain of flatMap and map, so at the end we can use orElse
  }
  println(forConnectionStatus)
  forConnectionStatus.foreach(println)

  // in short:
  // - Options are to avoid nulls (crushes and null-related assertions)
  // - map flatMap and filter are well to utilize Option (also fold, collect, toList)
  // - to chain use orElse
  // - for-comprehension is more readable than flatMap, map, filter chain, and it works like this chain anyway
}
