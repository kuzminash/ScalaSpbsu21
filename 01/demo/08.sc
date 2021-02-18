// todo: "try/catch"

val x = {println("x"); 123}
println("foo")
x

try {
  throw new RuntimeException
} catch {
  case e: RuntimeException => println(s"ex $e")
}