name := "lab"
organization := "roy"
version := "0.1"
scalaVersion := "2.12.8"

import pl.project13.scala.sbt.JmhPlugin

lazy val global = project
  .in(file("."))
  .aggregate(
    bench,
    algo,
    etl
  )

lazy val algo = project
  .settings(
    name := "algo",
    libraryDependencies ++= Seq(
      "org.scalatest" %% "scalatest" % "3.0.5" % Test
    )
  )

lazy val etl = project
  .settings(
    name := "etl",
    libraryDependencies ++= Seq(
      "org.apache.spark" %% "spark-sql" % "2.4.0",
      "org.scalatest" %% "scalatest" % "3.0.5" % Test
    )
  )
  .dependsOn(
    algo
  )

lazy val bench = project
  .enablePlugins(JmhPlugin)
  .settings(
    name := "bench"
  )
  .dependsOn(
    algo,
    etl
  )
