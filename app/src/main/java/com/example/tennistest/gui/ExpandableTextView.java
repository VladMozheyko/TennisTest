package com.example.tennistest.gui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.tennistest.R;

public class ExpandableTextView extends LinearLayout {
    //Контроль текста для отображения содержимого
    private TextView mContentTextView;
    // Полный текст / Свернуть кнопку
    private TextView mExpansionButton;
    // Максимальное количество отображаемых строк
    private int mMaxLine = 3;
    //Что отображается
    private CharSequence mContent;
    // Находится ли он в состоянии «полный текст»
    private boolean mIsExpansion;

    public ExpandableTextView(Context context) {
        super(context);
        init(context, null);
    }

    public ExpandableTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public ExpandableTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        setOrientation(VERTICAL);
        LayoutInflater.from(context).inflate(R.layout.view_expandable, this);
        mContentTextView = findViewById(R.id.tv_content);
        mExpansionButton = findViewById(R.id.v_expansion);
        // Мониторинг макета чертежа текстового элемента управления
        mContentTextView.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        if (mContentTextView.getWidth() == 0) {
                            return;
                        }
                        mContentTextView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                        // После того, как текстовый элемент управления успешно прорисован, обновляем текст
                        setText(mContent);
                    }
                }
        );

        // Монитор нажатия кнопки «Полный текст / сворачивание»
        mExpansionButton.setOnClickListener(
                new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        toggleExpansionStatus();
                    }
                }
        );
        // скрываем кнопку «полный текст / коллапс» по умолчанию
        mExpansionButton.setVisibility(GONE);
    }
    /**
     * Переключить полный текст / свернуть статус
     */
    private void toggleExpansionStatus() {
        // переключаем состояние
        mIsExpansion = !mIsExpansion;

        // Обновление содержимого и отображение кнопок переключения
        if (mIsExpansion) {
            mExpansionButton.setText("Свернуть");                       // Полнотекстовый статус, кнопка отображает «свернуть»
            mContentTextView.setMaxLines(Integer.MAX_VALUE);            // Полнотекстовый статус, количество строк установлено на максимум
        } else {
            mExpansionButton.setText("Показать");                       // Свернуть статус, кнопка отображает «полный текст»
            mContentTextView.setMaxLines(mMaxLine);                     // свернутое состояние, отображается максимальное количество указанных строк
        }
    }

    /**
     * Установите максимальное количество строк, которое может отображаться в свернутом состоянии (кнопка «свернуть» отображается, если количество строк превышено)
     */
    public void setMaxLine(int maxLine) {
        this.mMaxLine = maxLine;
        setText(mContent);                                      // Обновление статуса, повторное отображение текста
    }

    /**
     * Установите текст для отображения
     */
    public void setText(CharSequence text) {
        mContent = text;

        // Только когда текстовый элемент управления имеет ширину (после успешного прорисовки), можно получить количество строк, необходимое для отображения текста
        // Если элемент управления еще не нарисован, дождитесь успешного рисования, прежде чем задавать текст
        if (mContentTextView.getWidth() == 0) {
            return;
        }

        mContentTextView.setMaxLines(Integer.MAX_VALUE);        // По умолчанию максимальное количество строк установлено равным максимальному значению (то есть количество строк не ограничено)
        mContentTextView.setText(mContent);                     // установить текст

        int lineCount = mContentTextView.getLineCount();        // После установки текста получаем количество строк, необходимое для отображения текста

        if (lineCount > mMaxLine) {
            // количество строк превышает отображаемое, отображается кнопка «полный текст»
            mExpansionButton.setVisibility(VISIBLE);
            mExpansionButton.setText("Показать");

            mContentTextView.setMaxLines(mMaxLine);             // Устанавливаем максимально допустимое количество строк для текстового элемента управления
            mIsExpansion = false;

        } else {
            // Количество строк не превышает лимит, нет необходимости отображать кнопку «полный текст / сворачивание»
            mExpansionButton.setVisibility(GONE);
        }
    }
}
