package com.example.project;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.ProgressBar;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class LoadingScreenActivity extends AppCompatActivity {

    private ImageView loadingImage;
    private ProgressBar progressBar;
    private int[] images = {R.drawable.circle, R.drawable.triangle, R.drawable.square};
    private Random random = new Random();
    private int screenWidth;
    private int screenHeight;
    private Handler handler = new Handler();
    private int imageWidth;
    private int imageHeight;

    private float velocityX = 5f;
    private float velocityY = 5f;

    private int progress = 0;
    private final int maxProgress = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loadingscreen);

        loadingImage = findViewById(R.id.loading_image);
        progressBar = findViewById(R.id.progress_bar);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        screenWidth = displayMetrics.widthPixels;
        screenHeight = displayMetrics.heightPixels;


        loadingImage.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                loadingImage.getViewTreeObserver().removeOnGlobalLayoutListener(this);

                imageWidth = loadingImage.getWidth();
                imageHeight = loadingImage.getHeight();

                startBouncingAnimation();
            }
        });

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(LoadingScreenActivity.this, MainActivity.class));
                finish();
            }
        }, 20000);

        updateProgressBar();
    }

    private void startBouncingAnimation() {
        int startX = (screenWidth - imageWidth) / 2;
        int startY = (screenHeight - imageHeight) / 2;

        moveImage(startX, startY);
    }

    private void moveImage(final int startX, final int startY) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                int newStartX = startX + (int) velocityX;
                int newStartY = startY + (int) velocityY;

                if (newStartX <= 0 || newStartX + imageWidth >= screenWidth) {
                    velocityX = -velocityX;
                    changeImage();
                }
                if (newStartY <= 0 || newStartY + imageHeight >= screenHeight) {
                    velocityY = -velocityY;
                    changeImage();
                }


                loadingImage.setX(newStartX);
                loadingImage.setY(newStartY);

                moveImage(newStartX, newStartY);
            }
        });
    }

    private void changeImage() {
        int newImageIndex = random.nextInt(images.length);
        loadingImage.setImageResource(images[newImageIndex]);
    }

    private void updateProgressBar() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    while (progress < maxProgress) {
                        try {
                            Thread.sleep(200);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        progress += 1;
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                progressBar.setProgress(progress);
                            }
                        });
                    }

                }
            }
        }).start();
    }

}
