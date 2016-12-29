package carrousel.leo.mx.presentation.presenter

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.support.v4.app.Fragment
import android.util.Log
import carrousel.leo.mx.R
import carrousel.leo.mx.model.Image
import carrousel.leo.mx.presentation.view.CarouselView
import pub.devrel.easypermissions.EasyPermissions

/**
 * Created by Leo on 29/12/16.
 */

open class CarouselPresenter(var fragment: Fragment? = null, var carouselView:CarouselView? = null) {

    object RESULT_CODES {
        val GALLERY_CODE = 0x001
        val READ_PERMISSION_CODE  = 0x020
    }

    fun openGallery() {
        if(EasyPermissions.hasPermissions(fragment?.context!!, Manifest.permission.READ_EXTERNAL_STORAGE)) {
            val intent = Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            fragment?.startActivityForResult(intent, RESULT_CODES.GALLERY_CODE)
        }else {
            EasyPermissions.requestPermissions(fragment!!,fragment?.getString(R.string.gallery_image_message).toString(),RESULT_CODES.READ_PERMISSION_CODE, Manifest.permission.READ_EXTERNAL_STORAGE)
        }
    }

    fun activityResult(requestCode: Int, resultCode: Int, data: Intent?) {
       if(requestCode == RESULT_CODES.GALLERY_CODE){
           val image = Image(uri = data?.data)
            carouselView?.showImage(image)
       }
    }
}