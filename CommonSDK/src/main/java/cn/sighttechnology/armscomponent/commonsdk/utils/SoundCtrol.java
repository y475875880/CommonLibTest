package cn.sighttechnology.armscomponent.commonsdk.utils;

import android.app.Service;
import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.support.annotation.RequiresApi;


import static android.os.VibrationEffect.DEFAULT_AMPLITUDE;

/**
 * Description:
 * Data：2018/9/12-17:31
 * Author: yangjichao
 */
public class SoundCtrol {
    private Vibrator mVibrator;
    private int MUTE = 0; //静音
    private int VIBRATE = 1;//振动
    private int SOUND = 2;//响玲

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void playSound(Context context, MediaPlayer player) {
        //创建震动服务对象
        mVibrator=(Vibrator)context.getSystemService(Service.VIBRATOR_SERVICE);

        AudioManager am = (AudioManager)context.getSystemService(Context.AUDIO_SERVICE);
        //ringerMode为手机的系统声音设置的状态值，0位静音，1为震动，2为响铃
        final int ringerMode = am.getRingerMode();

        if (ringerMode == MUTE) {
            //do nothing
        }else if (ringerMode == VIBRATE) {
            /**
             * 创建一次性振动
             *
             * @param milliseconds 震动时长（ms）
             * @param amplitude 振动强度。这必须是1到255之间的值，或者DEFAULT_AMPLITUDE
             */
            //VibrationEffect vibrationEffect = VibrationEffect.createOneShot(long milliseconds, int amplitude);

            /**
             * 创建波形振动
             *
             * @param timings 交替开关定时的模式，从关闭开始。0的定时值将导致定时/振幅对被忽略。
             * @param repeat 振动重复的模式，如果您不想重复，则将索引放入计时数组中重复，或者-1。
             *               -1 为不重复
             *               0 为一直重复振动
             *               1 则是指从数组中下标为1的地方开始重复振动，重复振动之后结束
             *               2 从数组中下标为2的地方开始重复振动，重复振动之后结束
             */
            //VibrationEffect vibrationEffect = VibrationEffect.createWaveform(long[] timings, int repeat);

            /**
             * 创建波形振动
             *
             * @param timings 振幅值中的定时值。定时值为0振幅可忽视的。
             * @param amplitudes 振幅值中的振幅值。振幅值必须为0和255之间，或为DEFAULT_AMPLITUDE。振幅值为0意味着断开。
             * @param repeat 振动重复的模式，如果您不想重复，则将索引放入计时数组中重复，或者-1。
             */
            //VibrationEffect vibrationEffect = VibrationEffect.createWaveform(long[] timings, int[] amplitudes, int repeat);

            VibrationEffect vibrationEffect = VibrationEffect.createOneShot(1000, DEFAULT_AMPLITUDE);
            mVibrator.vibrate(vibrationEffect);

        } else if (ringerMode == SOUND) {
            //播放声音
            player.start();
        }
    }
}
