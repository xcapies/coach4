package com.tuk.coacher.mpesa.interfaces;

import com.tuk.coacher.mpesa.Mpesa;
import com.tuk.coacher.mpesa.utils.Pair;

/**
 * Created by miles on 18/11/2017.
 */

public interface AuthListener {
    public void onAuthError(Pair<Integer, String> result);
    public void onAuthSuccess();
}
