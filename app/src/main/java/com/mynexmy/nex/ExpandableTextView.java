package com.mynexmy.nex;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.LinearLayout;

public class ExpandableTextView extends LinearLayout {
    private TextView textView;
    private TextView moreTextView;
    private boolean isExpanded = false;
    private String fullText;
    private String shortText;

    public ExpandableTextView(Context context) {
        super(context);
        init(context);
    }

    public ExpandableTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater.from(context).inflate(R.layout.expandable_textview_layout, this, true);
        textView = findViewById(R.id.textView);
        moreTextView = findViewById(R.id.moreTextView);

        moreTextView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                toggle();
            }
        });
    }

    public void setText(String text) {
        fullText = text;
        shortText = text.length() > 100 ? text.substring(0, 100) + "..." : text;
        textView.setText(shortText);
    }

    private void toggle() {
        if (isExpanded) {
            textView.setText(shortText);
            moreTextView.setText("Show More");
        } else {
            textView.setText(fullText);
            moreTextView.setText("Show Less");
        }
        isExpanded = !isExpanded;
    }
}
