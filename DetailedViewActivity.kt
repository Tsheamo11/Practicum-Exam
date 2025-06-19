mport android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.practicumexam.R

class DetailedViewActivity : AppCompatActivity() {

    private lateinit var songTitles: ArrayList<String>
    private lateinit var artistNames: ArrayList<String>
    private lateinit var ratings: ArrayList<Int>
    private lateinit var comments: ArrayList<String>

    private lateinit var tvSongList: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detailed_view)

        // Retrieve data from the Intent
        songTitles = intent.getStringArrayListExtra("songTitles") ?: ArrayList()
        artistNames = intent.getStringArrayListExtra("artistNames") ?: ArrayList()
        ratings = intent.getIntegerArrayListExtra("ratings") ?: ArrayList()
        comments = intent.getStringArrayListExtra("comments") ?: ArrayList()

        val btnDisplaySongs: Button = findViewById(R.id.btnDisplaySongs)
        val btnCalculateAverage: Button = findViewById(R.id.btnCalculateAverage)
        val btnReturnToMain: Button = findViewById(R.id.btnReturnToMain)
        tvSongList = findViewById(R.id.tvSongList)

        // --- Q.1.3: Add a button that when clicked displays the list of songs with details using a loop ---
        btnDisplaySongs.setOnClickListener {
            displaySongs()
        }

        // --- Q.1.3: Add a button that when clicked calculates and displays the average rating using a loop ---
        btnCalculateAverage.setOnClickListener {
            calculateAndDisplayAverageRating()
        }

        // --- Q.1.3: Add a button to return to the main screen ---
        btnReturnToMain.setOnClickListener {
            finish() // This will pop the current activity off the stack and return to the previous one (MainActivity)
        }
    }

    /**
     * Q.1.3: Displays the list of songs with corresponding details using a loop.
     * Q.1.1: Create methods/functions required.
     */
    @SuppressLint("SetTextI18n")
    private fun displaySongs() {
        if (songTitles.isEmpty()) {
            tvSongList.text = "No songs in the playlist yet."
            return
        }

        val stringBuilder = StringBuilder()
        // Q.1.1: Correct implementation of loops
        for (i in songTitles.indices) {
            stringBuilder.append("Song: ${songTitles[i]}\n")
            stringBuilder.append("Artist: ${artistNames[i]}\n")
            stringBuilder.append("Rating: ${ratings[i]}/5\n")
            stringBuilder.append("Comments: ${comments[i]}\n")
            stringBuilder.append("\n-------------------------\n\n") // Separator
        }
        tvSongList.text = stringBuilder.toString()
    }

    /**
     * Q.1.3: Calculates and displays the average rating for the songs using a loop.
     * Q.1.1: Accuracy in calculating and displaying the average rating.
     * Q.1.1: Create methods/functions required.
     */
    @SuppressLint("SetTextI18n")
    private fun calculateAndDisplayAverageRating() {
        if (ratings.isEmpty()) {
            tvSongList.text = "No ratings available to calculate an average."
            return
        }

        var totalRating = 0.0
        // Q.1.1: Correct implementation of loops
        for (rating in ratings) {
            totalRating += rating
        }

        val averageRating = totalRating / ratings.size
        val formattedAverage = String.format("%.2f", averageRating) // Format to 2 decimal places

        tvSongList.text = "Average Rating: $formattedAverage / 5"
        Toast.makeText(this, "Average rating calculated: $formattedAverage", Toast.LENGTH_SHORT).show()
    }
