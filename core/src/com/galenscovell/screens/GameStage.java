package com.galenscovell.screens;

import com.galenscovell.logic.Cell;
import com.galenscovell.logic.Grid;
import com.galenscovell.tween.ActorAccessor;
import com.galenscovell.util.ResourceManager;

import aurelienribon.tweenengine.equations.Bounce;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenManager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.scenes.scene2d.Touchable;
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
    private GameScreen root;
    private Grid grid;

    public GameStage(GameScreen root, SpriteBatch spriteBatch, TweenManager tweenManager, int difficulty, int levelNumber) {
        super(new FitViewport(480, 800), spriteBatch);
        this.root = root;
        create(tweenManager);
        grid.loadLevel(difficulty, levelNumber);
    }

    private void create(TweenManager tweenManager) {
        Table mainTable = new Table();
        mainTable.setFillParent(true);


        // Top bar
        Table topTable = new Table();
        topTable.setBackground(ResourceManager.barUp);
        Table menuButton = createButton(topTable, "barsHorizontal");
        menuButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                root.returnToLevelSelect();
            }
        });
        topTable.add(menuButton).width(50).height(50).expand().fill().left();
        Table checkButton = createButton(topTable, "checkmark");
        checkButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                checkSolution();
            }
        });
        topTable.add(checkButton).width(50).height(50).expand().fill().right();
        mainTable.add(topTable).height(80).expand().fillX().top().padBottom(64);
        mainTable.row();


        // Main game board
        Table gameBoard = new Table();

        Table gridTable = buildBoard(9, 9);
        gameBoard.add(gridTable).center();
        mainTable.add(gameBoard).height(480).expand().fillX().center().padBottom(96);
        mainTable.row();


        // Bottom bar
        Table bottomTable = new Table();
        bottomTable.setBackground(ResourceManager.barUp);
        mainTable.add(bottomTable).height(80).expand().fillX().center().bottom();

        this.addActor(mainTable);

        Tween.registerAccessor(Actor.class, new ActorAccessor());
        Tween.from(mainTable, ActorAccessor.ALPHA, 1.0f)
                .target(0)
                .start(tweenManager);
        Tween.from(gridTable, ActorAccessor.POS_Y, 1.0f)
                .target(100)
                .ease(Bounce.OUT)
                .start(tweenManager);
        tweenManager.update(Gdx.graphics.getDeltaTime());
    }

    private Table buildBoard(int rows, int columns) {
        Table gridTable = new Table();
        this.grid = new Grid();

        Cell[][] cells = new Cell[rows][columns];
        for (int y = 0; y < rows; y++) {
            for (int x = 0; x < columns; x++) {
                Cell cell = new Cell(x, y, grid);
                cells[y][x] = cell;
                gridTable.add(cell).width(48).height(48);
            }
            gridTable.row();
        }
        grid.updateCells(cells);
        return gridTable;
    }

    private Table createButton(Table table, String name) {
        Table button = new Table();
        button.setTouchable(Touchable.enabled);
        button.setBackground(new TextureRegionDrawable(ResourceManager.atlas.findRegion(name)));
        return button;
    }

    private void checkSolution() {
        if (grid.isComplete()) {
            updateScreen(1);
        } else {
            updateScreen(0);
        }
    }

    private void updateScreen(int type) {

    }
}

