package com.example

import com.espertech.esper.client.{EPStatement, UpdateListener, EPServiceProviderManager, EventBean}

case class HelloEsper(id: Int, name: String, date: Long)

class EventListner extends UpdateListener  {

  def update(events: Array[EventBean], oldEvents: Array[EventBean]): Unit ={
    println("event arriving")
    events.map(event => println("current events: " + event.get("name")))
    oldEvents.map(event => println("old events: " + event.get("name")))
  }

}

object EventEngine {
  val service  = EPServiceProviderManager.getDefaultProvider()

  def addListner(listner: UpdateListener, statement: EPStatement) = statement.addListener(listner)

  def addStatement(query: String) = service.getEPAdministrator.createEPL(query)

}


object EventConsumer {

  def configure(): Unit ={
    val statement = EventEngine.addStatement("select * from com.example.HelloEsper.win:length(5)")
    EventEngine.addListner(new EventListner, statement)
  }

}

object Main extends  App {
  EventConsumer.configure()
  new EventProducer().produce
  new EventProducer().produce
  new EventProducer().produce
  new EventProducer().produce
  new EventProducer().produce
  new EventProducer().produce

}

class EventProducer {

  def produce() = EventEngine.service.getEPRuntime.sendEvent(
    HelloEsper(id = 1, name = "name", date = System.currentTimeMillis()))

}
