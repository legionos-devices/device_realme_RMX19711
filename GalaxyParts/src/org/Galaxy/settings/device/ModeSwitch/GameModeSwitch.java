/*
* Copyright (C) 2020 The LineageOS Project
*
* This program is free software: you can redistribute it and/or modify
* it under the terms of the GNU General Public License as published by
* the Free Software Foundation, either version 2 of the License, or
* (at your option) any later version.
*
* This program is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
* GNU General Public License for more details.
*
* You should have received a copy of the GNU General Public License
* along with this program. If not, see <http://www.gnu.org/licenses/>.
*
*/
package org.Galaxy.settings.device.ModeSwitch;

import android.util.Log;
import android.content.Context;
import android.content.SharedPreferences;
import androidx.preference.Preference;
import androidx.preference.Preference.OnPreferenceChangeListener;
import androidx.preference.PreferenceManager;

import org.Galaxy.settings.device.GalaxyParts;
import org.Galaxy.settings.device.Utils;

public class GameModeSwitch implements OnPreferenceChangeListener {

    private static final String FILE = "/proc/touchpanel/game_switch_enable";
    private static final String TAG = Utils.class.getSimpleName();

    public static String getFile() {
        if (Utils.fileWritable(FILE)) {
            return FILE;
        }
        return null;
    }

    public static boolean isSupported() {
        return Utils.fileWritable(getFile());
    }

    public static boolean isCurrentlyEnabled(Context context) {
        return Utils.getFileValueAsBoolean(getFile(), false);
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        Boolean enabled = (Boolean) newValue;
        Utils.writeValue(getFile(), enabled ? "1" : "0");
        Utils.writeValue(GalaxyParts.TP_LIMIT_ENABLE, enabled ? "0" : "1" );
        Utils.writeValue(GalaxyParts.TP_DIRECTION, enabled ? "1" : "0" );
        Log.e(TAG, "game mode set to " + enabled);
        return true;
    }
}
