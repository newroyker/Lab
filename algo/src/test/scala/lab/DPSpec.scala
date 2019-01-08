package lab

import org.scalatest.{FlatSpec, MustMatchers}

class DPSpec extends FlatSpec with MustMatchers {

  val ns: Seq[Long] = Seq(0L, 1L, 2L, 3L, 4L, 5L)
  val fns: Seq[Long] = Seq(0L, 1L, 1L, 2L, 3L, 5L)

  "DP" should "generate fibonacci given n" in {
    val tests: Map[Long, Long] = ns.zip(fns).toMap

    tests.foreach { case (k, v) =>
      DP.fib(k) must be(v)
    }
  }
}
