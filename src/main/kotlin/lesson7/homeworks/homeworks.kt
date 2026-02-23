package ru.stimmax.lesson7.homeworks

import org.hamcrest.Matchers.*
import org.hamcrest.TypeSafeDiagnosingMatcher
import org.hamcrest.Description
import org.hamcrest.Matcher

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
            mismatchDescription.appendText("number of parties ").
            appendValue(item.numberSides)
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

    fun build(): Matcher<GeomFigure> {
        return allOf(matcherFigureBuilder)
    }

}






















