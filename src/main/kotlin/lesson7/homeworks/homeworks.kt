package ru.stimmax.lesson7.homeworks

import org.hamcrest.Matchers.*
import org.hamcrest.TypeSafeDiagnosingMatcher
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.MatcherAssert.assertThat

//Проверка длины стороны в заданном диапазоне (например, от 0.1 до 100.0).
//Проверка количества углов:
//Для фигур с 3 и более сторонами количество углов совпадает с количеством сторон.
//Для фигур с 1 или 2 сторонами (линий) углы отсутствуют (значение углов = 0).
//Проверка на чётное количество сторон.
//Проверка цвета фигуры.
//Проверка на наличие отрицательной длины стороны (недопустимо).
//Проверка на наличие отрицательного количества сторон (недопустимо).

enum class Color {
    RED,
    BLUE,
    GREEN,
    YELLOW,
    BLACK,
    WHITE
}

data class GeomFigure(
    val sideLength: Float,
    val numberSides: Int,
    val color: Color
)

class FromSideLengthMatcher(
    private val expectedFromLength: Float
) : TypeSafeDiagnosingMatcher<GeomFigure>() {

    override fun matchesSafely(
        item: GeomFigure,
        mismatchDescription: Description
    ): Boolean {
        if (item.sideLength < expectedFromLength) {
            mismatchDescription.appendText("side length was ")
                .appendValue(item.sideLength)
            return false
        }
        return true
    }

    override fun describeTo(description: Description) {
        description.appendText("side length from ")
            .appendValue(expectedFromLength)
    }
}

class ToSideLengthMatcher(
    private val expectedToLength: Float
) : TypeSafeDiagnosingMatcher<GeomFigure>() {

    override fun matchesSafely(
        item: GeomFigure,
        mismatchDescription: Description
    ): Boolean {
        if (item.sideLength > expectedToLength) {
            mismatchDescription.appendText("side length was ")
                .appendValue(item.sideLength)
            return false
        }
        return true
    }

    override fun describeTo(description: Description) {
        description.appendText("side length to ")
            .appendValue(expectedToLength)
    }
}
////Проверка количества углов:
////Для фигур с 3 и более сторонами количество углов совпадает с количеством сторон.
////Для фигур с 1 или 2 сторонами (линий) углы отсутствуют (значение углов = 0).

class NumberOfAnglesMatcher(
    private val expectedAngles: Int
) : TypeSafeDiagnosingMatcher<GeomFigure>() {

    override fun matchesSafely(
        item: GeomFigure,
        mismatchDescription: Description
    ): Boolean {
        if (item.numberSides >= 3) {
            if (expectedAngles != item.numberSides) {
                mismatchDescription.appendText("side length was ")
                    .appendValue(item.numberSides)
                return false
            }
        } else {
            if (expectedAngles != 0) {
                mismatchDescription.appendText("side length was ")
                    .appendValue(0)
                return false
            }
        }
        return true
    }

    override fun describeTo(description: Description) {
        description.appendText("side number to ")
            .appendValue(expectedAngles)

    }
}

////Проверка на чётное количество сторон.
class EvenNumberSidesMatcher() : TypeSafeDiagnosingMatcher<GeomFigure>() {

    override fun matchesSafely(
        item: GeomFigure,
        mismatchDescription: Description
    ): Boolean {
        if (item.numberSides % 2 != 0) {
            mismatchDescription.appendText("number of parties ").appendValue(item.numberSides)
            return false
        }
        return true
    }

    override fun describeTo(description: Description) {
        description.appendText("the number of sides is odd")

    }
}

////Проверка цвета фигуры.
class ColorMatcher(
    private val expectedColor: Color
) : TypeSafeDiagnosingMatcher<GeomFigure>() {

    override fun matchesSafely(
        item: GeomFigure,
        mismatchDescription: Description
    ): Boolean {
        if (expectedColor != item.color) {
            mismatchDescription.appendText("color was ")
                .appendValue(item.color)
            return false
        }
        return true
    }

    override fun describeTo(description: Description) {
        description.appendText("were looking for color ")
            .appendValue(expectedColor)
    }
}

////Проверка на наличие отрицательной длины стороны (недопустимо).
class NegativeLengthMatcher() : TypeSafeDiagnosingMatcher<GeomFigure>() {
    override fun matchesSafely(
        item: GeomFigure,
        mismatchDescription: Description
    ): Boolean {
        if (item.sideLength < 0) {
            mismatchDescription.appendText("negative length is specified ")
                .appendValue(item.sideLength)
            return false
        }
        return true
    }

    override fun describeTo(description: Description) {
        description.appendText("length cannot be negative")
    }
}

////Проверка на наличие отрицательного количества сторон (недопустимо).
class NegativeSideMatcher() : TypeSafeDiagnosingMatcher<GeomFigure>() {
    override fun matchesSafely(
        item: GeomFigure,
        mismatchDescription: Description
    ): Boolean {
        if (item.numberSides < 0) {
            mismatchDescription.appendText("a negative number of sides is specified ")
                .appendValue(item.numberSides)
            return false
        }
        return true
    }

    override fun describeTo(description: Description) {
        description.appendText("the number of sides cannot be negative")
    }
}

class MatcherFigureBuilder() {
    private val matcherFigureBuilder = mutableListOf<Matcher<GeomFigure>>()

    fun fromLength(length: Float) {
        matcherFigureBuilder.add(FromSideLengthMatcher(length))
    }
    fun toLength(length: Float) {
        matcherFigureBuilder.add(ToSideLengthMatcher(length))
    }
    fun quantityAngles(numberSide: Int) {
        matcherFigureBuilder.add(NumberOfAnglesMatcher(numberSide))
    }
    fun even() {
        matcherFigureBuilder.add(EvenNumberSidesMatcher())
    }
    fun withColor(color: Color) {
        matcherFigureBuilder.add(ColorMatcher(color))
    }
    fun negativeLength(){
        matcherFigureBuilder.add(NegativeLengthMatcher())
    }
    fun negativeNumberSide() {
        matcherFigureBuilder.add(NegativeSideMatcher())
    }

    fun build(): Matcher<GeomFigure> {
        return allOf(matcherFigureBuilder)
    }

    fun buildAnyOff(): Matcher<GeomFigure> {
        return anyOf(matcherFigureBuilder)
    }
}

fun filterShapes(
    shapes: List<GeomFigure>,
    useAnyOff: Boolean,
    builder: MatcherFigureBuilder.() -> Unit
): List<GeomFigure> {
    if (!useAnyOff) {
        val matcherFigureBuilder = MatcherFigureBuilder()

        builder(matcherFigureBuilder)

        val matherBuild = matcherFigureBuilder.build()
        return shapes.filter { matherBuild.matches(it) }
    } else {
        val matcherFigureBuilder = MatcherFigureBuilder()

        builder(matcherFigureBuilder)

        val matherBuild = matcherFigureBuilder.buildAnyOff()
        return shapes.filter { matherBuild.matches(it) }
    }
}

fun main() {

    val shapes = listOf(
        GeomFigure(10f, 3, Color.RED),
        GeomFigure(5f, 4, Color.BLUE),
        GeomFigure(7f, 2, Color.GREEN),
        GeomFigure(0.5f, 1, Color.YELLOW),
        GeomFigure(-3f, 5, Color.BLACK),
        GeomFigure(8f, -2, Color.WHITE),
        GeomFigure(12f, 6, Color.RED),
        GeomFigure(15f, 8, Color.BLUE),
        GeomFigure(20f, 4, Color.GREEN),
        GeomFigure(9f, 5, Color.YELLOW),
        GeomFigure(2f, 3, Color.BLACK),
        GeomFigure(11f, 7, Color.WHITE),
        GeomFigure(6f, 10, Color.RED),
        GeomFigure(3f, 2, Color.BLUE),
        GeomFigure(4f, 1, Color.GREEN),
        GeomFigure(25f, 12, Color.YELLOW),
        GeomFigure(30f, 14, Color.BLACK),
        GeomFigure(35f, 16, Color.WHITE),
        GeomFigure(40f, 18, Color.RED),
        GeomFigure(50f, 20, Color.BLUE)
    )
    val result = filterShapes(shapes, false) {
        withColor(Color.RED)
        even()
        quantityAngles(4)
    }

    val result1 = filterShapes(shapes, false) {
        fromLength(2f)
        toLength(11f)
        even()
        withColor(Color.BLUE)
    }

    val result2 = filterShapes(shapes, false) {
        fromLength(2f)
        toLength(19f)
        quantityAngles(8)
    }

    val result3 = filterShapes(shapes, true) {
        withColor(Color.BLACK)
        even()
    }

    try {
        assertThat(shapes[5], NegativeSideMatcher())
    } catch (e: AssertionError) {
        println(e.message)
    }

    try {
        assertThat(shapes[4], NegativeLengthMatcher())
    } catch (e: AssertionError) {
        println(e.message)
    }

    println(result)
    println(result1)
    println(result2)
    println(result3)
}






















