package com.shengqf.dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.util.SparseArray;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

/**
 * Created by shengqf
 * Email : shengqf@bsoft.com.cn
 * date : 2020/4/23
 * describe :
 */
public class DialogController {

    private CustomDialog mDialog;
    private Window mWindow;
    private DialogViewHolder mViewHolder;

    private DialogController(CustomDialog dialog, Window window) {
        this.mDialog = dialog;
        this.mWindow = window;
    }

    public static DialogController create(CustomDialog dialog, Window window) {
        return new DialogController(dialog, window);
    }

    public CustomDialog getDialog() {
        return mDialog;
    }

    /**
     * 获取Dialog的窗体
     */
    public Window getWindow() {
        return mWindow;
    }

    public <T extends View> T getView(int viewId) {
        return mViewHolder.getView(viewId);
    }

    public void setViewHolder(DialogViewHolder viewHolder) {
        mViewHolder = viewHolder;
    }

    public void setText(int viewId, CharSequence text) {
        mViewHolder.setText(viewId, text);
    }

    public void setOnClickListener(int viewId, View.OnClickListener clickListener) {
        mViewHolder.setOnClickListener(viewId, clickListener);
    }

    /**
     * Dialog参数类
     */
    public static class DialogParams {

        public Context mContext;
        //dialog的theme
        public int mThemeResId;
        //dialog的布局View
        public View mContentView;
        //dialog的布局的id
        public int mLayoutResId;
        //点击空白是否消失
        public boolean mCancelable = true;
        //dialog cancel监听
        public DialogInterface.OnCancelListener mOnCancelListener;
        //dialog dismiss监听
        public DialogInterface.OnDismissListener mOnDismissListener;
        //dialog key监听
        public DialogInterface.OnKeyListener mOnKeyListener;
        //存放dialog的TextView（key=viewId,value=str）
        public SparseArray<CharSequence> mTextArray = new SparseArray<>();
        //存放dialog的点击事件（key=viewId,value=clickListener）
        public SparseArray<View.OnClickListener> mClickArray = new SparseArray<>();
        //dialog的宽度
        public int mWidth = ViewGroup.LayoutParams.WRAP_CONTENT;
        //dialog的高度
        public int mHeight = ViewGroup.LayoutParams.WRAP_CONTENT;
        //dialog的显示位置
        public int mGravity = Gravity.CENTER;
        //dialog的动画
        public int mAnimations;
        //dialog的圆角
        public float mCornerRadius;

        public DialogParams(Context context, int themeResId) {
            this.mContext = context;
            this.mThemeResId = themeResId;
        }

        private DialogViewHolder getViewHolder() {
            DialogViewHolder viewHolder = null;
            if (mLayoutResId != 0) {
                viewHolder = new DialogViewHolder(mContext, mLayoutResId);
            }
            if (mContentView != null) {
                viewHolder = new DialogViewHolder(mContentView);
            }
            if (viewHolder == null) {
                throw new NullPointerException("the ContentView of dialog cannot be null");
            }
            return viewHolder;
        }


        /**
         * 设置参数,也就是将Builder中设置的参数应用到dialog中
         */
        public void apply(DialogController controller) {
            DialogViewHolder viewHolder = getViewHolder();
            controller.setViewHolder(viewHolder);
            if (mCornerRadius != 0) {
                RoundCornerView roundCornerView = new RoundCornerView(mContext);
                roundCornerView.addView(viewHolder.getContentView());
                roundCornerView.setRadius(mCornerRadius);
                controller.getDialog().setContentView(roundCornerView);
                RoundCornerView.LayoutParams lp = (RoundCornerView.LayoutParams) roundCornerView.getLayoutParams();
                lp.width = mWidth;
                lp.height = mHeight;
                roundCornerView.setLayoutParams(lp);
            } else {
                //1.给dialog设置布局
                controller.getDialog().setContentView(viewHolder.getContentView());
            }

            //设置文本
            for (int i = 0; i < mTextArray.size(); i++) {
                controller.setText(mTextArray.keyAt(i), mTextArray.valueAt(i));
            }

            //设置点击事件
            for (int i = 0; i < mClickArray.size(); i++) {
                controller.setOnClickListener(mClickArray.keyAt(i), mClickArray.valueAt(i));
            }

            Window window = controller.getWindow();
            WindowManager.LayoutParams params = window.getAttributes();
            params.width = mWidth;
            params.height = mHeight;
            params.gravity = mGravity;
            params.windowAnimations = mAnimations;
            window.setAttributes(params);
        }
    }
}

