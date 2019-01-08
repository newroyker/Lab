package lab

import java.util.concurrent.TimeUnit

import org.openjdk.jmh.annotations._

import scala.util.Random

@OutputTimeUnit(TimeUnit.MILLISECONDS)
@BenchmarkMode(Array(Mode.Throughput))
@State(Scope.Benchmark)
class DPBench {

  @Benchmark
  def fib(): Unit = DP.fib(randomLong)

  val rand: Random.type = scala.util.Random

  def randomLong: Long = rand.nextInt(50).toLong
}
