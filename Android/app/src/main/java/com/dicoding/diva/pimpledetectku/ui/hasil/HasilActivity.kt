package com.dicoding.diva.pimpledetectku.ui.hasil

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.dicoding.diva.pimpledetectku.R
import com.dicoding.diva.pimpledetectku.ViewModelFactory
import com.dicoding.diva.pimpledetectku.adapter.ListAcneAdapter
import com.dicoding.diva.pimpledetectku.adapter.ResultAcneAdapter
import com.dicoding.diva.pimpledetectku.api.*
import com.dicoding.diva.pimpledetectku.databinding.ActivityHasilBinding
import com.dicoding.diva.pimpledetectku.ml.Model2
import com.dicoding.diva.pimpledetectku.model.ResultModel
import com.dicoding.diva.pimpledetectku.model.ResultPreference
import com.dicoding.diva.pimpledetectku.model.UserModel
import com.dicoding.diva.pimpledetectku.model.UserPreference
import com.dicoding.diva.pimpledetectku.ui.daftarJerawat.DaftarJerawatActivity
import com.dicoding.diva.pimpledetectku.ui.detail.DetailActivity
import com.dicoding.diva.pimpledetectku.ui.main.MainActivity
import com.dicoding.diva.pimpledetectku.ui.welcome.WelcomeActivity
import org.tensorflow.lite.DataType
import org.tensorflow.lite.support.image.ImageProcessor
import org.tensorflow.lite.support.image.TensorImage
import org.tensorflow.lite.support.image.ops.ResizeOp
import org.tensorflow.lite.support.label.Category
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class HasilActivity : AppCompatActivity() {
    private lateinit var binding : ActivityHasilBinding
    private lateinit var hasilViewModel: HasilViewModel

    private var getFile: File? = null

    val probabilityAsCategory = mutableListOf<Category>()

    private val tfImageProcesor by lazy{
        ImageProcessor.Builder()
            .add(ResizeOp(224, 224, ResizeOp.ResizeMethod.BILINEAR))
            .build()
    }
    private val imageTensor = TensorImage(DataType.FLOAT32)

    companion object {
        const val TAG = "HasilActivity"
        const val EXTRA_TOKEN = "extra_token"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHasilBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val actionBar = supportActionBar
        actionBar!!.title = getString(R.string.hasil)

        val myFile = intent.getSerializableExtra("image") as File
        getFile = myFile
        val result = BitmapFactory.decodeFile(getFile?.path)


        //load file txt
        val fileName = "label.txt"
        val labelFile = application.assets.open(fileName).bufferedReader().use{it.readText()}.split("\n")

        val resized = result.copy(Bitmap.Config.ARGB_8888, true)
        imageTensor.load(resized)

        val resultResized = tfImageProcesor.process(imageTensor)

        val model = Model2.newInstance(this)

        // Runs model inference and gets result.
        val outputs = model.process(resultResized.tensorBuffer)
        val outputFeature0 = outputs.outputFeature0AsTensorBuffer

        for (i in 0..outputFeature0.floatArray.size - 1){
            probabilityAsCategory.add(Category(labelFile[i], outputFeature0.floatArray[i]))
            Log.d("Hasil", outputFeature0.floatArray[i].toString() + " " + labelFile[i])
        }
        Log.d("Output", outputs.toString())

        val outputAcne = probabilityAsCategory.apply { sortByDescending { it.score } }

        binding.resultNameTv.text = outputAcne[0].label

        // Releases model resources if no longer used.

        model.close()

        binding.imageResultIv.setImageBitmap(result)


        binding.daftarJerawatBtn.setOnClickListener {
            val intent = Intent(this@HasilActivity, DaftarJerawatActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun showLoading(isLoading: Boolean){
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

}