package org.spbsu.mkn.scala

import org.spbsu.mkn.scala.List.undef

import java.util.Comparator
import scala.annotation.tailrec

sealed trait List[+A] {

  def head: A

  def tail: List[A]

  def drop(n: Int): List[A]

  def take(n: Int): List[A]

  def map[B](f: A => B): List[B]

  def ::[T >: A](elem: T): List[T] = new ::(elem, this)

  //I knew I would need this function one day
  def ++[T >: A](elem: List[T]): List[T] = this match {
    case MyNil => elem
    case ::(head, tail) => head :: (tail ++ elem)
  }

  def withFilter(p: A => Boolean) : List[A]
}

object List {
  def undef: Nothing = throw new UnsupportedOperationException("operation is undefined")

  def fromSeq[A](seq: Seq[A]): List[A] = {
    seq.foldRight(MyNil: List[A])((head: A, tail: List[A]) => ::(head, tail))
  }

  def sum[A <: Int](list: List[A]): Int = list match {
    case MyNil => undef
    case _ => foldLeft[A, Int](0, list)((x, y) => x + y)
  }

  def size[A](list: List[A]): Int = {
    foldLeft[A, Int](0, list)((x, _) => x + 1)
  }

  /*
  we use recursive sorting algorithm that split the list by head value +
  took the Ainur's advice to write sorting algorithm using
  for comprehension with yield because this looks amazing:)
   */
  def sort[T](list: List[T])(implicit comparator: Ordering[T]): List[T] = list match {
    case head :: tail =>
      sort(for {element <- tail if comparator.compare(head, element) > 0} yield element) ++ ::(head,
        sort(for {element <- tail if comparator.compare(head, element) <= 0} yield element))
    case MyNil => MyNil
  }

  @tailrec
  def foldLeft[A, B](ini: B, intList: List[A])(f: (B, A) => B): B = intList match {
    case MyNil => ini
    case ::(head, tail) => foldLeft(f(ini, head), tail)(f)
  }

}

final case class ::[A](override val head: A, override val tail: List[A]) extends List[A] {
  override def drop(n: Int): List[A] = n match {
    case 0 => this
    case _ => tail.drop(n - 1)
  }

  override def take(n: Int): List[A] = n match {
    case 0 => MyNil
    case _ => new ::(head, tail.take(n - 1))
  }

  override def map[B](f: A => B): List[B] = new ::(f(head), tail.map(f))

  override def withFilter(p: A => Boolean): List[A] = {
    if (p(head)) head :: tail.withFilter(p)
    else tail.withFilter(p)
  }

}


case object MyNil extends List[Nothing] {
  override def head: Nothing = undef

  override def tail: Nothing = undef

  override def drop(n: Int): List[Nothing] = n match {
    case 0 => this
    case _ => undef
  }

  override def take(n: Int): List[Nothing] = n match {
    case 0 => this
    case _ => undef
  }

  override def map[A](f: Nothing => A): List[Nothing] = MyNil

  override def withFilter(p: Nothing => Boolean): List[Nothing] = MyNil

}