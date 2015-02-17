package com.rubenmayayo.picassoandpalette;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

import static android.support.v7.graphics.Palette.*;


public class MainActivity extends ActionBarActivity implements PaletteAsyncListener {

    @InjectView(R.id.image_view) ImageView imageView;
    @InjectView(R.id.toolbar) Toolbar toolbar;
    @InjectView(R.id.content) View content;
    @InjectView(R.id.edit_text) EditText editText;

    private static String imageURL = "http://i.imgur.com/HUVqmm0l.jpg";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.inject(this);
        setSupportActionBar(toolbar);

        editText.setText(imageURL);

    }

    @OnClick(R.id.button)
    public void load() {

        Target target = new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {

                imageView.setImageBitmap(bitmap);
                Palette.generateAsync(bitmap, MainActivity.this);

            }

            @Override
            public void onBitmapFailed(Drawable errorDrawable) {
            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {
            }
        };

        Picasso.with(this)
                .load(editText.getText().toString())
                .into(target);
    }


    @Override
    public void onGenerated(Palette palette) {

        if (palette != null) {

            Palette.Swatch vibrantSwatch = palette.getVibrantSwatch();
            Palette.Swatch darkVibrantSwatch = palette.getDarkVibrantSwatch();
            Palette.Swatch lightSwatch = palette.getLightVibrantSwatch();

            if (darkVibrantSwatch != null) {
                content.setBackgroundColor(darkVibrantSwatch.getRgb());
                toolbar.setBackgroundColor(darkVibrantSwatch.getRgb());
                toolbar.setTitleTextColor(darkVibrantSwatch.getTitleTextColor());

            }
            else if (vibrantSwatch != null) {

                content.setBackgroundColor(vibrantSwatch.getRgb());
                toolbar.setBackgroundColor(vibrantSwatch.getRgb());
                toolbar.setTitleTextColor(vibrantSwatch.getTitleTextColor());

            }
            else if (lightSwatch != null) {

                content.setBackgroundColor(lightSwatch.getRgb());
                toolbar.setBackgroundColor(lightSwatch.getRgb());
                toolbar.setTitleTextColor(lightSwatch.getTitleTextColor());
            }
        }
        else {
            Toast.makeText(this, "No palette!", Toast.LENGTH_SHORT);
        }

    }
}