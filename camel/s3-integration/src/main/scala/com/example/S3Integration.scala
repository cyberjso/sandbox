package com.example

import org.apache.camel.component.aws.s3.S3Constants
import org.apache.camel.{Message, Exchange, Processor}
import org.apache.camel.builder.RouteBuilder


class MyRouteBuilder extends RouteBuilder {
  var sourceJsonIn = "aws-s3://json-in?amazonS3Client=#client&maxMessagesPerPoll=15&delay=3000&region=sa-east-1"
  var targetJsonOut = "aws-s3://json-out?amazonS3Client=#client&region=sa-east-1"
  var targetJsonError = "aws-s3://json-error?amazonS3Client=#client&region=sa-east-1"

  override def configure(): Unit ={
    from(sourceJsonIn).
      to("bean:fileProcessor").
      choice().
        when(header("status").isEqualTo("ok")).
          to(targetJsonOut).to("mock:success").
        otherwise().
          to(targetJsonError).to("mock:error")
  }

}

class FileProcessor extends Processor {
  import java.util.logging.Logger

  val logger = Logger.getLogger(classOf[FileProcessor].getName)

  override def process(msg: Exchange): Unit ={
    val content = msg.getIn.getBody(classOf[String])
    // Do Whatever you need with the content
    logger.info("processing message")
    Messenger.send(message = msg, status = Some("ok"))
  }

}

object Messenger {
  import org.apache.commons.io.IOUtils

  def send(message: Exchange, status: Option[String]): Unit ={
    message.getOut.setHeader("status", status.getOrElse("error"))
    message.getOut.setHeader(S3Constants.KEY,  message.getIn.getHeader(S3Constants.KEY).asInstanceOf[String])
    message.getOut.setHeader(S3Constants.BUCKET_NAME, message.getIn.getHeader(S3Constants.BUCKET_NAME))
    message.getOut.setBody(IOUtils.toInputStream(message.getIn.getBody(classOf[String])))
  }

}

object CamelContext {
  import com.amazonaws.auth.BasicAWSCredentials
  import com.amazonaws.services.s3.AmazonS3Client
  import org.apache.camel.main.Main

  val accessKey = ""
  val secretKey = ""

  def start(): Unit ={

    def  validateConfig(): Unit= {
      if (secretKey.isEmpty) throw new IllegalStateException("AWS secret key  is not defined")
      if (accessKey.isEmpty) throw new IllegalStateException("AWS access key  is not defined")
    }

    validateConfig()
    val camel  = new Main
    camel.bind("client", new AmazonS3Client(new BasicAWSCredentials(accessKey, secretKey)))
    camel.bind("fileProcessor", new FileProcessor)
    camel.addRouteBuilder(new MyRouteBuilder)
    camel.enableHangupSupport()
    camel.run()
  }

}

object Main extends App {
  CamelContext.start()
}