package com.example

import com.amazonaws.auth.BasicAWSCredentials
import com.amazonaws.services.s3.AmazonS3Client
import org.apache.camel.builder.RouteBuilder
import org.apache.camel.spring.javaconfig.{CamelConfiguration, Main}
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.{Bean, ComponentScan, Configuration}
import org.springframework.stereotype.Component


trait NoThirdPartBeans {

  @Bean def fileProcessor() = new FileProcessor

}

@Configuration
@ComponentScan(Array("com.example"))
class MyApplicationContext extends CamelConfiguration with S3Beans with NoThirdPartBeans {}

trait S3Beans {

  @Bean
  def sourceJsonIn = "aws-s3://json-in?amazonS3Client=#client&maxMessagesPerPoll=15&delay=3000&region=sa-east-1"

  @Bean
  def targetJsonOut = "aws-s3://json-out?amazonS3Client=#client&region=sa-east-1"

  @Bean
  def targetJsonError = "aws-s3://json-error?amazonS3Client=#client&region=sa-east-1"

  @Bean
  def client = new AmazonS3Client(new BasicAWSCredentials("[use your credentials]", "[use your credentials]"))

}

@Component
class FileRouter extends RouteBuilder {
  @Autowired val sourceJsonIn: String = null
  @Autowired val targetJsonOut: String = null
  @Autowired var targetJsonError: String = null

  override def configure(): Unit = {
    from(sourceJsonIn).
      to("bean:fileProcessor").
      choice().
        when(header("status").isEqualTo("ok")).
          to(targetJsonOut).
        otherwise().
          to(targetJsonError)
  }

}

object MayMain extends App {
  val context = new Main
  context.setConfigClassesString(classOf[MyApplicationContext].getName)
  context.run(args)
}