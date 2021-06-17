package com.example.testcoroutine.自定义View;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import com.example.testcoroutine.Utils.DensityUtil;
import com.example.testcoroutine.R;

public class CustomGalleryGroup extends ViewGroup {
    int childWidthMargin  = DensityUtil.dip2px(getContext(),10);
    int childHeightMargin = DensityUtil.dip2px(getContext(),10);
    int childWidth;
    int currentImageNum = 0;
    private int[] imageGroup = {R.drawable.add,
            R.drawable.flag_ad,R.drawable.flag_ae,R.drawable.flag_af,R.drawable.flag_ag,R.drawable.flag_ai,
            R.drawable.flag_al, R.drawable.flag_am,R.drawable.flag_an, R.drawable.flag_ao,R.drawable.flag_aq};

    static int MAX_SIZE = 9;
    public CustomGalleryGroup(Context context) {
        super(context);
    }

    public CustomGalleryGroup(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public CustomGalleryGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray array= context.obtainStyledAttributes(attrs, R.styleable.CustomGalleryGroup,0,0);
        childWidthMargin = array.getInteger(R.styleable.CustomGalleryGroup_widthMargin, DensityUtil.dip2px(context,10));
        childHeightMargin = array.getInteger(R.styleable.CustomGalleryGroup_heightMargin,DensityUtil.dip2px(context,10));
        array.recycle();
        View addPhoto = new View(context);
        addView(addPhoto);
        currentImageNum++;
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int childCount = getChildCount();
        for (int i = 0;i < childCount;i++){
            View child = getChildAt(i);
            CustomLayoutParams layoutParams = (CustomLayoutParams) child.getLayoutParams();
            child.layout(layoutParams.left,layoutParams.top,layoutParams.left + childWidth,layoutParams.top + childWidth);
            if (i == currentImageNum-1 && currentImageNum != MAX_SIZE){
                child.setBackgroundResource(R.drawable.add);
                child.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        addImage();
                    }
                });
            } else {
                child.setBackgroundResource(imageGroup[i]);
                child.setOnClickListener(null);
            }
        }
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new CustomLayoutParams(getContext(),attrs);
    }

    @Override
    protected boolean checkLayoutParams(LayoutParams p) {
        return p instanceof CustomLayoutParams;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec,heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int childCount = getChildCount();
        childWidth = (widthSize - 2*childWidthMargin);
        int totalWidth = widthSize;
        int totalHeight = heightSize;
        for (int i = 0;i<childCount;i++){
            View view = getChildAt(i);
            CustomLayoutParams layoutParams = (CustomLayoutParams) view.getLayoutParams();
            layoutParams.left = (i % 3)*(childWidth + childWidthMargin);
            layoutParams.top = (i / 3)*(childWidth + childHeightMargin);
        }
        if (childCount < 3){
            totalWidth = childCount * (childWidth + childWidthMargin);
        }
        totalHeight = ((childCount + 3) / 3)*(childWidth + childHeightMargin);
        setMeasuredDimension(totalWidth, totalHeight);
    }


    private void addImage(){
        if (currentImageNum < MAX_SIZE){
            View view = new View(getContext());
            addView(view);
            currentImageNum ++;
            requestLayout();
            invalidate();
        }
    }
}
