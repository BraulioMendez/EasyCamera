package carrousel.leo.mx.app

import android.app.Application
import com.facebook.drawee.backends.pipeline.Fresco

/**
 * Created by Leo on 27/12/16.
 */
class CarouselApp : Application() {
    override fun onCreate() {
        Fresco.initialize(this)
        super.onCreate()
    }
}