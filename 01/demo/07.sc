// todo: pattern matching

"123" match {
  case "123" => 123
  case _ => 0
}

123 match {
  case x if x % 2 == 0 => 1
  case _ => 2
}
//todo: match everywhere

List(1, 2, 3) match {
  case x::xs =>
  case Nil =>
}

//todo: name shadowing
