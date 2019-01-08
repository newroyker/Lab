import org.apache.spark.sql.{Dataset, Encoder}

package object lab {

  implicit class UnaryTransformation(ds: Dataset[Long]) {
    def fib(implicit enc: Encoder[Long]): Dataset[Long] = ds.map(DP.fib)
  }

}
