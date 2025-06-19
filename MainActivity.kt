package com.yourpackage.practicumExam

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.practicumexam.R

class MainActivity : AppCompatActivity() {

    // --- Q.1.1: Declare four parallel arrays ---
    private val songTitles = ArrayList<String>()
    private val artistNames = ArrayList<String>()
    private val ratings = ArrayList<Int>()
    private val comments = ArrayList<String>()

    private lateinit var tvStatusMessage: TextView // To display messages to the user

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // --- Q.1.1: Good programming practices - get references to views ---
        val btnAddSong: Button = findViewById(R.id.btnAddSong)
        val btnViewDetails: Button = findViewById(R.id.btnViewDetails)
        val btnExitApp: Button = findViewById(R.id.btnExitApp)
        tvStatusMessage = findViewById(R.id.tvStatusMessage)

        // --- Initialize with example data (as per your requirement) ---
        // You only need to initialize if you're not adding through the UI initially
        // If you're strictly following "four songs" to be added *by the user*,
        // you might start with empty arrays and add them via the dialog.
        // For demonstration, I'll add them initially.
        addSongToList("Song A", "Artist A", 4, "Rap")
        addSongToList("Song B", "Artist B", 1, "Dance song")
        addSongToList("Song C", "Artist C", 2, "Best Love song")
        addSongToList("Song D", "Artist D", 3, "Memories")


        // --- Q.1.2: Add a button for "Add to Playlist" ---
        btnAddSong.setOnClickListener {
            showAddSongDialog()
        }

        // --- Q.1.2: Add a 2nd button that, when clicked, navigates to the second screen ---
        btnViewDetails.setOnClickListener {
            val intent = Intent(this, DetailedViewActivity::class.java).apply {
                // Pass data to the DetailedViewActivity
                putStringArrayListExtra("songTitles", songTitles)
                putStringArrayListExtra("artistNames", artistNames)
                putIntegerArrayListExtra("ratings", ratings)
                putStringArrayListExtra("comments", comments)
            }
            startActivity(intent)
        }

        // --- Q.1.2: Add a 3rd button to exit the app ---
        btnExitApp.setOnClickListener {
            finishAffinity()
        }
    }

    /**
     * Q.1.2: When the user clicks the button, the user must be asked to enter the details for the playlist.
     * Uses an AlertDialog for input.
     * Q.1.1: Create methods/functions required.
     * Q.1.1: Error handling for rating input.
     */
    @SuppressLint("SetTextI18n")
    private fun showAddSongDialog() {
        val dialogView = layoutInflater.inflate(R.layout.dialog_add_song, null)
        val etSongTitle = dialogView.findViewById<EditText>(R.id.etSongTitle)
        val etArtistName = dialogView.findViewById<EditText>(R.id.etArtistName)
        val etRating = dialogView.findViewById<EditText>(R.id.etRating)
        val etComments = dialogView.findViewById<EditText>(R.id.etComments)

        AlertDialog.Builder(this)
            .setTitle("Add New Song")
            .setView(dialogView)
            .setPositiveButton("Add") {dialog, _ ->
                val songTitle = etSongTitle.text.toString().trim()
                val artistName = etArtistName.text.toString().trim()
                val ratingString = etRating.text.toString().trim()
                val comment = etComments.text.toString().trim()


                if (songTitle.isEmpty() || artistName.isEmpty() || ratingString.isEmpty()) {
                    Toast.makeText(this, "Please fill in all required fields (Song, Artist, Rating)", Toast.LENGTH_LONG).show()
                    tvStatusMessage.text = "Error: Missing song details."
                    return@setPositiveButton
                }

                val rating = ratingString.toIntOrNull()
                if (rating == null || rating !in 1..5) {
                    Toast.makeText(this, "Rating must be a number between 1 and 5.", Toast.LENGTH_LONG).show()
                    tvStatusMessage.text = "Error: Invalid rating."
                    return@setPositiveButton
                }

                // Add the song if validation passes
                addSongToList(songTitle, artistName, rating, comment)
                Toast.makeText(this, "$songTitle added to playlist!", Toast.LENGTH_SHORT).show()
                tvStatusMessage.text = "Successfully added '$songTitle'."

                dialog.dismiss()
            }
            .setNegativeButton("Cancel") { dialog, _ ->
                dialog.cancel()
                tvStatusMessage.text = "Add song cancelled."
            }
            .show()
    }

    private fun addSongToList(title: String, artist: String, rating: Int, comment: String) {
        songTitles.add(title)
        artistNames.add(artist)
        ratings.add(rating)
        comments.add(comment)
    }


}
