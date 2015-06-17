

import com.lmax.disruptor.dsl.Disruptor
import com.lmax.disruptor._

import scala.util.Random

class Transaction(var id: String = "", var category: Category = new Category(), var value: Long = 0)

class Category(var id: String =  "", var name: String  = "")

object TransactionMocker {
  val categories = Seq(new Category("1", "Restaurant"), new Category("2", "Supermarket"), new Category("1", "Bullshit"))

  def produce = new Transaction(id = uuid, giveMeACategoryFrom(categories), giveMeAValue)

  private def uuid = java.util.UUID.randomUUID.toString

  private def giveMeACategoryFrom(categories: Seq[Category]) =  categories(Random.nextInt(2))

  private def giveMeAValue = Random.nextLong()
}

class Publisher(disruptor: Disruptor[Transaction]) {

  def publish(transaction: Transaction) =  disruptor.publishEvent(new EventTranslator[Transaction] {

    override def translateTo(event: Transaction, sequence: Long): Unit ={
      event.id = transaction.id
      event.category = transaction.category
      event.value = transaction.value
    }

  })

}

class TransactionProducer(publisher: Publisher) extends Runnable {

  override def run(): Unit ={

    while (true) {
      publisher.publish(TransactionMocker.produce)
      Thread.sleep(1000)
    }

  }

}

object DisruptorFactory {
  import java.util.concurrent.Executors

  import com.lmax.disruptor.dsl.{Disruptor, ProducerType}
  import com.lmax.disruptor.{YieldingWaitStrategy}

  def makeIt() : Disruptor[Transaction] ={
    val disruptor = new Disruptor[Transaction](TransactionFactory, 1024, Executors.newCachedThreadPool(), ProducerType.SINGLE, new SleepingWaitStrategy)
    disruptor.handleEventsWith(new TransactionEvenHandler).then(new NextEventHandler)
    disruptor.start()
    disruptor
  }

}

object TransactionFactory extends EventFactory[Transaction]{
  override def newInstance = new Transaction()
}

object DisruptorTest {

  def start(): Unit ={
     new Thread(new TransactionProducer(new Publisher(DisruptorFactory.makeIt()))).start()
  }

}


class TransactionEvenHandler extends EventHandler[Transaction] {

  override def onEvent(transaction: Transaction, sequence: Long, endOfBatch: Boolean): Unit ={

    if (!transaction.category.name.equals("Supermarket")) {
      transaction.value = -1
      println("processing event: " + transaction.id + " sequence: " + sequence )
    }

  }

}


class NextEventHandler extends EventHandler[Transaction] {
  override def onEvent(transaction: Transaction, sequence: Long, endOfBatch: Boolean): Unit ={

    if (!transaction.category.name.equals("Supermarket")) {
      println("Next : " + transaction.value + " sequence: " + sequence + " " + transaction.category.name)
    } else {
      println("Next: sequence: " + sequence )
    }
  }
}

object Main extends  App {
  DisruptorTest.start()
}
