package org.spbsu.mkn.scala

import scala.io.StdIn.readLine
import scala.util.Random

object TheGame {

  sealed trait GuessResult

  case class Correct(numTries: Int) extends GuessResult

  case class Incorrect(bulls: Int, cows: Int) extends GuessResult

  class RepeatingDigitsException extends RuntimeException

  class WrongNumberLengthException(expected: Int, got: Int) extends RuntimeException

  def createString(n: Int, alphabet: String): String =
    (1 to n).map(_ => alphabet(Random.nextInt(alphabet.length))).mkString

  def generateNumberString(length: Int): String = {
    val alphabet = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789"
    Random.shuffle(alphabet).take(length).toString()
  }

  def validate(secret: String, userInput: String, numTries: Int = 1): GuessResult = {

    if (secret.length != userInput.length) throw new WrongNumberLengthException(secret.length, userInput.length)
    if (!userInput.forall(i => userInput.count(_ == i) == 1)) throw new RepeatingDigitsException

    var bulls = 0
    var cows = 0

    for (c <- 0 until userInput.length) {
      val position = secret.indexOf(userInput(c))
      if (position != -1) {
        if (position == c) bulls += 1
        else cows += 1
      }
    }

    if (bulls == secret.length) Correct(numTries)
    else Incorrect(bulls, cows)
  }

  def main(args: Array[String]): Unit = {

    println("Write the string length")
    val stringLength = readLine().toInt
    val secret = generateNumberString(stringLength)
    println("Let's play! I spy with my little eye a string, try to guess it:)")

    var isOn = true
    var attempt = 0

    while (isOn) {

      try {

        val userInput = readLine()
        val result = validate(secret, userInput, attempt)

        attempt += 1
        result match {
          case correct: Correct =>
            isOn = false
            println("Congrats! You guessed the string after " + correct.numTries + " attempt(s)")
          case incorrect: Incorrect =>
            println("Not there yet. You got " + incorrect.cows + " moo and " + incorrect.bulls + " \"angry bull sound\"")
          //interesting fact I couldn't find what a bull sounds like
        }
      } catch {
        case e: RepeatingDigitsException => {
          println("Ooooops there are some repeating characters...")
        }
        case e: WrongNumberLengthException => {
          println("Looks like the string is too short or too long")
        }
      }
    }
  }
}
