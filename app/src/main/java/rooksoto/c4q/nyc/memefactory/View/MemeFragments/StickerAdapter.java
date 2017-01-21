package rooksoto.c4q.nyc.memefactory.View.MemeFragments;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Arrays;
import java.util.List;

import rooksoto.c4q.nyc.memefactory.R;

/**
 * Created by ashiquechowdhury on 1/21/17.
 */
public class StickerAdapter extends RecyclerView.Adapter<StickerViewHolder> {
    private Listener mListener;
    private List<Integer> stickerDrawableList = Arrays.asList(R.drawable.smiley_face, R.drawable.devil_imp, R.drawable.angry_face, R.drawable.lion, R.drawable.mouse, R.drawable.panda, R.drawable.kiss_face);

    public StickerAdapter(Listener mListener) {
        this.mListener = mListener;
    }

    @Override
    public StickerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.sticker_box, parent, false);
        return new StickerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(StickerViewHolder holder, final int position) {
        holder.bind(stickerDrawableList.get(position));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onStickerPressed(stickerDrawableList.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return stickerDrawableList.size();
    }

    public interface Listener {
        void onStickerPressed(int stickerDrawable);
    }
}
