package com.example.time_api

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    var bu_start:Button? = null
    var tv_time:TextView? = null
    var call:Call<Timing>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bu_start = findViewById(R.id.bu_start)
        tv_time = findViewById(R.id.tv_time)

        val retrofit = Retrofit.Builder().baseUrl("http://worldtimeapi.org/api/timezone/Africa/").addConverterFactory(GsonConverterFactory.create()).build()
        val apiInterface = retrofit.create(ApiInterface::class.java)

        call = apiInterface.get_time()

        bu_start!!.setOnClickListener {
            start()
        }

    }

    fun start()
    {
        val view:View = View.inflate(this,R.layout.progress,null)
        val dialog = AlertDialog.Builder(this)
        val progress = findViewById<ProgressBar>(R.id.progress)
        val c = dialog.setView(view).create()
        c.window!!.setBackgroundDrawableResource(android.R.color.transparent)
        c.setCancelable(false)
        c.show()
        call!!.clone().enqueue(object :Callback<Timing>
        {
            override fun onResponse(call: Call<Timing>, response: Response<Timing>) {
               tv_time!!.text = response.body()!!.datetime
               c.hide()
            }

            override fun onFailure(call: Call<Timing>, t: Throwable) {
                tv_time!!.text = t.message
                c.hide()
            }

        })
    }
}