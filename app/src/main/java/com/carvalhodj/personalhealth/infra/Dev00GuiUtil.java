package com.carvalhodj.personalhealth.infra;

import android.content.Context;
import android.widget.Toast;

public class Dev00GuiUtil {
    private static Dev00GuiUtil guiUtil = new Dev00GuiUtil();

    private Dev00GuiUtil(){
    }

    public static Dev00GuiUtil getGuiUtil(){
        return guiUtil;
    }

    public void toastLong(Context context, String texto){
        Toast.makeText(context, texto, Toast.LENGTH_LONG).show();
    }

    public void toastShort(Context context, String texto){
        Toast.makeText(context, texto, Toast.LENGTH_SHORT).show();
    }
}
