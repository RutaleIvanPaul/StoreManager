package com.kotlin.ivanpaulrutale.storemanager.views

import android.app.Activity
import android.content.Context
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.support.v7.app.AppCompatActivity
import android.widget.EditText
import android.widget.Toast
import com.kotlin.ivanpaulrutale.storemanager.R
import com.kotlin.ivanpaulrutale.storemanager.models.MonthListItem
import java.lang.IllegalArgumentException

var from_Report_details = false

val listItemObjects:ArrayList<MonthListItem> = arrayListOf(
    MonthListItem("January"),
    MonthListItem("February"),
    MonthListItem("March"),
    MonthListItem("April"),
    MonthListItem("May"),
    MonthListItem("June"),
    MonthListItem("July"),
    MonthListItem("August"),
    MonthListItem("September"),
    MonthListItem("October"),
    MonthListItem("November"),
    MonthListItem("December")
)

fun changeFromFragmentToFragment(activity:FragmentActivity?, fragment: Fragment){
    activity?.getSupportFragmentManager()
        ?.beginTransaction()
        ?.replace(R.id.content_frame, fragment)
        ?.addToBackStack(null)
        ?.commit()
}

fun changeFromActivityToFragment(activity: AppCompatActivity?,fragment: Fragment){
    activity?.supportFragmentManager
        ?.beginTransaction()
        ?.replace(R.id.content_frame, fragment)
        ?.addToBackStack(null)
        ?.commit()
}

fun changeFromclassToFragment(activity:AppCompatActivity,fragment: Fragment){
    activity.getSupportFragmentManager()
        .beginTransaction()
        .replace(R.id.content_frame, fragment)
        .addToBackStack(null)
        .commit()
}

fun noEmptyFields(editTexts:ArrayList<EditText>):Boolean{
    try {
        for(editext in 0 .. editTexts.lastIndex){
            val enteredString = editTexts[editext].text.toString()
            if (enteredString.isEmpty()){
                throw IllegalArgumentException("${editext.toString()}")
            }
        }
    }
    catch (e: IllegalArgumentException){
        val position = e.message?.toInt()?:0
        editTexts[position].error = "You left this field empty!"
        return false
    }
    return true
}

fun clearFields(editTexts:ArrayList<EditText>){
    for(editext in 0 .. editTexts.lastIndex){
        editTexts[editext].text.clear()
    }
}


