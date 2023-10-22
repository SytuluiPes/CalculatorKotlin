package com.example.calculatorisworking

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.renderscript.ScriptGroup.Binding
import android.util.Log
import android.util.Log.*
import android.view.LayoutInflater
import android.widget.Button
import android.widget.TextView
import com.example.calculatorisworking.databinding.ActivityMainBinding
import com.google.android.material.button.MaterialButton
import kotlin.math.roundToLong


object FunResult {
    var number1: Double? = null
    var number2: Double? = null
    var answer: Double = 0.0
    var saved_value: Double? = null
}

enum class Buttons {
    Plus, Minus, Division, Multiply, Equility
}

object FunAction {
    var btn_name: Buttons = Buttons.Equility
    var prev_btn_name: Buttons = btn_name
    var isAdd: Boolean = false
}

class MainActivity() : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("CalcActivity", "I AM CREATED: $this")
        //
        binding = ActivityMainBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)
        val btn_0: Button = binding.btn0
        btn_0.setOnClickListener { setText("0") }
        val btn_1: Button = binding.btn1
        btn_1.setOnClickListener { setText("1") }
        val btn_2: Button = binding.btn2
        btn_2.setOnClickListener { setText("2") }
        val btn_3: Button = binding.btn3
        btn_3.setOnClickListener { setText("3") }
        val btn_4: Button = binding.btn4
        btn_4.setOnClickListener { setText("4") }
        val btn_5: Button = binding.btn5
        btn_5.setOnClickListener { setText("5") }
        val btn_6: Button = binding.btn6
        btn_6.setOnClickListener { setText("6") }
        val btn_7: Button = binding.btn7
        btn_7.setOnClickListener { setText("7") }
        val btn_8: Button = binding.btn8
        btn_8.setOnClickListener { setText("8") }
        val btn_9: Button = binding.btn9
        btn_9.setOnClickListener { setText("9") }
        val btn_equlity: Button = binding.btnEqulity
        btn_equlity.setOnClickListener { resultView() }
        val btn_C: MaterialButton = binding.btnC
        btn_C.setOnClickListener { delText() }
        val btn_percent: Button = binding.btnPercent
        btn_percent.setOnClickListener { setPercent() }
        val btn_plus_minus: Button = binding.btnPlusMinus
        btn_plus_minus.setOnClickListener { setChar() }
        val btn_point: Button = binding.btnPoint
        btn_point.setOnClickListener { setPoint() }
        val btn_plus: Button = binding.btnPlus
        btn_plus.setOnClickListener { action(Buttons.Plus) }
        val btn_minus: Button = binding.btnMinus
        btn_minus.setOnClickListener { action(Buttons.Minus) }
        val btn_division: Button = binding.btnDivision
        btn_division.setOnClickListener { action(Buttons.Division) }
        val btn_multiply: Button = binding.btnMultiply
        btn_multiply.setOnClickListener { action(Buttons.Multiply) }
        delText()
    }

    fun setText(str: String) {
        test("setText - START")
        val result_text: TextView = binding.resultText
        if (result_text.text.toString() == "0" && str == "0") return Unit
        when {
            !FunAction.isAdd && FunAction.btn_name == Buttons.Equility -> {
                result_text.text = str
                FunAction.isAdd = true
                FunResult.number1 = result_text.text.toString().toDouble()
                result_text.textSize = 80F
            }

            FunAction.isAdd && FunAction.btn_name == Buttons.Equility -> {
                result_text.append(str)
                FunResult.number1 = result_text.text.toString().toDouble()
                result_text.textSize = 80F
            }

            !FunAction.isAdd && FunAction.btn_name != Buttons.Equility -> {
                val currentButton = getActionButton(FunAction.btn_name)
                setActionButtonState(currentButton, false)
                result_text.text = str
                FunAction.isAdd = true
                FunResult.number2 = result_text.text.toString().toDouble()
            }

            else -> {
                result_text.append(str)
                FunResult.number2 = result_text.text.toString().toDouble()
            }
        }
        resizeText()
        if (result_text.text.toString() != "0") {
            val btn_C: Button = binding.btnC
            btn_C.text = "C"
        }
        test("setText - END")
    }

    fun setActionButtonState(button: Button, isPressed: Boolean) {
        if (isPressed) {
            button.setBackgroundColor(getColor(R.color.action_button_active_background))
            button.setTextColor(getColor(R.color.action_button_active_text))
        } else {
            button.setBackgroundColor(getColor(R.color.action_button_inactive_background))
            button.setTextColor(getColor(R.color.action_button_inactive_text))
        }
    }

    fun getActionButton(id: Buttons): Button {
        return when (id) {
            Buttons.Plus -> {
                binding.btnPlus
            }

            Buttons.Minus -> {
                binding.btnMinus
            }

            Buttons.Division -> {
                binding.btnDivision
            }

            Buttons.Multiply -> {
                binding.btnMultiply
            }

            Buttons.Equility -> {
                binding.btnEqulity
            }
        }
    }

    fun setPercent() {
        test("setPercent - START")
        val result_text: TextView = binding.resultText
        var result_division_hundred: Double = result_text.text.toString().toDouble()
        result_division_hundred /= 100
        result_division_hundred = (result_division_hundred * 10000000.0).roundToLong() / 10000000.0
        if (result_division_hundred == result_division_hundred.toLong().toDouble())
            result_text.text = result_division_hundred.toLong().toString()
        else
            result_text.text = result_division_hundred.toString()
        if (FunAction.btn_name == Buttons.Equility)
            FunResult.number1 = result_text.text.toString().toDouble()
        else if (FunResult.number2 == null)
            FunResult.number1 = result_text.text.toString().toDouble()
        else
            FunResult.number2 = result_text.text.toString().toDouble()
        test("setPercent - END")
    }

    fun setChar() {
        test("setChar - START")
        val result_text: TextView = binding.resultText
        var result_text_setChar = ""
        if (result_text.text.toString().indexOf("-") == -1) {
            result_text_setChar = "-${result_text.text}"
            result_text.text = result_text_setChar
        } else {
            result_text_setChar = result_text.text.toString().replace("-", "")
            result_text.text = result_text_setChar
        }
        resizeText()
        if (FunAction.btn_name == Buttons.Equility)
            FunResult.number1 = result_text.text.toString().toDouble()
        else if (FunResult.number2 == null)
            FunResult.number1 = result_text.text.toString().toDouble()
        else
            FunResult.number2 = result_text.text.toString().toDouble()
        test("setChar - END")
    }

    fun setPoint() {
        test("setPoint - START")
        val result_text: TextView = binding.resultText
        var result_text_setPoint = ""
        if (result_text.text.toString().indexOf(".") == -1) {
            result_text_setPoint = "${result_text.text}."
            result_text.text = result_text_setPoint
        }
        FunAction.isAdd = true
        if (FunAction.btn_name == Buttons.Equility)
            FunResult.number1 = result_text.text.toString().toDouble()
        else if (FunResult.number2 == null)
            FunResult.number1 = result_text.text.toString().toDouble()
        else
            FunResult.number2 = result_text.text.toString().toDouble()
        resizeText()
        test("setPoint - END")
    }

    fun resultView() {
        test("resultView - START")
        if (FunResult.number1 != null && (FunAction.btn_name != Buttons.Equility ||
                    FunAction.prev_btn_name != Buttons.Equility)
        ) {
            val btn: Button
            val result_text: TextView = binding.resultText
            if (FunResult.saved_value == null && FunResult.number2 == null)
                FunResult.saved_value = FunResult.number1
            if ((FunResult.number1 == 0.0 || FunResult.number2 == 0.0) && FunAction.btn_name == Buttons.Division)
                result_text.text = "0"
            else {
                val number2 = FunResult.number2
                if (number2 != null) {
                    FunResult.answer =
                        when (if (FunAction.btn_name != Buttons.Equility) FunAction.btn_name
                        else FunAction.prev_btn_name) {
                            Buttons.Plus -> {
                                FunResult.number1?.plus(number2)!!
                            }

                            Buttons.Minus -> {
                                FunResult.number1?.minus(FunResult.number2!!)!!
                            }

                            Buttons.Division -> {
                                FunResult.number1?.div(FunResult.number2!!)!!
                            }

                            Buttons.Multiply -> {
                                FunResult.number1?.times(FunResult.number2!!)!!
                            }

                            Buttons.Equility -> 0.0
                        }
                } else {
                    FunResult.answer =
                        when (if (FunAction.btn_name != Buttons.Equility) FunAction.btn_name
                        else FunAction.prev_btn_name) {
                            Buttons.Plus -> {
                                FunResult.number1?.plus(FunResult.saved_value!!)!!
                            }

                            Buttons.Minus -> {
                                FunResult.number1?.minus(FunResult.saved_value!!)!!
                            }

                            Buttons.Division -> {
                                FunResult.number1?.div(FunResult.saved_value!!)!!
                            }

                            Buttons.Multiply -> {
                                FunResult.number1?.times(FunResult.saved_value!!)!!
                            }

                            Buttons.Equility -> 0.0
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
            btn =
                getActionButton(if (FunAction.btn_name != Buttons.Equility) FunAction.btn_name else FunAction.prev_btn_name)
            setActionButtonState(btn, false)
            FunAction.isAdd = false
            if (FunResult.number2 != null) FunResult.saved_value = FunResult.number2
            FunResult.number2 = null
            FunResult.number1 = FunResult.answer
            if (FunAction.btn_name != Buttons.Equility) FunAction.prev_btn_name = FunAction.btn_name
            FunAction.btn_name = Buttons.Equility
        }
        resizeText()
        test("resultView - END")
    }

    fun delText() {
        test("delText - START")
        val result_text: TextView = binding.resultText
        val btn: Button
        val btn_C: Button = binding.btnC
        if (result_text.text.toString() != "0") {
            result_text.text = "0"
            FunResult.saved_value = null
            FunAction.isAdd = false
            btn_C.text = "AC"
            result_text.textSize = 80F
            val currentButton = getActionButton(FunAction.btn_name)
            if (FunAction.btn_name != Buttons.Equility)
                setActionButtonState(currentButton, true)
        } else if (FunAction.btn_name != Buttons.Equility) {
            btn = when (FunAction.btn_name) {
                Buttons.Plus -> {
                    binding.btnPlus
                }

                Buttons.Minus -> {
                    binding.btnMinus
                }

                Buttons.Division -> {
                    binding.btnDivision
                }

                Buttons.Multiply -> {
                    binding.btnMultiply
                }

                else -> binding.btnEqulity
            }
            setActionButtonState(btn, false)
            FunAction.btn_name = Buttons.Equility
            FunResult.number1 = null
            FunResult.number2 = null
            FunResult.answer = 0.0
            FunAction.prev_btn_name = Buttons.Equility
            FunResult.saved_value = null
        }
        test("delText - END")
    }

    fun action(btn_name: Buttons) {
        test("action - START")
        var btn: Button
        if (FunAction.btn_name != btn_name) {
            btn = when (FunAction.btn_name) {
                Buttons.Plus -> {
                    binding.btnPlus
                }

                Buttons.Minus -> {
                    binding.btnMinus
                }

                Buttons.Division -> {
                    binding.btnDivision
                }

                Buttons.Multiply -> {
                    binding.btnMultiply
                }

                else -> binding.btnEqulity
            }
            //old button work
            setActionButtonState(btn, false)

            btn = when (btn_name) {
                Buttons.Plus -> {
                    binding.btnPlus
                }

                Buttons.Minus -> {
                    binding.btnMinus
                }

                Buttons.Division -> {
                    binding.btnDivision
                }

                Buttons.Multiply -> {
                    binding.btnMultiply
                }

                else -> binding.btnEqulity
            }
            setActionButtonState(btn, true)
            FunAction.btn_name = btn_name
            FunAction.isAdd = false
        }
        test("action - END")
    }

    private fun resizeText(TextSize: Float? = null) {
        val result_text: TextView = binding.resultText
        var textsize = result_text.textSize
        val paint = result_text.paint
        val textViewWidth = result_text.width
        var textWidth = paint.measureText(result_text.text.toString())
        i(
            "", """
            |textsize = $textsize
            |textWidth = $textWidth
            |textViewWidth = $textViewWidth
        """.trimMargin()
        )
        while (textWidth.toDouble() <= textViewWidth.toDouble()) {
            textsize -= 3
            result_text.textSize = textsize
            textWidth = paint.measureText(result_text.text.toString())
        }
    }

    private fun test(text: String = "None") {
        val result_text: TextView = binding.resultText
        i(
            text, """
            |FunResult.number1 = ${FunResult.number1}
            |FunResult.number2 = ${FunResult.number2}
            |FunResult.answer = ${FunResult.answer}
            |FunResult.saved_value = ${FunResult.saved_value}
            |result_text.text = ${result_text.text}
            |FunAction.btn_name = ${FunAction.btn_name}
            |FunAction.btn_name = ${FunAction.prev_btn_name}
        """.trimMargin()
        )
    }
}
// Проблема в работе с LONG переменными
// Проблема деление на 0, с использование строки "Ошибка"
// Проблема с изменение размера окна вывода ответа