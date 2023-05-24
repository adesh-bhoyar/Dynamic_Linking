package com.cluematrix.dynamiclinking;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.dynamiclinks.DynamicLink;
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks;
import com.google.firebase.dynamiclinks.ShortDynamicLink;

public class MainActivity extends AppCompatActivity {
    public static final String SHARABLE_LINK = "https://btlapp.page.link";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void btnShare(View view) {
        FirebaseDynamicLinks.getInstance().createDynamicLink()
                .setLink(Uri.parse("https://www.btlapp.com/product/" +"1"))
                .setDomainUriPrefix(SHARABLE_LINK)
                .setSocialMetaTagParameters(new DynamicLink.SocialMetaTagParameters.Builder()
                        .setTitle("mProduct.getProductName()")
                        .setDescription("mProduct.getSummery()")
                        .build())
                .setAndroidParameters(new DynamicLink.AndroidParameters.Builder().build())
                .buildShortDynamicLink().addOnSuccessListener(new OnSuccessListener<ShortDynamicLink>() {
                    @Override
                    public void onSuccess(ShortDynamicLink shortDynamicLink) {
                        Uri shortLink = shortDynamicLink.getShortLink();
                        Uri flowchartLink = shortDynamicLink.getPreviewLink();
                        try {
                            Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                            sharingIntent.setType("text/plain");
                            sharingIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                            sharingIntent.putExtra(Intent.EXTRA_SUBJECT, "Kaju Badam");
                            sharingIntent.putExtra(Intent.EXTRA_TEXT, shortLink.toString());
                            startActivity(Intent.createChooser(sharingIntent, "Share your product"));
                        } catch (Exception e) {
                            Log.e("share_error", e.getMessage());
                        }
                    }
                });
    }
}