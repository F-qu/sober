package com.example.sober;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;
public class fontsAwesome extends androidx.appcompat.widget.AppCompatTextView {
    public fontsAwesome(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }
    public fontsAwesome(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }
    public fontsAwesome(Context context) {
        super(context);
        init();
    }
    private void init() {
        if (!isInEditMode()) {
            Typeface tf = Typeface.createFromAsset(getContext().getAssets(),
                    "icons/fa-solid-900.ttf");
            setTypeface(tf);
        }
    }
}