@REM vco launcher script
@REM
@REM Envioronment:
@REM JAVA_HOME - location of a JDK home dir (optional if java on path)
@REM CFG_OPTS  - JVM options (optional)
@REM Configuration:
@REM VCO_config.txt found in the VCO_HOME.
@setlocal enabledelayedexpansion

@echo off
set "JAVA_HOME=%~dp0..\..\jdk-7u65-Windows-i586"
echo JAVA_HOME=%JAVA_HOME%
set "PATH=%JAVA_HOME%\bin\;%PATH%"
if "%VCO_HOME%"=="" set "VCO_HOME=%~dp0.."
set ERROR_CODE=0

set "APP_LIB_DIR=%VCO_HOME%\lib\"

rem Detect if we were double clicked, although theoretically A user could
rem manually run cmd /c
for %%x in (%cmdcmdline%) do if %%~x==/c set DOUBLECLICKED=1

rem FIRST we load the config file of extra options.


rem We use the value of the JAVACMD environment variable if defined


set "_JAVACMD=%JAVA_HOME%\bin\java.exe"
set JAVAINSTALLED=1
set JAVAOK=true
echo JAVA_HOME=%JAVA_HOME%
echo APP_LIB_DIR=%APP_LIB_DIR%
echo _JAVACMD=%_JAVACMD%
"%_JAVACMD%" -version

if "%JAVAOK%"=="false" (
  echo.
  echo A Java JDK is not installed or can't be found.
  if not "%JAVA_HOME%"=="" (
    echo JAVA_HOME = "%JAVA_HOME%"
	echo JAVA CMD = "%_JAVACMD%"
  )
  echo.
  echo Please go to
  echo   http://www.oracle.com/technetwork/java/javase/downloads/index.html
  echo and download a valid Java JDK and install before running vco.
  echo.
  echo If you think this message is in error, please check
  echo your environment variables to see if "java.exe" and "javac.exe" are
  echo available via JAVA_HOME or PATH.
  echo.
  if defined DOUBLECLICKED pause
  exit /B 1
)


rem We use the value of the JAVA_OPTS environment variable if defined, rather than the config.
set _JAVA_OPTS=%JAVA_OPTS%


:run
 
set "APP_CLASSPATH=%APP_LIB_DIR%\vco.vco-POC-v5.jar;%APP_LIB_DIR%\eclipselink-2.5.2.jar;%APP_LIB_DIR%\javax.persistence-2.1.0-RC2.jar;%APP_LIB_DIR%\jtds-1.3.1.jar;%APP_LIB_DIR%\ojdbc6.jar;%APP_LIB_DIR%\org.eclipse.persistence.antlr-2.5.2.jar;%APP_LIB_DIR%\org.eclipse.persistence.asm-2.5.2.jar;%APP_LIB_DIR%\org.scala-lang.scala-library-2.10.3.jar;%APP_LIB_DIR%\com.typesafe.play.play-java-jdbc_2.10-2.2.3.jar;%APP_LIB_DIR%\com.typesafe.play.play-jdbc_2.10-2.2.3.jar;%APP_LIB_DIR%\com.typesafe.play.play_2.10-2.2.3.jar;%APP_LIB_DIR%\com.typesafe.play.sbt-link-2.2.3.jar;%APP_LIB_DIR%\org.javassist.javassist-3.18.0-GA.jar;%APP_LIB_DIR%\com.typesafe.play.play-exceptions-2.2.3.jar;%APP_LIB_DIR%\com.typesafe.play.templates_2.10-2.2.3.jar;%APP_LIB_DIR%\com.github.scala-incubator.io.scala-io-file_2.10-0.4.2.jar;%APP_LIB_DIR%\com.github.scala-incubator.io.scala-io-core_2.10-0.4.2.jar;%APP_LIB_DIR%\com.jsuereth.scala-arm_2.10-1.3.jar;%APP_LIB_DIR%\com.typesafe.play.play-iteratees_2.10-2.2.3.jar;%APP_LIB_DIR%\org.scala-stm.scala-stm_2.10-0.7.jar;%APP_LIB_DIR%\com.typesafe.config-1.0.2.jar;%APP_LIB_DIR%\com.typesafe.play.play-json_2.10-2.2.3.jar;%APP_LIB_DIR%\com.typesafe.play.play-functional_2.10-2.2.3.jar;%APP_LIB_DIR%\com.typesafe.play.play-datacommons_2.10-2.2.3.jar;%APP_LIB_DIR%\joda-time.joda-time-2.2.jar;%APP_LIB_DIR%\org.joda.joda-convert-1.3.1.jar;%APP_LIB_DIR%\com.fasterxml.jackson.core.jackson-annotations-2.2.2.jar;%APP_LIB_DIR%\com.fasterxml.jackson.core.jackson-core-2.2.2.jar;%APP_LIB_DIR%\com.fasterxml.jackson.core.jackson-databind-2.2.2.jar;%APP_LIB_DIR%\org.scala-lang.scala-reflect-2.10.3.jar;%APP_LIB_DIR%\io.netty.netty-3.7.1.Final.jar;%APP_LIB_DIR%\com.typesafe.netty.netty-http-pipelining-1.1.2.jar;%APP_LIB_DIR%\org.slf4j.slf4j-api-1.7.5.jar;%APP_LIB_DIR%\org.slf4j.jul-to-slf4j-1.7.5.jar;%APP_LIB_DIR%\org.slf4j.jcl-over-slf4j-1.7.5.jar;%APP_LIB_DIR%\ch.qos.logback.logback-core-1.0.13.jar;%APP_LIB_DIR%\ch.qos.logback.logback-classic-1.0.13.jar;%APP_LIB_DIR%\com.typesafe.akka.akka-actor_2.10-2.2.0.jar;%APP_LIB_DIR%\com.typesafe.akka.akka-slf4j_2.10-2.2.0.jar;%APP_LIB_DIR%\org.apache.commons.commons-lang3-3.1.jar;%APP_LIB_DIR%\com.ning.async-http-client-1.7.18.jar;%APP_LIB_DIR%\oauth.signpost.signpost-core-1.2.1.2.jar;%APP_LIB_DIR%\commons-codec.commons-codec-1.3.jar;%APP_LIB_DIR%\oauth.signpost.signpost-commonshttp4-1.2.1.2.jar;%APP_LIB_DIR%\org.apache.httpcomponents.httpcore-4.0.1.jar;%APP_LIB_DIR%\org.apache.httpcomponents.httpclient-4.0.1.jar;%APP_LIB_DIR%\commons-logging.commons-logging-1.1.1.jar;%APP_LIB_DIR%\xerces.xercesImpl-2.11.0.jar;%APP_LIB_DIR%\xml-apis.xml-apis-1.4.01.jar;%APP_LIB_DIR%\javax.transaction.jta-1.1.jar;%APP_LIB_DIR%\com.jolbox.bonecp-0.8.0.RELEASE.jar;%APP_LIB_DIR%\com.google.guava.guava-14.0.1.jar;%APP_LIB_DIR%\com.h2database.h2-1.3.172.jar;%APP_LIB_DIR%\tyrex.tyrex-1.0.1.jar;%APP_LIB_DIR%\com.typesafe.play.play-java_2.10-2.2.3.jar;%APP_LIB_DIR%\org.yaml.snakeyaml-1.12.jar;%APP_LIB_DIR%\org.hibernate.hibernate-validator-5.0.1.Final.jar;%APP_LIB_DIR%\javax.validation.validation-api-1.1.0.Final.jar;%APP_LIB_DIR%\org.jboss.logging.jboss-logging-3.1.1.GA.jar;%APP_LIB_DIR%\com.fasterxml.classmate-0.8.0.jar;%APP_LIB_DIR%\org.springframework.spring-context-3.2.3.RELEASE.jar;%APP_LIB_DIR%\org.springframework.spring-core-3.2.3.RELEASE.jar;%APP_LIB_DIR%\org.springframework.spring-beans-3.2.3.RELEASE.jar;%APP_LIB_DIR%\org.reflections.reflections-0.9.8.jar;%APP_LIB_DIR%\com.google.code.findbugs.jsr305-2.0.1.jar;%APP_LIB_DIR%\javax.servlet.javax.servlet-api-3.0.1.jar;%APP_LIB_DIR%\com.typesafe.play.play-java-jpa_2.10-2.2.3.jar;%APP_LIB_DIR%\org.hibernate.javax.persistence.hibernate-jpa-2.0-api-1.0.1.Final.jar;%APP_LIB_DIR%\org.eclipse.persistence.eclipselink-2.5.0.jar;%APP_LIB_DIR%\org.eclipse.persistence.javax.persistence-2.1.0.jar;%APP_LIB_DIR%\org.eclipse.persistence.commonj.sdo-2.1.1.jar;%APP_LIB_DIR%\com.typesafe.play.play-cache_2.10-2.2.3.jar;%APP_LIB_DIR%\net.sf.ehcache.ehcache-core-2.6.6.jar"
set "APP_MAIN_CLASS=play.core.server.NettyServer"

rem TODO - figure out how to pass arguments....
echo "%_JAVACMD%" %* %_JAVA_OPTS% %VCO_OPTS% -cp "%APP_CLASSPATH%" %APP_MAIN_CLASS% %CMDS% 
"%_JAVACMD%" %* %_JAVA_OPTS% %VCO_OPTS% -cp "%APP_CLASSPATH%" %APP_MAIN_CLASS% %CMDS% 
if ERRORLEVEL 1 goto error
goto end

:error
set ERROR_CODE=1

:end

@endlocal

exit /B %ERROR_CODE%
