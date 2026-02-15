package ru.stimmax.lesson5

import java.time.LocalTime
import java.time.format.DateTimeFormatter
import ru.stimmax.lesson5.Days.*



//Для создания диапазона через две точки переопредели оператор rangeTo
//Для передачи нужного дня недели используй внешнюю относительно метода
// переменную var в которую нужно сохранить используемый в текущем блоке
// день недели и потом обнулить по завершении блока.

enum class Days {
    MONDAY,
    TUESDAY,
    WEDNESDAY,
    THURSDAY,
    FRIDAY,
    SATURDAY,
    SUNDAY
}

data class ScheduleEntity(
    val lesson: String,
    val startTime: LocalTime,
    val endTime: LocalTime
)



class Schedule {

    private val scheduleOfWeek = mutableMapOf<Days, MutableList<ScheduleEntity>>()
    private val timeFormatter = DateTimeFormatter.ofPattern("HH:mm")
    private var dayWeek: Days? = null

    fun addSchedule(day: Days, scheduleEntity: ScheduleEntity) {
        scheduleOfWeek.getOrPut(day) { mutableListOf() }
            .add(scheduleEntity)
    }

    override fun toString(): String {
        return scheduleOfWeek.toSortedMap()
            .map { (day, list) ->
                list.sortedBy { it.startTime }
                    .joinToString("\n") {
                        "%-15s${it.startTime.format(timeFormatter)} - ${
                            it.endTime.format(
                                timeFormatter
                            )
                        }".format("\t${it.lesson}:")
                    }.let {
                        "${day.name.lowercase().replaceFirstChar { day.name[0].uppercase() }}:\n$it"
                    }
            }.joinToString("\n\n")
    }

    operator fun invoke(func: Schedule.() -> Unit) {
        func()
    }

    operator fun String.rangeTo(endTime: String): Pair<String, String> {
        return Pair(this, endTime)
    }

    //меняем дни для разных функций
    fun days(days: Days,add: Schedule.()-> Unit) {
        dayWeek = days
        add()
        dayWeek = null
    }

    fun monday(add: Schedule.() -> Unit) = days(MONDAY, add)
    fun tuesday(add: Schedule.() -> Unit) = days(TUESDAY, add)
    fun wednesday(add: Schedule.() -> Unit) = days(WEDNESDAY, add)
    fun thursday(add: Schedule.() -> Unit) = days(Days.THURSDAY, add)
    fun friday(add: Schedule.() -> Unit) = days(FRIDAY, add)
    fun saturday(add: Schedule.() -> Unit) = days(SATURDAY, add)
    fun sunday(add: Schedule.() -> Unit) = days(SUNDAY, add)


    infix fun Pair<String, String>.schedule(lesson: String) {

        val start = LocalTime.parse(first, timeFormatter)
        val end = LocalTime.parse(second, timeFormatter)

        //проверка на налл
        val daysFilter = dayWeek ?: error("Введите день недели")

        // !! игнорировать проверку на налл
        addSchedule(daysFilter, ScheduleEntity(lesson,start,end))
    }
}





fun main() {

    val schedule = Schedule()

    // Так добавляется расписание без DSL
//    schedule {
//        addSchedule(
//            MONDAY, ScheduleEntity(
//                "Biology",
//                LocalTime.of(10, 30),
//                LocalTime.of(11, 10)
//            )
//        )
//        addSchedule(
//            MONDAY, ScheduleEntity(
//                "Chemistry",
//                LocalTime.of(11, 15),
//                LocalTime.of(11, 55)
//            )
//        )
//    }
//     Так добавляется расписание с использованием DSL
    schedule {

        monday {
            "10:30".."11:10" schedule "Biology"
            "11:15".."11:55" schedule "Chemistry"
            "09:00".."09:40" schedule "Mathematics"
            "09:45".."10:25" schedule "History"
        }

        tuesday {
            "09:00".."09:40" schedule "English"
            "09:45".."10:25" schedule "Geography"
            "11:15".."11:55" schedule "Art"
            "10:30".."11:10" schedule "Physics"
        }

        wednesday {
            "11:15".."11:55" schedule "Biology"
            "09:00".."09:40" schedule "Literature"
            "10:30".."11:10" schedule "History"
            "09:45".."10:25" schedule "Mathematics"
        }

        thursday {
            "11:15".."11:55" schedule "Physics"
            "10:30".."11:10" schedule "Geography"
            "09:00".."09:40" schedule "Chemistry"
            "09:45".."10:25" schedule "English"
        }

        friday {
            "09:45".."10:25" schedule "Literature"
            "11:15".."11:55" schedule "History"
            "09:00".."09:40" schedule "Art"
            "10:30".."11:10" schedule "Mathematics"
        }
    }

    println(schedule.toString())
}
