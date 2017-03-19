/*
 * Copyright (c) 2016-2017 Projekt Substratum
 * This file is part of Substratum.
 *
 * Substratum is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Substratum is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Substratum.  If not, see <http://www.gnu.org/licenses/>.
 */

package projekt.substratum.services;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import projekt.substratum.config.BootAnimationManager;
import projekt.substratum.config.FileOperations;
import projekt.substratum.config.FontManager;
import projekt.substratum.config.References;
import projekt.substratum.config.SoundManager;
import projekt.substratum.config.ThemeManager;
import projekt.substratum.config.WallpaperManager;

public class ThemeUninstallDetector extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if ("android.intent.action.PACKAGE_FULLY_REMOVED".equals(intent.getAction())) {
            Uri packageName = intent.getData();
            String package_name = packageName.toString().substring(8);

            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
            if (prefs.contains("installed_themes")) {
                Set installed_themes = prefs.getStringSet("installed_themes", null);
                if (installed_themes != null && installed_themes.contains(package_name)) {
                    // Get all installed overlays for this package
                    List<String> stateAll = ThemeManager.listOverlays(4);
                    stateAll.addAll(ThemeManager.listOverlays(5));

                    ArrayList<String> all_overlays = new ArrayList<>();
                    for (int j = 0; j < stateAll.size(); j++) {
                        try {
                            String current = stateAll.get(j);
                            ApplicationInfo appInfo = context
                                    .getPackageManager()
                                    .getApplicationInfo(
                                            current, PackageManager.GET_META_DATA);
                            if (appInfo.metaData != null &&
                                    appInfo.metaData.getString(
                                            "Substratum_Parent") != null) {
                                String parent =
                                        appInfo.metaData.getString("Substratum_Parent");
                                if (parent != null && parent.equals(package_name)) {
                                    all_overlays.add(current);
                                }
                            }
                        } catch (Exception e) {
                            // NameNotFound
                        }
                    }

                    // Uninstall all overlays for this package
                    ThemeManager.uninstallOverlay(context, all_overlays);

                    // Clear SubstratumBuilder cache for this package
                    Log.d(References.SUBSTRATUM_LOG, "Now purging caches for \"" + package_name +
                            "\"...");
                    FileOperations.delete(context, context.getCacheDir().getAbsolutePath() +
                            "/SubstratumBuilder/" + package_name + "/");

                    SharedPreferences.Editor editor = prefs.edit();
                    if (prefs.getString("sounds_applied", "").equals(package_name)) {
                        SoundManager.clearSounds(context);
                        editor.remove("sounds_applied");
                    }
                    if (prefs.getString("fonts_applied", "").equals(package_name)) {
                        FontManager.clearFonts(context);
                        editor.remove("fonts_applied");
                    }
                    if (prefs.getString("bootanimation_applied", "").equals(package_name)) {
                        BootAnimationManager.clearBootAnimation(context);
                        editor.remove("bootanimation_applied");
                    }
                    if (prefs.getString("home_wallpaper_applied", "").equals(package_name)) {
                        try {
                            WallpaperManager.clearWallpaper(context, "home");
                            editor.remove("home_wallpaper_applied");
                        } catch (IOException e) {
                            Log.e("ThemeUninstallDetector", "Failed to restore home screen " +
                                    "wallpaper!");
                        }
                    }
                    if (prefs.getString("lock_wallpaper_applied", "").equals(package_name)) {
                        try {
                            WallpaperManager.clearWallpaper(context, "lock");
                            editor.remove("lock_wallpaper_applied");
                        } catch (IOException e) {
                            Log.e("ThemeUninstallDetector", "Failed to restore lock screen " +
                                    "wallpaper!");
                        }
                    }
                    if (prefs.getString("app_shortcut_theme", "").equals(package_name)) {
                        References.clearShortcut(context);
                        editor.remove("app_shortcut_theme");
                    }
                    editor.apply();
                }
            }
        }
    }
}