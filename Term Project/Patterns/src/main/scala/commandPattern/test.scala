package commandPattern
package commandPatternScala
/**
  * Created by @kash on 9/29/2017.
  */
object testScala {
  def makePurchase(cashRegister: CashRegister, amount: Int) = () => {
      println("Making purchase of " + amount)
      cashRegister.addCash(amount)
    }
  var purchases: Vector[() => Unit] = Vector()

  def executePurchases(purchase: () => Unit) = {
    purchases = purchases :+ purchase
    purchase()
  }

  def main(args: Array[String]): Unit = {
    val cashRegister = new CashRegister(0)
    val purchase1 = makePurchase(cashRegister,50)
    val purchase2 = makePurchase(cashRegister,100)
    executePurchases(purchase1)
    executePurchases(purchase2)
    println("Cash after two purchases = " + cashRegister.getTotal)
    println("Setting the cash to 0")
    cashRegister.reset()
    purchases.foreach(_())
    println("After replaying cash = " + cashRegister.getTotal)
  }
}

class CashRegister(var total: Int)  {
  def addCash(amount: Int) = {
    total += amount
  }

  def getTotal(): Int = {
    return total
  }

  def reset() {
    total = 0
  }
}


