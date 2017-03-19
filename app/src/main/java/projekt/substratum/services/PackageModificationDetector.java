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

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import projekt.substratum.R;
import projekt.substratum.config.References;
import projekt.substratum.config.ThemeInterfacerService;
import projekt.substratum.config.ThemeManager;

import static projekt.substratum.config.References.DEBUG;
import static projekt.substratum.config.References.FIRST_WINDOW_REFRESH_DELAY;
import static projekt.substratum.config.References.INTERFACER_PACKAGE;
import static projekt.substratum.config.References.MAIN_WINDOW_REFRESH_DELAY;
import static projekt.substratum.config.References.SECOND_WINDOW_REFRESH_DELAY;

public class PackageModificationDetector extends BroadcastReceiver {

    private ArrayList<String> to_be_disabled;

    @Override
    public void onReceive(Context context, Intent intent) {
        if ("android.intent.action.PACKAGE_ADDED".equals(intent.getAction()) ||
                "android.intent.action.PACKAGE_REPLACED".equals(intent.getAction()) ||
                "android.intent.action.MY_PACKAGE_REPLACED".equals(intent.getAction())) {

            Uri packageName = intent.getData();
            String package_name = packageName.toString().substring(8);

            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
            if (prefs.contains("installed_icon_pack")) {
                to_be_disabled = new ArrayList<>();
                String current = prefs.getString("installed_icon_pack", null);
                if (current != null) {
                    if (current.equals(context.getPackageName())) {
                        List<ApplicationInfo> list = context.getPackageManager()
                                .getInstalledApplications(PackageManager.GET_META_DATA);
                        list.stream().filter(packageInfo ->
                                (packageInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 0).forEach(
                                packageInfo ->
                                        getSubstratumPackages(context,
                                                packageInfo.packageName));
                        String final_commands = ThemeManager.disableOverlay;
                        // TODO: do something with this
                        for (int i = 0; i < to_be_disabled.size(); i++) {
                            final_commands = final_commands + " " + to_be_disabled.get(i);
                        }
                        if (References.checkThemeInterfacer(context)) {
                            if (DEBUG)
                                Log.e(References.SUBSTRATUM_LOG, "Initializing the Theme " +
                                        "Interface...");
                            Intent runCommand = ThemeInterfacerService.getInterfacer(context);
                            runCommand.addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
                            runCommand.setAction(INTERFACER_PACKAGE + ".COMMANDS");
                            ArrayList<String> final_array = new ArrayList<>();
                            final_array.add(0, context.getString(R.string.studio_system)
                                    .toLowerCase());
                            final_array.add(1, final_commands);
                            final_array.add(2, (final_commands.contains("projekt.substratum") ?
                                    String.valueOf(MAIN_WINDOW_REFRESH_DELAY) : String.valueOf(0)));
                            final_array.add(3, String.valueOf(FIRST_WINDOW_REFRESH_DELAY));
                            final_array.add(4, String.valueOf(SECOND_WINDOW_REFRESH_DELAY));
                            final_array.add(5, null);
                            runCommand.putExtra("icon-handler", final_array);
                            context.sendBroadcast(runCommand);
                        }
                        prefs.edit().remove("installed_icon_pack").apply();
                    }
                }
            } else if (References.spreadYourWingsAndFly(context)) {
                SharedPreferences prefsPrivate = context.getSharedPreferences(
                        "filter_state", Context.MODE_PRIVATE);
                prefsPrivate.edit().clear().apply();
                Log.d(References.SUBSTRATUM_LOG,
                        "The filter cache has been wiped in accordance to intent: " +
                                package_name + " [" + intent.getAction() + "]");
            } else {
                Boolean found_valid_theme = false;
                if (!prefs.getBoolean("display_old_themes", true)) {
                    List<ResolveInfo> themes = References.getThemes(context);
                    for (int i = 0; i < themes.size(); i++) {
                        if (themes.get(i).activityInfo.packageName.equals(package_name)) {
                            SharedPreferences prefsPrivate = context.getSharedPreferences(
                                    "filter_state", Context.MODE_PRIVATE);
                            prefsPrivate.edit().clear().apply();
                            Log.d(References.SUBSTRATUM_LOG,
                                    "The filter cache has been wiped in accordance to intent:" +
                                            " " + package_name + " [" +
                                            intent.getAction() + "]");
                            found_valid_theme = true;
                        }
                    }
                } else {
                    try {
                        ApplicationInfo appInfo = context.getPackageManager().getApplicationInfo(
                                package_name, PackageManager.GET_META_DATA);
                        if (appInfo.metaData != null) {
                            if (appInfo.metaData.getString(References.metadataName) != null) {
                                if (appInfo.metaData.getString(References.metadataAuthor) != null) {
                                    SharedPreferences prefsPrivate = context.getSharedPreferences(
                                            "filter_state", Context.MODE_PRIVATE);
                                    prefsPrivate.edit().clear().apply();
                                    Log.d(References.SUBSTRATUM_LOG,
                                            "The filter cache has been wiped in accordance to " +
                                                    "intent: " + package_name + " [" +
                                                    intent.getAction() + "]");
                                    found_valid_theme = true;
                                }
                            }
                        }
                    } catch (Exception e) {
                        // Exception
                    }
                }
                if (!found_valid_theme) {
                    try {
                        ApplicationInfo appInfo = context.getPackageManager().getApplicationInfo(
                                package_name, PackageManager.GET_META_DATA);
                        if (appInfo.metaData != null) {
                            if (appInfo.metaData.getString(References.metadataName) != null) {
                                if (appInfo.metaData.getString(References.metadataAuthor) != null) {
                                    Log.e(References.SUBSTRATUM_LOG,
                                            "An outdated theme has been reported from " +
                                                    ": " + package_name);

                                    Intent showIntent = new Intent();
                                    PendingIntent contentIntent = PendingIntent.getActivity(
                                            context, 0, showIntent, 0);

                                    NotificationManager notificationManager =
                                            (NotificationManager) context.getSystemService(
                                                    Context.NOTIFICATION_SERVICE);
                                    NotificationCompat.Builder mBuilder =
                                            new NotificationCompat.Builder(context)
                                                    .setContentIntent(contentIntent)
                                                    .setAutoCancel(true)
                                                    .setSmallIcon(
                                                            R.drawable.notification_warning_icon)
                                                    .setContentTitle(context.getString(
                                                            R.string.legacy_theme_notification_title))
                                                    .setContentText(context.getString(
                                                            R.string.legacy_theme_notification_text));
                                    Notification notification = mBuilder.build();
                                    notificationManager.notify(
                                            References.notification_id, notification);

                                    References.uninstallPackage(context, package_name);
                                }
                            }
                        }
                    } catch (Exception e) {
                        // Suppress warning
                    }
                }
            }
        }
    }

    private void getSubstratumPackages(Context context, String package_name) {
        // Simulate the Layers Plugin feature by filtering all installed apps and their metadata
        try {
            ApplicationInfo appInfo = context.getPackageManager().getApplicationInfo(
                    package_name, PackageManager.GET_META_DATA);
            if (appInfo.metaData != null) {
                if (appInfo.metaData.getString("Substratum_IconPack") != null) {
                    to_be_disabled.add(package_name);
                }
            }
        } catch (Exception e) {
            // Suppress warnings
        }
    }
}
