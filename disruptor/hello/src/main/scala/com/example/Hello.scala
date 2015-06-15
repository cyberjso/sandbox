package com.example

import java.nio.ByteBuffer
import java.util.concurrent.{Executors, Executor}

import com.lmax.disruptor.dsl.Disruptor
import com.lmax.disruptor.{RingBuffer, EventFactory, EventHandler}


class LongEvent {
  var value:Int = _
}

class LongEventFactory extends EventFactory[LongEvent] {

  def newInstance() = new LongEvent

}


class LongEventHander extends EventHandler[LongEvent] {

  override def onEvent(event: LongEvent, sequence: Long, endOfBatch: Boolean)= println("event " + event.value)

}

class LongEventProducer(ringBuffer: RingBuffer[LongEvent]) {

  def onData(buffer: ByteBuffer): Unit ={
    var seq = ringBuffer.next()
    var event = ringBuffer.get(seq)
    event.value =  buffer.getInt(0)
    
    ringBuffer.publish(seq)
  }

}


object Main extends  App  {

  val executors = Executors.newCachedThreadPool
  val factory = new LongEventFactory
  val bufferSize = 1024
  var disruptor = new Disruptor(factory, bufferSize, executors)
  disruptor.handleEventsWith(new LongEventHander)
  disruptor.start()
  var ringBuffer = disruptor.getRingBuffer
  var producer = new LongEventProducer(ringBuffer)
  var buffer = ByteBuffer.allocate(8)

  while (true) {
    buffer.putInt(0, 1)
    producer.onData(buffer)
    Thread.sleep(1000)
  }



}
