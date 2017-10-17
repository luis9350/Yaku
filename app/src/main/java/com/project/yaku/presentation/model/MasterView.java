package com.project.yaku.presentation.model;

/**
 * Created by luis on 17/10/17.
 */

public interface MasterView {

    void initializeComponents();
    void showMessage(String mensaje);
    void returnActivity();
    void navigateToActivity();
}
