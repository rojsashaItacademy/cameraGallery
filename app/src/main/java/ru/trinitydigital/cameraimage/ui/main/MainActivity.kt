package ru.trinitydigital.cameraimage.ui.main

import android.net.Uri
import android.os.Bundle
import android.util.Log
import com.google.android.material.shape.CornerFamily
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.trinitydigital.cameraimage.R
import ru.trinitydigital.cameraimage.common.BaseUserPhotoActivity
import ru.trinitydigital.cameraimage.common.pickPhotoFromGalleryWithPermissionCheck
import ru.trinitydigital.cameraimage.common.shootPhotoWithPermissionCheck
import java.io.File
import java.lang.Exception

class MainActivity : BaseUserPhotoActivity() {

    private val viewModel by viewModel<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupListeners()

        viewModel.authUser()

        val radius = resources.getDimension(R.dimen.imageRadius)
        image.shapeAppearanceModel = image.shapeAppearanceModel.toBuilder()
            .setTopLeftCorner(CornerFamily.ROUNDED, radius)
            .setTopRightCorner(CornerFamily.ROUNDED, radius)
            .setBottomLeftCorner(CornerFamily.ROUNDED, radius)
            .setBottomRightCorner(CornerFamily.ROUNDED, radius)
            .build()

        setupViewModel()


    }

    private fun setupViewModel() {
        viewModel.userData.observe(this, {
            Picasso.get()
                .load(it.avatar)
                .into(image, object : Callback {
                    override fun onSuccess() {
                        Log.d("adssadasd", "adasdasd")
                    }

                    override fun onError(e: Exception?) {
                        Log.d("adssadasd", "adasdasd")
                    }

                })
        })
    }

    private fun setupListeners() {
        camera.setOnClickListener {
            shootPhotoWithPermissionCheck()
        }

        gallery.setOnClickListener {
            pickPhotoFromGalleryWithPermissionCheck()
        }
    }

    override fun showPhoto(file: File) {
//        val bitmap = BitmapFactory.decodeFile(file.absolutePath)
//        image.setImageBitmap(bitmap)
        viewModel.updateUserWithPhoto(file)
    }

    override fun showPhoto1(file: Uri?) {

    }
}