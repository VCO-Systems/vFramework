name := "vFramework"

version := "1.0-SNAPSHOT"

libraryDependencies ++= Seq(
  javaJdbc,
  javaEbean,
  javaJpa,
  cache
)     

play.Project.playJavaSettings
