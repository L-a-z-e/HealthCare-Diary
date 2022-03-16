package gachon.example.p_project.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import gachon.example.p_project.R;
import gachon.example.p_project.galleryimage;

public class GalleryAdapter extends  RecyclerView.Adapter<GalleryAdapter.ViewHolder> {

    Context context;

   ArrayList<galleryimage> galleryimages=new ArrayList<>();

    public GalleryAdapter(){}
    public GalleryAdapter(Context context,ArrayList<galleryimage> galleryimages){
        this.galleryimages=galleryimages;
        this.context=context;

    }




    public class ViewHolder extends  RecyclerView.ViewHolder {
     ImageView gallery_img;

        public ViewHolder(View view) {
            super(view);

        gallery_img=(ImageView)view.findViewById(R.id.gallery_img);

        }



    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.galleryadapter, viewGroup, false);
        context=viewGroup.getContext();
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder myviewHolder, final int position) {

        ViewHolder myviewholder=(ViewHolder) myviewHolder;
        Picasso.with(context)
                .load("http://"+context.getString(R.string.ip)+":65000"+galleryimages.get(position).getImageurl())
                .resize(100,150)
                .into(myviewHolder.gallery_img);

    }

    @Override
    public int getItemCount() {
        return galleryimages.size();
    }
}
