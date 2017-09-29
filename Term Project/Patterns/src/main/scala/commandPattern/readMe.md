**Commmand Pattern**

The command pattern is a behavioral pattern as it is used to 
 manage algorithms, relationships and responsibilities between 
 objects. The GOF defines Command Pattern as:
 
 *Encapsulate a request as an object, thereby letting you 
 parameterize clients with different requests, queue or log 
 requests, and support undoable operations.*
 
 In OOP, it's common to have objects execute actions, where the result 
 of these actions is reflected back to the receiver object. But we 
 want to be able decouple act of requesting the action from 
 what the action actually does. This way we can tinker the action 
 without actually dealing with the requester of the action. As the 
GOF states, there are three entities involved in this pattern: 
the client, the invoker and the receiver. First of all, we declare a 
`Commmand` interface providing a simple `execute` method which asks 
the receiver of the command to carry out an operation. The receiever has 
the knowledge of what to do to carry out the request. The sole job 
 of an Invoker is to call the `execute()` method on the `Command` that 
  it holds. The Client creates ConcreteCommands and sets a receiver 
  for the command. The ConcreteCommand defines a binding between the 
  action and the receiver. When the invoker calls `execute`, the 
  ConcreteCommand will run one or more actions on the Receiver.
  
  
 Why would I use this Pattern ?
 
 1) A history of requests in needed 
 2) You need callback functionality
 3) The requests need to be executed in some order.
 4) To decouple the requester from the executor of the action.
 
 The following example illustrates the same. The entities involved are:
 
 Command: 
 - Interface specifying the method `execute` 
 
 CashRegister: 
 - Acts as the receiver class. 
 - Contains a account of total money through an integer variable `total`.
 - Defines methods for adding cash and getting back the total.
 
 Purchase: 
 - Acts as the client requesting for an action of purchase.
 - Implements the `Command` interface, specifies binding between the 
 action of purchase and the effect on cash register
 
PurchaseInvoker:
- Simple calls the `execute` method of the `Command` it receives.
- Also stores the commands in a separate list as the history for playback.

The code in Java can be found [here](https://github.com/akash-07/Software-Engineering-Assignments/blob/master/Term%20Project/Patterns/src/main/scala/commandPattern/test.java).

**Functional Replacement**

We would like to focus on the functional replacement for this pattern. Let's 
think on what the `Command` interface is for. It solely wraps a function 
 `execute` into an object. People say Java is verbose, not because of the 
 language constructs but it's because of the boilerplate code that we keep 
 on writing just to encapsulate the idea of function common all classes. We try 
 and push, or try and capture an idea into an object when it's not required. Here, 
 in the functional world we can capture the same idea as higher order functions.
 Let's get to work straightaway.
 
 We will keep the `CashRegister` class as it is which acts as our client class.
 
 ```scala
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
```

The act of making a purchase can just be captured into a higher order function of type 
`() => Unit`. We define a helper function `makePurchase` that takes in a cash Register and 
an amount and returns a higher order function of above type. Essentially the type `() => Unit` 
is typical in Scala suggesting that the functions performs some action.

```scala
//A method which returns an action of purchase
def makePurchase(cashRegister: CashRegister, amount: Int) = () => {
  println("Making purchase of " + amount)
  cashRegister.addCash(amount)
}
```

So `makePurchase` takes in `CashRegister` and an amount, it returns a function which 
performs the action of printing the cash added and calling the method `addCash` on the 
`CashRegister` instance. This captures the action of purchase.

Next we would like to maintain a history of purchases, so we define a `Vector` of type 
`() => Unit`. It holds all actions of purchase. We also need a function to actually perform 
 the purchase and add the purchase to our history, we write another function `executePurchase` 
 that does it for us.
 
 ```scala
var purchases: Vector[() => Unit] = Vector()    //Storing as history

//A method to do an actual purchase
def executePurchases(purchase: () => Unit) = {
  purchases = purchases :+ purchase   //purchase being added to history
  purchase()                          //Purchasing
}
```

Let's look at the analogies once before we get to testing our functions.
- `Purchase` class === `makePurchase`
- `purchaseInvoker` === `executePurchase`
- `CashRegister` class === `CashRegister` class
- `Command` interface === The `execute` method of `Command` interface is captured in the 
 return value of `makePurchase`
  
 With all our functions ready, we do some purchases now.
 
 ```scala
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
```

Output:

```scala
Making purchase of 50
Making purchase of 100
Cash after two purchases = 150
Setting the cash to 0
Making purchase of 50
Making purchase of 100
After replaying cash = 150
```

**Conclusion:** We didn't write any bolierplate code simply because it was not needed. All actions 
could simply be captured as higher order functions. This makes our code clean, readable and 
much more maintainable. This reduces noise in our code significantly, allowing us to focus only on 
the important part.