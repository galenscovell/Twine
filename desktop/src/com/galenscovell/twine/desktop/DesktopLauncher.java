package com.galenscovell.twine.desktop;

import com.galenscovell.twine.TwineMain;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

/**
 * DESKTOP LAUNCHER
 * Sets up application for run on desktop environment.
 *
 * @author Galen Scovell
 */

public class DesktopLauncher {
    public static void main (String[] arg) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.width = 480;
        config.height = 800;
        config.title = "Twine";
        config.resizable = true;
        new LwjglApplication(new TwineMain(), config);
    }
}
