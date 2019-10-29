package project.myshulteproject.com.util;

import android.animation.ValueAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

/**
 * Created by fanxin on 2019/10/28.
 */

public class ExpandLayout extends RelativeLayout {
    private View layoutView;
    private int viewHeight;
    private boolean isExpand;
    private long animationDuration;

    public ExpandLayout(Context context) {
        this(context, null);
    }

    public ExpandLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ExpandLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    //初始化View
    private void initView() {
        layoutView = this;
        isExpand = true;
        animationDuration = 300;
        setViewDimensions();
    }

    //设置View的总高度
    //View.post()的runnable对象中的方法会在View的measure、layout等事件后触发
    private void setViewDimensions() {
        layoutView.post(new Runnable() {
            @Override
            public void run() {
                if (viewHeight <= 0) {
                    viewHeight = layoutView.getMeasuredHeight();
                }
            }
        });
    }

    //设置动画时间
    public void setAnimationDuration(long animationDuration){
        this.animationDuration = animationDuration;
    }

    //设置View的高度
    public static void setViewHeight(View view, int height) {
        final ViewGroup.LayoutParams params = view.getLayoutParams();
        params.height = height;
        view.requestLayout();
    }

    //isExpand初始值,是否折叠
    public void initExpand(boolean isExpand) {
        this.isExpand = isExpand;
        if (!isExpand) {
            animateToggle(0);//切换动画
        }
    }

    //切换动画
    private void animateToggle(long i) {
        //根据isExpand 判断是展开还是折叠
        ValueAnimator heightAnimation = isExpand ? ValueAnimator.ofFloat(0f, viewHeight) : ValueAnimator.ofFloat(viewHeight, 0f);
        heightAnimation.setDuration(animationDuration / 2);
        heightAnimation.setStartDelay(animationDuration / 2);

        heightAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float val = (float) valueAnimator.getAnimatedValue();
                setViewHeight(layoutView, (int) val);
            }
        });
        heightAnimation.start();
    }

    public boolean isExpand(){
        return isExpand;
    }

    //折叠view
    public void collapse(){
        isExpand = false;
        animateToggle( animationDuration);
    }

    //展开view
    public void expand(){
        isExpand = true;
        animateToggle(animationDuration);
    }


    //判断是展开还是折叠
    public void toggleExpand(){
        if (isExpand){
            collapse();
        }else {
            expand();
        }
    }

}
