package rooksoto.c4q.nyc.memefactory.View.MemeFragments;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import rooksoto.c4q.nyc.memefactory.R;

/**
 * Created by ashiquechowdhury on 1/21/17.
 */
public class StickerViewHolder extends RecyclerView.ViewHolder {
    private ImageView myStickerImage;

    public StickerViewHolder(View itemView) {
        super(itemView);
        myStickerImage  = (ImageView) itemView.findViewById(R.id.sticker_imagev);

    }

    public void bind(Integer integer) {
        myStickerImage.setImageResource(integer);
    }
}
