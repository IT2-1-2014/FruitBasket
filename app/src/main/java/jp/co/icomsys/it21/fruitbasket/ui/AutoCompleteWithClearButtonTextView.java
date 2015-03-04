package jp.co.icomsys.it21.fruitbasket.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import jp.co.icomsys.it21.fruitbasket.R;

/**
 * AutoCompleteTextView with Clear Button.
 */
public class AutoCompleteWithClearButtonTextView extends LinearLayout implements View.OnClickListener {

    private AutoCompleteTextView mTextView;
    private ImageButton mClearButton;

    public AutoCompleteWithClearButtonTextView(Context context) {
        super(context);
        init(context);
    }

    public AutoCompleteWithClearButtonTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public AutoCompleteWithClearButtonTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        setOrientation(LinearLayout.HORIZONTAL);
        View.inflate(context, R.layout.viewgroup_single_item_input, this);

        LayoutInflater li = LayoutInflater.from(context);

        AutoCompleteWithClearButtonTextView a = (AutoCompleteWithClearButtonTextView) li.inflate(R.layout.viewgroup_single_item_input, this);
        mTextView = (AutoCompleteTextView) a.findViewById(R.id.textView);
        mClearButton = (ImageButton) a.findViewById(R.id.clearButton);

        //this.removeAllViews();
        //this.addView(mTextView);
        //this.addView(mClearButton);
    }

    @Override
    public void onClick(View v) {
        if (v == mClearButton) {
            mTextView.setText("");
        }
    }
}
