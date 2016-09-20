package com.qiuhong.qhlibrary.Dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.qiuhong.qhlibrary.NavigationView.NavigationView;
import com.qiuhong.qhlibrary.R;
import com.qiuhong.qhlibrary.Utils.DensityUtil;

/**
 * Created by qiuhong on 9/4/16.
 */

public class QHDialogClass extends Dialog {

    public QHDialogClass(Context context) {
        super(context);
    }

    public QHDialogClass(Context context, int theme) {
        super(context, theme);
    }

    public static class Builder {
        private Context context;
        private String title;
        private String message;
        private String positiveButtonText;
        private String negativeButtonText;
        private String onlyOneButtonText = "";
        private boolean mCancelable;

        private View contentView;
        private OnClickListener positiveButtonClickListener;
        private OnClickListener negativeButtonClickListener;

        public Builder(Context context) {
            this.context = context;
        }

        public Builder setMessage(String message) {
            this.message = message;
            return this;
        }

        /**
         * Set the Dialog message from resource
         *
         * @param message
         * @return
         */
        public Builder setMessage(int message) {
            this.message = (String) context.getText(message);
            return this;
        }

        /**
         * Set the Dialog title from resource
         *
         * @param title
         * @return
         */
        public Builder setTitle(int title) {
            this.title = (String) context.getText(title);
            return this;
        }

        /**
         * Set the Dialog title from String
         *
         * @param title
         * @return
         */
        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder setContentView(View v) {
            this.contentView = v;
            return this;
        }

        public Builder setCancelable(boolean cancelable) {
            this.mCancelable = cancelable;
            return this;
        }

        public Builder setOnlyOneButtonText(String onlyOneButtonText) {
            this.onlyOneButtonText = onlyOneButtonText;
            return this;
        }

        /**
         * Set the positive button resource and it's listener
         *
         * @param positiveButtonText
         * @return
         */
        public Builder setPositiveButton(int positiveButtonText, OnClickListener listener) {
            this.positiveButtonText = (String) context
                    .getText(positiveButtonText);
            if (listener == null) {
                this.positiveButtonClickListener = new OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                };
            } else {
                this.positiveButtonClickListener = listener;
            }
            return this;
        }

        public Builder setPositiveButton(String positiveButtonText, OnClickListener listener) {
            this.positiveButtonText = positiveButtonText;
            this.positiveButtonClickListener = listener;
            if (listener == null) {
                this.positiveButtonClickListener = new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                };
            } else {
                this.positiveButtonClickListener = listener;
            }
            return this;
        }

        public Builder setNegativeButton(int negativeButtonText, OnClickListener listener) {
            this.negativeButtonText = (String) context
                    .getText(negativeButtonText);
            if (listener == null) {
                this.negativeButtonClickListener = new OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                };
            } else {
                this.negativeButtonClickListener = listener;
            }


            return this;
        }

        public Builder setNegativeButton(String negativeButtonText, OnClickListener listener) {
            this.negativeButtonText = negativeButtonText;
            if (listener == null) {
                this.negativeButtonClickListener = new OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                };
            } else {
                this.negativeButtonClickListener = listener;
            }
            return this;
        }

        public QHDialogClass create() {

            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            /**
             * instantiate the dialog with the custom Theme
             */
            final QHDialogClass dialog = new QHDialogClass(context, R.style.myDialogActivityStyle);
            View dialogView = inflater.inflate(R.layout.dialog, null);
            dialog.addContentView(dialogView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

            /**
             * set dialog title
             */
            NavigationView navigationView = (NavigationView) dialogView.findViewById(R.id.nav_main_in_dialog);
            navigationView.setBackgroundResource(R.drawable.top_select);
            navigationView.setTitle(title);
            navigationView.setClickCallback(new NavigationView.ClickCallback() {
                @Override
                public void onRightClick() {
                    Log.i("main","点击了右侧按钮!");
                }

                @Override
                public void onBackClick() {
                    Log.i("main","点击了左侧按钮!");
                }
            });

            /**
             * set dialog width
             */
            WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
            int width = wm.getDefaultDisplay().getWidth();
            LinearLayout content = (LinearLayout) dialogView.findViewById(R.id.content_in_dialog);
            int dialogWidth = DensityUtil.px2dip(context, width * 4 / 5);
            if (dialogWidth > 320) dialogWidth = 320;
            content.setLayoutParams(new LinearLayout.LayoutParams(DensityUtil.dip2px(context, dialogWidth) , ViewGroup.LayoutParams.WRAP_CONTENT));



            Button positiveButton = (Button) dialogView.findViewById(R.id.left_button_in_dialog);
            Button negativeButton = (Button) dialogView.findViewById(R.id.right_button_in_dialog);
            int margins = DensityUtil.dip2px(context, 10);


            if (onlyOneButtonText == null || onlyOneButtonText.isEmpty()) {
                /**
                 * set the confirm button
                 */
                if (positiveButtonText != null) {
                    positiveButton.setText(positiveButtonText);
                    if (positiveButtonClickListener != null) {
                        positiveButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                positiveButtonClickListener.onClick(dialog, DialogInterface.BUTTON_POSITIVE);
                            }
                        });
                    }
                    if (positiveButtonText.equals("删除") || positiveButtonText.equals("DELETE")) {
                        positiveButton.setBackgroundResource(R.drawable.single_select_red);
                    }
                } else {
                    // if no confirm button just set the visibility to GONE
                    positiveButton.setVisibility(View.GONE);
                    LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) negativeButton.getLayoutParams();
                    params.setMargins(margins, 0, margins, margins);
                    negativeButton.setLayoutParams(params);
                }

                /**
                 * set the cancel button
                 */
                if (negativeButtonText != null) {
                    negativeButton.setText(negativeButtonText);
                    if (negativeButtonClickListener != null) {
                        negativeButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                negativeButtonClickListener.onClick(dialog, DialogInterface.BUTTON_NEGATIVE);
                            }
                        });
                    }
                } else {
                    // if no confirm button just set the visibility to GONE
                    negativeButton.setVisibility(View.GONE);
                    LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) positiveButton.getLayoutParams();
                    params.setMargins(margins, 0, margins, margins);
                    positiveButton.setLayoutParams(params);
                }
            } else {

                /**
                 * only one button
                 */
                positiveButton.setVisibility(View.GONE);
                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) negativeButton.getLayoutParams();
                params.setMargins(margins, 0, margins, margins);
                negativeButton.setLayoutParams(params);

                negativeButton.setText(onlyOneButtonText);
                negativeButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
            }


            /**
             * set the content message
             */
            TextView messageTextView = (TextView) dialogView.findViewById(R.id.textView_in_dialog);
            LinearLayout contentLayout = (LinearLayout) dialogView.findViewById(R.id.content_in_dialog);
            if (message != null) {
                messageTextView.setText(message);
            } else if (contentView != null) {
                // if no message set
                // add the contentView to the dialog body
                contentLayout.removeAllViews();
                contentLayout.addView(contentView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.FILL_PARENT));
            }

            dialog.setContentView(dialogView);
            dialog.setCancelable(mCancelable);
            dialog.setCanceledOnTouchOutside(mCancelable);
            return dialog;
        }
    }
}

