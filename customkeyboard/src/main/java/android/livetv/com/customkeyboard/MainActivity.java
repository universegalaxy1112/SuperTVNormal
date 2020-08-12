//package android.livetv.com.customkeyboard;
//import android.inputmethodservice.Keyboard;
//import android.inputmethodservice.KeyboardView;
//import android.support.v7.app.AppCompatActivity;
//import android.os.Bundle;
//import android.view.View;
//import android.view.WindowManager;
//import android.widget.EditText;
//
//public class MainActivity extends AppCompatActivity {
//    private KeyboardView keyboardView;
//    private Keyboard keyboard;
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//
//
//        keyboardView = (KeyboardView)findViewById(R.id.keyboard);
//        keyboardView.setPreviewEnabled(false);
//        keyboard = new Keyboard(this, R.xml.qwerty_two);
////        keyboard = new Keyboard(this, R.xml.qwerty);
//        keyboardView.setKeyboard(keyboard);
//        keyboardView.setVisibility(View.VISIBLE);
//        keyboardView.setOnKeyboardActionListener(new KeyboardActionListener(keyboard, keyboardView, (EditText)findViewById(R.id.edit_text_view)));
//
//        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
//    }
//}
