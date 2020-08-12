package android.livetv.com.customkeyboard;

import android.content.Context;
import android.inputmethodservice.Keyboard;

public class CustomKeyboard extends Keyboard {
    public CustomKeyboard(Context context, int xmlLayoutResId) {
        super(context, xmlLayoutResId);
    }

    public CustomKeyboard(Context context, int xmlLayoutResId, int modeId, int width, int height) {
        super(context, xmlLayoutResId, modeId, width, height);
    }

    public CustomKeyboard(Context context, int xmlLayoutResId, int modeId) {
        super(context, xmlLayoutResId, modeId);
    }

    public CustomKeyboard(Context context, int layoutTemplateResId, CharSequence characters, int columns, int horizontalPadding) {
        super(context, layoutTemplateResId, characters, columns, horizontalPadding);
    }
}
