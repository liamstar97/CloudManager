package com.github.liamstar97.cloudmanager

import org.scalatra.test.scalatest._

class CloudManagerServletTests extends ScalatraFunSuite {

  addServlet(classOf[CloudManagerServlet], "/*")

  test("GET / on CloudManagerServlet should return status 200") {
    get("/") {
      status should equal (200)
    }
  }

}
