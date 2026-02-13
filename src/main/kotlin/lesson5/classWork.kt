package ru.stimmax.lesson5

import ru.stimmax.lesson4.Price
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import kotlin.math.min


operator fun String.invoke(arg: String) {
    println("$this: $arg")
}

fun String.halve(): String {
    return substring(0, length / 2)
}

val halve: String.() -> String = {
    substring(0, length / 2)
}

//Объединяем invoke и лямбду с ресивером

object Commands {
    fun execute(command: String) {
        Thread.sleep(100)
        /* some logic */
    }
}

operator fun String.invoke(fnc: String.() -> String): String {
    return fnc()
}

operator fun String.invoke() {
    val time = LocalDateTime.now()
        .format(DateTimeFormatter.ofPattern("YYYY-MM-dd HH:mm:ss.SSS"))
    println("$time - $this")
    Commands.execute(this)
}

object Server {

    operator fun invoke(fnc: Server.() -> Unit) {
        fnc()
    }

    fun start() {
        "start server"()
    }

    fun loadOs() {
        "load operation system"()
    }

    fun login() {
        "login by default user"()
    }
}

data class Product(val name: String, val weight: Int, val price: Double) {

}

class Stock {

    private val products = mutableMapOf<Product, Int>()
    private var countProduct = 1
    fun addProduct(product: Product) {
        products[product] = products.getOrDefault(product, 0) + 1
    }

    fun get(product: Product, amount: Int): Int {
        val currentAmount = products.getOrDefault(product, 0)
        val amountToReturn = min(currentAmount, amount)
        products[product] = currentAmount - amountToReturn
        return amountToReturn
    }

    override fun toString(): String {
        return products.map { (product, quantity) ->
            "${product.name} (${product.weight}g): $${product.price} $quantity items"
        }.joinToString("\n")
            .let { "** Stock **\n$it" }
    }

    operator fun invoke(func: Stock.() -> Unit) {
        func()
    }

    fun addProduct(name: String, weight: Int, price: Double) = addProduct(Product(name, weight, price))

    infix fun String.x(weight: Int): Pair<String, Int> {
        return this to weight
    }

    infix fun Pair<String, Int>.x(price: Double) {
        repeat(countProduct) {
            addProduct(first, second, price)
        }
        countProduct = 1
    }

    infix fun Int.x(name: String): String {
        countProduct = this
        return name
    }
}

fun main() {
    "Kotlin"("forever")
    Server {
        start()
        loadOs()
        login()
    }

    val stock = Stock()

    stock.addProduct(Product("bread", 200, 30.0))
    stock.addProduct(Product("bread", 200, 30.0))
    stock.addProduct(Product("bread", 200, 30.0))
    stock.addProduct(Product("bread", 250, 38.0))
    stock.addProduct(Product("apple", 1000, 200.0))

    val stock1 = Stock()
    stock1 {
        3 x "bread" x 200 x 30.0
        "bread" x 250 x 38.0
        "apple" x 1000 x 200.0
    }
    println(stock1.toString())
}