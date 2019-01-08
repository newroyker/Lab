package lab

import java.util.concurrent.TimeUnit

import org.apache.spark.sql.functions.desc
import org.apache.spark.sql.{Dataset, SparkSession}
import org.openjdk.jmh.annotations._

case class Continuous(id: Long)

@OutputTimeUnit(TimeUnit.SECONDS)
@BenchmarkMode(Array(Mode.Throughput))
@State(Scope.Benchmark)
class SparkTransformBench {

  val spark: SparkSession = SparkSession
    .builder
    .master("local[*]")
    .appName("SparkTransformBench")
    .config("spark.driver.host", "localhost")
    .getOrCreate()

  spark.sparkContext.setLogLevel("ERROR")

  import spark.implicits._

  implicit class ContinuousTransformations(ds: Dataset[Continuous]) {
    def goodMax: Continuous =
      ds.reduce((i, j) => if (i.id > j.id) i else j)

    def badMax: Continuous = ds
      .orderBy(desc("id"))
      .rdd.zipWithIndex()
      .filter { case (_, i) => i == 0 }
      .map { case (c, _) => c }
      .take(1)
      .head
  }

  val BOUND: Long = 10000L

  val testInput: Dataset[Continuous] = {
    val cs = spark.range(BOUND).map(Continuous(_))
    cs.cache()
    assert(cs.count() == BOUND)
    cs
  }

  @Benchmark
  def goodMax(): Unit = {
    val out = testInput.goodMax
    assert(out.id == BOUND - 1)
  }

  @Benchmark
  def badMax(): Unit = {
    val out = testInput.badMax
    assert(out.id == BOUND - 1)
  }

}




