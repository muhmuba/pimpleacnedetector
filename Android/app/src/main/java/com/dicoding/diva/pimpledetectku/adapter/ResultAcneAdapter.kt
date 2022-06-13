package com.dicoding.diva.pimpledetectku.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.diva.pimpledetectku.R
import com.dicoding.diva.pimpledetectku.api.AcneItems
import com.dicoding.diva.pimpledetectku.api.AcneItemsResult
import com.dicoding.diva.pimpledetectku.databinding.ItemRowResultBinding
import com.dicoding.diva.pimpledetectku.ui.hasil.HasilActivity


class ResultAcneAdapter(private val listResultAcne: List<AcneItemsResult>) : RecyclerView.Adapter<ResultAcneAdapter.ViewHolder>()  {
    private var listAcne: List<AcneItemsResult> = listResultAcne

    private lateinit var onItemClickCallback: ResultAcneAdapter.OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: ResultAcneAdapter.OnItemClickCallback){
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemRowResultBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listResultAcne[position])
    }

    override fun getItemCount(): Int = listResultAcne.size

    inner class ViewHolder(val binding: ItemRowResultBinding) : RecyclerView.ViewHolder(binding.root) {
        private var image: ImageView = itemView.findViewById(R.id.imageResult_iv)
        private var name: TextView = itemView.findViewById(R.id.result_name_tv)
        private var cause: TextView = itemView.findViewById(R.id.result_cause_tv)
        private var solution: TextView = itemView.findViewById(R.id.result_solution_tv)
        fun bind(item: AcneItemsResult) {
            Glide.with(itemView.context)
                .load(item.image)
                .centerCrop()
                .into(image)
            name.text = item.name
            cause.text = item.cause
            solution.text = item.solution

//            val intent = Intent(itemView.context, HasilActivity::class.java)
//            intent.putExtra("AcneItemsResult", item)
        }
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: AcneItemsResult)
    }


}