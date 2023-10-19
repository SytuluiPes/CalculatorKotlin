package com.example.calculatorisworking

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Log.*
import android.widget.Button
import android.widget.TextView
import kotlin.math.roundToInt
import androidx.lifecycle.findViewTreeViewModelStoreOwner
import com.google.android.material.button.MaterialButton
import java.lang.Exception
import kotlin.math.roundToLong


object FunResult {
    var number1: Double? = null
    var number2: Double? = null
    var answer: Double = 0.0
    var saved_value : Double? = null
}

enum class Buttons{
    Plus, Minus, Division, Multiply, Equility,
}

object FunAction {
    var actionflag: Boolean = false
    var btn_name: String = "btn_quality"
    var prev_btn_name : String = ""
    var input_text : Boolean = false
}
class MainActivity() : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //
        setContentView(R.layout.activity_main)
        val btn_0 : Button = findViewById(R.id.btn_0)
        btn_0.setOnClickListener {setText("0")}
        val btn_1 : Button = findViewById(R.id.btn_1)
        btn_1.setOnClickListener {setText("1")}
        val btn_2 : Button = findViewById(R.id.btn_2)
        btn_2.setOnClickListener {setText("2")}
        val btn_3 : Button = findViewById(R.id.btn_3)
        btn_3.setOnClickListener {setText("3")}
        val btn_4 : Button = findViewById(R.id.btn_4)
        btn_4.setOnClickListener {setText("4")}
        val btn_5 : Button = findViewById(R.id.btn_5)
        btn_5.setOnClickListener {setText("5")}
        val btn_6 : Button = findViewById(R.id.btn_6)
        btn_6.setOnClickListener {setText("6")}
        val btn_7 : Button = findViewById(R.id.btn_7)
        btn_7.setOnClickListener {setText("7")}
        val btn_8 : Button = findViewById(R.id.btn_8)
        btn_8.setOnClickListener {setText("8")}
        val btn_9 : Button = findViewById(R.id.btn_9)
        btn_9.setOnClickListener {setText("9")}
        val btn_equlity : Button = findViewById(R.id.btn_equlity)
        btn_equlity.setOnClickListener { resultView() }
        val btn_C : MaterialButton = findViewById(R.id.btn_C)
        btn_C.setOnClickListener {delText()}
        val btn_percent : Button = findViewById(R.id.btn_percent)
        btn_percent.setOnClickListener { setPercent() }
        val btn_plus_minus : Button = findViewById(R.id.btn_plus_minus)
        btn_plus_minus.setOnClickListener { setChar() }
        val btn_point : Button = findViewById(R.id.btn_point)
        btn_point.setOnClickListener { setPoint() }
        val btn_plus : Button = findViewById(R.id.btn_plus)
        btn_plus.setOnClickListener { action("btn_plus") }
        val btn_minus : Button = findViewById(R.id.btn_minus)
        btn_minus.setOnClickListener { action("btn_minus") }
        val btn_division : Button = findViewById(R.id.btn_division)
        btn_division.setOnClickListener { action("btn_division") }
        val btn_multiply : Button = findViewById(R.id.btn_multiply)
        btn_multiply.setOnClickListener { action("btn_multiply") }
        delText()
    }
    fun setText(str: String) {
        test("setText - START")
        val result_text: TextView = findViewById(R.id.result_text)
        if (result_text.text.toString() == "0" && str == "0") return Unit
        if (!FunAction.input_text && !FunAction.actionflag) {
            result_text.text = str
            FunAction.input_text = true
            FunResult.number1 = result_text.text.toString().toDouble()
            result_text.textSize = 80F
        }
        else if (FunAction.input_text && !FunAction.actionflag) {
            result_text.append(str)
            FunResult.number1 = result_text.text.toString().toDouble()
            result_text.textSize = 80F
        }
        else if (!FunAction.input_text && FunAction.actionflag) {
            result_text.text = str
            FunAction.input_text = true
            FunResult.number2 = result_text.text.toString().toDouble()
        }
        else {
            result_text.append(str)
            FunResult.number2 = result_text.text.toString().toDouble()
        }
        resizeText()
        if (result_text.text.toString() != "0") {
            val btn_C : Button = findViewById(R.id.btn_C)
            btn_C.text = "C"
        }
        test("setText - END")
    }

    fun setPercent(){
        test("setPercent - START")
        val result_text: TextView = findViewById(R.id.result_text)
        var result_division_hundred : Double = result_text.text.toString().toDouble()
        result_division_hundred /= 100
        result_division_hundred = (result_division_hundred * 10000000.0).roundToLong() / 10000000.0
        if (result_division_hundred == result_division_hundred.toLong().toDouble())
            result_text.text = result_division_hundred.toLong().toString()
        else
            result_text.text = result_division_hundred.toString()
        if (!FunAction.actionflag)
            FunResult.number1 = result_text.text.toString().toDouble()
        else if (FunResult.number2 == null)
            FunResult.number1 = result_text.text.toString().toDouble()
        else
            FunResult.number2 = result_text.text.toString().toDouble()
        test("setPercent - END")
    }

    fun setChar(){
        test("setChar - START")
        val result_text: TextView = findViewById(R.id.result_text)
        var result_text_setChar = ""
        var textsize = 0f
        if (result_text.text.toString().indexOf("-") == -1){
            result_text.textSize = textsize
            result_text_setChar = "-${result_text.text}"
            result_text.text = result_text_setChar
        }
        else{
            result_text_setChar = result_text.text.toString().replace("-","")
            result_text.text = result_text_setChar
        }
        resizeText()
        if (!FunAction.actionflag)
            FunResult.number1 = result_text.text.toString().toDouble()
        else if (FunResult.number2 == null)
            FunResult.number1 = result_text.text.toString().toDouble()
        else
            FunResult.number2 = result_text.text.toString().toDouble()
        test("setChar - END")
    }

    fun setPoint(){
        test("setPoint - START")
        val result_text: TextView = findViewById(R.id.result_text)
        var result_text_setPoint = ""
        if (result_text.text.toString().indexOf(".") == -1) {
            result_text_setPoint = "${result_text.text}."
            result_text.text = result_text_setPoint
        }
        FunAction.input_text = true
        if (!FunAction.actionflag)
            FunResult.number1 = result_text.text.toString().toDouble()
        else if (FunResult.number2 == null)
            FunResult.number1 = result_text.text.toString().toDouble()
        else
            FunResult.number2 = result_text.text.toString().toDouble()
        test("setPoint - END")
    }
    fun resultView() {
        test("resultView - START")
        if (FunResult.number1 != null && (FunAction.btn_name != "btn_quality" ||
                    FunAction.prev_btn_name != "btn_quality")) {
            var btn: Button = findViewById(R.id.btn_equlity)
            val result_text: TextView = findViewById(R.id.result_text)
            if (FunResult.saved_value == null && FunResult.number2 == null)
                FunResult.saved_value = FunResult.number1
            if ((FunResult.number1 == 0.0 || FunResult.number2 == 0.0) && FunAction.btn_name == "btn_division")
                result_text.text = "0"
            else {
                if (FunResult.number2 != null) {
                    when (if (FunAction.btn_name != "btn_quality") FunAction.btn_name
                    else FunAction.prev_btn_name) {
                        "btn_plus" -> {
                            FunResult.answer = FunResult.number1?.plus(FunResult.number2!!)!!
                        }

                        "btn_minus" -> {
                            FunResult.answer = FunResult.number1?.minus(FunResult.number2!!)!!
                        }

                        "btn_division" -> {
                            FunResult.answer = FunResult.number1?.div(FunResult.number2!!)!!
                        }

                        "btn_multiply" -> {
                            FunResult.answer = FunResult.number1?.times(FunResult.number2!!)!!
                        }
                    }
                } else {
                    when (if (FunAction.btn_name != "btn_quality") FunAction.btn_name else FunAction.prev_btn_name) {
                        "btn_plus" -> {
                            FunResult.answer = FunResult.number1?.plus(FunResult.saved_value!!)!!
                        }

                        "btn_minus" -> {
                            FunResult.answer = FunResult.number1?.minus(FunResult.saved_value!!)!!
                        }

                        "btn_division" -> {
                            FunResult.answer = FunResult.number1?.div(FunResult.saved_value!!)!!
                        }

                        "btn_multiply" -> {
                            FunResult.answer = FunResult.number1?.times(FunResult.saved_value!!)!!
                        }
                    }
                }
                if (FunResult.answer.toLong().toDouble() == FunResult.answer) {
                    i(
                        "", "FunResult.answer.toLong().toDouble() = " +
                                "${FunResult.answer.toLong().toDouble()}"
                    )
                    i("", "FunResult.answer = ${FunResult.answer}")
                    result_text.text = FunResult.answer.toLong().toString()
                } else {
                    FunResult.answer = (FunResult.answer * 10000000.0).toLong() / 10000000.0
                    result_text.text = FunResult.answer.toString()
                }
            }
            when (if (FunAction.btn_name != "btn_quality") FunAction.btn_name else FunAction.prev_btn_name) {
                "btn_plus" -> {
                    btn = findViewById(R.id.btn_plus)
                }

                "btn_minus" -> {
                    btn = findViewById(R.id.btn_minus)
                }

                "btn_division" -> {
                    btn = findViewById(R.id.btn_division)
                }

                "btn_multiply" -> {
                    btn = findViewById(R.id.btn_multiply)
                }
            }
            btn.setBackgroundColor(Color.parseColor("#ffaf25"))
            btn.setTextColor(Color.parseColor("#ffffff"))
            FunAction.actionflag = false
            FunAction.input_text = false
            if (FunResult.number2 != null) FunResult.saved_value = FunResult.number2
            FunResult.number2 = null
            FunResult.number1 = FunResult.answer
            if (FunAction.btn_name != "btn_quality") FunAction.prev_btn_name = FunAction.btn_name
            FunAction.btn_name = "btn_quality"
        }
        resizeText()
        test("resultView - END")
    }

    fun delText() {
        test("delText - START")
        val result_text: TextView = findViewById(R.id.result_text)
        var btn : Button = findViewById(R.id.btn_equlity)
        val btn_C : Button = findViewById(R.id.btn_C)
        if (result_text.text.toString() != "0"){
            result_text.text = "0"
            FunResult.saved_value = null
            FunAction.input_text = false
            btn_C.text = "AC"
            result_text.textSize = 80F
        }
        else if (FunAction.actionflag == true){
            when (FunAction.btn_name){
                "btn_plus" -> {
                    btn = findViewById(R.id.btn_plus)
                }
                "btn_minus" -> {
                    btn = findViewById(R.id.btn_minus)
                }
                "btn_division" -> {
                    btn = findViewById(R.id.btn_division)
                }
                "btn_multiply" -> {
                    btn = findViewById(R.id.btn_multiply)
                }
            }
            btn.setBackgroundColor(Color.parseColor("#ffaf25"))
            btn.setTextColor(Color.parseColor("#ffffff"))
            FunAction.btn_name = "btn_equlity"
            FunAction.actionflag = false
            FunResult.number1 = null
            FunResult.number2 = null
            FunResult.answer = 0.0
            FunAction.prev_btn_name = ""
            FunResult.saved_value = null
        }
        test("delText - END")
    }
    fun action(btn_name : String) {
        test("action - START")
        var btn : Button = findViewById(R.id.btn_equlity)
        if (FunAction.btn_name != btn_name){
            when(FunAction.btn_name){
                "btn_plus" -> {btn = findViewById(R.id.btn_plus)}
                "btn_minus" -> {btn = findViewById(R.id.btn_minus)}
                "btn_division" -> {btn = findViewById(R.id.btn_division)}
                "btn_multiply" -> {btn = findViewById(R.id.btn_multiply)}
            }
            btn.setBackgroundColor(Color.parseColor("#ffaf25"))
            btn.setTextColor(Color.parseColor("#ffffff"))
            when(btn_name) {
                "btn_plus" -> {btn = findViewById(R.id.btn_plus)}
                "btn_minus" -> {btn = findViewById(R.id.btn_minus)}
                "btn_division" -> {btn = findViewById(R.id.btn_division)}
                "btn_multiply" -> {btn = findViewById(R.id.btn_multiply)}
            }
            btn.setBackgroundColor(Color.parseColor("#ffffff"))
            btn.setTextColor(Color.parseColor("#ffaf25"))
            FunAction.btn_name = btn_name
            FunAction.input_text = false
            FunAction.actionflag = true
        }
        test("action - END")
    }
    private fun resizeText(){
        val result_text : TextView = findViewById(R.id.result_text)
        var textsize = result_text.textSize
        val paint = result_text.paint
        val textViewWidth = result_text.width
        var textWidth = paint.measureText(result_text.text.toString())
        i("", """
            |textsize = $textsize
            |textWidth = $textWidth
            |textViewWidth = $textViewWidth
        """.trimMargin())
        while (textWidth >= textViewWidth) {
            textsize -= 3
            result_text.textSize = textsize
            textWidth = paint.measureText(result_text.text.toString())
        }
    }
    private fun test(text : String = "None"){
        val result_text: TextView = findViewById(R.id.result_text)
        i(text,"""
            |FunResult.number1 = ${FunResult.number1}
            |FunResult.number2 = ${FunResult.number2}
            |FunResult.answer = ${FunResult.answer}
            |FunResult.saved_value = ${FunResult.saved_value}
            |result_text.text = ${result_text.text}
        """.trimMargin())
    }
}
// Проблема в работе с LONG переменными
// Проблема деление на 0, с использование строки "Ошибка"
// Проблема с изменение размера окна вывода ответа