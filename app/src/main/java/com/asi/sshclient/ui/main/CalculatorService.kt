package com.asi.sshclient.ui.main

import java.lang.NumberFormatException
import java.math.BigDecimal
import java.math.RoundingMode
import kotlin.math.pow
import kotlin.math.round
import kotlin.math.sqrt

class CalculatorService {
    private var presentValue = ""
    private var previousValue = ""
    private var operation  = ""
    private var inLineHistory = ""
    private var history = ""

    fun getHistory() = history
    fun getInLineHistory() = inLineHistory

    fun clear() {
        if(presentValue.isBlank()) {
            if(operation.isNotBlank()) {
                inLineHistory = when(operation) {
                    "√" -> {
                        operation = ""
                        inLineHistory.drop(1)
                    }
                    else -> {
                        presentValue = previousValue
                        operation = ""
                        inLineHistory.dropLast(3)
                    }

                }
                previousValue = ""

            }
            //everything is blank
        } else {
            presentValue = presentValue.dropLast(1)
            inLineHistory = inLineHistory.dropLast(1)
        }
    }

    fun clearAll() {
        presentValue = ""
        previousValue = ""
        inLineHistory = ""
        operation = ""
    }

    fun writeDigit(digit : Int) {
        if(presentValue == "0") {
            presentValue = digit.toString()
            inLineHistory = inLineHistory.dropLast(1) + digit
        } else {
            presentValue += digit
            inLineHistory += digit
        }
    }

    fun writeDecimalPoint() {
        if(presentValue.contains("."))
            return

        if(presentValue.isBlank()) {
            presentValue = "0"
            inLineHistory ="0"
        }

        if(presentValue.last() == '.') return

        presentValue += "."
        inLineHistory += "."
    }

    fun addOperator(operation : String) {
        if(presentValue.isNotBlank() && previousValue.isNotBlank()) return

        if(presentValue.isBlank())  {
            if (operation == "-") {
                presentValue = "-"
                inLineHistory += "-"
            }
            return
        }

        if(presentValue.last() == '.') {
            presentValue += "0"
            inLineHistory += "0"
        }

        when(operation) {
            "√" -> {
                if(this.operation == operation) {
                    inLineHistory = inLineHistory.drop(1)
                    this.operation = ""
                } else {
                    inLineHistory = "√$inLineHistory"
                    this.operation = operation
                }
            }
            else -> {
                if(this.operation == "√") return

                previousValue = presentValue
                presentValue = ""
                inLineHistory += " $operation "
                this.operation = operation
            }
        }
    }

    fun calculateResult() {
        var result = ""
        if(operation.isBlank()) return

        try {
            if (presentValue.isNotBlank()) presentValue.toDouble()
            if (previousValue.isNotBlank()) previousValue.toDouble()
        } catch (e: NumberFormatException) {
            return
        }

        if(previousValue.isNotBlank() && previousValue.last() == 'E') {
            previousValue = previousValue.dropLast(1)
            inLineHistory = inLineHistory.replace("E", "")
        } else if(presentValue.last() == 'E') {
            presentValue = presentValue.dropLast(1)
            inLineHistory = inLineHistory.replace("E", "")
        }

        when(operation) {
            "+" -> result = (BigDecimal(previousValue) + BigDecimal(presentValue)).
                toDouble().toString()
            "-" -> result = (BigDecimal(previousValue) - BigDecimal(presentValue)).
                toDouble().toString()
            "*" -> result = (BigDecimal(previousValue) * BigDecimal(presentValue)).
                toDouble().toString()
            "/" -> {
                if(presentValue.toDouble() == 0.0) {
                    updateResults("NaN", "")
                    return
                }
                result = (BigDecimal(previousValue).
                    divide(BigDecimal(presentValue), 10, RoundingMode.HALF_UP)).
                    toDouble().toString()
            }
            "^" -> result = previousValue.toDouble().pow(presentValue.toDouble()).toString()
            "√" -> {
                val temp = presentValue.toDouble()
                if(temp < 0.0) {
                    updateResults("NaN", "")
                    return
                }


                result = sqrt(temp).toString()
            }
        }

        val tmp = result.toDouble()
        when {
            tmp.isInfinite() -> updateResults("Inf", "")
            tmp == round(tmp) -> {
                if(tmp > Int.MAX_VALUE || tmp < Int.MIN_VALUE)
                    updateResults(tmp.toString())
                else
                    updateResults(tmp.toInt().toString())
            }

            else -> updateResults(result)
        }
    }

    private fun updateResults(result: String, presentValue : String = result) {
        history += "\n$inLineHistory = $result\n"
        inLineHistory = presentValue
        this.presentValue = presentValue
        previousValue = ""
        operation = ""
    }
}
