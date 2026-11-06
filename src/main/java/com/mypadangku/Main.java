package com.mypadangku;

import com.mypadangku.controller.MenuController;
import com.mypadangku.view.MenuView;
import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MenuController controller = new MenuController();
            MenuView view = new MenuView(controller);
            view.setVisible(true);
        });
    }
}