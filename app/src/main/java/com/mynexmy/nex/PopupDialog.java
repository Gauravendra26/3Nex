package com.mynexmy.nex;
import android.app.Dialog;
        import android.content.Context;
        import android.graphics.drawable.BitmapDrawable;
        import android.os.Bundle;
        import android.view.Window;
        import android.widget.ImageView;

public class PopupDialog extends Dialog {

    private Context context;
    private int imageResId;

    public PopupDialog(Context context, int imageResId) {
        super(context);
        this.context = context;
        this.imageResId = imageResId;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.popup_layout);

        ImageView popupImage = findViewById(R.id.popupImage);
        popupImage.setImageResource(imageResId);

        getWindow().setBackgroundDrawable(new BitmapDrawable()); // Make the background transparent
        getWindow().setLayout(800, 800); // Set dialog width and height
    }
}