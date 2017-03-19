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

package projekt.substratum.adapters;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import java.io.File;
import java.util.List;

import projekt.substratum.R;
import projekt.substratum.config.References;
import projekt.substratum.model.OverlaysInfo;

public class OverlaysAdapter extends
        RecyclerView.Adapter<OverlaysAdapter.ViewHolder> {

    private List<OverlaysInfo> overlayList;

    public OverlaysAdapter(List<OverlaysInfo> overlayInfo) {
        this.overlayList = overlayInfo;

    }

    @Override
    public OverlaysAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.overlays_list_row, parent, false);
        return new ViewHolder(itemLayoutView);
    }

    // Magical easy reset checking for the adapter
    // Function that runs when a user picks a spinner dropdown item that is index 0

    private void zeroIndex(OverlaysInfo current_object, ViewHolder viewHolder) {
        if (current_object.isPackageInstalled((current_object.getFullOverlayParameters()))) {
            viewHolder.overlayState.setVisibility(View.VISIBLE);
            // Check whether currently installed overlay is up to date with
            // theme_pid's versionName
            if (!current_object.compareInstalledOverlay()) {
                String format = String.format(current_object
                                .getInheritedContext()
                                .getString(R.string.overlays_update_available),
                        current_object.versionName);
                viewHolder.overlayState.setText(format);
                viewHolder.overlayState.setTextColor(current_object
                        .getInheritedContext().getColor(R.color.overlay_update_available));
            } else {
                viewHolder.overlayState.setText(current_object
                        .getInheritedContext()
                        .getString(R.string.overlays_up_to_date));
                viewHolder.overlayState.setTextColor(current_object
                        .getInheritedContext().getColor(R.color.overlay_update_not_needed));
            }
        } else {
            if (viewHolder.overlayState.getVisibility() == View.VISIBLE) {
                viewHolder.overlayState.setText(current_object
                        .getInheritedContext()
                        .getString(R.string
                                .overlays_overlay_not_installed));
                viewHolder.overlayState.setTextColor(current_object
                        .getInheritedContext().getColor(R.color.overlay_not_approved_list_entry));
            } else {
                viewHolder.overlayState.setVisibility(View.GONE);
            }
        }
        if (current_object.isOverlayEnabled()) {
            viewHolder.overlayTargetPackageName.setTextColor(
                    current_object.getInheritedContext().getColor(
                            R.color.overlay_installed_list_entry));
        } else {
            if (current_object.isPackageInstalled(current_object
                    .getFullOverlayParameters())) {
                viewHolder.overlayTargetPackageName.setTextColor(
                        current_object.getInheritedContext().getColor(
                                R.color.overlay_not_enabled_list_entry));
            } else {
                viewHolder.overlayTargetPackageName.setTextColor(
                        current_object.getInheritedContext().getColor(
                                R.color.overlay_not_installed_list_entry));
            }
        }
    }

    // Function that runs when a user picks a spinner dropdown item that is index >= 1

    private void commitChanges(OverlaysInfo current_object, ViewHolder viewHolder, String
            packageName) {
        if (current_object.isPackageInstalled(current_object
                .getPackageName() + "." + current_object.getThemeName() + "." +
                packageName + ((current_object.getBaseResources().length
                () > 0) ? "." + current_object.getBaseResources() : ""))) {
            viewHolder.overlayState.setVisibility(View.VISIBLE);
            // Check whether currently installed overlay is up to date with
            // theme_pid's versionName
            if (!current_object.compareInstalledVariantOverlay(
                    packageName)) {
                String format = String.format(current_object
                                .getInheritedContext()
                                .getString(R.string
                                        .overlays_update_available),
                        current_object.versionName);

                viewHolder.overlayState.setText(format);
                viewHolder.overlayState.setTextColor(current_object
                        .getInheritedContext()
                        .getColor(R.color.overlay_update_available));
            } else {
                viewHolder.overlayState.setText(current_object
                        .getInheritedContext()
                        .getString(R.string.overlays_up_to_date));
                viewHolder.overlayState.setTextColor(current_object
                        .getInheritedContext().getColor(R.color.overlay_update_not_needed));
            }
            if (current_object.isOverlayEnabled()) {
                viewHolder.overlayTargetPackageName.setTextColor(
                        current_object.getInheritedContext().getColor(
                                R.color.overlay_installed_list_entry));
            } else {
                if (current_object.isPackageInstalled(current_object
                        .getFullOverlayParameters())) {
                    viewHolder.overlayTargetPackageName.setTextColor(
                            current_object.getInheritedContext().getColor
                                    (R.color.overlay_not_enabled_list_entry));
                } else {
                    viewHolder.overlayTargetPackageName.setTextColor(
                            current_object.getInheritedContext().getColor
                                    (R.color.overlay_not_installed_list_entry));
                }
            }
        } else {
            if (viewHolder.overlayState.getVisibility() == View.VISIBLE) {
                viewHolder.overlayState.setText(current_object
                        .getInheritedContext()
                        .getString(R.string
                                .overlays_overlay_not_installed));
                viewHolder.overlayState.setTextColor(current_object
                        .getInheritedContext()
                        .getColor(R.color.overlay_not_approved_list_entry));
                if (current_object.isOverlayEnabled()) {
                    viewHolder.overlayTargetPackageName.setTextColor(
                            current_object.getInheritedContext().getColor(
                                    R.color.overlay_installed_list_entry));
                } else {
                    if (current_object.isPackageInstalled(current_object
                            .getFullOverlayParameters())) {
                        viewHolder.overlayTargetPackageName.setTextColor(
                                current_object.getInheritedContext()
                                        .getColor(R.color.overlay_not_enabled_list_entry));
                    } else {
                        viewHolder.overlayTargetPackageName.setTextColor(
                                current_object.getInheritedContext()
                                        .getColor(R.color.overlay_not_installed_list_entry));
                    }
                }
            } else {
                viewHolder.overlayState.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, int position) {

        final OverlaysInfo current_object = overlayList.get(position);

        if (current_object.getAppIcon() == null) {
            current_object.setAppIcon(References.grabAppIcon(current_object.getInheritedContext(),
                    current_object.getPackageName()));
        }

        viewHolder.app_icon.setImageDrawable(current_object.getAppIcon());

        viewHolder.overlayTargetPackageName.setText(current_object.getName());

        viewHolder.overlayTargetPackage.setText(current_object.getPackageName());

        if (!current_object.isDeviceOMS()) viewHolder.overlayState.setVisibility(View.GONE);

        if (current_object.isPackageInstalled((current_object.getPackageName()) +
                "." + current_object.getThemeName() + ((current_object
                .getBaseResources().length() > 0) ? "." + current_object
                .getBaseResources() : "")) && current_object.isDeviceOMS()) {
            viewHolder.overlayState.setVisibility(View.VISIBLE);
            // Check whether currently installed overlay is up to date with theme_pid's versionName
            if (!current_object.compareInstalledOverlay()) {
                String format = String.format(current_object
                                .getInheritedContext()
                                .getString(R.string.overlays_update_available),
                        current_object.versionName);
                viewHolder.overlayState.setText(format);
                viewHolder.overlayState.setTextColor(current_object.getInheritedContext().
                        getColor(R.color.overlay_update_available));
            } else {
                viewHolder.overlayState.setText(current_object.getInheritedContext()
                        .getString(R.string.overlays_up_to_date));
                viewHolder.overlayState.setTextColor(current_object.getInheritedContext().
                        getColor(R.color.overlay_update_not_needed));
            }
        } else {
            viewHolder.overlayState.setVisibility(View.GONE);
        }

        viewHolder.checkBox.setChecked(current_object.isSelected());

        viewHolder.checkBox.setTag(current_object);

        viewHolder.checkBox.setOnClickListener(v -> {
            CheckBox cb = (CheckBox) v;
            OverlaysInfo contact = (OverlaysInfo) cb.getTag();

            contact.setSelected(cb.isChecked());
            current_object.setSelected(cb.isChecked());
        });

        viewHolder.card.setOnClickListener(v -> {
            viewHolder.checkBox.setChecked(!viewHolder.checkBox.isChecked());

            CheckBox cb = viewHolder.checkBox;
            OverlaysInfo contact = (OverlaysInfo) cb.getTag();

            contact.setSelected(cb.isChecked());
            current_object.setSelected(cb.isChecked());
        });

        if (current_object.variant_mode) {
            if (current_object.getSpinnerArray() != null) {
                viewHolder.optionsSpinner.setVisibility(View.VISIBLE);
                viewHolder.optionsSpinner.setAdapter(current_object.getSpinnerArray());
                viewHolder.optionsSpinner.setOnItemSelectedListener(new AdapterView
                        .OnItemSelectedListener() {

                    @Override
                    public void onItemSelected(AdapterView<?> arg0, View arg1, int pos, long id) {
                        current_object.setSelectedVariant(pos);
                        current_object.setSelectedVariantName(arg0.getSelectedItem().toString());

                        if (pos == 0) {
                            zeroIndex(current_object, viewHolder);
                        }

                        if (pos >= 1) {
                            String packageName = "";
                            packageName = packageName + arg0.getSelectedItem().toString()
                                    .replaceAll("\\s+", "").replaceAll("[^a-zA-Z0-9]+", "");
                            if (viewHolder.optionsSpinner2 != null && viewHolder
                                    .optionsSpinner2.getVisibility() == View.VISIBLE)
                                if (viewHolder.optionsSpinner2.getSelectedItemPosition() != 0)
                                    packageName = packageName + viewHolder.optionsSpinner2
                                            .getSelectedItem().toString().replaceAll("\\s+",
                                                    "").replaceAll("[^a-zA-Z0-9]+", "");
                            if (viewHolder.optionsSpinner3 != null && viewHolder
                                    .optionsSpinner3.getVisibility() == View.VISIBLE)
                                if (viewHolder.optionsSpinner3.getSelectedItemPosition() != 0)
                                    packageName = packageName + viewHolder.optionsSpinner3
                                            .getSelectedItem().toString().replaceAll("\\s+",
                                                    "").replaceAll("[^a-zA-Z0-9]+", "");
                            if (viewHolder.optionsSpinner4 != null && viewHolder
                                    .optionsSpinner4.getVisibility() == View.VISIBLE)
                                if (viewHolder.optionsSpinner4.getSelectedItemPosition() != 0)
                                    packageName = packageName + viewHolder.optionsSpinner4
                                            .getSelectedItem().toString().replaceAll("\\s+",
                                                    "").replaceAll("[^a-zA-Z0-9]+", "");
                            commitChanges(current_object, viewHolder, packageName);
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> arg0) {
                    }
                });
                viewHolder.optionsSpinner.setSelection(current_object.getSelectedVariant());
            } else {
                viewHolder.optionsSpinner.setVisibility(View.GONE);
            }
            if (current_object.getSpinnerArray2() != null) {
                viewHolder.optionsSpinner2.setVisibility(View.VISIBLE);
                viewHolder.optionsSpinner2.setAdapter(current_object.getSpinnerArray2());
                viewHolder.optionsSpinner2.setOnItemSelectedListener(new AdapterView
                        .OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> arg0, View arg1, int pos, long id) {
                        current_object.setSelectedVariant2(pos);
                        current_object.setSelectedVariantName2(arg0.getSelectedItem().toString());

                        if (pos == 0) {
                            zeroIndex(current_object, viewHolder);
                        }
                        if (pos >= 1) {
                            String packageName = "";
                            if (viewHolder.optionsSpinner != null && viewHolder
                                    .optionsSpinner.getVisibility() == View.VISIBLE)
                                if (viewHolder.optionsSpinner.getSelectedItemPosition() != 0)
                                    packageName = packageName + viewHolder.optionsSpinner
                                            .getSelectedItem().toString().replaceAll("\\s+", "")
                                            .replaceAll("[^a-zA-Z0-9]+", "");
                            packageName = packageName + arg0.getSelectedItem().toString()
                                    .replaceAll("\\s+", "").replaceAll("[^a-zA-Z0-9]+", "");
                            if (viewHolder.optionsSpinner3 != null && viewHolder
                                    .optionsSpinner3.getVisibility() == View.VISIBLE)
                                if (viewHolder.optionsSpinner3.getSelectedItemPosition() != 0)
                                    packageName = packageName + viewHolder.optionsSpinner3
                                            .getSelectedItem().toString().replaceAll("\\s+", "")
                                            .replaceAll("[^a-zA-Z0-9]+", "");
                            if (viewHolder.optionsSpinner4 != null && viewHolder
                                    .optionsSpinner4.getVisibility() == View.VISIBLE)
                                if (viewHolder.optionsSpinner4.getSelectedItemPosition() != 0)
                                    packageName = packageName + viewHolder.optionsSpinner4
                                            .getSelectedItem().toString().replaceAll("\\s+", "")
                                            .replaceAll("[^a-zA-Z0-9]+", "");
                            commitChanges(current_object, viewHolder, packageName);
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> arg0) {
                    }
                });
                viewHolder.optionsSpinner2.setSelection(current_object.getSelectedVariant2());
            } else {
                viewHolder.optionsSpinner2.setVisibility(View.GONE);
            }
            if (current_object.getSpinnerArray3() != null) {
                viewHolder.optionsSpinner3.setVisibility(View.VISIBLE);
                viewHolder.optionsSpinner3.setAdapter(current_object.getSpinnerArray3());
                viewHolder.optionsSpinner3.setOnItemSelectedListener(new AdapterView
                        .OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> arg0, View arg1, int pos, long id) {
                        current_object.setSelectedVariant3(pos);
                        current_object.setSelectedVariantName3(arg0.getSelectedItem().toString());

                        if (pos == 0) {
                            zeroIndex(current_object, viewHolder);
                        }
                        if (pos >= 1) {
                            String packageName = "";
                            if (viewHolder.optionsSpinner != null && viewHolder
                                    .optionsSpinner.getVisibility() == View.VISIBLE)
                                if (viewHolder.optionsSpinner.getSelectedItemPosition() != 0)
                                    packageName = packageName + viewHolder.optionsSpinner
                                            .getSelectedItem().toString().replaceAll("\\s+",
                                                    "").replaceAll("[^a-zA-Z0-9]+", "");
                            if (viewHolder.optionsSpinner2 != null && viewHolder
                                    .optionsSpinner2.getVisibility() == View.VISIBLE)
                                if (viewHolder.optionsSpinner2.getSelectedItemPosition() != 0)
                                    packageName = packageName + viewHolder.optionsSpinner2
                                            .getSelectedItem().toString().replaceAll("\\s+",
                                                    "").replaceAll("[^a-zA-Z0-9]+", "");
                            packageName = packageName + arg0.getSelectedItem().toString()
                                    .replaceAll("\\s+", "");
                            if (viewHolder.optionsSpinner4 != null && viewHolder
                                    .optionsSpinner4.getVisibility() == View.VISIBLE)
                                if (viewHolder.optionsSpinner4.getSelectedItemPosition() != 0)
                                    packageName = packageName + viewHolder.optionsSpinner4
                                            .getSelectedItem().toString().replaceAll("\\s+",
                                                    "").replaceAll("[^a-zA-Z0-9]+", "");
                            commitChanges(current_object, viewHolder, packageName);
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> arg0) {
                    }
                });
                viewHolder.optionsSpinner3.setSelection(current_object.getSelectedVariant3());
            } else {
                viewHolder.optionsSpinner3.setVisibility(View.GONE);
            }
            if (current_object.getSpinnerArray4() != null) {
                viewHolder.optionsSpinner4.setVisibility(View.VISIBLE);
                viewHolder.optionsSpinner4.setAdapter(current_object.getSpinnerArray4());
                viewHolder.optionsSpinner4.setOnItemSelectedListener(new AdapterView
                        .OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> arg0, View arg1, int pos, long id) {
                        current_object.setSelectedVariant4(pos);
                        current_object.setSelectedVariantName4(arg0.getSelectedItem().toString());

                        if (pos == 0) {
                            zeroIndex(current_object, viewHolder);
                        }
                        if (pos >= 1) {
                            String packageName = "";
                            if (viewHolder.optionsSpinner != null && viewHolder
                                    .optionsSpinner.getVisibility() == View.VISIBLE)
                                if (viewHolder.optionsSpinner.getSelectedItemPosition() != 0)
                                    packageName = packageName + viewHolder.optionsSpinner
                                            .getSelectedItem().toString().replaceAll("\\s+",
                                                    "").replaceAll("[^a-zA-Z0-9]+", "");
                            if (viewHolder.optionsSpinner2 != null && viewHolder
                                    .optionsSpinner2.getVisibility() == View.VISIBLE)
                                if (viewHolder.optionsSpinner2.getSelectedItemPosition() != 0)
                                    packageName = packageName + viewHolder.optionsSpinner2
                                            .getSelectedItem().toString().replaceAll("\\s+",
                                                    "").replaceAll("[^a-zA-Z0-9]+", "");
                            if (viewHolder.optionsSpinner3 != null && viewHolder
                                    .optionsSpinner3.getVisibility() == View.VISIBLE)
                                if (viewHolder.optionsSpinner3.getSelectedItemPosition() != 0)
                                    packageName = packageName + viewHolder.optionsSpinner3
                                            .getSelectedItem().toString().replaceAll("\\s+",
                                                    "").replaceAll("[^a-zA-Z0-9]+", "");
                            packageName = packageName + arg0.getSelectedItem().toString()
                                    .replaceAll("\\s+", "").replaceAll("[^a-zA-Z0-9]+", "");
                            commitChanges(current_object, viewHolder, packageName);
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> arg0) {
                    }
                });
                viewHolder.optionsSpinner4.setSelection(current_object.getSelectedVariant4());
            } else {
                viewHolder.optionsSpinner4.setVisibility(View.GONE);
            }
            if (current_object.isDeviceOMS()) {
                if (current_object.isOverlayEnabled()) {
                    viewHolder.overlayTargetPackageName.setTextColor(
                            current_object.getInheritedContext().getColor
                                    (R.color.overlay_installed_list_entry));
                } else {
                    if (current_object.isPackageInstalled(current_object
                            .getFullOverlayParameters())) {
                        viewHolder.overlayTargetPackageName.setTextColor(
                                current_object.getInheritedContext()
                                        .getColor(R.color.overlay_not_enabled_list_entry));
                    } else {
                        viewHolder.overlayTargetPackageName.setTextColor(
                                current_object.getInheritedContext()
                                        .getColor(R.color.overlay_not_installed_list_entry));
                    }
                }
            } else {
                // At this point, the object is an RRO formatted check
                String current_directory;
                if (References.inNexusFilter()) {
                    current_directory = "/system/overlay/";
                } else {
                    current_directory = "/system/vendor/overlay/";
                }
                File file = new File(current_directory);
                if (file.exists()) {
                    File file2 = new File(current_directory +
                            current_object.getPackageName() + "." +
                            current_object.getThemeName() + ".apk");
                    if (file2.exists()) {
                        viewHolder.overlayTargetPackageName.setTextColor(
                                current_object.getInheritedContext().getColor(
                                        R.color.overlay_installed_list_entry));
                    } else {
                        viewHolder.overlayTargetPackageName.setTextColor(
                                current_object.getInheritedContext().getColor(
                                        R.color.overlay_not_installed_list_entry));
                    }
                }
            }
        } else {
            viewHolder.optionsSpinner.setVisibility(View.GONE);
            viewHolder.optionsSpinner2.setVisibility(View.GONE);
            viewHolder.optionsSpinner3.setVisibility(View.GONE);
            viewHolder.optionsSpinner4.setVisibility(View.GONE);
            if (current_object.isDeviceOMS()) {
                if (current_object.isOverlayEnabled()) {
                    viewHolder.overlayTargetPackageName.setTextColor(
                            current_object.getInheritedContext().getColor
                                    (R.color.overlay_installed_list_entry));
                } else {
                    if (current_object.isPackageInstalled(current_object
                            .getFullOverlayParameters())) {
                        viewHolder.overlayTargetPackageName.setTextColor(
                                current_object.getInheritedContext()
                                        .getColor(R.color.overlay_not_enabled_list_entry));
                    } else {
                        viewHolder.overlayTargetPackageName.setTextColor(
                                current_object.getInheritedContext()
                                        .getColor(R.color.overlay_not_installed_list_entry));
                    }
                }
            }
        }
    }

    @Override
    public int getItemCount() {
        return overlayList.size();
    }

    public List<OverlaysInfo> getOverlayList() {
        return overlayList;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        CardView card;
        TextView overlayTargetPackageName;
        TextView overlayTargetPackage;
        TextView overlayState;
        CheckBox checkBox;
        Spinner optionsSpinner;
        Spinner optionsSpinner2;
        Spinner optionsSpinner3;
        Spinner optionsSpinner4;
        ImageView app_icon;

        ViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            app_icon = (ImageView) itemLayoutView.findViewById(R.id.app_icon);
            card = (CardView) itemLayoutView.findViewById(R.id.card);
            checkBox = (CheckBox) itemLayoutView.findViewById(R.id.checkBox);
            overlayState = (TextView) itemLayoutView.findViewById(R.id
                    .installedState);
            optionsSpinner = (Spinner) itemLayoutView.findViewById(R.id.optionsSpinner);
            optionsSpinner2 = (Spinner) itemLayoutView.findViewById(R.id.optionsSpinner2);
            optionsSpinner3 = (Spinner) itemLayoutView.findViewById(R.id.optionsSpinner3);
            optionsSpinner4 = (Spinner) itemLayoutView.findViewById(R.id.optionsSpinner4);
            overlayTargetPackageName = (TextView) itemLayoutView.findViewById(R.id
                    .overlayTargetPackageName);
            overlayTargetPackage = (TextView) itemLayoutView.findViewById(R.id
                    .overlayTargetPackage);
        }
    }
}