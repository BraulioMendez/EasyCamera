package carrousel.leo.mx.presentation.view

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AlertDialog
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import carrousel.leo.mx.R
import carrousel.leo.mx.model.Image
import carrousel.leo.mx.presentation.adapter.CarrouselAdapter
import carrousel.leo.mx.presentation.presenter.CarouselPresenter
import carrousel.leo.mx.presentation.presenter.CarouselPresenter.RESULT_CODES
import kotlinx.android.synthetic.main.carrousel_layout.*
import mx.leo.easyrecycler.util.RecyclerViewHeaderClickListener
import mx.leo.easyrecycler.util.RecyclerViewItemClickListener
import mx.leo.easyrecycler.util.extensions.onHeaderAndItemClickListener
import pub.devrel.easypermissions.EasyPermissions

class CarrouselFragment : Fragment(), EasyPermissions.PermissionCallbacks {

    var carrouselAdapter: CarrouselAdapter
    var carouselPresenter: CarouselPresenter
    var carouseView: CarouselView

    init {
        carrouselAdapter = CarrouselAdapter()
        carouseView = object : CarouselView {
            override fun showImage(image: Image?) {
                carrouselAdapter.addItem(image)
            }
        }

        carouselPresenter = CarouselPresenter(this, carouseView)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View?
            = inflater?.inflate(R.layout.carrousel_layout, container, false)


    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        configCarrousel()
    }

    fun configCarrousel() {
        val orientationManager = LinearLayoutManager(activity)
        orientationManager.orientation = LinearLayoutManager.HORIZONTAL
        carrousel.layoutManager = orientationManager
        carrousel.adapter = carrouselAdapter
        carrousel.onHeaderAndItemClickListener(object : RecyclerViewHeaderClickListener.OnHeaderClickListener {
            override fun onHeaderClick() {
                showImageOption()
            }

        }, object : RecyclerViewItemClickListener.OnItemClickListener {
            override fun onItemClick(view: View?, position: Int?) {

            }
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        carouselPresenter.activityResult(requestCode, resultCode, data)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }

    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>?) {

    }

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>?) {
        when (requestCode) {
            RESULT_CODES.READ_PERMISSION_CODE -> carouselPresenter.openGallery()
            RESULT_CODES.CAMERA_AND_STORAGE_PERMISSION_CODE -> carouselPresenter.openCamera()
        }
    }

    fun showImageOption() {
        val imageOptionsDialog = AlertDialog.Builder(activity);
        imageOptionsDialog.setTitle(getString(R.string.image_options_title))
        imageOptionsDialog.setItems(R.array.image_options, {
            dialogInterface, position ->
            when (position) {
                0 -> carouselPresenter.openCamera()
                1 -> carouselPresenter.openGallery()
            }
        })
        imageOptionsDialog.show()
    }
}