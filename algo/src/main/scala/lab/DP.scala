package lab

object DP {

  def fib(n: Long): Long =
    if (n == 0L) 0L
    else if (n == 1L) 1L
    else fib(n - 1) + fib(n - 2)

}
