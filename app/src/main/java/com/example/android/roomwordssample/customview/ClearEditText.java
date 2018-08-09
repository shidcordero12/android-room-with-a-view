package com.example.android.roomwordssample.customview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.AppCompatEditText;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.example.android.roomwordssample.R;

public class ClearEditText extends AppCompatEditText implements View.OnTouchListener {

    private Drawable mClearDrawable;
    private ClearEditText context;

    public ClearEditText(Context context) {
        super(context);
        init();
    }

    public ClearEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ClearEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        Drawable drawable = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_clear, null);
        assert drawable != null;
        Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
        mClearDrawable = new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(bitmap, 50, 50, true));
        addTextChangedListener(new CustomTextWatcher());
        setOnTouchListener(this);
        context = this;
    }

    private void showClearButton() {
        setCompoundDrawablesRelativeWithIntrinsicBounds(null, null,  mClearDrawable,null);
    }

    private void hideClearButton() {
        setCompoundDrawablesRelativeWithIntrinsicBounds(null,null,null,null);
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        if (getCompoundDrawablesRelative()[2] != null){
            float clearButtonStart; // Used for LTR languages
            float clearButtonEnd;  // Used for RTL languages
            boolean isClearButtonClicked = false;
            // Detect the touch in RTL or LTR layout direction.
            if (getLayoutDirection() == LAYOUT_DIRECTION_RTL) {
                // If RTL, get the end of the button on the left side.
                clearButtonEnd = mClearDrawable
                        .getIntrinsicWidth() + getPaddingStart();
                // If the touch occurred before the end of the button,
                // set isClearButtonClicked to true.
                if (motionEvent.getX() < clearButtonEnd) {
                    isClearButtonClicked = true;
                }
            } else {
                // Layout is LTR.
                // Get the start of the button on the right side.
                clearButtonStart = (getWidth() - getPaddingEnd()
                        - mClearDrawable.getIntrinsicWidth());
                // If the touch occurred after the start of the button,
                // set isClearButtonClicked to true.
                if (motionEvent.getX() > clearButtonStart) {
                    isClearButtonClicked = true;
                }
            }
            // Check for actions if the button is tapped.
            if (isClearButtonClicked) {
                context.setText("");
                hideClearButton();
            } else {
                return false;
            }
        }
        return false;
    }

    private class CustomTextWatcher implements TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            if (TextUtils.isEmpty(context.getText())) {
                hideClearButton();
            } else {
                showClearButton();
            }
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    }
}
