package carrousel.leo.mx.presentation.viewholder

import android.view.View
import carrousel.leo.mx.model.Image
import kotlinx.android.synthetic.main.item_layout.view.*
import mx.leo.easyrecycler.viewholder.EasyItemViewHolder

/**
 * Created by Braulio on 17/12/2016.
 */
class ItemViewHolder(view: View) : EasyItemViewHolder(view) {

    fun bindItem(image : Image?){
        itemView.item_image.setImageURI(image?.uri, itemView.item_image.context)
    }
}