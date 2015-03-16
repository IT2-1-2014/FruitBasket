package jp.co.icomsys.it21.fruitbasket.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import jp.co.icomsys.it21.fruitbasket.R;

/**
 * AutoCompleteTextView with Clear Button.
 */
public class AutoCompleteWithClearButtonEditText extends LinearLayout implements View.OnClickListener {

    private EditText mEditText;
    private Button mClearButton;

    public AutoCompleteWithClearButtonEditText(Context context) {
        super(context);
        init(context);
    }

    public AutoCompleteWithClearButtonEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public AutoCompleteWithClearButtonEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        setOrientation(LinearLayout.HORIZONTAL);
        View.inflate(context, R.layout.viewgroup_single_item_input, this);

        LayoutInflater li = LayoutInflater.from(context);

        AutoCompleteWithClearButtonEditText a = (AutoCompleteWithClearButtonEditText) li.inflate(R.layout.viewgroup_single_item_input, this);
        mEditText = (EditText) a.findViewById(R.id.editText);
        mClearButton = (Button) a.findViewById(R.id.clearButton);

        this.removeAllViews();
        this.addView(mEditText);
        this.addView(mClearButton);
    }

    @Override
    public void onClick(View v) {
        if (v == mClearButton) {
            mEditText.setText("");
        }
    }
}
