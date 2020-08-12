package android.livetv.com.customkeyboard;

import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.text.InputType;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

public class KeyboardActionListener implements KeyboardView.OnKeyboardActionListener {

    private final EditText mEditText;
    private final KeyboardView mKeyboardView;
    private final Keyboard mKeyboard;
    private boolean caps = false;

    public KeyboardActionListener(Keyboard keyboard, KeyboardView keyboardView, EditText editText) {
        mEditText = editText;
        mKeyboard = keyboard;
        mKeyboardView = keyboardView;

        mEditText.setOnTouchListener(new View.OnTouchListener() {
            @Override public boolean onTouch(View v, MotionEvent event) {
                EditText edittext = (EditText) v;
                int inType = edittext.getInputType();       // Backup the input type
                edittext.setInputType(InputType.TYPE_NULL); // Disable standard keyboard
                edittext.onTouchEvent(event);               // Call native handler
                edittext.setInputType(inType);              // Restore input type
                edittext.setSelection(edittext.length());
                return true; // Consume touch event
            }
        });
    }


    @Override
    public void onKey(int primaryCode, int[] keyCodes) {
        playClick(primaryCode);
        switch(primaryCode){
            case Keyboard.EDGE_BOTTOM :
            case Keyboard.EDGE_LEFT:
            case Keyboard.EDGE_RIGHT:
            case Keyboard.EDGE_TOP:
                break;
            case Keyboard.KEYCODE_DELETE :
                mEditText.setText(mEditText.getText().subSequence(0, mEditText.getText().length()-1));
                mEditText.setSelection(mEditText.getText().length());
                break;
            case Keyboard.KEYCODE_SHIFT:
                caps = !caps;
                mKeyboard.setShifted(caps);
                mKeyboardView.invalidateAllKeys();
                break;
            case Keyboard.KEYCODE_DONE:
                mKeyboardView.setVisibility(View.INVISIBLE);
                mEditText.requestFocus();
                break;
            default:
                char code = (char)primaryCode;
                if(Character.isLetter(code) && caps){
                    code = Character.toUpperCase(code);
                }
                mEditText.setText(mEditText.getText() + String.valueOf(code));
                mEditText.setSelection(mEditText.getText().length());
//                ic.commitText(String.valueOf(code),1);
        }
    }

    @Override
    public void onPress(int primaryCode) {
    }

    @Override
    public void onRelease(int primaryCode) {
    }

    @Override
    public void onText(CharSequence text) {
    }

    @Override
    public void swipeDown() {
    }

    @Override
    public void swipeLeft() {
    }

    @Override
    public void swipeRight() {
    }

    @Override
    public void swipeUp() {
    }

    private void playClick(int keyCode){

    }
}
