package io.monadless.stdlib

import io.monadless.Monadless
import scala.util.Try
import scala.util.control.NonFatal

trait MonadlessTry extends Monadless[Try] {

  def collect[T](list: List[Try[T]]): Try[List[T]] =
    list.foldLeft(Try(List.empty[T])) {
      (acc, item) =>
        for {
          l <- acc
          i <- item
        } yield l :+ i
    }

  def rescue[T](m: Try[T])(pf: PartialFunction[Throwable, Try[T]]) =
    m.recoverWith(pf)

  def ensure[T](m: Try[T])(f: => Unit) = {
    try f
    catch {
      case NonFatal(e) => ()
    }
    m
  }
}

object MonadlessTry extends MonadlessTry