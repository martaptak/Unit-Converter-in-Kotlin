package converter

import java.util.*

enum class Units(val type: Int, val nameSingular: String, val namePlural: String, val factor: Double) {
    METER(1, "meter", "meters", 1.0),
    KILOMETER(1, "kilometer", "kilometers", 1000.0),
    CENTIMETER(1, "centimeter", "centimeters", 0.0100),
    MILLIMETER(1, "millimeter", "millimeters", 0.001),
    MILE(1, "mile", "miles", 1609.35),
    YARD(1, "yard", "yards", 0.9144),
    FOOT(1, "foot", "feet", 0.3048),
    INCH(1, "inch", "inches", 0.0254),
    GRAM(2, "gram", "grams", 1.0),
    KILOGRAM(2, "kilogram", "kilograms", 1000.0),
    MILLIGRAM(2, "milligram", "milligrams", 0.001),
    POUND(2, "pound", "pounds", 453.592),
    OUNCE(2, "ounce", "ounces", 28.3495),
    CELSIUS(3, "degree Celsius", "degrees Celsius", 1.0),
    FAHRENHEIT(3, "degree Fahrenheit", "degrees Fahrenheit", 1.0),
    KELVIN(3, "Kelvin", "Kelvins", 1.0),
    NULL(0, "???", "???", 0.0);

    companion object {
        fun findUnit(unit: String): Units {
            return when (unit) {
                "m", "meter", "meters" -> METER
                "km", "kilometer", "kilometers" -> KILOMETER
                "cm", "centimeter", "centimeters" -> CENTIMETER
                "mm", "millimeter", "millimeters" -> MILLIMETER
                "mi", "mile", "miles" -> MILE
                "yd", "yard", "yards" -> YARD
                "ft", "foot", "feet" -> FOOT
                "in", "inch", "inches" -> INCH
                "g", "gram", "grams" -> GRAM
                "kg", "kilogram", "kilograms" -> KILOGRAM
                "mg", "milligram", "milligrams" -> MILLIGRAM
                "lb", "pound", "pounds" -> POUND
                "oz", "ounce", "ounces" -> OUNCE
                "degree celsius", "degrees celsius",  "celsius", "dc", "c" -> CELSIUS
                "degree fahrenheit", "degrees fahrenheit", "fahrenheit", "df", "f" -> FAHRENHEIT
                "kelvin", "kelvins", "k" -> KELVIN
                else -> NULL
            }
        }

        fun convertFromInput(measurement: Double, unit: Units): Double {
            return measurement * unit.factor
        }

        fun convertToOutput(measurement: Double, unit: Units): Double {
            return measurement / unit.factor
        }

        fun convertToCelsius(amount: Double, unit: Units): Double {
            return when (unit) {
                FAHRENHEIT -> (amount - 32) * 5 / 9
                KELVIN -> amount - 273.15
                CELSIUS -> amount * 1.0
                else -> 0.0
            }
        }

        fun convertTempToOutput(amount: Double, unit: Units): Double {
            return when (unit) {
                FAHRENHEIT -> amount * 9 / 5 + 32
                KELVIN -> amount + 273.15
                CELSIUS -> amount * 1.0
                else -> 0.0
            }
        }


    }

}


fun main() {


    val scanner = Scanner(System.`in`)

    do {
        println("Enter what you want to convert (or exit):")

        val statement = scanner.nextLine()

        if (statement != "exit") {
            val words = statement.trim().toLowerCase().split(" ")

            val amount: Double = words[0].toDouble()
            val unitFirst = if (words[1] == "degree" || words[1] =="degrees") words[1] + " " + words[2] else words[1]


            val unitLast = if (words[words.size -2] == "degree" || words[words.size -2] =="degrees") words[words.size
                    -2] + " " +
                    words[words.size -1]
            else
                words[words.size -1]


            val inputUnit = Units.findUnit(unitFirst)
            val outputUnit = Units.findUnit(unitLast)

            var amountConvertedToOutput: Double


            if (inputUnit.type != outputUnit.type || inputUnit == Units.NULL || outputUnit == Units.NULL) {
                println("Conversion from ${inputUnit.namePlural} to ${outputUnit.namePlural} is impossible")
            } else if ((inputUnit.type == 1 || inputUnit.type == 2) && amount < 0) {
                if (inputUnit.type == 1)
                    println("Length shouldn't be negative")
                else
                    println("Weight shouldn't be negative")
            } else {
                amountConvertedToOutput = if (inputUnit.type == 1 || inputUnit.type == 2) {

                    val amountConvertedToMain = Units.convertFromInput(amount, inputUnit)
                    Units.convertToOutput(amountConvertedToMain, outputUnit)

                } else {
                    val convertToCelsius = Units.convertToCelsius(amount, inputUnit)
                    Units.convertTempToOutput(convertToCelsius, outputUnit)

                }


                var answer: String = amount.toString()

                answer += if (amount == 1.0) " ${inputUnit.nameSingular}"
                else " ${inputUnit.namePlural}"

                answer += if (amountConvertedToOutput == 1.0) " is $amountConvertedToOutput " + outputUnit.nameSingular
                else " is $amountConvertedToOutput " + outputUnit.namePlural

                println(answer)
            }


        }

    } while (statement != "exit")


}
