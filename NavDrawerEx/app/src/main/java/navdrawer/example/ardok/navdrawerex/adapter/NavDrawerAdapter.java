package navdrawer.example.ardok.navdrawerex.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

import navdrawer.example.ardok.navdrawerex.R;
import navdrawer.example.ardok.navdrawerex.model.NavDrawerLink;

/**
 * Created by ardokusuma on 1/3/15.
 */
public class NavDrawerAdapter extends RecyclerView.Adapter<NavDrawerAdapter.ViewHolder> {
    List<NavDrawerLink> data = Collections.emptyList();
    private LayoutInflater inflater;

    public NavDrawerAdapter(Context context, List<NavDrawerLink> data) {
        inflater = LayoutInflater.from(context);
        this.data = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        ViewHolder holder = null;
        switch (viewType) {
            case ViewType.HEADER_IMG:
                view = inflater.inflate(R.layout.nav_drawer_top_img, parent, false);
                holder = new ViewHolder(view);
                break;
            default:
                view = inflater.inflate(R.layout.nav_drawer_row, parent, false);
                holder = new ViewHolder(view);
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        switch (position) {
            case ViewType.HEADER_IMG:
                return;
            default:
                NavDrawerLink current = data.get(position);
                holder.bindViewHolder(current);
        }
    }

    @Override
    public int getItemViewType(int position) {
        // for example
        // Note that unlike ListView adapters, types need not be contiguous
        switch (position) {
            case 0:
                return ViewType.HEADER_IMG;
            default:
                return ViewType.ICON_TEXT;
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        ImageView icon;

        public ViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.titleTV);
            icon = (ImageView) itemView.findViewById(R.id.iconIV);
        }

        public void bindViewHolder(NavDrawerLink link) {
            title.setText(link.title);
            icon.setImageResource(link.iconId);
        }
    }

    class ViewType {
        public static final int HEADER_IMG = 0;
        public static final int ICON_TEXT = 1;
    }
}