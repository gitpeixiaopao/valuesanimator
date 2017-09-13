package com.example.myvaluesanimator;

import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.graphics.PointF;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

/**
 * ValueAnimator实现动画
 * 无需设置操作的属性；这就是和ObjectAnimator的区别
 * 好处：不需要操作对象的属性，一定要有getter,setter方法，你可以根据当前动画的计算值，来操作任何属性
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView mBlueBall;
    private int mScreenHeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        //获取屏幕的高度
        DisplayMetrics outMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(outMetrics);
        mScreenHeight = outMetrics.heightPixels;
    }
    private void initView() {
        mBlueBall = (ImageView) findViewById(R.id.id_ball);
        mBlueBall.setOnClickListener(this);
    }
    //垂直  自由落体
    public void verticalRun(View view){
        //Toast.makeText(MainActivity.this,"verticalRun",Toast.LENGTH_SHORT).show();
        //参数一：X轴起始位置；参数二：Y轴的位置（屏幕的高度减去控件的高度开始计算）
        ValueAnimator animator = ValueAnimator.ofFloat(0, mScreenHeight - mBlueBall.getHeight());
        animator.setTarget(mBlueBall);
        //设置动画时间及开启动画
        animator.setDuration(1000).start();
//      animator.setInterpolator(value)
        //添加动画更新的监听
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener()
        {
            @Override
            public void onAnimationUpdate(ValueAnimator animation)
            {
                mBlueBall.setTranslationY((Float) animation.getAnimatedValue());
            }
        });

    }
    //抛物线
    public void paowuxian(View view){
       //Toast.makeText(MainActivity.this,"paowuxian",Toast.LENGTH_SHORT).show();
        //获取动画执行类
        ValueAnimator valueAnimator=new ValueAnimator();
        //设置执行时间
        valueAnimator.setDuration(2000);
        //设置起始坐标
        valueAnimator.setObjectValues(new PointF(0,0));
        //设置类型估值
        valueAnimator.setEvaluator(new TypeEvaluator<PointF>(){

            @Override
            public PointF evaluate(float fraction, PointF startValue, PointF endValue) {
                Log.e("PY", fraction * 3 + "");
                // x方向200px/s ，则y方向0.5 * 10 * t
                PointF point = new PointF();
                point.x = 200 * fraction * 3;
                point.y = 0.5f * 200 * (fraction * 3) * (fraction * 3);
                return point;
            }
        });
        valueAnimator.start();
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                PointF point = (PointF) valueAnimator.getAnimatedValue();
                mBlueBall.setX(point.x);
                mBlueBall.setY(point.y);
            }
        });
    }

    @Override
    public void onClick(View view) {
        Toast.makeText(MainActivity.this,"帅帅帅",Toast.LENGTH_SHORT).show();
    }
}
