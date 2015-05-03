package com.example

import org.apache.camel.component.mock.MockEndpoint
import org.apache.camel.spring.javaconfig.SingleRouteCamelConfiguration
import org.apache.camel.test.spring.CamelSpringJUnit4ClassRunner
import org.apache.camel.{EndpointInject, Produce, ProducerTemplate}
import org.apache.commons.io.IOUtils
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.context.annotation.{Bean, Configuration}
import org.springframework.test.context.ContextConfiguration


@RunWith(classOf[CamelSpringJUnit4ClassRunner])
@ContextConfiguration(classes = Array(classOf[TestApplicationContext]))
class SimpleTest {

  @EndpointInject(uri =  "mock:success")
  val mockSuccess:MockEndpoint = null

  @EndpointInject(uri =  "mock:error")
  val mockError:MockEndpoint = null
  
  @Produce(uri = "direct:in")
  val template:ProducerTemplate = null

  @Test
  def shouldHitTheSuccesEndpoiny(): Unit ={
    val fileContent =  IOUtils.toString(getClass.getResourceAsStream("/json-file.json"))
    template.sendBody(fileContent)
    mockError.expectedMessageCount(0)
    mockSuccess.message(0).body().convertToString().isEqualTo(fileContent)
    mockSuccess.assertIsSatisfied()
    mockError.assertIsSatisfied()
  }

}

@Configuration
class TestApplicationContext extends SingleRouteCamelConfiguration with NoThirdPartBeans {

  @Bean override def route() = new FileRouter

  @Bean def sourceJsonIn = "direct:in"

  @Bean def targetJsonOut = "mock:success"

  @Bean def targetJsonError = "mock:error"

}

