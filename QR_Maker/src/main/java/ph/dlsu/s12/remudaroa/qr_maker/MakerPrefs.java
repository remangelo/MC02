package ph.dlsu.s12.remudaroa.qr_maker;

import android.content.Context;
import android.content.SharedPreferences;

public class MakerPrefs {

    private SharedPreferences makerPreferences;
    private final String PREFS = "makerPreferences";

    public MakerPrefs(Context context)
    {
        makerPreferences = context.getSharedPreferences(PREFS, Context.MODE_PRIVATE);
    }

    public void saveStringPreferences(String key, String value) {
        SharedPreferences.Editor prefsEditor = makerPreferences.edit();
        prefsEditor.putString(key, value);
        prefsEditor.commit();
    }

    public void removePreferences(){
        SharedPreferences.Editor prefsEditor = makerPreferences.edit();
        prefsEditor.clear();
        prefsEditor.commit();
    }

    public String getStringPreferences(String key) {
        return (makerPreferences.getString(key, "Nothing Saved"));
    }

}