package com.github.liamstar97.cloudmanager

import java.io.{File, PrintWriter}

import org.scalatra._

import scala.io.Source

class CloudManagerServlet extends ScalatraServlet {
  val dbPath = "data" + File.separatorChar + "db.csv"
  def loadFakeDBFromFile(path: String, separator: String = ",") = {
    Source.fromFile(path).getLines().toList.map(_.split(separator).toList)
  }

  var fakeDb = loadFakeDBFromFile(dbPath)

  get("/") {
    views.html.hello()
  }

  get("/hello") {
    "hi"
  }

  get("/login") {
    views.html.login()
  }

  get("/users/list") {
    fakeDb
      .take(params.getOrElse("max_rows", "10").toInt)
      .map(_.mkString(" | "))
      .mkString("\n")
  }

  get("/users/find") {
    val user = fakeDb
      .find(row => row.head.equalsIgnoreCase(params("id")))
      .map(_.mkString(" | "))
      .getOrElse(s"unknown userId: ${params("id")}")
    user
  }

  get("/users/add") {
    val newRow = List(params("email"), params("pass"))
    fakeDb = fakeDb ++ List(newRow)
    updateDB
    redirect("/users/list?max_rows=2000")
  }

  def updateDB = {
    val printWriter = new PrintWriter(dbPath)
    fakeDb.map(_.mkString(",")).foreach(printWriter.println)
    printWriter.close()
  }

}
