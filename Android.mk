LOCAL_PATH := $(call my-dir)
include $(CLEAR_VARS)

#LOCAL_MODULE_TAGS := optional
#Needed for http://stackoverflow.com/questions/8446337/how-to-solve-error-this-attribute-must-be-localized-at-text-with-value-top
LOCAL_MODULE_TAGS := tests

# Taken from here: https://android.googlesource.com/platform/packages/apps/TV/+/android-live-tv-l-mr1/Android.mk
LOCAL_BUILDCONFIG_CLASS := app/src/main/java/projekt/substratum/BuildConfig.java
BC_OUT_DIR := $(LOCAL_PATH)
BC_APPLICATION_ID := "projekt.substratum"
BC_VERSION_CODE := 561
BC_VERSION_NAME := "five hundred sixty one"
include $(LOCAL_PATH)/buildconfig.mk

LOCAL_SRC_FILES := $(call all-java-files-under, app/src/main) \
   $(LOCAL_BUILDCONFIG_CLASS)

LOCAL_MANIFEST_FILE := app/src/main/AndroidManifest.xml
LOCAL_RESOURCE_DIR := $(LOCAL_PATH)/app/src/main/res
LOCAL_RESOURCE_DIR += frameworks/support/v7/appcompat/res
LOCAL_RESOURCE_DIR += frameworks/support/v7/cardview/res
LOCAL_RESOURCE_DIR += frameworks/support/v7/recyclerview/res
LOCAL_RESOURCE_DIR += frameworks/support/v7/preference/res
LOCAL_RESOURCE_DIR += frameworks/support/v14/preference/res
LOCAL_RESOURCE_DIR += frameworks/support/design/res
LOCAL_ASSET_DIR += $(LOCAL_PATH)/app/src/main/assets

LOCAL_STATIC_JAVA_LIBRARIES := android-support-v4
LOCAL_STATIC_JAVA_LIBRARIES += android-support-v7-appcompat
LOCAL_STATIC_JAVA_LIBRARIES += android-support-v7-cardview
LOCAL_STATIC_JAVA_LIBRARIES += android-support-v7-recyclerview
LOCAL_STATIC_JAVA_LIBRARIES += android-support-v7-palette
LOCAL_STATIC_JAVA_LIBRARIES += android-support-v7-preference
LOCAL_STATIC_JAVA_LIBRARIES += android-support-v13
LOCAL_STATIC_JAVA_LIBRARIES += android-support-v14-preference
LOCAL_STATIC_JAVA_LIBRARIES += android-support-annotations
LOCAL_STATIC_JAVA_LIBRARIES += android-support-design

LOCAL_STATIC_JAVA_LIBRARIES += playtasks1001
LOCAL_STATIC_JAVA_LIBRARIES += firebase-common1001
LOCAL_STATIC_JAVA_LIBRARIES += firebase-database1001
LOCAL_STATIC_JAVA_LIBRARIES += firebase-database-connection1001
LOCAL_STATIC_JAVA_LIBRARIES += firebase-iid1001
LOCAL_STATIC_JAVA_LIBRARIES += firebase-messaging1001

LOCAL_STATIC_JAVA_LIBRARIES += kellinwood-android14
LOCAL_STATIC_JAVA_LIBRARIES += kellinwood-lib11
LOCAL_STATIC_JAVA_LIBRARIES += kellinwood-log4j10
LOCAL_STATIC_JAVA_LIBRARIES += zip4j132
LOCAL_STATIC_JAVA_LIBRARIES += zipiolib18
LOCAL_STATIC_JAVA_LIBRARIES += zipsignerlib117

LOCAL_STATIC_JAVA_LIBRARIES += oms
LOCAL_STATIC_JAVA_LIBRARIES += commons-io25
LOCAL_STATIC_JAVA_LIBRARIES += kenburnsview107
LOCAL_STATIC_JAVA_LIBRARIES += picasso252
LOCAL_STATIC_JAVA_LIBRARIES += glide370
LOCAL_STATIC_JAVA_LIBRARIES += nineoldandroids240
LOCAL_STATIC_JAVA_LIBRARIES += zt-zip18
LOCAL_STATIC_JAVA_LIBRARIES += slf4j-api166

LOCAL_STATIC_JAVA_AAR_LIBRARIES := playbasement1001
LOCAL_STATIC_JAVA_AAR_LIBRARIES += substrate
LOCAL_STATIC_JAVA_AAR_LIBRARIES += wangavilib213
LOCAL_STATIC_JAVA_AAR_LIBRARIES += blurview112
LOCAL_STATIC_JAVA_AAR_LIBRARIES += materialprogressbar116
LOCAL_STATIC_JAVA_AAR_LIBRARIES += welcome072
LOCAL_STATIC_JAVA_AAR_LIBRARIES += materialsheetfab121
LOCAL_STATIC_JAVA_AAR_LIBRARIES += arcanimator100
LOCAL_STATIC_JAVA_AAR_LIBRARIES += materialdrawer581
LOCAL_STATIC_JAVA_AAR_LIBRARIES += aboutlibraries590
LOCAL_STATIC_JAVA_AAR_LIBRARIES += fastadapter210
LOCAL_STATIC_JAVA_AAR_LIBRARIES += iconics-core280
LOCAL_STATIC_JAVA_AAR_LIBRARIES += materialize100
LOCAL_STATIC_JAVA_AAR_LIBRARIES += itemanimators050
LOCAL_STATIC_JAVA_AAR_LIBRARIES += gesturerecycler131
LOCAL_STATIC_JAVA_AAR_LIBRARIES += galleryview100
LOCAL_STATIC_JAVA_AAR_LIBRARIES += androidimagecropper231
LOCAL_STATIC_JAVA_AAR_LIBRARIES += expandablelayout26

LOCAL_AAPT_INCLUDE_ALL_RESOURCES := true
LOCAL_AAPT_FLAGS := --auto-add-overlay
LOCAL_AAPT_FLAGS += --generate-dependencies
LOCAL_AAPT_FLAGS += --extra-packages android.support.v7.appcompat
LOCAL_AAPT_FLAGS += --extra-packages android.support.v7.cardview
LOCAL_AAPT_FLAGS += --extra-packages android.support.v7.recyclerview
LOCAL_AAPT_FLAGS += --extra-packages android.support.v7.preference
LOCAL_AAPT_FLAGS += --extra-packages android.support.v13
LOCAL_AAPT_FLAGS += --extra-packages android.support.v14.preference
LOCAL_AAPT_FLAGS += --extra-packages android.support.design
LOCAL_AAPT_FLAGS += --extra-packages com.google.android.gms
LOCAL_AAPT_FLAGS += --extra-packages projekt.substrate
LOCAL_AAPT_FLAGS += --extra-packages com.wang.avi
LOCAL_AAPT_FLAGS += --extra-packages eightbitlab.com.blurview
LOCAL_AAPT_FLAGS += --extra-packages me.zhanghai.android.materialprogressbar
LOCAL_AAPT_FLAGS += --extra-packages com.stephentuso.welcome
LOCAL_AAPT_FLAGS += --extra-packages com.gordonwong.materialsheetfab
LOCAL_AAPT_FLAGS += --extra-packages io.codetail.animation
LOCAL_AAPT_FLAGS += --extra-packages com.mikepenz.materialdrawer
LOCAL_AAPT_FLAGS += --extra-packages com.mikepenz.aboutlibraries
LOCAL_AAPT_FLAGS += --extra-packages com.mikepenz.fastadapter
LOCAL_AAPT_FLAGS += --extra-packages com.mikepenz.iconics.core
LOCAL_AAPT_FLAGS += --extra-packages com.mikepenz.materialize
LOCAL_AAPT_FLAGS += --extra-packages com.mikepenz.itemanimators
LOCAL_AAPT_FLAGS += --extra-packages com.thesurix.gesturerecycler
LOCAL_AAPT_FLAGS += --extra-packages me.wangyuwei.galleryview
LOCAL_AAPT_FLAGS += --extra-packages com.theartofdev.edmodo.cropper
LOCAL_AAPT_FLAGS += --extra-packages net.cachapa.expandablelayout

LOCAL_PACKAGE_NAME := Substratum
#LOCAL_PROGUARD_FLAG_FILES := app/proguard-rules.pro

LOCAL_PROGUARD_ENABLED := enabled

include $(BUILD_PACKAGE)

include $(CLEAR_VARS)

LOCAL_PREBUILT_STATIC_JAVA_LIBRARIES := kellinwood-android14:app/src/main/libs/kellinwood-logging-android-1.4.jar
LOCAL_PREBUILT_STATIC_JAVA_LIBRARIES += kellinwood-lib11:app/src/main/libs/kellinwood-logging-lib-1.1.jar
LOCAL_PREBUILT_STATIC_JAVA_LIBRARIES += kellinwood-log4j10:app/src/main/libs/kellinwood-logging-log4j-1.0.jar
LOCAL_PREBUILT_STATIC_JAVA_LIBRARIES += zip4j132:app/src/main/libs/zip4j_1.3.2.jar
LOCAL_PREBUILT_STATIC_JAVA_LIBRARIES += zipiolib18:app/src/main/libs/zipio-lib-1.8.jar
LOCAL_PREBUILT_STATIC_JAVA_LIBRARIES += zipsignerlib117:app/src/main/libs/zipsigner-lib-1.17.jar

LOCAL_PREBUILT_STATIC_JAVA_LIBRARIES += oms:app/src/main/libs/overlay-manager-service.jar

LOCAL_PREBUILT_STATIC_JAVA_LIBRARIES += playbasement1001:app/src/main/libs/play-services-basement-10.0.1.aar
LOCAL_PREBUILT_STATIC_JAVA_LIBRARIES += playtasks1001:app/src/main/libs/play-services-tasks-10.0.1.jar
LOCAL_PREBUILT_STATIC_JAVA_LIBRARIES += firebase-common1001:app/src/main/libs/firebase-common-10.0.1.jar
LOCAL_PREBUILT_STATIC_JAVA_LIBRARIES += firebase-database1001:app/src/main/libs/firebase-database-10.0.1.jar
LOCAL_PREBUILT_STATIC_JAVA_LIBRARIES += firebase-database-connection1001:app/src/main/libs/firebase-database-connection-10.0.1.jar
LOCAL_PREBUILT_STATIC_JAVA_LIBRARIES += firebase-iid1001:app/src/main/libs/firebase-iid-10.0.1.jar
LOCAL_PREBUILT_STATIC_JAVA_LIBRARIES += firebase-messaging1001:app/src/main/libs/firebase-messaging-10.0.1.jar

LOCAL_PREBUILT_STATIC_JAVA_LIBRARIES += commons-io25:app/src/main/libs/commons-io-2.5.jar
LOCAL_PREBUILT_STATIC_JAVA_LIBRARIES += kenburnsview107:app/src/main/libs/kenburnsview-1.0.7.jar
LOCAL_PREBUILT_STATIC_JAVA_LIBRARIES += picasso252:app/src/main/libs/picasso-2.5.2.jar
LOCAL_PREBUILT_STATIC_JAVA_LIBRARIES += glide370:app/src/main/libs/glide-3.7.0.jar
LOCAL_PREBUILT_STATIC_JAVA_LIBRARIES += nineoldandroids240:app/src/main/libs/nineoldandroids-2.4.0.jar
LOCAL_PREBUILT_STATIC_JAVA_LIBRARIES += zt-zip18:app/src/main/libs/zt-zip-1.8.jar
LOCAL_PREBUILT_STATIC_JAVA_LIBRARIES += slf4j-api166:app/src/main/libs/slf4j-api-1.6.6.jar

LOCAL_PREBUILT_STATIC_JAVA_LIBRARIES += wangavilib213:app/src/main/libs/wang-avi-lib-2.1.3.aar
LOCAL_PREBUILT_STATIC_JAVA_LIBRARIES += blurview112:app/src/main/libs/blurview-1.1.2.aar
LOCAL_PREBUILT_STATIC_JAVA_LIBRARIES += materialprogressbar116:app/src/main/libs/materialprogressbar-1.1.6.aar
LOCAL_PREBUILT_STATIC_JAVA_LIBRARIES += welcome072:app/src/main/libs/welcome-0.7.2.aar
LOCAL_PREBUILT_STATIC_JAVA_LIBRARIES += materialsheetfab121:app/src/main/libs/material-sheet-fab-1.2.1.aar
LOCAL_PREBUILT_STATIC_JAVA_LIBRARIES += arcanimator100:app/src/main/libs/arcanimator-1.0.0.aar
LOCAL_PREBUILT_STATIC_JAVA_LIBRARIES += materialdrawer581:app/src/main/libs/materialdrawer-5.8.1.aar
LOCAL_PREBUILT_STATIC_JAVA_LIBRARIES += aboutlibraries590:app/src/main/libs/aboutlibraries-5.9.0.aar
LOCAL_PREBUILT_STATIC_JAVA_LIBRARIES += fastadapter210:app/src/main/libs/fastadapter-2.1.0.aar
LOCAL_PREBUILT_STATIC_JAVA_LIBRARIES += iconics-core280:app/src/main/libs/iconics-core-2.8.0.aar
LOCAL_PREBUILT_STATIC_JAVA_LIBRARIES += materialize100:app/src/main/libs/materialize-1.0.0.aar
LOCAL_PREBUILT_STATIC_JAVA_LIBRARIES += itemanimators050:app/src/main/libs/itemanimators-0.5.0.aar
LOCAL_PREBUILT_STATIC_JAVA_LIBRARIES += gesturerecycler131:app/src/main/libs/gesture-recycler-1.3.1.aar
LOCAL_PREBUILT_STATIC_JAVA_LIBRARIES += galleryview100:app/src/main/libs/GalleryView-1.0.0.aar
LOCAL_PREBUILT_STATIC_JAVA_LIBRARIES += androidimagecropper231:app/src/main/libs/android-image-cropper-2.3.1.aar
LOCAL_PREBUILT_STATIC_JAVA_LIBRARIES += expandablelayout26:app/src/main/libs/expandablelayout-2.6.aar
LOCAL_PREBUILT_STATIC_JAVA_LIBRARIES += substrate:substrate/substrate.aar

include $(BUILD_MULTI_PREBUILT)

include $(call all-makefiles-under,$(LOCAL_PATH))
