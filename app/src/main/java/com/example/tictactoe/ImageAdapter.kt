package com.example.tictactoe

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

// Class Adapter pour afficher les images

class ImageAdapter(val listImage : ArrayList<ImageGame>) : RecyclerView.Adapter<ImageAdapter.ViewHolder>(){

    private lateinit var mListener: onItemClickListener

    interface onItemClickListener{
        fun OnItemClick(position: Int)
    }

    fun setOnItemClickListener(listener: onItemClickListener){
        mListener = listener
    }

    class ViewHolder(imageView: View, listener: onItemClickListener): RecyclerView.ViewHolder(imageView){
        val image_name = imageView.findViewById<TextView>(R.id.imageName)
        val image_game = imageView.findViewById<ImageView>(R.id.imageJeu)

        init {
            itemView.setOnClickListener {
                listener.OnItemClick(adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val  imageView = inflater.inflate(R.layout.image_rv, parent, false)
        return ViewHolder(imageView, mListener)
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val imageGame : ImageGame = listImage.get(position)
        holder.image_name.text = imageGame.name
        holder.image_game.setImageResource(imageGame.image)
    }
    override fun getItemCount(): Int {
        return listImage.size
    }
}