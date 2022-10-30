package com.example.tictactoe

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

// Class Adapter pour Afficher les infos de chaque joueur suivant player_rv.xml

class PlayerAdapter( val listPlayer : ArrayList<Player>) : RecyclerView.Adapter<PlayerAdapter.ViewHolder>(){

    private lateinit var mListner : onItemClickListner

    interface  onItemClickListner{
        fun onItemClick(position: Int){

        }
    }

    fun setOnItemClickListner(listener: onItemClickListner){
        mListner = listener
    }

    class ViewHolder(playerView: View, listener: onItemClickListner): RecyclerView.ViewHolder(playerView){

        val plyerId = playerView.findViewById<TextView>(R.id.nom_home)
        val plyerScore = playerView.findViewById<TextView>(R.id.pointage_home)

        init {
            itemView.setOnClickListener {
                listener.onItemClick(adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val inflater = LayoutInflater.from(parent.context)
        val playerView = inflater.inflate(R.layout.player_rv, parent, false)
        return  ViewHolder(playerView, mListner)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val player : Player = listPlayer.get(position)


        holder.plyerId.text = player.name
        holder.plyerScore.text = player.score.toString()

    }

    override fun getItemCount(): Int {
        return  listPlayer.size
    }

    public interface ItemClickListner{
        fun onItemClick(players: Player)
    }
}