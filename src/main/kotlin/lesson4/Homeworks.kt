package ru.stimmax.lesson4

import javax.print.attribute.standard.MediaSize

//Перегрузка операторов
//Есть класс Inventory, внутри которого хранится список строк items.
//Перегрузи оператор + чтобы добавлять новые элементы в список.
//Перегрузи оператор [ ], чтобы получать предмет по индексу.
//Перегрузи оператор in, чтобы проверять вхождение строки в список items.

class Inventory(
    val items: MutableList<String> = mutableListOf()
) {

    operator fun plus(other: String): Inventory {
        items.add(other)
        return this
    }

    operator fun get(index: Int): String {
        return items[index]
    }

    operator fun contains(string: String): Boolean {
        return items.contains(string)
    }
}

//Инверсия состояния (!)
//Есть класс Toggle с полем enabled: Boolean.
//Перегрузи оператор !, чтобы он возвращал новый объект с противоположным состоянием.

class Toggle(
    val enabled: Boolean
) {

    operator fun not(): Toggle {
        return Toggle(!this.enabled)
    }

    //что бы println вернул не адрес в памяти
    override fun toString(): String {
        return "$enabled"
    }

}

//Умножение значения (*)
//Есть класс Price с полем amount: Int.
//Перегрузи оператор *, чтобы можно было умножать цену на целое число (например, количество товаров).

class Price(
    val amount: Int
) {

    operator fun times(number: Int): Price {
        return Price(this.amount * number)
    }

    //что бы println вернул не адрес в памяти
    override fun toString(): String {
        return "$amount"
    }
}

//Диапазон значений (..)
//Есть класс Step с полем number: Int.
//Перегрузи оператор .., чтобы можно было создавать диапазон шагов между двумя объектами Step.
//Сделай возможной проверку: входит ли один Step в диапазон шагов с помощью оператора in.
//Обрати внимание, что это обратная операция и нужно расширять класс IntRange для проверки вхождения в него Step.

class Step(
    val number: Int
) {

    operator fun rangeTo(step: Step): IntRange {
        return IntRange(this.number, step.number)
    }

    //что бы println вернул не адрес в памяти
    override fun toString(): String {
        return "$number"
    }

}

//число входит в диапазон если оно больше или равно началу и меньше или равно концу
//расширяя IntRange, необходимо вынести его за пределы класса, что бы не привязывать его как метод класса
operator fun IntRange.contains(step: Step): Boolean {
    return step.number >= this.first && step.number <= this.last
}

//Последовательное объединение (+)
//Есть класс Log с полем entries: List<String>.
//Перегрузи оператор +, чтобы при сложении логов записи объединялись в один лог.

class Log(
    val entries: List<String> = listOf()
) {

    operator fun plus(other: Log): Log{
        val sumLog = this.entries + other.entries
        return Log(sumLog)
    }

    //что бы println вернул не адрес в памяти
    override fun toString(): String {
        return "$entries"
    }

}

fun main() {
    val inventory = Inventory()
    val result = inventory + "sword" + "SWAGA"

    println(result.items)
    println(inventory[0])
    println("swaga" in inventory)

    val toggle = Toggle(false)
    println(!toggle)

    println(Price(10)*2)

    val step1 = Step(1)
    val step10 = Step(10)
    println(step1..step10)
    println((Step(2) in step1..step10))

    val log1 = Log(listOf("1","2"))
    val log2 = Log(listOf("3","4"))
    println(log2 + log1)


}