package tj.com.streamdemo.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jun on 17/6/6.
 */

public class FlowLayout extends ViewGroup {
    public FlowLayout(Context context) {
        this(context,null);
    }

    public FlowLayout(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public FlowLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int sizeWidth=MeasureSpec.getSize(widthMeasureSpec);
        int modeWidth=MeasureSpec.getMode(widthMeasureSpec);

        int sizeHeight=MeasureSpec.getSize(heightMeasureSpec);
        int modeHeight=MeasureSpec.getMode(heightMeasureSpec);

        //wrap_content
        int width=0;
        int height=0;

        //记录每一行的宽度和高度
        int lineWidth=0;
        int lineHeight=0;

        //得到内部元素个数
        int cCount=getChildCount();
        for(int i=0;i<cCount;i++){
            View child=getChildAt(i);
            //测量子view的宽和高
            measureChild(child,widthMeasureSpec,heightMeasureSpec);
            //得到layoutparams
            MarginLayoutParams params= (MarginLayoutParams) child.getLayoutParams();

            //子view占据的宽度和高度
            int childWidth=child.getMeasuredWidth()+params.leftMargin+params.rightMargin;
            int childHeight=child.getMeasuredHeight()+params.topMargin+params.bottomMargin;
//          换行
            if (lineWidth+childWidth>sizeWidth-getPaddingLeft()-getPaddingRight()){
                width=Math.max(width,lineWidth);
                lineWidth=childWidth;
                height+=lineHeight;
                lineHeight=childHeight;
            }else{//未换行
                lineWidth+=childWidth;
                lineHeight=Math.max(lineHeight,childHeight);
            }
            if (i==cCount-1){
                width=Math.max(lineWidth,width);
                height+=lineHeight;
            }

        }

        //wrap_content
        setMeasuredDimension(modeWidth==MeasureSpec.EXACTLY?sizeWidth:width+getPaddingLeft()+getPaddingRight()
                ,modeHeight==MeasureSpec.EXACTLY?sizeHeight:height+getPaddingBottom()+getPaddingTop());

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }


//  存储所有的view
    private List<List<View>> mAllViews=new ArrayList<List<View>>();
    //每一行的高度
    private List<Integer> mLineHeight=new ArrayList<>();


    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        mAllViews.clear();
        mLineHeight.clear();
        int width=getWidth();//当前viewgroup的宽度

        int lineWidth=0;
        int lineHeight=0;

        List<View> lineViews=new ArrayList<>();
        int cCount=getChildCount();
        for (int i = 0; i <cCount ; i++) {
            View child=getChildAt(i);
            MarginLayoutParams lp= (MarginLayoutParams) child.getLayoutParams();
            int childWidth=child.getMeasuredWidth();
            int childHeight=child.getMeasuredHeight();
            //换行
            if (childWidth+lineWidth+lp.leftMargin+lp.rightMargin>width-getPaddingLeft()-getPaddingRight()){
                mLineHeight.add(lineHeight);
                mAllViews.add(lineViews);
                lineWidth=0;
                lineHeight=childHeight+lp.topMargin+lp.bottomMargin;
                lineViews=new ArrayList<>();
            }
            lineWidth+=childWidth+lp.leftMargin+lp.rightMargin;
            lineHeight=Math.max(lineHeight,childHeight+lp.topMargin+lp.bottomMargin);
            lineViews.add(child);
        }
        mLineHeight.add(lineHeight);
        mAllViews.add(lineViews);

        //设置子view的位置
        int left=getPaddingLeft();
        int top=getPaddingTop();
        //行数
        int lineNum=mAllViews.size();
        for (int i = 0; i < lineNum; i++) {
            lineViews=mAllViews.get(i);
            lineHeight=mLineHeight.get(i);
            for (int j = 0; j < lineViews.size(); j++) {
                View child=lineViews.get(j);
                 if (child.getVisibility()==View.GONE){
                     continue;
                 }
                 MarginLayoutParams lp= (MarginLayoutParams) child.getLayoutParams();
                int lc=left+lp.leftMargin;
                int tc=top+lp.topMargin;
                int rc=lc+child.getMeasuredWidth();
                int rb=tc+child.getMeasuredHeight();
                child.layout(lc,tc,rc,rb);

                left+=child.getMeasuredWidth()+lp.leftMargin+lp.rightMargin;
            }
            left=getPaddingLeft();
            top+=lineHeight;
        }
    }

    /**
     * 与当前viewgroup对应的layoutparams
     * @param attrs
     * @return
     */
    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(),attrs);
    }
}
