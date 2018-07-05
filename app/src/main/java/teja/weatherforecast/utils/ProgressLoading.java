package teja.weatherforecast.utils;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.pnikosis.materialishprogress.ProgressWheel;

import teja.weatherforecast.R;


/**
 * Created by raghul on 17-07-2015.
 */
public class ProgressLoading extends Dialog {

    ProgressWheel myProgressView;

    TextView myMessageTXT;

    public ProgressLoading(Context aContext) {
        super(aContext);

        try {
            this.requestWindowFeature(Window.FEATURE_NO_TITLE);
            this.setContentView(R.layout.dialog_progress);
            this.getWindow().setBackgroundDrawable(
                    new ColorDrawable(Color.TRANSPARENT));
            myProgressView = (ProgressWheel) this
                    .findViewById(R.id.progress_view);
            myMessageTXT = (TextView) this
                    .findViewById(R.id.progress_TXT);

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    private void ClassAndWidgetInitialise() {


        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        this.setContentView(R.layout.dialog_progress);

//        CircularProgressView progressView = (CircularProgressView) this.findViewById(R.id.progress_view);
//        progressView.startAnimation();


    }


    public void setMessage(String aMessage) {
        myMessageTXT.setText(aMessage);
        myMessageTXT.setVisibility(View.VISIBLE);
    }

    @Override
    public void show() {
        try {
            super.show();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
