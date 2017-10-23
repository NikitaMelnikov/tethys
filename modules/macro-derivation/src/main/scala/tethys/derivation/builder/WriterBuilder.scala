package tethys.derivation.builder

import tethys.derivation.builder.WriterBuilder._

import scala.annotation.compileTimeOnly


/**
  * Created by eld0727 on 22.04.17.
  */

sealed trait WriterBuilder[A] {
  def remove[B](field: A => B): WriterBuilder[A]

  def update[B](field: A => B): FunApply[A, B]

  def updatePartial[B](field: A => B): PartialFunApply[A, B]

  def add(name: String): FunApply[A, A]
}

object WriterBuilder {
  
  @compileTimeOnly("ReaderBuilder should be defined in describe block")
  def apply[A <: Product]: WriterBuilder[A] = throw new NotDescribedException

  sealed trait FunApply[A, B] {
    def apply[C](fun: B => C): WriterBuilder[A]
    def fromRoot[C](fun: A => C): WriterBuilder[A]
  }

  sealed trait PartialFunApply[A, B] {
    def apply[C](partial: PartialFunction[B, C]): WriterBuilder[A]
    def fromRoot[C](partial: PartialFunction[A, C]): WriterBuilder[A]
  }
}



