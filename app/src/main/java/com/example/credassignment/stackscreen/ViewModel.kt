package com.example.credassignment.stackscreen

import android.util.Log
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import com.example.credassignment.network.ItemX
import androidx.compose.runtime.State
import androidx.lifecycle.ViewModel

class StackViewModel: ViewModel(){
    val emiItems = listOf(
        ItemX(
            duration = "for 12 months",
            emi = "4247 /mo",
            tag =" See calculations"
        ),
        ItemX(
            duration = "for 9 months",
            emi = "5,580 /mo",
            tag =" See calculations"
        ),
        ItemX(
            duration = "for 6 months",
            emi = "8,270 /mo",
            tag =" See calculations"
        )
    )
    private var _creditAmount = mutableFloatStateOf(500f)
    private var _emiOptionCost = mutableStateOf("")
    private var _emiOptionDuration = mutableStateOf("")
    private var _bank = mutableStateOf("")

    val creditAmount: State<Float>
        get() = _creditAmount

    val emiOptionCost: State<String>
        get() = _emiOptionCost

    val emiOptionDuration: State<String>
        get() = _emiOptionDuration

    val bank: State<String>
        get() = _bank

    fun updateCredit(newVal: Float){
        _creditAmount.floatValue = newVal
        Log.i("taggggg", creditAmount.toString())
    }

    fun updateEmiOption(newCost: String, newDuration: String){
        _emiOptionCost.value = newCost
        _emiOptionDuration.value = newDuration
    }

    fun updateBank(newVal: String){
        _bank.value = newVal
    }
}