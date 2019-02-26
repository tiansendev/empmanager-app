package empmanager.alochol.empmanager.util;

import android.content.Context;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;


import java.util.Timer;
import java.util.TimerTask;

import empmanager.alochol.empmanager.base.BaseAppliction;

public class KeyBordHeler {

    private static KeyBordHeler instance;

    private KeyBordHeler() {
        // construct
    }

    public static KeyBordHeler getInstance() {
        if (instance == null) {
            instance = new KeyBordHeler();
        }
        return instance;
    }

    /**
     * 隐藏软键盘
     * @param editText
     */
    public void hideKeyBoard(EditText editText){
        InputMethodManager imm=
                (InputMethodManager)
                        BaseAppliction.getInstance()
                                .getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(editText.getWindowToken(),0);
    }

    /**
     * 弹起软键盘
     * @param editText
     */
    public void openKeyBoard(final EditText editText){
        editText.setFocusable(true);
        editText.setFocusableInTouchMode(true);
        editText.requestFocus();
        Timer timer=new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                InputMethodManager imm=
                        (InputMethodManager)
                                BaseAppliction.getInstance()
                                        .getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(editText,0);
                editText.setSelection(editText.getText().length());
            }
        },200);


    }
}
