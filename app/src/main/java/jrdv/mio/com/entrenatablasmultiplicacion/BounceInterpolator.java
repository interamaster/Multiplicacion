package jrdv.mio.com.entrenatablasmultiplicacion;

/**
 * Created by esq00931 on 19/10/2016.
 * copiada de http://evgenii.com/blog/spring-button-animation-on-android/
 * para la animaciond e los botones de las tablas elegidas
 */

class BounceInterpolator implements android.view.animation.Interpolator {
    double mAmplitude = 1;
    double mFrequency = 10;

    BounceInterpolator(double amplitude, double frequency) {
        mAmplitude = amplitude;
        mFrequency = frequency;
    }

    public float getInterpolation(float time) {
        return (float) (-1 * Math.pow(Math.E, -time/ mAmplitude) *
                Math.cos(mFrequency * time) + 1);
    }
}