package com.example.tictactoe

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.GridLayout
import android.widget.ImageButton
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.view.*

class MainActivity : AppCompatActivity() {
    /*************** DECLARATIONS ***************/

    var playedCells = mutableMapOf<Int, String>(1 to "N", 2 to "N",3 to "N",4 to "N",5 to "N",6 to "N",7 to "N",8 to "N",9 to "N")
    var clicks = 0

    lateinit var player1: Player
    lateinit var player2: Player
    lateinit var activePlayer: Player
    lateinit var playersReferences :SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //RÉCUPÉRATION DES DONNÉES RELATIVES AUX DEUX JOUEURS ALLIGNÉS
        var name1 = intent.getStringExtra("player1")
        var name2 = intent.getStringExtra("player2")

        val bundle: Bundle = intent.extras!!
        var image1 = bundle.getInt("image1")
        var image2 = bundle.getInt("image2")

        /*var score1: Int = 0
        var score2: Int = 0*/

        // implement valeurs scores
        playersReferences = this.getSharedPreferences("players_reference", Context.MODE_PRIVATE)
        val recuperationPlayers = playersReferences.all.toList()

        var score1 = playersReferences.getInt(name1, 0)
        var score2 = playersReferences.getInt(name2, 0)
       /* for ((key,value) in recuperationPlayers){
            if(key == name1) {score1 = value as Int}
            if(key == name2) {score2 = value as Int}
        }*/

        // Création des objets players
        player1  = Player(name1, score1, image1)
        player2= Player(name2, score2, image2)

        // attribution du premier tour de jeu à joueur1
        activePlayer = player1

        Toast.makeText(this,"${activePlayer.name} a la main", Toast.LENGTH_LONG).show()

        // affichage nom des joueurs et scores
        player1name.text = player1.name
        player2name.text = player2.name
        scorePly1.text = player1.score.toString()
        scorePly2.text = player2.score.toString()


    }

    fun ticTac(view: View){
        var button = view as ImageButton
        var key = button.tag.toString().toInt()
        button.setBackgroundResource(activePlayer.image)
        playedCells.put(key, activePlayer.name.toString())
        if(!checkWinner()) {
            if (activePlayer == player1) {
                activePlayer = player2
            } else {
                activePlayer = player1
            }
        }

    }

    private fun checkWinner(): Boolean {
        var winner = false;
        clicks++
        if((playedCells.get(1) == playedCells.get(2) && playedCells.get(2) == playedCells.get(3) && !playedCells.get(1).equals("N"))
            || (playedCells.get(4) == playedCells.get(5) && playedCells.get(5) == playedCells.get(6) && !playedCells.get(4).equals("N"))
            || (playedCells.get(7) == playedCells.get(8) && playedCells.get(8) == playedCells.get(9) && !playedCells.get(7).equals("N"))
            || (playedCells.get(1) == playedCells.get(4) && playedCells.get(4) == playedCells.get(7) && !playedCells.get(1).equals("N"))
            || (playedCells.get(2) == playedCells.get(5) && playedCells.get(5) == playedCells.get(8) && !playedCells.get(2).equals("N"))
            || (playedCells.get(3) == playedCells.get(6) && playedCells.get(6) == playedCells.get(9) && !playedCells.get(3).equals("N"))
            || (playedCells.get(1) == playedCells.get(5) && playedCells.get(5) == playedCells.get(9) && !playedCells.get(1).equals("N"))
            || (playedCells.get(3) == playedCells.get(5) && playedCells.get(5) == playedCells.get(7) && !playedCells.get(3).equals("N"))){

            winner = true
        }
        if (winner) {
            activePlayer.score++
            if (activePlayer == player1) {
                scorePly1.text = activePlayer.score.toString()
            } else {
                scorePly2.text = activePlayer.score.toString()
            }

            Toast.makeText(this, "${activePlayer.name} gagne!!! FÉLICITATION", Toast.LENGTH_LONG).show()
            // Mettre à jour score dans SharedReferences

            val editeur = playersReferences.edit()
            editeur.putInt(activePlayer.name,activePlayer.score)
            editeur.commit()
            resetGame()
        }
        else if(clicks == 9){
            Toast.makeText(this, "Pas de gagnant!!!!", Toast.LENGTH_LONG).show()
            resetGame()
        }
        return winner
    }


    // Fonction d'initialisation du jeu pour engager de nouvelles manche
    fun resetGame(){
        toggleAllButtons(true)
        playedCells = mutableMapOf<Int, String>(1 to "N", 2 to "N",3 to "N",4 to "N",5 to "N",6 to "N",7 to "N",8 to "N",9 to "N")
        clicks = 0
    }

    fun toggleAllButtons(value: Boolean){
        for (i in 0 until findViewById<ConstraintLayout>(R.id.layout).getChildCount()) {
            val v: View = layout.getChildAt(i)
            if (v is GridLayout) {
                //validate your EditText here
                for (j in 0 until v.getChildCount()){
                    val x: View = v.getChildAt(j)
                    if (x is ImageButton){
                        x.isEnabled = value
                        if(value){
                            x.setBackgroundResource(R.drawable.button_border)
                        }
                    }
                }
            }
        }
    }
}