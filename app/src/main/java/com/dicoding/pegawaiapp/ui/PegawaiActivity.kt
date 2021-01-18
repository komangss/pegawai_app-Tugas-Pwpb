package com.dicoding.pegawaiapp.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.dicoding.pegawaiapp.R
import com.dicoding.pegawaiapp.network.RetrofitBuilder
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_pegawai.*
import kotlinx.android.synthetic.main.activity_pegawai.edit_text_desg
import kotlinx.android.synthetic.main.activity_pegawai.edit_text_name
import kotlinx.android.synthetic.main.activity_pegawai.edit_text_salary
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PegawaiActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_ID_PEGAWAI = "id_pegawai"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pegawai)

        supportActionBar?.title = "Detail Pegawai"

        val idPegawai = intent.getIntExtra(EXTRA_ID_PEGAWAI, -1)

        Toast.makeText(this@PegawaiActivity, "Please Wait", Toast.LENGTH_SHORT)
            .show()

        lifecycleScope.launchWhenCreated {
            try {
                val pegawai =
                    RetrofitBuilder.pegawaiServices.getPegawaiById(idPegawai).result[0]

                withContext(Dispatchers.Main) {
                    linear_layout_activity_pegawai.visibility = View.VISIBLE
                    edit_text_id.setText(pegawai.id.toString())
                    edit_text_name.setText(pegawai.nama)
                    edit_text_desg.setText(pegawai.posisi)
                    edit_text_salary.setText(pegawai.gaji.toString())
                }

            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@PegawaiActivity, e.message, Toast.LENGTH_LONG)
                        .show()
                }
            }
        }

        btn_update_pegawai.setOnClickListener {
            val name = edit_text_name.text.toString().trim()
            val desg = edit_text_desg.text.toString().trim()
            val salary = edit_text_salary.text.toString().trim()
            if (name.isNotEmpty() && desg.isNotEmpty() && salary.isNotEmpty()) {
                lifecycleScope.launch {
                    try {
                        val response = RetrofitBuilder.pegawaiServices
                            .updatePegawai(idPegawai, name, desg, salary.toInt())
                        withContext(Dispatchers.Main) {
                            if (response.isSuccessful) {
                                Toast.makeText(
                                    this@PegawaiActivity,
                                    response.body(),
                                    Toast.LENGTH_LONG
                                ).show()
                                backToPegawaiListActivity()
                            }
                        }
                    } catch (e: Exception) {
                        withContext(Dispatchers.Main) {
                            Toast.makeText(this@PegawaiActivity, e.message, Toast.LENGTH_LONG)
                                .show()
                        }
                    }
                }
            } else {
                Toast.makeText(this@PegawaiActivity, "Please Check Your Input", Toast.LENGTH_LONG)
                    .show()
            }
        }

        btn_delete_pegawai.setOnClickListener {
            lifecycleScope.launch {
                try {
                    val response = RetrofitBuilder.pegawaiServices
                        .hapusPegawai(idPegawai)
                    withContext(Dispatchers.Main) {
                        if (response.isSuccessful) {
                            Toast.makeText(
                                this@PegawaiActivity,
                                response.body(),
                                Toast.LENGTH_LONG
                            ).show()
                            backToPegawaiListActivity()
                        }
                    }
                } catch (e: Exception) {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(this@PegawaiActivity, e.message, Toast.LENGTH_LONG)
                            .show()
                    }
                }
            }
        }
    }

    private fun backToPegawaiListActivity() {
        startActivity(Intent(this@PegawaiActivity, PegawaiListActivity::class.java))
        finish()
    }

    override fun onBackPressed() {
        backToPegawaiListActivity()
    }
}