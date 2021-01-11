package com.dicoding.pegawaiapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.dicoding.pegawaiapp.R
import com.dicoding.pegawaiapp.network.RetrofitBuilder.pegawaiServices
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_activity_main_add_pegawai.setOnClickListener {
            progressbar_activity_main.visibility = View.VISIBLE

            val namaPegawai = edit_text_name.text.toString()
            val posisiPegawai = edit_text_desg.text.toString()
            val gajiPegawai = edit_text_salary.text.toString().toInt()

            lifecycleScope.launch {
                try {
                    val response =
                        pegawaiServices.addPegawai(namaPegawai, posisiPegawai, gajiPegawai)
                    withContext(Dispatchers.Main) {
                        if (response.isSuccessful) {
                            progressbar_activity_main.visibility = View.GONE
                            Toast.makeText(this@MainActivity, response.body(), Toast.LENGTH_LONG)
                                .show()
                        }
                    }
                } catch (e: Exception) {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(this@MainActivity, e.message, Toast.LENGTH_LONG)
                            .show()
                    }
                }

            }
        }
    }
}