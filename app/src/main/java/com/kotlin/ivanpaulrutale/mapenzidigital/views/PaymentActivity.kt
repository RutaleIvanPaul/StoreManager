package com.kotlin.ivanpaulrutale.mapenzidigital.views

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_payment.*
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import java.lang.IllegalArgumentException
//import android.R
import java.util.concurrent.ExecutionException
import android.widget.LinearLayout


class PaymentActivity : AppCompatActivity() {

    var waitTimeCounter = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.kotlin.ivanpaulrutale.mapenzidigital.R.layout.activity_payment)

        val shoppingCart = intent.getIntegerArrayListExtra("shoppingCart")
        var shoppingCartTextArea:TextView = findViewById(com.kotlin.ivanpaulrutale.mapenzidigital.R.id.descriptionTextView)
        val confirmOrderButton:Button = findViewById(com.kotlin.ivanpaulrutale.mapenzidigital.R.id.confirm_order)
        var shoppingCartString = ""
        var totalCharge:Double = 0.0
        val params = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT)
        params.setMargins(0, 2, 0, 2)
        if (shoppingCart.contains(1)){
            var textView1 = TextView(this)
            textView1.text = "QR code access control: 2,000,000"
            textView1.setTextColor(resources.getColor(com.kotlin.ivanpaulrutale.mapenzidigital.R.color.colorPrimaryDark))
            textView1.setBackgroundColor(resources.getColor(com.kotlin.ivanpaulrutale.mapenzidigital.R.color.colorWhite))
            textView1.textSize = 20f
            textView1.setPadding(3,3,3,3)
            textView1.setLayoutParams(params)
            order_list.addView(textView1)
            totalCharge+=2000000
        }
        if (shoppingCart.contains(2)){
            var textView2 = TextView(this)
            textView2.text = "E-ticketing and e-cards: 1,500,000"
            textView2.setTextColor(resources.getColor(com.kotlin.ivanpaulrutale.mapenzidigital.R.color.colorPrimaryDark))
            textView2.setBackgroundColor(resources.getColor(com.kotlin.ivanpaulrutale.mapenzidigital.R.color.colorWhite))
            textView2.textSize = 20f
            textView2.setPadding(3,3,3,3)
            textView2.setLayoutParams(params)
            order_list.addView(textView2)
            totalCharge+=1500000
        }
        if (shoppingCart.contains(3)){
            var textView3 = TextView(this)
            textView3.text = "Social media management: 3,000,000"
            textView3.setTextColor(resources.getColor(com.kotlin.ivanpaulrutale.mapenzidigital.R.color.colorPrimaryDark))
            textView3.setBackgroundColor(resources.getColor(com.kotlin.ivanpaulrutale.mapenzidigital.R.color.colorWhite))
            textView3.textSize = 20f
            textView3.setPadding(3,3,3,3)
            textView3.setLayoutParams(params)
            order_list.addView(textView3)
            totalCharge+=3000000
        }
        if (shoppingCart.contains(4)){
            var textView4 = TextView(this)
            textView4.text = "Website analytics: 3,000,000"
            textView4.setTextColor(resources.getColor(com.kotlin.ivanpaulrutale.mapenzidigital.R.color.colorPrimaryDark))
            textView4.setBackgroundColor(resources.getColor(com.kotlin.ivanpaulrutale.mapenzidigital.R.color.colorWhite))
            textView4.textSize = 20f
            textView4.setPadding(3,3,3,3)
            textView4.setLayoutParams(params)
            order_list.addView(textView4)
            totalCharge+=3000000
        }
        if (shoppingCart.contains(5)){
            var textView5 = TextView(this)
            textView5.text = "Audio Visual : 1,500,000"
            textView5.setTextColor(resources.getColor(com.kotlin.ivanpaulrutale.mapenzidigital.R.color.colorPrimaryDark))
            textView5.setBackgroundColor(resources.getColor(com.kotlin.ivanpaulrutale.mapenzidigital.R.color.colorWhite))
            textView5.textSize = 20f
            textView5.setPadding(3,3,3,3)
            textView5.setLayoutParams(params)
            order_list.addView(textView5)
            totalCharge+=1500000
        }
        if (shoppingCart.contains(6)){
            var textView6 = TextView(this)
            textView6.text = "Video shoot: 1,500,000"
            textView6.setTextColor(resources.getColor(com.kotlin.ivanpaulrutale.mapenzidigital.R.color.colorPrimaryDark))
            textView6.setBackgroundColor(resources.getColor(com.kotlin.ivanpaulrutale.mapenzidigital.R.color.colorWhite))
            textView6.textSize = 20f
            textView6.setPadding(3,3,3,3)
            textView6.setLayoutParams(params)
            order_list.addView(textView6)
            totalCharge+=1500000
        }
        if (shoppingCart.contains(7)){
            var textView7 = TextView(this)
            textView7.text = "Business cards, posters: 1,000,000"
            textView7.setTextColor(resources.getColor(com.kotlin.ivanpaulrutale.mapenzidigital.R.color.colorPrimaryDark))
            textView7.setBackgroundColor(resources.getColor(com.kotlin.ivanpaulrutale.mapenzidigital.R.color.colorWhite))
            textView7.textSize = 20f
            textView7.setPadding(3,3,3,3)
            textView7.setLayoutParams(params)
            order_list.addView(textView7)
            totalCharge+=1000000
        }
        if (shoppingCart.contains(8)){
            var textView8 = TextView(this)
            textView8.text = "Animations: 2,000,000"
            textView8.setTextColor(resources.getColor(com.kotlin.ivanpaulrutale.mapenzidigital.R.color.colorPrimaryDark))
            textView8.setBackgroundColor(resources.getColor(com.kotlin.ivanpaulrutale.mapenzidigital.R.color.colorWhite))
            textView8.textSize = 20f
            textView8.setPadding(3,3,3,3)
            textView8.setLayoutParams(params)
            order_list.addView(textView8)
            totalCharge+=2000000
        }
        if (shoppingCart.contains(9)){
            var textView9 = TextView(this)
            textView9.text = "Public Relations: 1,500,000"
            textView9.setTextColor(resources.getColor(com.kotlin.ivanpaulrutale.mapenzidigital.R.color.colorPrimaryDark))
            textView9.setBackgroundColor(resources.getColor(com.kotlin.ivanpaulrutale.mapenzidigital.R.color.colorWhite))
            textView9.textSize = 20f
            textView9.setPadding(3,3,3,3)
            textView9.setLayoutParams(params)
            order_list.addView(textView9)
            totalCharge+=15000000
        }
        if (shoppingCart.contains(10)){
            var textView10 = TextView(this)
            textView10.text = "YouTube channels: 1,000,000"
            textView10.setTextColor(resources.getColor(com.kotlin.ivanpaulrutale.mapenzidigital.R.color.colorPrimaryDark))
            textView10.setBackgroundColor(resources.getColor(com.kotlin.ivanpaulrutale.mapenzidigital.R.color.colorWhite))
            textView10.textSize = 20f
            textView10.setPadding(3,3,3,3)
            textView10.setLayoutParams(params)
            order_list.addView(textView10)
            totalCharge+=1000000
        }
        if (shoppingCart.contains(11)){
            var textView11 = TextView(this)
            textView11.text = "Search Engine Management: 2,000,000"
            textView11.setTextColor(resources.getColor(com.kotlin.ivanpaulrutale.mapenzidigital.R.color.colorPrimaryDark))
            textView11.setBackgroundColor(resources.getColor(com.kotlin.ivanpaulrutale.mapenzidigital.R.color.colorWhite))
            textView11.textSize = 20f
            textView11.setPadding(3,3,3,3)
            textView11.setLayoutParams(params)
            order_list.addView(textView11)
            totalCharge+=2000000
        }
        if (shoppingCart.contains(12)){
            var textView12 = TextView(this)
            textView12.text = "E-mail  management: 1,000,000"
            textView12.setTextColor(resources.getColor(com.kotlin.ivanpaulrutale.mapenzidigital.R.color.colorPrimaryDark))
            textView12.setBackgroundColor(resources.getColor(com.kotlin.ivanpaulrutale.mapenzidigital.R.color.colorWhite))
            textView12.textSize = 20f
            textView12.setPadding(3,3,3,3)
            textView12.setLayoutParams(params)
            order_list.addView(textView12)
            totalCharge+=1000000
        }
        if (shoppingCart.contains(13)){
            var textView13 = TextView(this)
            textView13.text = "Pay-Per-Click: 1,500,000"
            textView13.setTextColor(resources.getColor(com.kotlin.ivanpaulrutale.mapenzidigital.R.color.colorPrimaryDark))
            textView13.setBackgroundColor(resources.getColor(com.kotlin.ivanpaulrutale.mapenzidigital.R.color.colorWhite))
            textView13.textSize = 20f
            textView13.setPadding(3,3,3,3)
            textView13.setLayoutParams(params)
            order_list.addView(textView13)
            totalCharge+=1500000
        }
        if (shoppingCart.contains(14)){
            var textView14 = TextView(this)
            textView14.text = "Reputation Management: 1,500,000"
            textView14.setTextColor(resources.getColor(com.kotlin.ivanpaulrutale.mapenzidigital.R.color.colorPrimaryDark))
            textView14.setBackgroundColor(resources.getColor(com.kotlin.ivanpaulrutale.mapenzidigital.R.color.colorWhite))
            textView14.textSize = 20f
            textView14.setPadding(3,3,3,3)
            textView14.setLayoutParams(params)
            order_list.addView(textView14)
            totalCharge+=1500000
        }
        if (shoppingCart.contains(15)){
            var textView15 = TextView(this)
            textView15.text = "Photography: 1,000,000"
            textView15.setTextColor(resources.getColor(com.kotlin.ivanpaulrutale.mapenzidigital.R.color.colorPrimaryDark))
            textView15.setBackgroundColor(resources.getColor(com.kotlin.ivanpaulrutale.mapenzidigital.R.color.colorWhite))
            textView15.textSize = 20f
            textView15.setPadding(3,3,3,3)
            textView15.setLayoutParams(params)
            order_list.addView(textView15)
            totalCharge+=1000000
        }
        if (shoppingCart.contains(16)){
            var textView16 = TextView(this)
            textView16.text = "Videography: 1,200,000"
            textView16.setTextColor(resources.getColor(com.kotlin.ivanpaulrutale.mapenzidigital.R.color.colorPrimaryDark))
            textView16.setBackgroundColor(resources.getColor(com.kotlin.ivanpaulrutale.mapenzidigital.R.color.colorWhite))
            textView16.textSize = 20f
            textView16.setPadding(3,3,3,3)
            textView16.setLayoutParams(params)
            order_list.addView(textView16)
            totalCharge+=1200000
        }
        if (shoppingCart.contains(17)){
            var textView17 = TextView(this)
            textView17.text = "Content Management: 800,000"
            textView17.setTextColor(resources.getColor(com.kotlin.ivanpaulrutale.mapenzidigital.R.color.colorPrimaryDark))
            textView17.setBackgroundColor(resources.getColor(com.kotlin.ivanpaulrutale.mapenzidigital.R.color.colorWhite))
            textView17.textSize = 20f
            textView17.setPadding(3,3,3,3)
            textView17.setLayoutParams(params)
            order_list.addView(textView17)
            totalCharge+=800000
        }
        if (shoppingCart.contains(18)){
            var textView18 = TextView(this)
            textView18.text = "Mobile Marketing : 1,000,000"
            textView18.setTextColor(resources.getColor(com.kotlin.ivanpaulrutale.mapenzidigital.R.color.colorPrimaryDark))
            textView18.setBackgroundColor(resources.getColor(com.kotlin.ivanpaulrutale.mapenzidigital.R.color.colorWhite))
            textView18.textSize = 20f
            textView18.setPadding(3,3,3,3)
            textView18.setLayoutParams(params)
            order_list.addView(textView18)
            totalCharge+=1000000
        }
        if (shoppingCart.contains(19)){
            var textView19 = TextView(this)
            textView19.text = "QR coded cards: 1,200,000"
            textView19.setTextColor(resources.getColor(com.kotlin.ivanpaulrutale.mapenzidigital.R.color.colorPrimaryDark))
            textView19.setBackgroundColor(resources.getColor(com.kotlin.ivanpaulrutale.mapenzidigital.R.color.colorWhite))
            textView19.textSize = 20f
            textView19.setPadding(3,3,3,3)
            textView19.setLayoutParams(params)
            order_list.addView(textView19)
            totalCharge+=1200000
        }
        if (shoppingCart.contains(20)){
            var textView20 = TextView(this)
            textView20.text = "Conversion Management: 1,000,000"
            textView20.setTextColor(resources.getColor(com.kotlin.ivanpaulrutale.mapenzidigital.R.color.colorPrimaryDark))
            textView20.setBackgroundColor(resources.getColor(com.kotlin.ivanpaulrutale.mapenzidigital.R.color.colorWhite))
            textView20.textSize = 20f
            textView20.setPadding(3,3,3,3)
            textView20.setLayoutParams(params)
            order_list.addView(textView20)
            totalCharge+=1000000
        }
        if (shoppingCart.contains(21)){
            var textView21 = TextView(this)
            textView21.text = "Hour photo shoot: 1,000,000"
            textView21.setTextColor(resources.getColor(com.kotlin.ivanpaulrutale.mapenzidigital.R.color.colorPrimaryDark))
            textView21.setBackgroundColor(resources.getColor(com.kotlin.ivanpaulrutale.mapenzidigital.R.color.colorWhite))
            textView21.textSize = 20f
            textView21.setPadding(3,3,3,3)
            textView21.setLayoutParams(params)
            order_list.addView(textView21)
            totalCharge+=1000000
        }
        if (shoppingCart.contains(22)){
            var textView22 = TextView(this)
            textView22.text = "Printed tags and cards: 800,000"
            textView22.setTextColor(resources.getColor(com.kotlin.ivanpaulrutale.mapenzidigital.R.color.colorPrimaryDark))
            textView22.setBackgroundColor(resources.getColor(com.kotlin.ivanpaulrutale.mapenzidigital.R.color.colorWhite))
            textView22.textSize = 20f
            textView22.setPadding(3,3,3,3)
            textView22.setLayoutParams(params)
            order_list.addView(textView22)
            totalCharge+=800000
        }
        if (shoppingCart.contains(23)){
            var textView23 = TextView(this)
            textView23.text = "Footage Sales: 700,000"
            textView23.setTextColor(resources.getColor(com.kotlin.ivanpaulrutale.mapenzidigital.R.color.colorPrimaryDark))
            textView23.setBackgroundColor(resources.getColor(com.kotlin.ivanpaulrutale.mapenzidigital.R.color.colorWhite))
            textView23.textSize = 20f
            textView23.setPadding(3,3,3,3)
            textView23.setLayoutParams(params)
            order_list.addView(textView23)
            totalCharge+=700000
        }
        if (shoppingCart.contains(24)){
            var textView24 = TextView(this)
            textView24.text = "Architectural deign: 1,300,000"
            textView24.setTextColor(resources.getColor(com.kotlin.ivanpaulrutale.mapenzidigital.R.color.colorPrimaryDark))
            textView24.setBackgroundColor(resources.getColor(com.kotlin.ivanpaulrutale.mapenzidigital.R.color.colorWhite))
            textView24.textSize = 20f
            textView24.setPadding(3,3,3,3)
            textView24.setLayoutParams(params)
            order_list.addView(textView24)
            totalCharge+=1300000
        }
        if (shoppingCart.contains(25)){
            var textView25 = TextView(this)
            textView25.text = "UI and UX design: 1,500,000"
            textView25.setTextColor(resources.getColor(com.kotlin.ivanpaulrutale.mapenzidigital.R.color.colorPrimaryDark))
            textView25.setBackgroundColor(resources.getColor(com.kotlin.ivanpaulrutale.mapenzidigital.R.color.colorWhite))
            textView25.textSize = 20f
            textView25.setPadding(3,3,3,3)
            textView25.setLayoutParams(params)
            order_list.addView(textView25)
            totalCharge+=1500000
        }
        if (shoppingCart.contains(26)){
            var textView26 = TextView(this)
            textView26.text = "Documentary Production: 1,500,000"
            textView26.setTextColor(resources.getColor(com.kotlin.ivanpaulrutale.mapenzidigital.R.color.colorPrimaryDark))
            textView26.setBackgroundColor(resources.getColor(com.kotlin.ivanpaulrutale.mapenzidigital.R.color.colorWhite))
            textView26.textSize = 20f
            textView26.setPadding(3,3,3,3)
            textView26.setLayoutParams(params)
            order_list.addView(textView26)
            totalCharge+=1500000
        }
        if (shoppingCart.contains(27)){
            var textView27 = TextView(this)
            textView27.text = "Customizing websites: 1,500,000"
            textView27.setTextColor(resources.getColor(com.kotlin.ivanpaulrutale.mapenzidigital.R.color.colorPrimaryDark))
            textView27.setBackgroundColor(resources.getColor(com.kotlin.ivanpaulrutale.mapenzidigital.R.color.colorWhite))
            textView27.textSize = 20f
            textView27.setPadding(3,3,3,3)
            textView27.setLayoutParams(params)
            order_list.addView(textView27)
            totalCharge+=1500000
        }
        shoppingCartString+="\n\nTOTAL: UGX"+totalCharge
        shoppingCartTextArea.text = shoppingCartString


        confirmOrderButton.setOnClickListener{
            confirmOrderButton.isEnabled = false
            confirmOrderButton.text = "Waiting ..."
            progressBar.visibility = View.VISIBLE
            startPayment(totalCharge)
        }

    }

    private fun startPayment(total:Double) {
        try {
            if (phonenumber.text.isEmpty() || phonenumber.text.length < 10 ){
                throw IllegalArgumentException("Please Input a correct number!")
            }
            val trimmedNumber = "256"+phonenumber.text.substring(1,10)

            CoroutineScope(IO).launch {
                val transactionReference = makePayment(trimmedNumber,total)
                checkTransaction(transactionReference,trimmedNumber,total)

            }


        } catch (e: InterruptedException) {
            progressBar.visibility = View.INVISIBLE
            e.printStackTrace()
            Toast.makeText(this,"Something went wrong! Please retry",Toast.LENGTH_LONG).show()
            startActivity(Intent(this,OrderActivity::class.java))
        } catch (e: ExecutionException) {
            progressBar.visibility = View.INVISIBLE
            e.printStackTrace()
        } catch (e: IllegalArgumentException){
            e.printStackTrace()
            progressBar.visibility = View.INVISIBLE
            Toast.makeText(this,e.message,Toast.LENGTH_LONG).show()
        }
    }

    private suspend fun makePayment(phoneNumber:String,amount:Double):String{
        yoAPI.nonBlocking = true
        val response =  yoAPI.ac_deposit_funds(phoneNumber,amount,"Mapenzi Digital Services")
        delay(3000)
        Log.i("YO PAY RESPONSE",response.toString())

        if (response.transactionReference == null){
            withContext(Main){
                throw InterruptedException("Something went wrong! Please retry")
            }
        }

        return response.transactionReference

    }

    private suspend fun checkTransaction(transactionReference: String?,phoneNumber: String,total: Double) {
        yoAPI.nonBlocking = true
        val transactionStatus = yoAPI.ac_check_transaction_status(transactionReference)
        val confirmOrderButton:Button = findViewById(com.kotlin.ivanpaulrutale.mapenzidigital.R.id.confirm_order)
        val dialogBuilder = AlertDialog.Builder(this)
        if (transactionStatus == null){
            delay(5000)
        }
        if (transactionStatus.transactionStatus.equals("success",ignoreCase = true)){
            withContext(Main){
                titleTextView3.visibility = View.VISIBLE
                clientLabel.visibility = View.VISIBLE
                clientID.visibility = View.VISIBLE
                amountLabel.visibility = View.VISIBLE
                amount.visibility = View.VISIBLE
                messageLabel.visibility = View.VISIBLE
                message.visibility = View.VISIBLE


                clientID.text = phoneNumber
                amount.text = total.toString()
                message.text = "SUCCESSFULLY PAID"
                confirmOrderButton.text = "DONE"
                progressBar.visibility = View.INVISIBLE

                dialogBuilder.setTitle("Success")
                dialogBuilder.setMessage("You have successfully paid.")
                dialogBuilder.setPositiveButton("OK"){dialog,which ->
                    startActivity(Intent(applicationContext,OrderActivity::class.java))
                }
            }

        }
        else if (transactionStatus.transactionStatus.equals("pending",ignoreCase = true) && waitTimeCounter < 36){
            Log.i("CHECKINGPENDINGTRNSCN",waitTimeCounter.toString())
            waitTimeCounter++
            checkTransaction(transactionReference,phoneNumber,total)
        }
        else{
            withContext(Main){
                titleTextView3.visibility = View.VISIBLE
                clientLabel.visibility = View.VISIBLE
                clientID.visibility = View.VISIBLE
                amountLabel.visibility = View.VISIBLE
                amount.visibility = View.VISIBLE
                messageLabel.visibility = View.VISIBLE
                message.visibility = View.VISIBLE

                clientID.text = phoneNumber
                amount.text = total.toString()
                message.text = "FAILED TRANSACTION"
                confirmOrderButton.isEnabled = true
                confirmOrderButton.text = "Submit"
                progressBar.visibility = View.INVISIBLE

                dialogBuilder.setTitle("Failed")
                dialogBuilder.setMessage("Transaction Failed. Are you sure the phone number is correct?")
                dialogBuilder.setPositiveButton("OK"){ _, which ->
                    startActivity(Intent(applicationContext,OrderActivity::class.java))
                }
            }
        }
    }



}
