package com.kotlin.ivanpaulrutale.mapenzidigital.views

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.Spinner
import com.kotlin.ivanpaulrutale.mapenzidigital.R

class OrderActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order)

        val submit_order:Button = findViewById(R.id.submit_order)
        val checkBox1:CheckBox= findViewById(R.id.checkBox1)
        val checkBox2:CheckBox= findViewById(R.id.checkBox2)
        val checkBox3:CheckBox= findViewById(R.id.checkBox3)
        val checkBox4:CheckBox= findViewById(R.id.checkBox4)
        val checkBox5:CheckBox= findViewById(R.id.checkBox5)
        val checkBox6:CheckBox= findViewById(R.id.checkBox6)
        val checkBox7:CheckBox= findViewById(R.id.checkBox7)
        val checkBox8:CheckBox= findViewById(R.id.checkBox8)
        val checkBox9:CheckBox= findViewById(R.id.checkBox9)
        val checkBox10:CheckBox= findViewById(R.id.checkBox10)
        val checkBox11:CheckBox= findViewById(R.id.checkBox11)
        val checkBox12:CheckBox= findViewById(R.id.checkBox12)
        val checkBox13:CheckBox= findViewById(R.id.checkBox13)
        val checkBox14:CheckBox= findViewById(R.id.checkBox14)
        val checkBox15:CheckBox= findViewById(R.id.checkBox15)
        val checkBox16:CheckBox= findViewById(R.id.checkBox16)
        val checkBox17:CheckBox= findViewById(R.id.checkBox17)
        val checkBox18:CheckBox= findViewById(R.id.checkBox18)
        val checkBox19:CheckBox= findViewById(R.id.checkBox19)
        val checkBox20:CheckBox= findViewById(R.id.checkBox20)
        val checkBox21:CheckBox= findViewById(R.id.checkBox21)
        val checkBox22:CheckBox= findViewById(R.id.checkBox22)
        val checkBox23:CheckBox= findViewById(R.id.checkBox23)
        val checkBox24:CheckBox= findViewById(R.id.checkBox24)
        val checkBox25:CheckBox= findViewById(R.id.checkBox25)
        val checkBox26:CheckBox= findViewById(R.id.checkBox26)
        val checkBox27:CheckBox= findViewById(R.id.checkBox27)
        submit_order.setOnClickListener{
            val shoppingCart = ArrayList<Int>()
            val intent= Intent(this,PaymentActivity::class.java)
            if (checkBox1.isChecked){
                shoppingCart.add(1)
            }
            if (checkBox2.isChecked){
                shoppingCart.add(2)
            }
            if (checkBox3.isChecked){
                shoppingCart.add(3)
            }
            if (checkBox4.isChecked){
                shoppingCart.add(4)
            }
            if (checkBox5.isChecked){
                shoppingCart.add(5)
            }
            if (checkBox6.isChecked){
                shoppingCart.add(6)
            }
            if (checkBox7.isChecked){
                shoppingCart.add(7)
            }
            if (checkBox8.isChecked){
                shoppingCart.add(8)
            }
            if (checkBox9.isChecked){
                shoppingCart.add(9)
            }
            if (checkBox10.isChecked){
                shoppingCart.add(10)
            }
            if (checkBox11.isChecked){
                shoppingCart.add(11)
            }
            if (checkBox12.isChecked){
                shoppingCart.add(12)
            }
            if (checkBox13.isChecked){
                shoppingCart.add(13)
            }
            if (checkBox14.isChecked){
                shoppingCart.add(14)
            }
            if (checkBox15.isChecked){
                shoppingCart.add(15)
            }
            if (checkBox16.isChecked){
                shoppingCart.add(16)
            }
            if (checkBox17.isChecked){
                shoppingCart.add(17)
            }
            if (checkBox18.isChecked){
                shoppingCart.add(18)
            }
            if (checkBox19.isChecked){
                shoppingCart.add(19)
            }
            if (checkBox20.isChecked){
                shoppingCart.add(20)
            }
            if (checkBox21.isChecked){
                shoppingCart.add(21)
            }
            if (checkBox22.isChecked){
                shoppingCart.add(22)
            }
            if (checkBox23.isChecked){
                shoppingCart.add(23)
            }
            if (checkBox24.isChecked){
                shoppingCart.add(24)
            }
            if (checkBox25.isChecked){
                shoppingCart.add(25)
            }
            if (checkBox26.isChecked){
                shoppingCart.add(26)
            }
            if (checkBox27.isChecked){
                shoppingCart.add(27)
            }
            intent.putIntegerArrayListExtra("shoppingCart",shoppingCart)
            startActivity(intent)
        }
    }

    override fun onBackPressed() {
        startActivity(Intent(this,MainActivity::class.java))
    }
}
