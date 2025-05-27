package com.example.lumi

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class CalculatorActivity : AppCompatActivity() {

    private lateinit var input1: EditText
    private lateinit var input2: EditText
    private lateinit var result: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calculator)

        input1 = findViewById(R.id.input1)
        input2 = findViewById(R.id.input2)
        result = findViewById(R.id.result)

        findViewById<Button>(R.id.btnAdd).setOnClickListener {
            calculate('+')
        }

        findViewById<Button>(R.id.btnSubtract).setOnClickListener {
            calculate('-')
        }

        findViewById<Button>(R.id.btnMultiply).setOnClickListener {
            calculate('*')
        }

        findViewById<Button>(R.id.btnDivide).setOnClickListener {
            calculate('/')
        }
    }

    private fun calculate(operator: Char) {
        val num1 = input1.text.toString().toDoubleOrNull()
        val num2 = input2.text.toString().toDoubleOrNull()

        if (num1 == null || num2 == null) {
            result.setText("Ошибка")
            return
        }

        val res = when (operator) {
            '+' -> num1 + num2
            '-' -> num1 - num2
            '*' -> num1 * num2
            '/' -> if (num2 != 0.0) num1 / num2 else "Деление на 0"
            else -> "Ошибка"
        }

        result.setText(res.toString())
    }
}
