package com.dicoding.pegawaiapp.ui.adapter

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.pegawaiapp.R
import com.dicoding.pegawaiapp.model.Pegawai
import com.dicoding.pegawaiapp.ui.PegawaiActivity
import com.dicoding.pegawaiapp.ui.PegawaiActivity.Companion.EXTRA_ID_PEGAWAI
import kotlinx.android.synthetic.main.item_pegawai_list.view.*

class PegawaiListAdapter(private val onPegawaiClicked : (pegawaiId : Int?) -> Unit) : RecyclerView.Adapter<PegawaiListAdapter.PegawaiListViewHolder>() {
    private val pegawaiList : MutableList<Pegawai> = ArrayList()

    fun setPegawaiList(pegawaiList: List<Pegawai>) {
        this.pegawaiList.clear()
        this.pegawaiList.addAll(pegawaiList)
        this.notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PegawaiListViewHolder =
        PegawaiListViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_pegawai_list, parent, false)
        )

    override fun onBindViewHolder(holder: PegawaiListViewHolder, position: Int) {
        holder.bind(pegawaiList[position])
    }

    override fun getItemCount(): Int = pegawaiList.size

    inner class PegawaiListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(pegawai: Pegawai) {
            with(itemView) {
                tv_id_pegawai.text = "Id : ${pegawai.id.toString()}"
                tv_nama_pegawai.text = pegawai.nama

                setOnClickListener {
                    onPegawaiClicked(pegawai.id)
                }
            }
        }
    }
}