name := "VCO"

version := "POC-v1"

libraryDependencies ++= Seq(
  javaJdbc,
  javaJpa,
  cache
)     

play.Project.playJavaSettings
