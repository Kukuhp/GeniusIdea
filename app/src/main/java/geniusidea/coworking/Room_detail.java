package geniusidea.coworking;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;

import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.TextSliderView;

import geniusidea.coworking.Service.ImageHandling.HeightEvaluator;

public class Room_detail extends AppCompatActivity {
    SliderLayout sliderLayout;
    CardView bookNow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_detail);
        sliderLayout = findViewById(R.id.slider);

        TextSliderView textSliderView = new TextSliderView(this);

        bookNow = findViewById(R.id.bookNow);
        bookNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Room_detail.this,Booking_Detail.class));
            }
        });
        DisplayMetrics dm = new DisplayMetrics();
        WindowManager windowManager = (WindowManager) Room_detail.this.getSystemService(WINDOW_SERVICE);
        if (windowManager != null) {
            windowManager.getDefaultDisplay().getMetrics(dm);
        }
        int heightInDP = Math.round(dm.heightPixels / dm.density);
        int myHeight = heightInDP - 500;
        final float scale = getApplicationContext().getResources().getDisplayMetrics().density;
        final int pixel1 = (int) (myHeight * scale +0.5f);

        final View slideList = findViewById(R.id.slideList);
        final View handle = findViewById(R.id.handle);
        final ImageView imgHandle= findViewById(R.id.imgHandle);
        imgHandle.setBackgroundResource(R.drawable.ic_keyboard_arrow_up_black);

        handle.setOnTouchListener(new View.OnTouchListener() {
            /* Starting Y point (where touch started). */
            float yStart = 0;
            /* Default height when in the open state. */
            float closedHeight = 150;
            /* Default height when in the closed state. */
            float openHeight = pixel1;
            /* The height during the transition (changed on ACTION_MOVE). */
            float currentHeight;
            /* The last y touch that occurred. This is used to determine if the view should snap up or down on release.
             * Used in conjunction with directionDown boolean. */
            float lastY = 0;
            boolean directionDown = false;
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                switch(event.getAction()) {

                    /* User tapped down on screen. */
                    case MotionEvent.ACTION_DOWN:
                        // User has tapped the screen
                        yStart = event.getRawY();
                        lastY = event.getRawY();
                        currentHeight = slideList.getHeight();
                      //  press = 1;
                        break;

                    /* User is dragging finger. */
                    case MotionEvent.ACTION_MOVE:

                        // Calculate the total height change thus far.
                        float totalHeightDiff = event.getRawY() - yStart;

                        // Adjust the slide down height immediately with touch movements.
                        ViewGroup.LayoutParams params = slideList.getLayoutParams();
                        params.height = (int)(currentHeight - totalHeightDiff);
                        slideList.setLayoutParams(params);

                        // Check and set which direction drag is moving.
                        if (event.getRawY() > lastY) {
                            directionDown = true;
                        } else {
                            directionDown = false;
                        }

                        // Set the lastY for comparison in the next ACTION_MOVE event.
                        lastY = event.getRawY();
                        break;

                    /* User lifted up finger. */
                    case MotionEvent.ACTION_CANCEL:
                    case MotionEvent.ACTION_UP:

                        /*
                         * Need to snap either up or down. Using ValueAnimator to "finish" the action.
                         * HeightEvaluator is a custom class.
                         *
                         * NOTE: I'm using the nineoldandroids library for
                         */
                        if (directionDown) {

                            // Close the sliding view.
                          //  press = 0;
                            int startHeight = slideList.getHeight();
                            ValueAnimator animation = ValueAnimator.ofObject(
                                    new HeightEvaluator(slideList),
                                    startHeight,
                                    (int) closedHeight).setDuration(300);
                            animation.setInterpolator(new OvershootInterpolator(1));
                            animation.start();
                            imgHandle.setBackgroundResource(R.drawable.ic_keyboard_arrow_up_black);

                        } else {

                         //   press = 1;
                            // Open the sliding view.
                            int startHeight = slideList.getHeight();

                            ValueAnimator animation = ValueAnimator.ofObject(
                                    new HeightEvaluator(slideList),
                                    startHeight,
                                    (int) openHeight).setDuration(300);

                            // See Table 3 for other interpolator options
                            // - http://developer.android.com/guide/topics/graphics/prop-animation.html
                            animation.setInterpolator(new OvershootInterpolator(1));
                            animation.start();
                            imgHandle.setBackgroundResource(R.drawable.ic_keyboard_arrow_down_black);
                        }
                        break;

                }
                return true;
            }
        });
  //      textSliderView.image("dasnda").setScaleType(BaseSliderView.ScaleType.Fit).setOnSliderClickListener(new On);
    }
}
