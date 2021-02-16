import scala.annotation.tailrec

// Tail recursion

def fac(n: Int, acc: BigInt = 1) : BigInt = {
  if(n <= 1) 1
  else fac(n - 1) * n
}

fac(100)