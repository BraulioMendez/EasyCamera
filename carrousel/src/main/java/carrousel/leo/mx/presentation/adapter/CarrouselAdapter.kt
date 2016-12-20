package carrousel.leo.mx.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import carrousel.leo.mx.R
import carrousel.leo.mx.model.Image
import carrousel.leo.mx.presentation.viewholder.HeaderViewHolder
import carrousel.leo.mx.presentation.viewholder.ItemViewHolder
import mx.leo.easyrecycler.adapter.EasyHeaderAdapter
import mx.leo.easyrecycler.viewholder.EasyHeaderViewHolder
import mx.leo.easyrecycler.viewholder.EasyItemViewHolder
import mx.leo.easyrecycler.viewholder.EasyViewHolder

/**
 * Created by Braulio on 17/12/2016.
 */
class CarrouselAdapter : EasyHeaderAdapter<Image>() {

    override fun onBindHeaderViewHolder(headerHolder: EasyViewHolder) {
        throw UnsupportedOperationException("not implemented")
    }

    override fun onBindItemViewHolder(holder: EasyViewHolder, item: Image, position: Int) {
        throw UnsupportedOperationException("not implemented")
    }

    override fun createHeaderViewHolder(parent: ViewGroup?): EasyHeaderViewHolder {
        return HeaderViewHolder(LayoutInflater.from(parent?.context).inflate(R.layout.header_layout, parent, false))
    }

    override fun createItemViewHolder(parent: ViewGroup?): EasyItemViewHolder {
        return ItemViewHolder(LayoutInflater.from(parent?.context).inflate(R.layout.item_layout, parent, false))
    }
}