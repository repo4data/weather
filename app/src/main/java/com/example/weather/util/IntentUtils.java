package com.example.weather.util;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.widget.Toast;

import java.net.URISyntaxException;
import java.util.List;

public class IntentUtils {
    /**
     * 检查intent是否能返回结果
     * @param context 上下文
     * @param intent 欲检测的intent
     * @return
     */
    public static boolean checkIntentResult(Context context, Intent intent) {
        List<ResolveInfo> list = context.getPackageManager().queryIntentActivities(intent, 0);
        return list.size() > 0;
    }

    /**
     * 通过URI构造Intent并启动Activity
     * @param context 上下文
     * @param uri 用于构造intent
     */
    public static void to(Context context, String uri) {
        try {
            Intent intent = Intent.getIntent(uri);
            if (checkIntentResult(context, intent)) {
                context.startActivity(intent);
            }else {
                Toast.makeText(context, "未找到相关地图应用", Toast.LENGTH_LONG).show();
            }
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    /**
     * 构造消息分享Intent并启动Activity
     * @param context
     * @param text
     * @param title
     */
    public static void sendMessage(Context context, String text, String title) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, text);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(Intent.createChooser(intent, title));
    }

    /**
     * 构造图文分享Intent并启动Activity
     *
     * @param context
     * @param text
     * @param uri
     * @param title
     */
    public static void sendImage(Context context, String text, Uri uri, String title) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_TEXT, text);
        intent.putExtra(Intent.EXTRA_STREAM, uri);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(Intent.createChooser(intent, title));
    }
}
