package ru.stimmax.lesson4

class Distance(
    val meters: Int
) {

    operator fun plus(other: Distance): Distance {
        val sum = meters + other.meters
        return Distance(meters = sum)
    }
}

class Score(
    private var points: Int
) {
    operator fun plusAssign(number: Int) {
        points += number
    }

    fun getPoints() = points
}

class Level(
    val number: Int
) {
    operator fun compareTo(other: Level): Int {
        return number.compareTo(other.number)
    }
}

class User(
    val id: Int,
    val name: String
) {
    override operator fun equals(other: Any?): Boolean {
        if (other !is User) return false
        return id == other.id
    }
}

infix fun Int.with(number: Int): Int {
    return "$this$number".toInt()
}

infix fun Int.withOut(number: Int): Int {
    return "$this".replace("$number", "").toInt()
}

fun main() {
    val distance = Distance(20)
    val otherDistance = Distance(10)
    val sum = distance + otherDistance
    println(sum)

    val score = Score(10)
    println(score.getPoints())

    val uran = Level(2)
    val cezium = Level(4)
    println(uran < cezium)

    val user1 = User(1, "Mike")
    val user2 = User(1, "Nike")
    println(user1 == user2)

    println(5 with 20)
    println(234 withOut 3)
}

