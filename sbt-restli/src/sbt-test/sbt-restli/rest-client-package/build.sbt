lazy val api = (project in file("api"))
  .enablePlugins(RestliSchemaPlugin)
  .settings(
    target := target.value / "schema",
    libraryDependencies ++= Seq(
      "com.linkedin.pegasus" % "data" % "24.0.2",
      "com.google.code.findbugs" % "jsr305" % "3.0.0"
    )
  )

lazy val client = (project in file("client"))
  .enablePlugins(RestliClientPlugin)
  .dependsOn(api)
  .settings(
    restliClientApi := api,
    libraryDependencies += "com.linkedin.pegasus" % "restli-client" % "24.0.2",
    unzipPackage := {
      IO.unzip((restliClientPackage in Compile).value, target.value / "rest-client")
    }
  )

lazy val server = (project in file("server"))
  .enablePlugins(RestliModelPlugin)
  .dependsOn(api)
  .settings(
    restliModelApi := api,
    libraryDependencies += "com.linkedin.pegasus" % "restli-server" % "24.0.2"
  )

lazy val unzipPackage = taskKey[Unit]("extract jar file")
