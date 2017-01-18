package rooksoto.c4q.nyc.memefactory.View.MemeFragments;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import rooksoto.c4q.nyc.memefactory.R;

public class VanillaMemeFragment extends Fragment {

    private int page;
    private String title;
    private Uri uri;

    // newInstance constructor for creating fragment with arguments
    public static VanillaMemeFragment newInstance(Uri uri, int page, String title) {
        VanillaMemeFragment vanillaMemeFragment = new VanillaMemeFragment();
        vanillaMemeFragment.uri = uri;
        Bundle args = new Bundle();
        args.putInt("someInt", page);
        args.putString("someTitle", title);
        vanillaMemeFragment.setArguments(args);
        return vanillaMemeFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        page = getArguments().getInt("someInt", 0);
        title = getArguments().getString("someTitle");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.vanilla_meme_fragment, container, false);
        ImageView memeImageView = (ImageView) view.findViewById(R.id.meme_imageview);
        EditText bottomTextEditor = (EditText) view.findViewById(R.id.bottom_text_editor);
        EditText topTextEditor = (EditText) view.findViewById(R.id.top_text_editor);

        // edittext buttons not showing up
        // Fix!

        Picasso.with(getContext()).load(uri).error(R.drawable.doge).into(memeImageView);


        return view;
    }
}
