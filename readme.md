# Lab:
Demonstrate micro benchmarking

### algo
* Test:
`sbt algo/test`

### etl
* Depends on `algo`
* Test:
`sbt algo/test`

### algo
* Depends on `algo`
* Benchmark:
`sbt "bench/jmh:run -i 3 -wi 1 -f 1 -t 1 .*DPBench.*"`
* Benchmark Spark:
`sbt "bench/jmh:run -i 3 -wi 1 -f 1 -t 1 .*SparkTransformBench.*"`

* Example output:
```$text
[info] # JMH version: 1.21
[info] # VM version: JDK 1.8.0_181, Java HotSpot(TM) 64-Bit Server VM, 25.181-b13
[info] # VM invoker: /Library/Java/JavaVirtualMachines/jdk1.8.0_181.jdk/Contents/Home/jre/bin/java
[info] # VM options: <none>
[info] # Warmup: 1 iterations, 10 s each
[info] # Measurement: 3 iterations, 10 s each
[info] # Timeout: 10 min per iteration
[info] # Threads: 1 thread, will synchronize iterations
[info] # Benchmark mode: Throughput, ops/time
[info] # Benchmark: lab.DPBench.fib
[info] # Run progress: 0.00% complete, ETA 00:00:40
[info] # Fork: 1 of 1
[info] # Warmup Iteration   1: 52097.579 ops/ms
[info] Iteration   1: 53606.492 ops/ms
[info] Iteration   2: 57737.500 ops/ms
[info] Iteration   3: 57933.048 ops/ms
[info] Result "lab.DPBench.fib":
[info]   56425.680 ±(99.9%) 44577.544 ops/ms [Average]
[info]   (min, avg, max) = (53606.492, 56425.680, 57933.048), stdev = 2443.446
[info]   CI (99.9%): [11848.136, 101003.224] (assumes normal distribution)
[info] # Run complete. Total time: 00:00:40
[info] REMEMBER: The numbers below are just data. To gain reusable insights, you need to follow up on
[info] why the numbers are the way they are. Use profilers (see -prof, -lprof), design factorial
[info] experiments, perform baseline and negative tests that provide experimental control, make sure
[info] the benchmarking environment is safe on JVM/OS/HW level, ask for reviews from the domain experts.
[info] Do not assume the numbers tell you what you want them to tell.
[info] Benchmark     Mode  Cnt      Score       Error   Units
[info] DPBench.fib  thrpt    3  56425.680 ± 44577.544  ops/ms
```

# Coverage:
* Runs tests: `sbt clean coverage test`
* Generate report: `sbt coverageReport`
* Display `etl` report: `open etl/target/scala-2.12/scoverage-report/index.html`
* Display `algo` report: `open algo/target/scala-2.12/scoverage-report/index.html`

# Cheat codes:
* Test
```$scala
    val ns: Seq[Long] = Seq(0L, 1L, 2L, 3L, 4L, 5L, 17L, 23L, 29L, 43L, 47L, 83L)
    val fns: Seq[Long] = Seq(0L, 1L, 1L, 2L, 3L, 5L, 1597L, 28657L, 514229L, 433494437L, 2971215073L, 99194853094755497L)
```
* Better fib
```$scala
    def fib(n: Long): Long =
        if (n == 0L) 0L
        else if (n == 1L) 1L
        else (2L to n).foldLeft((0L, 1L)){case ((fi_2, fi_1), _) => (fi_1, fi_1+fi_2)}._2
```
