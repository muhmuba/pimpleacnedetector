package com.dicoding.diva.pimpledetectku.adapter

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.diva.pimpledetectku.R
import com.dicoding.diva.pimpledetectku.api.AcneItems
import com.dicoding.diva.pimpledetectku.databinding.ItemRowBinding
import com.dicoding.diva.pimpledetectku.ui.detail.DetailActivity

class ListAcneAdapter(private val listAcnes: ArrayList<AcneItems>) : RecyclerView.Adapter<ListAcneAdapter.ViewHolder>() {

    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback){
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemRowBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listAcnes[position])
//        val acne = listAcnes[position]
//        holder.bind(acne)
//        holder.bind(acne)
//        holder.itemView.setOnClickListener { onItemClickCallback.onItemClicked(listAcnes[holder.adapterPosition]) }
    }

    override fun getItemCount(): Int = listAcnes.size

    inner class ViewHolder(val binding: ItemRowBinding) : RecyclerView.ViewHolder(binding.root) {
        private var image: ImageView = itemView.findViewById(R.id.imageView)
        private var name: TextView = itemView.findViewById(R.id.name_tv)
        private var description: TextView = itemView.findViewById(R.id.description_tv)
        fun bind(item: AcneItems) {
            Glide.with(itemView.context)
                .load(item.image)
                .centerCrop()
                .into(image)
            name.text = item.name
            description.text = item.description
            itemView.setOnClickListener {
                val intent = Intent(itemView.context, DetailActivity::class.java)
                intent.putExtra("AcneItems", item)

                val optionsCompat: ActivityOptionsCompat =
                    ActivityOptionsCompat.makeSceneTransitionAnimation(
                        itemView.context as Activity,
                        Pair(image, "image"),
                        Pair(name, "name"),
                        Pair(description, "description"),
                    )
                itemView.context.startActivity(intent, optionsCompat.toBundle())
            }
        }
    }

//        fun bind(item: AcneItems) {
//            binding.apply {
//
////                causeTv.text = item.cause
////                solutionTv.text = item.solution
//                itemView.setOnClickListener {
//
//
////                        val optionsCompat: ActivityOptionsCompat =
////                            ActivityOptionsCompat.makeSceneTransitionAnimation(
////                                itemView.context as Activity,
////                                Pair(imageView, "image"),
////                                Pair(nameTv, "name"),
////                                Pair(descriptionTv, "description"),
////                            )
////                        itemView.context.startActivity(intent, optionsCompat.toBundle())
//                }
////            binding.apply {
////                val detailStories = AcneItems(item.id, item.type, item.name, item.description, item.cause, item.solution, item.image, item.reference, item.created_at, item.updated_at)
////
////
//////                itemView.setOnClickListener {
//////                    val intent = Intent(itemView.context, DetailActivity::class.java)
//////                    intent.putExtra("AcneItems", item)
//////                }
////                }
////            }
//
//            }

//        var imgAcne: ImageView = itemView.findViewById(R.id.imageView)
//        var nameTv: TextView = itemView.findViewById(R.id.name_tv)
//        var desctiptionTv: TextView = itemView.findViewById(R.id.description_tv)
//        var causeTv: TextView = itemView.findViewById(R.id.cause_tv)
//        var solutionTv: TextView = itemView.findViewById(R.id.solution_tv)
//        }

    interface OnItemClickCallback {
        fun onItemClicked(data: AcneItems)
    }
}