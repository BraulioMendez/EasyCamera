package carrousel.leo.mx.presentation.presenter

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import android.support.v4.app.Fragment
import android.support.v4.content.FileProvider
import carrousel.leo.mx.R
import carrousel.leo.mx.model.Image
import carrousel.leo.mx.presentation.view.CarouselView
import pub.devrel.easypermissions.EasyPermissions
import java.io.File

/**
 * Created by Leo on 29/12/16.
 */

open class CarouselPresenter(var fragment: Fragment? = null, var carouselView: CarouselView? = null) {

    var imagePath : String

    init {
        imagePath = ""
    }

    object FILE_NAMES {
    val PICTURES = "Images"
    }

    object RESULT_CODES {
        val GALLERY_CODE = 0x001
        val CAMERA_CODE = 0x002
        val READ_PERMISSION_CODE = 0x020
        val CAMERA_AND_STORAGE_PERMISSION_CODE = 0x21
    }

    fun openCamera() {
        val permissions = arrayOf(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)
        if (EasyPermissions.hasPermissions(fragment?.context!!, permissions.toString())) {
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            if (intent.resolveActivity(fragment?.activity?.packageManager) != null) {
                val photoFile = createFile()
                if (photoFile != null) {
                    imagePath = photoFile.path
                    addImageToGallery(imagePath)
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile))
                    fragment?.startActivityForResult(intent, RESULT_CODES.CAMERA_CODE)
                }
            }
        } else {
            EasyPermissions.requestPermissions(fragment!!, fragment?.getString(R.string.camera_message).toString(),
                    RESULT_CODES.CAMERA_AND_STORAGE_PERMISSION_CODE, Manifest.permission.CAMERA)
        }
    }

    fun createFile(): File? {
        val file = File(Environment.getExternalStorageDirectory(), CarouselPresenter.FILE_NAMES.PICTURES)
        var isDirectoryCreated = file.exists()

        if (!isDirectoryCreated)
            isDirectoryCreated = file.mkdirs()

        if (isDirectoryCreated) {
            val timestamp = System.currentTimeMillis() / 1000
            val imageName = timestamp.toString() + ".jpg"

            imagePath = Environment.getExternalStorageDirectory().toString() + File.separator + CarouselPresenter.FILE_NAMES.PICTURES + File.separator + imageName
            val newFile = File(imagePath)
            return newFile
        } else {
            return null
        }
    }

    fun addImageToGallery(path : String) {
        val intent = Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE)
        val file = File(path)
        val uri = Uri.fromFile(file)
        intent.data = uri
        fragment?.activity!!.sendBroadcast(intent)
    }

    fun openGallery() {
        if (EasyPermissions.hasPermissions(fragment?.context!!, Manifest.permission.READ_EXTERNAL_STORAGE)) {
            val intent = Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            fragment?.startActivityForResult(intent, RESULT_CODES.GALLERY_CODE)
        } else {
            EasyPermissions.requestPermissions(fragment!!, fragment?.getString(R.string.gallery_image_message).toString(),
                    RESULT_CODES.READ_PERMISSION_CODE, Manifest.permission.READ_EXTERNAL_STORAGE)
        }
    }

    fun activityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode) {
            RESULT_CODES.GALLERY_CODE -> {
                val image = Image(uri = data?.data)
                carouselView?.showImage(image)
            }
            RESULT_CODES.CAMERA_CODE -> {
                val image = Image(uri = FileProvider.getUriForFile(fragment?.activity, "carrousel.leo.mx.fileprovider",
                        File(imagePath)))
                carouselView?.showImage(image)
            }
        }
    }
}
