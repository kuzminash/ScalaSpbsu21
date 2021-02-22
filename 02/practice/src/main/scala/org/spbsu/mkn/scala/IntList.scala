package org.spbsu.mkn.scala

import scala.annotation.tailrec

sealed trait IntList {
  def head: Int

  def tail: IntList

  def drop(n: Int): IntList

  def take(n: Int): IntList

  def map(f: Int => Int): IntList

  def ::(elem: Int): IntList = new ::(elem, this)

  def ++(other: IntList): IntList = this match {
    case IntNil => other
    case ::(head, tail) => new ::(head, tail ++ other)
  }

}

object IntList {
  def undef: Nothing = throw new UnsupportedOperationException("operation is undefined")

  def fromSeq(seq: Seq[Int]): IntList = {
    seq.foldRight(IntNil: IntList)((head: Int, tail: IntList) => ::(head, tail))
  }

  def sum(intList: IntList): Int = intList match {
    case IntNil => throw new UnsupportedOperationException()
    case _ => foldLeft(0, intList)((x, y) => x + y)
  }

  def size(intList: IntList): Int = foldLeft(0, intList)((x, _) => x + 1)

  @tailrec
  def foldLeft(ini: Int, intList: IntList)(f: (Int, Int) => Int): Int = intList match {
    case IntNil => ini
    case ::(head, tail) => foldLeft(f(ini, head), tail)(f)
  }
}

final case class ::(override val head: Int, override val tail: IntList) extends IntList {
  override def drop(n: Int): IntList = n match {
    case 0 => this
    case _ => tail.drop(n - 1)
  }

  override def take(n: Int): IntList = n match {
    case 0 => IntNil
    case _ => new ::(head, tail.take(n - 1))
  }

  override def map(f: Int => Int): IntList = new ::(f(head), tail.map(f))
}


case object IntNil extends IntList {
  override def head: Int = throw new UnsupportedOperationException()

  override def tail: IntList = throw new UnsupportedOperationException()

  override def drop(n: Int): IntList = n match {
    case 0 => this
    case _ =>  throw new UnsupportedOperationException()
  }

  override def take(n: Int): IntList = n match {
    case 0 => this
    case _ =>  throw new UnsupportedOperationException()
  }

  override def map(f: Int => Int): IntList = this

  override def ::(elem: Int): IntList = super.::(elem)
}