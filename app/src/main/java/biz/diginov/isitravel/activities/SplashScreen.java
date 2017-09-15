package biz.diginov.isitravel.activities;

import android.animation.Animator;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.wang.avi.AVLoadingIndicatorView;

import biz.diginov.isitravel.R;
import butterknife.BindView;
import butterknife.ButterKnife;

public class SplashScreen extends BaseActivity {

    @BindView(R.id.iv_image)
    ImageView main_image;
    @BindView(R.id.tv_slogan)
    TextView slogan_text;
    @BindView(R.id.left_line)
    View left_line;
    @BindView(R.id.center_dot)
    ImageView center_dot;
    @BindView(R.id.right_line)
    View right_line;
    @BindView(R.id.name_container)
    LinearLayout name_container;
    @BindView(R.id.loading_indicator)
    AVLoadingIndicatorView loader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        ButterKnife.bind(this);
        hideSystemUI();
        toggleVisibility(false, main_image, left_line, right_line, name_container, slogan_text, loader);
        startAnimation();
    }

    private void startAnimation() {
        YoYo.with(Techniques.FadeIn)
                .duration(2000).delay(1000)
                .onStart(new YoYo.AnimatorCallback() {
                    @Override
                    public void call(Animator animator) {
                        toggleVisibility(true, main_image);
                    }
                })
                .onEnd(new YoYo.AnimatorCallback() {
                    @Override
                    public void call(Animator animator) {
                        toggleVisibility(true, name_container);
                        YoYo.with(Techniques.FlipInX)
                                .duration(1500)
                                .onEnd(new YoYo.AnimatorCallback() {
                                    @Override
                                    public void call(Animator animator) {
                                        toggleVisibility(true, right_line, left_line);
                                        YoYo.with(Techniques.SlideInLeft)
                                                .duration(1500)
                                                .playOn(left_line);
                                        YoYo.with(Techniques.SlideInRight)
                                                .duration(1500)
                                                .onEnd(new YoYo.AnimatorCallback() {
                                                    @Override
                                                    public void call(Animator animator) {
                                                        toggleVisibility(true, slogan_text);
                                                        YoYo.with(Techniques.BounceInUp)
                                                                .duration(1500)
                                                                .onEnd(new YoYo.AnimatorCallback() {
                                                                    @Override
                                                                    public void call(Animator animator) {
                                                                        toggleVisibility(true, loader);
                                                                        loader.show();
                                                                        //TODO: Delay and start main activity
                                                                        new Handler().postDelayed(new Runnable() {
                                                                            @Override
                                                                            public void run() {
                                                                                Intent i = new Intent(SplashScreen.this, LoginActivity.class);
                                                                                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                                                                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                                                                                    ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(SplashScreen.this, name_container, "app-name");
                                                                                    startActivity(i, options.toBundle());
                                                                                }
                                                                                startActivity(i);
                                                                                finish();
                                                                            }
                                                                        }, 4000);
                                                                    }
                                                                })
                                                                .playOn(slogan_text);
                                                    }
                                                }).playOn(right_line);
                                    }
                                }).playOn(name_container);
                    }
                }).playOn(main_image);
    }

    public void toggleVisibility(boolean s, View... views) {
        for (View v : views) {
            v.setVisibility(s ? View.VISIBLE : View.INVISIBLE);
        }
    }
}
