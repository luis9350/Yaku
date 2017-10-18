package com.project.yaku.presentation.model.ui;

import android.content.Intent;

/**
 * Created by luis on 17/10/17.
 */

public interface MasterView {

    void initializeComponents();
    void showMessage(String mensaje);
    void returnActivity();
    void navigateToActivity(Intent i);
}
