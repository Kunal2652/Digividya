package com.example.yourapp

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.example.digividya.R
import kotlin.math.sqrt

class CalculatorDialog : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val builder = AlertDialog.Builder(requireContext())
        val inflater: LayoutInflater = requireActivity().layoutInflater
        val view: View = inflater.inflate(R.layout.dialog_calculator, null)

        val input1 = view.findViewById<EditText>(R.id.input1)
        val input2 = view.findViewById<EditText>(R.id.input2)
        val resultText = view.findViewById<TextView>(R.id.txtresult1)

        val btnAdd = view.findViewById<Button>(R.id.btnAdd)
        val btnSubtract = view.findViewById<Button>(R.id.btnSubtract)
        val btnMultiply = view.findViewById<Button>(R.id.btnMultiply)
        val btnDivide = view.findViewById<Button>(R.id.btnDivide)
        val calculateButton = view.findViewById<Button>(R.id.calculate1)
        val btnSquare = view.findViewById<Button>(R.id.btnsquare)
        val btnRoot = view.findViewById<Button>(R.id.btnroot)

        var operation = ""

        btnAdd.setOnClickListener { operation = "+" }
        btnSubtract.setOnClickListener { operation = "-" }
        btnMultiply.setOnClickListener { operation = "*" }
        btnDivide.setOnClickListener { operation = "/" }

        calculateButton.setOnClickListener {
            resultText.text = "" // Clear previous result
            val num1 = input1.text.toString().toDoubleOrNull()
            val num2 = input2.text.toString().toDoubleOrNull()

            if (num1 == null || num2 == null) {
                resultText.text = getString(R.string.invalid_input)
                return@setOnClickListener
            }

            val result = when (operation) {
                "+" -> num1 + num2
                "-" -> num1 - num2
                "*" -> num1 * num2
                "/" -> if (num2 != 0.0) num1 / num2 else getString(R.string.error_div_by_zero)
                else -> getString(R.string.select_operation)
            }
            resultText.text = result.toString()
        }

        btnSquare.setOnClickListener {
            val num1 = input1.text.toString().toDoubleOrNull()
            if (num1 == null) {
                resultText.text = getString(R.string.invalid_input)
                return@setOnClickListener
            }
            resultText.text = (num1 * num1).toString()
        }

        btnRoot.setOnClickListener {
            val num1 = input1.text.toString().toDoubleOrNull()
            if (num1 == null || num1 < 0) {
                resultText.text = getString(R.string.invalid_input)
                return@setOnClickListener
            }
            resultText.text = sqrt(num1).toString()
        }

        builder.setView(view)
        builder.setCancelable(true) // Set to false if you want to prevent outside touch dismissal
        return builder.create()
    }
}
