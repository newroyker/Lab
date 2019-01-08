package lab

import org.apache.spark.sql.{Dataset, SparkSession}
import org.scalatest.{BeforeAndAfterAll, FlatSpec, MustMatchers}

class UnaryTransformationSpec extends FlatSpec with MustMatchers with BeforeAndAfterAll {

  val ns: Seq[Long] = Seq(0L, 1L, 2L, 3L, 4L, 5L)
  val fns: Seq[Long] = Seq(0L, 1L, 1L, 2L, 3L, 5L)

  lazy val spark: SparkSession = SparkSession
    .builder
    .master("local[*]")
    .appName("UnaryTransformationSpec")
    .config("spark.driver.host", "localhost")
    .getOrCreate()
  spark.sparkContext.setLogLevel("ERROR")

  import spark.implicits._

  override def afterAll(): Unit = {
    super.afterAll()
    spark.stop
  }

  "UnaryTransformation" should "generate fibonacci given n" in {
    val input: Dataset[Long] = ns.toDS
    val expected: Dataset[Long] = fns.toDS
    val output = input.fib

    output.collect must contain theSameElementsInOrderAs expected.collect
  }
}
