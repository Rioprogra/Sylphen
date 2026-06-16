@REM ----------------------------------------------------------------------------
@REM Maven Wrapper startup batch script — Windows
@REM Descarga Maven automáticamente si no está instalado.
@REM Uso: mvnw.cmd spring-boot:run
@REM ----------------------------------------------------------------------------
@IF "%__MVNW_ARG0_NAME__%"=="" (SET "MVN_CMD=mvn") ELSE (SET "MVN_CMD=%__MVNW_ARG0_NAME__%")
@SET MAVEN_PROJECTBASEDIR=%MAVEN_BASEDIR%
@IF "%MAVEN_PROJECTBASEDIR%"=="" (
  @SET BEGIN_SRC_FILE=%~f0
  @SET BEGIN_PROJ_DIR=%~dp0
  @SET MAVEN_PROJECTBASEDIR=%BEGIN_PROJ_DIR%
)
@SET MAVEN_OPTS=%MAVEN_OPTS% -Xmx512m
@SET WRAPPER_JAR="%MAVEN_PROJECTBASEDIR%\.mvn\wrapper\maven-wrapper.jar"
@SET WRAPPER_LAUNCHER=org.apache.maven.wrapper.MavenWrapperMain
@SET WRAPPER_URL="https://repo.maven.apache.org/maven2/org/apache/maven/wrapper/maven-wrapper/3.3.2/maven-wrapper-3.3.2.jar"

@IF EXIST %WRAPPER_JAR% (
  @REM Wrapper JAR ya existe, usarlo directamente
) ELSE (
  @ECHO Descargando Maven Wrapper...
  @SET DOWNLOAD_URL=%WRAPPER_URL%

  @IF DEFINED MVNW_REPOURL (
    @SET DOWNLOAD_URL="%MVNW_REPOURL%/org/apache/maven/wrapper/maven-wrapper/3.3.2/maven-wrapper-3.3.2.jar"
  )

  @powershell -Command "&{"^
    "$webclient = new-object System.Net.WebClient;"^
    "if (-not ([string]::IsNullOrEmpty('%MVNW_USERNAME%') -and [string]::IsNullOrEmpty('%MVNW_PASSWORD%'))) {"^
    "$webclient.Credentials = new-object System.Net.NetworkCredential('%MVNW_USERNAME%', '%MVNW_PASSWORD%');"^
    "}"^
    "[Net.ServicePointManager]::SecurityProtocol = [Net.SecurityProtocolType]::Tls12; $webclient.DownloadFile('%DOWNLOAD_URL%', %WRAPPER_JAR%)"^
    "}"
  @IF "%ERRORLEVEL%"=="0" (
    @ECHO Maven Wrapper descargado correctamente.
  ) ELSE (
    @ECHO Error al descargar Maven Wrapper. Verifica tu conexión a internet.
    @EXIT /B 1
  )
)

@FOR /F "usebackq tokens=1,2 delims==" %%A IN ("%MAVEN_PROJECTBASEDIR%\.mvn\wrapper\maven-wrapper.properties") DO (
  @IF "%%A"=="distributionUrl" SET DISTRIBUTION_URL=%%B
)

@SET JAVA_HOME_CANDIDATE=%JAVA_HOME%
@IF "%JAVA_HOME_CANDIDATE%"=="" (
  @FOR /F "tokens=*" %%i IN ('where java 2^>NUL') DO (
    @SET JAVA_EXE=%%i
    @GOTO :found_java
  )
  @ECHO ERROR: JAVA_HOME no está configurado y Java no está en el PATH.
  @ECHO Por favor instala Java 21: https://adoptium.net/
  @EXIT /B 1
  :found_java
) ELSE (
  @SET JAVA_EXE=%JAVA_HOME_CANDIDATE%\bin\java.exe
)

@SET MAVEN_HOME_LOCAL=%USERPROFILE%\.m2\wrapper\dists
@%JAVA_EXE% -jar %WRAPPER_JAR% %WRAPPER_LAUNCHER% %*
