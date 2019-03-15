package io.sandbox.functional


class Cafe {

  def buyCofee(cc: CreditCard): (Coffee, Charge) = {
    val coffee  = Coffee(2.25)
    (coffee, Charge(cc, coffee.price))
  }

  def buyCoffees(cc: CreditCard, n : Int): (List[Coffee], Charge) = {    
    val purchases : List[(Coffee, Charge)]  = List.fill(n)(buyCofee(cc))

    val (coffees, charges) = purchases.unzip
      (coffees, charges.reduce((c1, c2) => c1.combine(c2)))
  }

}

case class Coffee(price: Double)

case class CreditCard()

case class Charge(cc: CreditCard, amount: Double) {

  def combine(other: Charge): Charge = {
    if (cc ==  other.cc)
      Charge(cc, amount + other.amount)
      
    else
      throw new Exception("Can't combine charges with different credit cards")
  }

}
