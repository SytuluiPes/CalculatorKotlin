package com.example.calculatorisworking

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import kotlin.math.roundToInt
import androidx.lifecycle.findViewTreeViewModelStoreOwner
import com.google.android.material.button.MaterialButton
import java.lang.Exception



object FunResult {
    var number1: Double? = null
    var number2: Double? = null
    var char1: String? = null
}
object FunAction {
    var actionflag: Boolean = false
    var btn_name: String = ""
}

class MainActivity() : AppCompatActivity() {
//    val result_text: TextView = findViewById(R.id.result_text)

//    fun exressionBuilder() : String{
//        try {
//            var beatyNumber1 = number1.toInt()
//            var beatyNumber2 = number2?.toInt()
//            return "$beatyNumber1" + if (char1 == null) "" else {
//                char1.toString() + if (beatyNumber2 == null) "" else beatyNumber2.toString()
//            }
//        } catch (e: Exception){
//            return "xui"
//        }
//    }
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
    fun resultView() {
        val result_text: TextView = findViewById(R.id.result_text)
        var answer : Double = 0.0
        FunResult.number2 = result_text.text.toString().toDouble()
        when (FunAction.btn_name){
            "btn_plus" -> {
                answer = FunResult.number1?.plus(FunResult.number2!!)!!
            }
            "btn_minus" -> {
                answer = FunResult.number1?.minus(FunResult.number2!!)!!
            }
            "btn_division" -> {
                answer = FunResult.number1?.div(FunResult.number2!!)!!
            }
            "btn_multiply" -> {
                answer = FunResult.number1?.times(FunResult.number2!!)!!
            }
        }
        try {
            val round_answer = (answer * 10000.0).roundToInt() / 10000.0 // ВЫДАЕТ ЧИСЛО В ФОРМАТЕ E
            result_text.text = round_answer.toString()
        } catch (e: Exception) {result_text.text = "Ошибка."}

    }
    fun setText(str: String) {
        val result_text: TextView = findViewById(R.id.result_text)
        if (result_text.text.toString() == "0" && FunAction.actionflag == false) result_text.text = str
        else if (result_text.text.toString() != "0" && FunAction.actionflag == false) result_text.append(str)

        if (FunAction.actionflag == true) {
            if (FunResult.number1 == null) {
                FunResult.number1 = result_text.text.toString().toDouble()
                result_text.text = str
            }
            else {
                if (result_text.text.toString() == "0") result_text.text = str
                else result_text.append(str)
            }
        }

        if (result_text.text.toString() != "0") {
            val btn_C : Button = findViewById(R.id.btn_C)
            btn_C.text = "C"
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
            FunAction.btn_name = "blabla"
            FunAction.actionflag = false
            FunResult.number1 = null
        }
    }
    fun action(btn_name : String) {
        val result_text: TextView = findViewById(R.id.result_text)
        var btn : Button = findViewById(R.id.btn_equlity)
        FunResult.char1 = btn_name
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
            FunAction.actionflag = true
        }
    }
    //    override fun onResume() {
    //        super.onResume()
    //    }
}