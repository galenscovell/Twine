package com.galenscovell.screens;

import com.galenscovell.logic.Cell;
import com.galenscovell.logic.Grid;
import com.galenscovell.util.ResourceManager;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;

/**
 * GAME STAGE
 * Stage holding all actors for the game as well as navigation bars.
 *
 * @author Galen Scovell
 */

public class GameStage extends Stage {
    private Grid grid;

    public GameStage() {
        super(new FitViewport(480, 800));
        create();
        grid.loadLevel();
    }

    private void create() {
        Table mainTable = new Table();
        mainTable.setFillParent(true);


        // Top bar
        Table topTable = new Table();
        topTable.setBackground(ResourceManager.hudbarBG);
        mainTable.add(topTable).height(64).expand().fill().center();
        mainTable.row();

        // Main game board
        Table gameBoard = new Table();
        gameBoard.setBackground(ResourceManager.skewedBoardBG);

        Table gridTable = buildBoard(9, 9);
        gameBoard.add(gridTable).center();
        mainTable.add(gameBoard).height(672).expand().fill().center();
        mainTable.row();


        // Bottom bar
        Table bottomTable = new Table();
        bottomTable.setBackground(ResourceManager.hudbarBG);
        mainTable.add(bottomTable).height(64).expand().fill().center();
        mainTable.row();

        this.addActor(mainTable);
    }

    private Table buildBoard(int rows, int columns) {
        Table gridTable = new Table();
        this.grid = new Grid();

        Cell[][] cells = new Cell[rows][columns];
        for (int y = 0; y < rows; y++) {
            for (int x = 0; x < columns; x++) {
                Cell cell = new Cell(x, y, grid);
                cells[y][x] = cell;
                gridTable.add(cell).width(48).height(64);
            }
            gridTable.row();
        }
        grid.updateCells(cells);
        return gridTable;
    }

}

