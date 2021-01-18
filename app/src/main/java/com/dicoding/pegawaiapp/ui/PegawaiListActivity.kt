package com.dicoding.pegawaiapp.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.pegawaiapp.R
import com.dicoding.pegawaiapp.network.RetrofitBuilder
import com.dicoding.pegawaiapp.ui.adapter.PegawaiListAdapter
import kotlinx.android.synthetic.main.activity_pegawai_list.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class PegawaiListActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pegawai_list)

        supportActionBar?.title = "Pegawai List"

        val onPegawaiClicked : (pegawaiId : Int?) -> Unit = { pegawaiId ->
            val intent = Intent(this@PegawaiListActivity, PegawaiActivity::class.java)
            intent.putExtra(PegawaiActivity.EXTRA_ID_PEGAWAI, pegawaiId)
            startActivity(intent)
            finish()
        }

        val pegawaiListAdapter = PegawaiListAdapter(onPegawaiClicked)

        with(recycler_view_activity_pegawai_list) {
            adapter = pegawaiListAdapter
            layoutManager = LinearLayoutManager(this@PegawaiListActivity)
            setHasFixedSize(true)
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        }

        lifecycleScope.launchWhenCreated {
            try {
                val response =
                    RetrofitBuilder.pegawaiServices.getAllPegawai()

                withContext(Dispatchers.Main) {
                    progress_bar_activity_pegawai_list.visibility = View.INVISIBLE
                    recycler_view_activity_pegawai_list.visibility = View.VISIBLE
                    pegawaiListAdapter.setPegawaiList(response.result)
                }

            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@PegawaiListActivity, e.message, Toast.LENGTH_LONG)
                        .show()
                }
            }
        }
    }

    override fun onBackPressed() {
        startActivity(Intent(this@PegawaiListActivity, MainActivity::class.java))
        finish()
    }
}