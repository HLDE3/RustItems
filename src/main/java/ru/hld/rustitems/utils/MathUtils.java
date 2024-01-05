package ru.hld.rustitems.utils;

public class MathUtils {
    public static float harp(float val, float current, float speed) {
        float emi = (current - val) * (speed / 2.0f) > 0.0f ? Math.max(speed, Math.min(current - val, (current - val) * (speed / 2.0f))) : Math.max(current - val, Math.min(-(speed / 2.0f), (current - val) * (speed / 2.0f)));
        return val + emi;
    }

    public static float clamp(float val, float min, float max) {
        float res = val;
        res = res < min ? min : (res > max ? max : res);
        return res;
    }

    public static float reclamp(float val, float min, float max) {
        float res = val;
        res = res < min ? max : (res > max ? min : res);
        return res;
    }

    public static double easeOutExpo(double x) {
        return x == 1 ? 1 : 1 - Math.pow(2, -10 * x);

    }

    public static double easeInExpo(double x) {
        return x == 0 ? 0 : Math.pow(2, 10 * x - 10);

    }

    public static double huease(double a) {
        return Math.abs(a - .5f) * 2;
    }

    public static double random(double min, double max) {
        return Math.random() * (max - min) + min;
    }

    public static float lerp(float start, float end, float t) {
        return start + (end - start) * t;
    }

    public static double lerp(double start, double end, float t) {
        return start + (end - start) * t;
    }

    public static float wrapDegrees(float value) {
        if ((value = (float) ((double) value % 360.0)) >= 180.0f) {
            value -= 360.0f;
        }
        if (value < -180.0f) {
            value += 360.0f;
        }
        return value;
    }

    public static double easeInOutQuad(double x, int step) {
        return x < 0.5 ? 2.0 * x * x : 1.0 - Math.pow(-2.0 * x + 2.0, step) / 2.0;
    }

}
