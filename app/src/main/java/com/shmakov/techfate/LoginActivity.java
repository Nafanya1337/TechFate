package com.shmakov.techfate;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;
import androidx.navigation.NavController;

import com.shmakov.techfate.database.UserDatabaseHelper;

import java.io.IOException;

public class LoginActivity extends AppCompatActivity implements LoginFragment.loginInterface, SignUpFragment.SignUpInterface, RemindPasswordFragment.remindPasswordInterface {

    UserDatabaseHelper userDatabaseHelper;

    private static final int MY_PERMISSION_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login2);
        userDatabaseHelper = new UserDatabaseHelper(this);
        try {
            userDatabaseHelper.createDataBase();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.white));
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence channelName = "Your Channel Name";
            String channelDescription = "Your Channel Description";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            String channelId = "notification";

            NotificationChannel channel = new NotificationChannel(channelId, channelName, importance);
            channel.setDescription(channelDescription);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);

            // Проверяем, есть ли разрешение на управление каналами уведомлений
            if (notificationManager.getNotificationChannel(channelId) == null) {
                // Запрашиваем разрешение на управление каналами уведомлений
                Intent intent = new Intent(Settings.ACTION_CHANNEL_NOTIFICATION_SETTINGS);
                intent.putExtra(Settings.EXTRA_APP_PACKAGE, getPackageName());
                intent.putExtra(Settings.EXTRA_CHANNEL_ID, channelId);
                startActivity(intent);
            }

            // Создаем канал уведомлений
            notificationManager.createNotificationChannel(channel);
        }

        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.POST_NOTIFICATIONS)
                != PackageManager.PERMISSION_GRANTED) {

            // Permission is not granted
            // Should we show an explanation?
            if (!ActivityCompat.shouldShowRequestPermissionRationale(this,
                    android.Manifest.permission.POST_NOTIFICATIONS)) {
                ActivityCompat.requestPermissions(this,
                        new String[]{android.Manifest.permission.POST_NOTIFICATIONS},
                        MY_PERMISSION_REQUEST);
            }

        }

    }

    @Override
    public void OnButtonClickLogin(String email, String password) {
        userDatabaseHelper.openDataBase();
        boolean exist = userDatabaseHelper.login(email, password);
        userDatabaseHelper.close();
        if (exist) {
            Intent intent = new Intent(this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            isLogging = true;
            startActivity(intent);
        }
        else {
            Toast.makeText(this, "Неверный почтовый ящик или пароль", Toast.LENGTH_SHORT).show();
        }

    }

    boolean isLogging = false;

    @Override
    public void loginOnBackPressed() {
        if (!isLogging) {
            Intent a = new Intent(Intent.ACTION_MAIN);
            a.addCategory(Intent.CATEGORY_HOME);
            a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(a);
        }
    }


    @Override
    public void SignUpInterface(String Name, String Email, String Password) {
        userDatabaseHelper.openDataBase();
        userDatabaseHelper.addNewUser(Name, Email, Password);
        userDatabaseHelper.close();
        OnButtonClickLogin(Email, Password);
    }

    @Override
    public boolean checkEmail(String Email) {
        userDatabaseHelper.openDataBase();
        boolean isExist = userDatabaseHelper.checkExistEmail(Email);
        userDatabaseHelper.close();
        return isExist;
    }

    @Override
    public void signUpOnBackPressed(NavController controller) {
        controller.popBackStack();
    }

    @Override
    public boolean remindPasswordInterface(String Email) {
        return checkEmail(Email);
    }

    @Override
    public void makeNotification(int code) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "notification");
        builder.setSmallIcon(R.drawable.ic_account);
        builder.setContentTitle("Код подтверждения");
        builder.setContentText(String.valueOf(code));
        builder.setAutoCancel(true);

        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(this);
        managerCompat.notify(1, builder.build());
    }
}