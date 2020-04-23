package com.shengqf.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;

/**
 * Created by shengqf
 * Email : shengqf@bsoft.com.cn
 * date : 2020/4/23
 * describe :
 */
public class CustomDialog extends Dialog implements DialogInterface {

    private DialogController mController;

    public CustomDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        mController = DialogController.create(this, getWindow());
    }

    public <T extends View> T getView(@IdRes int viewId) {
        return mController.getView(viewId);
    }

    public void setText(int viewId, CharSequence text) {
        mController.setText(viewId, text);
    }

    public String getText(int viewId) {
        return mController.<TextView>getView(viewId).getText().toString();
    }

    public void setOnClickListener(int viewId, View.OnClickListener clickListener) {
        mController.setOnClickListener(viewId, clickListener);
    }
    
    public static class Builder {
        private DialogController.DialogParams mDialogParams;
        private CustomDialog dialog;

        public Builder(Context context) {
            this(context, R.style.DialogTheme);
        }

        public Builder(Context context, int themeResId) {
            mDialogParams = new DialogController.DialogParams(context, themeResId);
        }

        public Builder setContentView(View view) {
            mDialogParams.mContentView = view;
            mDialogParams.mLayoutResId = 0;
            return this;
        }

        public Builder setContentView(int layoutResId) {
            mDialogParams.mContentView = null;
            mDialogParams.mLayoutResId = layoutResId;
            return this;
        }

        public Builder setText(int viewId, CharSequence text) {
            mDialogParams.mTextArray.put(viewId, text);
            return this;
        }

        public Builder setCancelable(boolean cancelable) {
            mDialogParams.mCancelable = cancelable;
            return this;
        }

        public Builder setOnCancelListener(OnCancelListener onCancelListener) {
            mDialogParams.mOnCancelListener = onCancelListener;
            return this;
        }

        public Builder setOnDismissListener(OnDismissListener onDismissListener) {
            mDialogParams.mOnDismissListener = onDismissListener;
            return this;
        }

        public Builder setOnClickListener(int viewId, View.OnClickListener onClickListener) {
            mDialogParams.mClickArray.put(viewId, onClickListener);
            return this;
        }

        public Builder setOnKeyListener(OnKeyListener onKeyListener) {
            mDialogParams.mOnKeyListener = onKeyListener;
            return this;
        }

        public Builder fullWidth() {
            mDialogParams.mWidth = ViewGroup.LayoutParams.MATCH_PARENT;
            return this;
        }

        public Builder setRoundCorner(float radius) {
            mDialogParams.mCornerRadius = radius;
            return this;
        }

        public Builder setWidth(int width) {
            mDialogParams.mWidth = width;
            return this;
        }

        public Builder setHeight(int height) {
            mDialogParams.mHeight = height;
            return this;
        }

        public Builder setWidthAndHeight(int width, int height) {
            mDialogParams.mWidth = width;
            mDialogParams.mHeight = height;
            return this;
        }

        public Builder fromBottom(boolean isAnimation) {
            if (isAnimation) {
                mDialogParams.mAnimations = R.style.dialog_from_bottom_anim;
            }
            mDialogParams.mGravity = Gravity.BOTTOM;
            return this;
        }

        public Builder setAnimations(int styleAnimations) {
            mDialogParams.mAnimations = styleAnimations;
            return this;
        }

        private CustomDialog create() {
            final CustomDialog dialog = new CustomDialog(mDialogParams.mContext, mDialogParams.mThemeResId);
            mDialogParams.apply(dialog.mController);
            dialog.setCancelable(mDialogParams.mCancelable);
            if (mDialogParams.mCancelable) {
                dialog.setCanceledOnTouchOutside(true);
            }
            dialog.setOnCancelListener(mDialogParams.mOnCancelListener);
            dialog.setOnDismissListener(mDialogParams.mOnDismissListener);
            if (mDialogParams.mOnKeyListener != null) {
                dialog.setOnKeyListener(mDialogParams.mOnKeyListener);
            }
            return dialog;
        }

        public CustomDialog show() {
            dialog = create();
            dialog.show();
            return dialog;
        }

        public void dismiss() {
            if (dialog != null && dialog.isShowing()) {
                dialog.dismiss();
            }
        }

    }


}
