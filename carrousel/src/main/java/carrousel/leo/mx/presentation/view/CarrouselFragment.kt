package carrousel.leo.mx.presentation.view

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import carrousel.leo.mx.R
import carrousel.leo.mx.presentation.adapter.CarrouselAdapter
import kotlinx.android.synthetic.main.carrousel_layout.*
import pub.devrel.easypermissions.EasyPermissions

class CarrouselFragment : Fragment(), EasyPermissions.PermissionCallbacks {

    var carrouselAdapter: CarrouselAdapter

    init{
        carrouselAdapter = CarrouselAdapter()
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.carrousel_layout,container,false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        configCarrousel()
    }

    fun configCarrousel(){
        val orientationManager = LinearLayoutManager(activity)
        orientationManager.orientation = LinearLayoutManager.HORIZONTAL
        carrousel.layoutManager = orientationManager
        carrousel.adapter = carrouselAdapter
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>?) {
        throw UnsupportedOperationException("not implemented")
    }

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>?) {
        throw UnsupportedOperationException("not implemented")
    }
}