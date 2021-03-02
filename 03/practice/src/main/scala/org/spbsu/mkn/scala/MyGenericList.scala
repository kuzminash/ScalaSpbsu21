package org.spbsu.mkn.scala

import org.spbsu.mkn.scala.List.undef

import scala.annotation.tailrec

sealed trait List[+A] {
  def head: A

  def tail: List[A]

  def drop(n: Int): List[A]

  def take(n: Int): List[A]

  def map[B](f: A => B): List[B]

  def ::[T >: A](elem: T): List[T] = new ::(elem, this)
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
}


case object MyNil extends List[Nothing] {
  override def head: Nothing = undef

  override def tail: Nothing = undef

  override def drop(n: Int): List[Nothing]  = n match {
    case 0 => this
    case _ =>  undef
  }

  override def take(n: Int): List[Nothing] = n match {
    case 0 => this
    case _ =>  undef
  }

  override def map[A](f: Nothing => A): List[Nothing] = MyNil
}