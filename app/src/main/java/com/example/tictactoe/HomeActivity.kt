package com.example.tictactoe

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {
    // joueurs selectionnés pour jouer
    lateinit var selectedPlayer: Player
    lateinit var player1: Player
    lateinit var player2: Player
    lateinit  var playersReferences : SharedPreferences
    // décpompte joueurs séléctionnés
    var selectedPlayers: Int = 0

    // liste objets joueurs
    val players :ArrayList<Player> = ArrayList()
    // liste joueurs disponible
    val allignedPlayers:ArrayList<Player> = ArrayList()
    // Adapter Joueurs
    val adapter = PlayerAdapter(players)

    // liste objets Image
    val img1 : ImageGame = ImageGame("Cross",R.drawable.cross )
    val img2 : ImageGame = ImageGame("Circle",  R.drawable.circle)
    val img3 : ImageGame = ImageGame("Ying-Yang",  R.drawable.ying_yang)
    val img4 : ImageGame = ImageGame("Pirate",  R.drawable.pirate)
    val images :ArrayList<ImageGame> = ArrayList()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        // récupération des données joueurs et les transferer à la liste player pour affichage
        playersReferences = this.getSharedPreferences("players_reference", Context.MODE_PRIVATE)
        val recuperationPlayers = playersReferences.all.toList()

        for ((player, value) in recuperationPlayers){

            val newPlayer = Player(player.toString(),value as Int)
            players.add(newPlayer)
        }

        // inplément liste images
        images.add(img1)
        images.add(img2)
        images.add(img3)
        images.add(img4)
        // Adapter images
        val adapterImg = ImageAdapter (images)

        //Activer adapter noms joueurs
        playersNames_rv.adapter = adapter



        // SELECTION NOM DU JOUEUR
        adapter.setOnItemClickListner(object : PlayerAdapter.onItemClickListner{
            override fun onItemClick(position: Int) {
                //Parametrage liste symboles
                buttonAdd.isClickable = false
                name_Txt.text = "Nom"
                value_Txt.text = "Symbole"

                //Activer adapter images
                playersNames_rv.adapter = adapterImg

                //definition joueur Séléctionné
                selectedPlayer = players[position]

                //enlever le joueur de la liste players pour affichage
                players.removeAt(position)
                // admission d'un joueur pour la partie
                selectedPlayers++

                // Affectation Joueurs
                when(selectedPlayers){
                    1 -> {
                        player1 = selectedPlayer
                    }
                    2 -> {
                        player2 = selectedPlayer
                    }
                }

                // Séléction de l"image
                adapterImg.setOnItemClickListener(object : ImageAdapter.onItemClickListener{
                    override fun OnItemClick(position: Int) {

                        when(selectedPlayers){
                            1 -> {
                                // parametrage Affichage liste joueurs
                                name_Txt.text = "Prenom"
                                value_Txt.text = "Score"
                                buttonAdd.isClickable = true
                                // Attribution de l'image choisie
                                player1.image = images[position].image
                                // enlever l'image selectionnée de la liste images à afficher
                                images.removeAt(position)
                                //Activer adapter noms joueurs
                                playersNames_rv.adapter = adapter
                            }
                            2 -> {
                                player2.image = images[position].image

                                // DIRECTION JEU
                                val intentToMainActivity =Intent(this@HomeActivity, MainActivity::class.java)
                                intentToMainActivity.putExtra("image1",player1.image)
                                intentToMainActivity.putExtra("image2",player2.image)
                                intentToMainActivity.putExtra("player1",player1.name)
                                intentToMainActivity.putExtra("player2",player2.name)

                                intent.setType("");
                                startActivity(intentToMainActivity)
                            }
                        }
                    }
                })
            }
        })

        playersNames_rv.layoutManager = LinearLayoutManager(this)
    }

    // Ajout de joueurs

    fun addPlayer(view: View){
        val player : String = nameText.text.toString()

        // securité pour obligation d'entrer nom du joueur
        if(player.trim() == ""){
            Toast.makeText(this,"Il faut rajouter un nom pour sauvegarder un nouveau joueur", Toast.LENGTH_LONG).show()
        }

        // creation Objet nouveau joueur et afficher son nom
        else{
            // Enregistrer joueur
            var score : Int = 0
            val editeur = playersReferences.edit()
            editeur.putInt(player,score)
            editeur.commit()
            // Ajouter le joueur à la liste d'affichage
            val newPlayer = Player(player)
            players.add(newPlayer)

            nameText.text.clear()
            nameText.requestFocus()

            // Rafréchir l'adapteur
            adapter.notifyDataSetChanged()


        }
    }

    fun Clearreferences(view: View){
        playersReferences.edit().clear().commit()
    }
}



