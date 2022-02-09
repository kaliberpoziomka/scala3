package lectures.part3fp
import scala.util.{Failure, Success, Try, Random}

object FailureHandling extends App {

  // create success and failure
  val aSuccess = Success(3)
  val aFailure = Failure(new RuntimeException("SUPER FAILUE"))

  println(aSuccess)
  println(aFailure)

  // most of time we don't need to create Success and Failure
  // try catch statement build that for us

  def unsafeMethod(): String = throw new RuntimeException("NO STRING FOR YOU, EAT THAT EXCEPTION")
  // Try object via the apply method
  val potentialFailure = Try(unsafeMethod())
  println(potentialFailure) // program didn't crash because of Try object

  // syntax sugar
  val anotherPotentialFailure = Try {
    // code that might throw
  }

  // utilities
  println(potentialFailure.isSuccess) // we can test if our code is successful

  // orElse - like in Option
  def backupMethod(): String = "A valid result"
  val fallbackTry = Try(unsafeMethod()).orElse(Try(backupMethod()))
  println(fallbackTry.isSuccess)
  println(fallbackTry)

  // better API
  // IF you design the API that may throw an exception, pack it with Try object
  def betterUnsafeMethod: Try[String] = Failure(new RuntimeException) // here we knew that something will trow an error (eg. if time is too long)
  def betterBackupMethod: Try[String] = Success("A valid result")
  val betterFallbackTry = betterUnsafeMethod orElse betterBackupMethod
  println(betterFallbackTry)

  // map flatMap filter
  println(aSuccess.map(_ * 2))
  println(aSuccess.flatMap(x => Success(x * 10)))
  println(aSuccess.filter(_ > 10)) // returns Failure

  // for-comprehension
  // exercise
  // we want to connect to a server
  val hostname = "localhost"
  val port = "8080"
  def renderHTML(page: String) = println(page)

  class Connection {
    def get(url: String): String = {
      val rand = new Random(System.nanoTime())
      if (rand.nextBoolean()) "<html>...</html>"
      else throw new RuntimeException("Connection Interrupted")
    }

    def getSafe(url: String): Try[String] = Try(get(url))
  }

  object HttpService {
    val rand = new Random(System.nanoTime())

    def getConnection(host: String, port: String): Connection = {
      if (rand.nextBoolean()) new Connection
      else throw new RuntimeException("Someone else took the port")
    }

    def getSafeConnection(host: String, port: String): Try[Connection] = Try(getConnection(host, port))
  }

  // if you get the html page from the connection, print it to the console i.e. call renderHTML
  // we have a lots of unsafe APIs
  val possibleConnection = HttpService.getSafeConnection(hostname, port)
  val possibleHTML = possibleConnection.flatMap(connection => connection.getSafe("/home"))
  possibleHTML.foreach(renderHTML)

  //shorter version
  HttpService.getSafeConnection(hostname, port)
    .flatMap(connection => connection.getSafe("/home"))
    .foreach(renderHTML)

  // for-comprehension version
  for {
    connection <- HttpService.getSafeConnection(hostname, port)
    page <- connection.getSafe("/localhost")
  } renderHTML(page)
  
  /*
    imperative approach would be:
  try {
    connection = HTTPService.getConnection(host, port)
    try {
      page = connection.get("/localhost")
      renderHTML(page)
    } catch {
    
    }
  } catch {
  
  }
  */


}
