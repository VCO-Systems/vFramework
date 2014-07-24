name := "vFramework"

version := "1.0-SNAPSHOT"

libraryDependencies ++= Seq(
  javaJdbc,
  javaJpa,
  "org.eclipse.persistence" % "eclipselink" % "2.5.0",
  cache
)     

play.Project.playJavaSettings
