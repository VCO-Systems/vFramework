name := "RGH"

version := "v1.0.1"

libraryDependencies ++= Seq(
  javaJdbc,
  javaJpa,
  "org.eclipse.persistence" % "eclipselink" % "2.5.0",
  cache
)     

play.Project.playJavaSettings
