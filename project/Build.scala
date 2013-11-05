import sbt._
import Keys._

object BuildSettings {
  val buildVersion = "0.0.1"

  val buildSettings = Defaults.defaultSettings ++ Seq(
    organization := "ch.hsr.intte.uebungXX",
    version := buildVersion,
    shellPrompt := ShellPrompt.buildShellPrompt,
    exportJars := true
  )
}

object Resolvers {
  val sunrepo = "Sun Maven2 Repo" at "http://download.java.net/maven/2"
  val sunrepoGF = "Sun GF Maven2 Repo" at "http://download.java.net/maven/glassfish"
  val oraclerepo = "Oracle Maven2 Repo" at "http://download.oracle.com/maven"

  val oracleResolvers = Seq(sunrepo, sunrepoGF, oraclerepo)
}

object Dependencies {
  val jettywebapp = "org.eclipse.jetty" % "jetty-webapp" % "8.1.7.v20120910" % "container,test"
  val jettyjsp = "org.eclipse.jetty" % "jetty-jsp" % "8.1.7.v20120910" % "container,test"
  val javaxservlet = "org.eclipse.jetty.orbit" % "javax.servlet" % "3.0.0.v201112011016" % "container,test" artifacts Artifact("javax.servlet", "jar", "jar")
  val javaxservletjsp = "org.eclipse.jetty.orbit" % "javax.servlet.jsp" % "2.2.0.v201112011158" % "container,test" artifacts Artifact("javax.servlet.jsp", "jar", "jar")
  val logbackclassic = "ch.qos.logback" % "logback-classic" % "1.0.6"
}

object IntteUBuild extends Build {

  import Dependencies._
  import BuildSettings._
  import com.earldouglas.xsbtwebplugin.WebPlugin.webSettings

  // Sub-project inttewebapp specific dependencies
  val intteWebappDeps = Seq(
    jettywebapp,
    jettyjsp,
    javaxservlet,
    javaxservletjsp,
    logbackclassic
  )

  lazy val intteu = Project(
    "intteu",
    file("."),
    settings = buildSettings ++ Seq(
      name := "IntTe Uebungen"
    )
  ) dependsOn (inttewebapp)

  lazy val inttewebapp = Project(
    "intteu-webapp",
    file("webapp"),
    settings = buildSettings ++ webSettings ++ Seq(libraryDependencies ++= intteWebappDeps)
  )
}