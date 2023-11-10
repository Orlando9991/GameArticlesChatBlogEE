/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package com.gameblog.app.utils;

/**
 *
 * @author orlan
 */
public enum EPostTab {
    ALL("All"),
    ACTION("Action"),
    ADVENTURE("Adventure"),
    RPG("RPG"),
    SIMULATION("Simulation"),
    PUZZLE("Puzzle"),
    SPORTS_RACING("Sports/Racing");

    private final String category;

    EPostTab(String category) {
        this.category = category;
    }

    public String getCategory() {
        return this.category;
    }
    
    public String convertCategoryName(String category) {
        String modCategory = category.replaceFirst("/", "_").toUpperCase();
        switch (EPostTab.valueOf(modCategory)) {
            case ALL:
                return EPostTab.ALL.getCategory();
            case ACTION:
                return EPostTab.ACTION.getCategory();
            case ADVENTURE:
                return EPostTab.ADVENTURE.getCategory();
            case RPG:
                return EPostTab.RPG.getCategory();
            case SIMULATION:
                return EPostTab.SIMULATION.getCategory();
            case PUZZLE:
                return EPostTab.PUZZLE.getCategory();
            case SPORTS_RACING:
                return EPostTab.SPORTS_RACING.getCategory();
        }
        throw new RuntimeException("Error Post -- (Convert category name) Something went wrong");
    }
}
