import sbt.Keys.scalaVersion
import sbt._

object BuildConfig extends Dependencies {

  val baseLibs = Seq(
    ScalaExt.kindProjectorCompilerPlugin
  )

  val protocolLibs = Seq(
    Enumeratum.enumeratum,
    Types.supertagged
  )

  val workflowLibs = baseLibs ++ Seq(
    ZTemporal.core,
    ZTemporal.scalapb,
    ZTemporal.scalapbProto
  )

  val workerLibs = baseLibs ++ Logging.logstageAll ++ DI.compileLibs ++ Seq(
    Zio.interopCats,
    Config.pureconfigEnumeratum,
    Doobie.core,
    Doobie.postgres,
    Doobie.hikari,
    Db.postgres,
    Db.flyway,
    Enumeratum.doobie,
    Scalapb.runtimeProtobuf,
    Utility.chimney,
    Utility.quicklens,
    ZTemporal.distage,
    ZTemporal.testkit,
    Json.circeCore,
    Json.circeParser
  )

  val endpointsLibs = baseLibs ++ Seq(
    Http.tapirCore,
    Http.tapirEnumeratum,
    Http.tapirJsonCirce,
    Enumeratum.circe,
    Json.circeCore,
    Json.circeGeneric,
    Json.circeGenericExtras,
    Json.circeParser
  )

  val serviceLibs = baseLibs ++ Logging.logstageAll ++ DI.compileLibs ++ Seq(
    Zio.interopCats,
    Config.pureconfigEnumeratum,
    Http.tapirZio,
    Http.tapirHttp4sServer,
    Http.tapirSttpClient,
    Http.tapirOpenapiDocs,
    Http.tapirOpenApiCirce,
    Http.tapirSwaggerUI,
    DI.distageFrameworkDocker,
    Utility.chimney,
    Utility.quicklens,
    ZTemporal.distage
  )
}

trait Dependencies {

  object version {
    val trzUtils   = "1.9.0"
    val zio        = "1.0.7"
    val doobie     = "0.13.2"
    val izumi      = "1.0.6"
    val enumeratum = "1.6.1"
    val chimney    = "0.6.1"
    val circe      = "0.13.0"
    val pureconfig = "0.14.1"
    val tapir      = "0.18.0-M11"
    val ztemporal  = "0.1.1-228fc08ec6acd740755e9fb07452f2dea348f6f8-SNAPSHOT"
  }

  object org {
    val trz                   = "com.tranzzo"
    val softwaremillSttpTapir = "com.softwaremill.sttp.tapir"
    val scalaland             = "io.scalaland"
    val circe                 = "io.circe"
    val izumi                 = "io.7mind.izumi"
    val pureconfig            = "com.github.pureconfig"
    val tpolecat              = "org.tpolecat"
    val beachape              = "com.beachape"
    val zio                   = "dev.zio"
    val evopay                = "ua.com.evopay"
  }

  object ZTemporal {
    val core         = org.evopay %% "ztemporal-core"    % version.ztemporal
    val scalapb      = org.evopay %% "ztemporal-scalapb" % version.ztemporal
    val scalapbProto = org.evopay %% "ztemporal-scalapb" % version.ztemporal % "protobuf"
    val distage      = org.evopay %% "ztemporal-distage" % version.ztemporal
    val testkit      = org.evopay %% "ztemporal-testkit" % version.ztemporal % Test
  }

  object Zio {
    val self        = org.zio %% "zio"              % version.zio
    val stream      = org.zio %% "zio-streams"      % version.zio
    val interopCats = org.zio %% "zio-interop-cats" % "2.2.0.1"
  }

  object Types {
    val supertagged = "org.rudogma" %% "supertagged" % "2.0-RC1"
  }

  object Doobie {
    val core     = org.tpolecat %% "doobie-core"     % version.doobie
    val postgres = org.tpolecat %% "doobie-postgres" % version.doobie
    val hikari   = org.tpolecat %% "doobie-hikari"   % version.doobie
  }

  object Db {
    val postgres = "org.postgresql" % "postgresql"  % "42.2.5"
    val flyway   = "org.flywaydb"   % "flyway-core" % "6.5.2"
  }

  object Config {
    val distageExtensionConfig = org.izumi      %% "distage-extension-config" % version.izumi
    val pureconfig             = org.pureconfig %% "pureconfig-magnolia"      % version.pureconfig
    val pureconfigEnumeratum   = org.pureconfig %% "pureconfig-enumeratum"    % version.pureconfig
  }

  object DI {
    val distage                = org.izumi %% "distage-core"             % version.izumi
    val distageFramework       = org.izumi %% "distage-framework"        % version.izumi
    val distageFrameworkDocker = org.izumi %% "distage-framework-docker" % version.izumi % Test

    val compileLibs = Seq(distage, distageFramework)
  }

  object Logging {
    val logstage             = org.izumi %% "logstage-core"              % version.izumi
    val logstageSlf4jAdapter = org.izumi %% "logstage-adapter-slf4j"     % version.izumi
    val distageLogstage      = org.izumi %% "distage-extension-logstage" % version.izumi

    val logstageAll = Seq(logstage, logstageSlf4jAdapter, distageLogstage)
  }

  object Enumeratum {
    val enumeratum = org.beachape %% "enumeratum"            % version.enumeratum
    val doobie     = org.beachape %% "enumeratum-doobie"     % "1.6.0"
    val circe      = org.beachape %% "enumeratum-circe"      % version.enumeratum
    val scalacheck = org.beachape %% "enumeratum-scalacheck" % version.enumeratum
  }

  object Http {
    val tapirCore         = org.softwaremillSttpTapir %% "tapir-core"               % version.tapir
    val tapirEnumeratum   = org.softwaremillSttpTapir %% "tapir-enumeratum"         % version.tapir
    val tapirZio          = org.softwaremillSttpTapir %% "tapir-zio"                % version.tapir
    val tapirHttp4sServer = org.softwaremillSttpTapir %% "tapir-http4s-server"      % version.tapir
    val tapirJsonCirce    = org.softwaremillSttpTapir %% "tapir-json-circe"         % version.tapir
    val tapirSttpClient   = org.softwaremillSttpTapir %% "tapir-sttp-client"        % version.tapir
    val tapirOpenapiDocs  = org.softwaremillSttpTapir %% "tapir-openapi-docs"       % version.tapir
    val tapirOpenApiCirce = org.softwaremillSttpTapir %% "tapir-openapi-circe-yaml" % version.tapir
    val tapirSwaggerUI    = org.softwaremillSttpTapir %% "tapir-swagger-ui-http4s"  % version.tapir
  }

  object Json {
    val circeCore          = org.circe %% "circe-core"           % version.circe
    val circeGeneric       = org.circe %% "circe-generic"        % version.circe
    val circeGenericExtras = org.circe %% "circe-generic-extras" % version.circe
    val circeParser        = org.circe %% "circe-parser"         % version.circe
  }

  object Scalapb {
    val runtime         = "com.thesamet.scalapb" %% "scalapb-runtime" % scalapb.compiler.Version.scalapbVersion
    val runtimeProtobuf = runtime                % "protobuf"
  }

  object Testing {
    val scalatest               = "org.scalatest" %% "scalatest"                 % "3.2.0"
    val scalaArbitraryTestkit   = org.trz         %% "scala-arbitrary-testkit"   % "0.0.4"
    val distageTestkitScalatest = org.izumi       %% "distage-testkit-scalatest" % version.izumi
    val distageFrameworkDocker  = org.izumi       %% "distage-framework-docker"  % version.izumi
  }

  object Utility {
    val chimney   = org.scalaland                %% "chimney"   % version.chimney
    val quicklens = "com.softwaremill.quicklens" %% "quicklens" % "1.7.1"
  }

  object ScalaExt {

    val kindProjectorCompilerPlugin = compilerPlugin(
      "org.typelevel" %% "kind-projector" % "0.11.3" cross CrossVersion.full
    )
  }
}
