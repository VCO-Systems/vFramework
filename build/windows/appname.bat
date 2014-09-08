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
 
@replace_classpath@
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
