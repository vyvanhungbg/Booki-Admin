package com.atom.android.bookshop.utils

import android.net.Uri
import android.widget.ImageView
import com.atom.android.bookshop.R
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView

fun ImageView.loadImage(uri: Uri) {
    Picasso
        .get()
        .load(uri)
        .resize(Constants.SIZE_IMAGE, Constants.SIZE_IMAGE)
        .centerCrop()
        .placeholder(R.drawable.img_logo_400)
        .error(R.drawable.img_logo_400)
        .into(this)
}

fun CircleImageView.loadUserImage(uri: Uri) {
    Picasso
        .get()
        .load(uri)
        .resize(Constants.SIZE_IMAGE, Constants.SIZE_IMAGE)
        .centerCrop()
        .placeholder(R.drawable.img_user_default)
        .error(R.drawable.img_user_default)
        .into(this)
}
