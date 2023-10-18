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



object FunResult {
    var number1: Double? = null
    var number2: Double? = null
    var answer: Double = 0.0
}
object FunAction {
    var actionflag: Boolean = false
    var btn_name: String = "btn_quality"
    var prev_btn_name : String = ""
    var input_text : Boolean = false
    var kostilb : Boolean = false
}

class MainActivity() : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
        val btn_plus_minus : Button = findViewById(R.id.btn_plus_minus)
        val btn_point : Button = findViewById(R.id.btn_point)
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
        val result_text: TextView = findViewById(R.id.result_text)
        if (!FunAction.input_text && !FunAction.actionflag) {
            result_text.text = str
            FunAction.input_text = true
        }
        else if (FunAction.input_text && !FunAction.actionflag) result_text.append(str)
        else if (!FunAction.input_text && FunAction.actionflag) {
            result_text.text = str
            FunAction.input_text = true
            FunAction.kostilb = false
        }
        else result_text.append(str)

        if (result_text.text.toString() != "0") {
            val btn_C : Button = findViewById(R.id.btn_C)
            btn_C.text = "C"
        }
    }
    fun resultView() {
        i("","""
            |FunResult.number1 = ${FunResult.number1}
            |FunResult.number2 = ${FunResult.number2}
            |FunAction.actionflag = ${FunAction.actionflag}
            |FunAction.btn_name = ${FunAction.btn_name}
            |FunAction.prev_btn_name = ${FunAction.prev_btn_name}
        """.trimMargin())
        if (FunResult.number1 != null && (FunAction.btn_name != "btn_quality" ||
                    FunAction.prev_btn_name != "btn_quality")) {
            var btn: Button = findViewById(R.id.btn_equlity)
            val result_text: TextView = findViewById(R.id.result_text)
            if (FunAction.prev_btn_name != "btn_quality" && FunAction.btn_name == "btn_quality" && FunAction.kostilb) {
                FunResult.number2 = FunResult.number1
                FunAction.kostilb = false
            }
            else
                FunResult.number2 = result_text.text.toString().toDouble()
            when (if (FunAction.btn_name != "btn_quality") FunAction.btn_name else FunAction.prev_btn_name) {
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
            result_text.text = FunResult.answer.toString()
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
            FunResult.number1 = FunResult.answer
            FunAction.prev_btn_name = FunAction.btn_name
            FunAction.btn_name = "btn_equlity"
        }
    }

    fun delText() {
        val result_text: TextView = findViewById(R.id.result_text)
        var btn : Button = findViewById(R.id.btn_equlity)
        val btn_C : Button = findViewById(R.id.btn_C)
        if (result_text.text.toString() != "0"){
            result_text.text = "0"
            btn_C.text = "AC"
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
            FunAction.input_text = false
            FunResult.number1 = null
            FunResult.number2 = null
            FunAction.kostilb = true
        }
    }
    fun action(btn_name : String) {
        val result_text: TextView = findViewById(R.id.result_text)
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
            FunResult.number1 = result_text.text.toString().toDouble()
            FunAction.input_text = false
            FunAction.actionflag = true
        }
    }
}