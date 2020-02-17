package controllers.v3

import java.util

import akka.actor.{ActorRef, ActorSystem}
import controllers.BaseController
import javax.inject.{Inject, Named}
import play.api.mvc.ControllerComponents
import utils.ActorNames

import scala.collection.JavaConversions._
import scala.concurrent.ExecutionContext

class PeriodController @Inject()(@Named(ActorNames.SCHOOL_ACTOR) schoolActor: ActorRef, cc: ControllerComponents, actorSystem: ActorSystem)(implicit exec: ExecutionContext) extends BaseController(cc) {

    val objectType = "Period"
    val schemaName: String = "period"
    val version = "1.0"


    def create() = Action.async { implicit request =>
        val headers = commonHeaders()
        val body = requestBody()
        val content = body.getOrElse("period", new java.util.HashMap()).asInstanceOf[java.util.Map[String, Object]]
        content.putAll(headers)
        val contentRequest = getRequest(content, headers, "createTeacher")
        setRequestContext(contentRequest, version, objectType, schemaName)
        getResult("api.period.create", schoolActor, contentRequest)
    }


    def read(identifier: String) = Action.async { implicit request =>
        val headers = commonHeaders()
        val content = new java.util.HashMap().asInstanceOf[java.util.Map[String, Object]]
        content.putAll(headers)
        content.putAll(Map("identifier" -> identifier, "mode" -> "read", "fields" -> new util.ArrayList[String]()))
        val readRequest = getRequest(content, headers, "readTeacher")
        setRequestContext(readRequest, version, objectType, schemaName)
        getResult("api.period.read", schoolActor, readRequest)
    }

    def addUserAttended() = Action.async { implicit request =>
        val headers = commonHeaders()
        val body = requestBody()
        val content = body.getOrElse("period", new java.util.HashMap()).asInstanceOf[java.util.Map[String, Object]]
        content.putAll(headers)
        val contentRequest = getRequest(content, headers, "addUserData")
        setRequestContext(contentRequest, version, objectType, schemaName)
        getResult("api.period.attendance", schoolActor, contentRequest)
    }

    def getAttendance(identifier: String) = Action.async { implicit request =>
        val headers = commonHeaders()
        val body = requestBody()
        val content = new java.util.HashMap().asInstanceOf[java.util.Map[String, Object]]
        content.putAll(headers)
        content.putAll(Map("sessionId" -> identifier))
        val contentRequest = getRequest(content, headers, "getUserData")
        setRequestContext(contentRequest, version, objectType, schemaName)
        getResult("api.period.attendance", schoolActor, contentRequest)
    }
}
