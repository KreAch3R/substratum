/*
 * Copyright (c) 2016-2017 Projekt Substratum
 * This file is part of Substratum.
 *
 * Substratum is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Substratum is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Substratum.  If not, see <http://www.gnu.org/licenses/>.
 */

package projekt.substratum.adapters;

import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import projekt.substratum.R;
import projekt.substratum.config.References;
import projekt.substratum.model.ShowcaseItem;

public class ShowcaseItemAdapter extends RecyclerView.Adapter<ShowcaseItemAdapter.ViewHolder> {
    private ArrayList<ShowcaseItem> information;

    public ShowcaseItemAdapter(ArrayList<ShowcaseItem> information) {
        this.information = information;
    }

    @Override
    public ShowcaseItemAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout
                .showcase_entry_card, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, int pos) {
        final int position = viewHolder.getAdapterPosition();

        Glide.with(information.get(position).getContext())
                .load(information.get(position).getThemeIcon())
                .centerCrop()
                .crossFade()
                .into(viewHolder.imageView);

        Glide.with(information.get(position).getContext())
                .load(information.get(position).getThemeBackgroundImage())
                .centerCrop()
                .crossFade()
                .into(viewHolder.backgroundImageView);

        viewHolder.themeName.setText(information.get(position).getThemeName());
        viewHolder.themeAuthor.setText(information.get(position).getThemeAuthor());

        if (information.get(position).getThemePricing().toLowerCase()
                .equals(References.paidTheme)) {
            viewHolder.themePricing.setVisibility(View.VISIBLE);
        } else {
            viewHolder.themePricing.setVisibility(View.GONE);
        }

        if (References.isPackageInstalled(
                information.get(position).getContext(),
                information.get(position).getThemePackage())) {
            viewHolder.installedOrNot.setVisibility(View.VISIBLE);
        } else {
            viewHolder.installedOrNot.setVisibility(View.GONE);
        }

        String[] supported = information.get(position).getThemeSupport().split("\\|");
        List supported_array = Arrays.asList(supported);
        if (supported_array.contains(References.showcaseWallpapers)) {
            viewHolder.wallpaper.setAlpha((float) 1.0);
        } else {
            viewHolder.wallpaper.setAlpha((float) 0.2);
        }
        if (supported_array.contains(References.showcaseSounds)) {
            viewHolder.sounds.setAlpha((float) 1.0);
        } else {
            viewHolder.sounds.setAlpha((float) 0.2);
        }
        if (supported_array.contains(References.showcaseFonts)) {
            viewHolder.fonts.setAlpha((float) 1.0);
        } else {
            viewHolder.fonts.setAlpha((float) 0.2);
        }
        if (supported_array.contains(References.showcaseBootanimations)) {
            viewHolder.bootanimations.setAlpha((float) 1.0);
        } else {
            viewHolder.bootanimations.setAlpha((float) 0.2);
        }
        if (supported_array.contains(References.showcaseOverlays)) {
            viewHolder.overlays.setAlpha((float) 1.0);
        } else {
            viewHolder.overlays.setAlpha((float) 0.2);
        }

        viewHolder.cardView.setOnClickListener(view -> {
            try {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(information.get(position).getThemeLink()));
                information.get(position).getContext().startActivity(intent);
            } catch (Exception e) {
                e.printStackTrace();
                Snackbar.make(view,
                        information.get(position).getContext()
                                .getString(R.string.activity_missing_toast),
                        Snackbar.LENGTH_LONG)
                        .show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return information.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        TextView themeName;
        TextView themeAuthor;
        TextView installedOrNot;
        ImageView themePricing;
        ImageView imageView, backgroundImageView, wallpaper, sounds, fonts, bootanimations,
                overlays;

        ViewHolder(View view) {
            super(view);
            cardView = (CardView) view.findViewById(R.id.theme_card);
            themeName = (TextView) view.findViewById(R.id.theme_name);
            themeAuthor = (TextView) view.findViewById(R.id.theme_author);
            themePricing = (ImageView) view.findViewById(R.id.theme_pricing);
            imageView = (ImageView) view.findViewById(R.id.theme_icon);
            backgroundImageView = (ImageView) view.findViewById(R.id.background_image);
            wallpaper = (ImageView) view.findViewById(R.id.theme_wallpapers);
            sounds = (ImageView) view.findViewById(R.id.theme_sounds);
            fonts = (ImageView) view.findViewById(R.id.theme_fonts);
            bootanimations = (ImageView) view.findViewById(R.id.theme_bootanimations);
            overlays = (ImageView) view.findViewById(R.id.theme_overlays);
            installedOrNot = (TextView) view.findViewById(R.id.themeinstalled);
        }
    }
}