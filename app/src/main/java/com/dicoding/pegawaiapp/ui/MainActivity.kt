package com.dicoding.pegawaiapp.ui

import android.content.Intent
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

            val namaPegawai = edit_text_name.text.toString().trim()
            val posisiPegawai = edit_text_desg.text.toString().trim()
            val gajiPegawai = edit_text_salary.text.toString().trim()

            if (namaPegawai.isNotEmpty() && posisiPegawai.isNotEmpty() && gajiPegawai.isNotEmpty()) {
                progressbar_activity_main.visibility = View.VISIBLE
                lifecycleScope.launch {
                    try {
                        val response =
                            pegawaiServices.addPegawai(
                                namaPegawai,
                                posisiPegawai,
                                gajiPegawai.toInt()
                            )
                        withContext(Dispatchers.Main) {
                            if (response.isSuccessful) {
                                progressbar_activity_main.visibility = View.GONE
                                Toast.makeText(
                                    this@MainActivity,
                                    response.body(),
                                    Toast.LENGTH_LONG
                                )
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
            } else {
                Toast.makeText(this@MainActivity, "Please Check Your Input", Toast.LENGTH_LONG)
                    .show()
            }
        }

        btn_activity_main_daftar_pegawai.setOnClickListener {
            startActivity(Intent(this@MainActivity, PegawaiListActivity::class.java))
        }
    }
}