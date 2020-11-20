package com.stonewolf.game.states;

import com.stonewolf.game.GamePanel;
import com.stonewolf.game.entity.Boss;
import com.stonewolf.game.entity.Enemy;
import com.stonewolf.game.entity.Player;
import com.stonewolf.game.graphics.Sprite;
import com.stonewolf.game.input.KeyHandler;
import com.stonewolf.game.input.MouseHandler;
import com.stonewolf.game.tiles.TileManager;
import com.stonewolf.game.utils.*;

import java.awt.*;
import java.util.ArrayList;

public class PlayState extends GameState {

    private Player player;
    private TileManager tileManager;
    private Camera camera;
    private ArrayList<Enemy> enemies;
    private ArrayList<Boss> bossParts;

    public static Vector2f map;

    public PlayState(GameStateManager gsm) {
        super(gsm);
        map = new Vector2f();
        Vector2f.setWorldVar(map.x, map.y);
        enemies = new ArrayList<>();
        bossParts = new ArrayList<>();

        camera = new Camera(new AABB(new Vector2f(0,0),
                GamePanel.width + 64, GamePanel.height + 64));

        tileManager = new TileManager("/tile/tilemap.xml", camera);

        player = new Player(camera, new Sprite("entity/LinkFormatted.png"),
                new Vector2f((GamePanel.width / 2) - 32, (GamePanel.height / 2) - 32), 64, gsm);

        enemies.add(new Enemy(camera, new Sprite("entity/ZombieFormatted.png", 48, 48),
                new Vector2f((GamePanel.width / 2) - 32 + 150, (GamePanel.height / 2) - 32 + 150), 64, gsm));
        enemies.add(new Enemy(camera, new Sprite("entity/GhoulFormatted.png", 48, 48),
                new Vector2f((GamePanel.width / 2) - 32 + 500, (GamePanel.height / 2) - 32 + 500), 64, gsm));
        enemies.add(new Enemy(camera, new Sprite("entity/ZombieFormatted.png", 48, 48),
                new Vector2f((GamePanel.width / 2) - 32 + 800, (GamePanel.height / 2) - 32 + 800), 64, gsm));
        enemies.add(new Enemy(camera, new Sprite("entity/GhoulFormatted.png", 48, 48),
                new Vector2f(1372.5f, 507.7f), 64, gsm));
        enemies.add(new Enemy(camera, new Sprite("entity/ZombieFormatted.png", 48, 48),
                new Vector2f(2168.2f, 775.4f), 64, gsm));
        enemies.add(new Enemy(camera, new Sprite("entity/GhoulFormatted.png", 48, 48),
                new Vector2f(2064.5f, 1670.499f), 64, gsm));
        enemies.add(new Enemy(camera, new Sprite("entity/ZombieFormatted.png", 48, 48),
                new Vector2f(1755.8f, 838.999f), 64, gsm));
        enemies.add(new Enemy(camera, new Sprite("entity/GhoulFormatted.png", 48, 48),
                new Vector2f(829.3997f, 1451.0997f), 64, gsm));
        enemies.add(new Enemy(camera, new Sprite("entity/ZombieFormatted.png", 48, 48),
                new Vector2f(1248.1001f, 2081.5996f), 64, gsm));
        enemies.add(new Enemy(camera, new Sprite("entity/GhoulFormatted.png", 48, 48),
                new Vector2f(1543.8f, 2641.2996f), 64, gsm));
        enemies.add(new Enemy(camera, new Sprite("entity/ZombieFormatted.png", 48, 48),
                new Vector2f(2768.0f, 2689.2996f), 64, gsm));
        enemies.add(new Enemy(camera, new Sprite("entity/GhoulFormatted.png", 48, 48),
                new Vector2f(2176.7f, 2083.2f), 64, gsm));
        enemies.add(new Enemy(camera, new Sprite("entity/ZombieFormatted.png", 48, 48),
                new Vector2f(817.0f, 2083.2f), 64, gsm));
        enemies.add(new Enemy(camera, new Sprite("entity/GhoulFormatted.png", 48, 48),
                new Vector2f(453.3f, 2470.9f), 64, gsm));

        bossParts.add(new Boss(camera, new Sprite("entity/PlantBossFormatted.png", 96,72),
                new Vector2f(751, 825), 128, gsm));
        bossParts.add(new Boss(camera, new Sprite("entity/PlantBossFormatted.png", 96,72),
                new Vector2f(1767, 831), 128, gsm));
        bossParts.add(new Boss(camera, new Sprite("entity/PlantBossFormatted.png", 96,72),
                new Vector2f(1279,1170), 128, gsm));
        bossParts.add(new Boss(camera, new Sprite("entity/PlantBossFormatted.png", 96,72),
                new Vector2f(779,1530), 128, gsm));
        bossParts.add(new Boss(camera, new Sprite("entity/PlantBossFormatted.png", 96,72),
                new Vector2f(1763, 1529), 128, gsm));

        camera.target(player);
    }

    @Override
    public void update() {

        Vector2f.setWorldVar(map.x, map.y);

        if (!gsm.paused) {
            for (int i = 0; i < enemies.size(); i++) {
                Enemy enemy = enemies.get(i);
                enemy.update(player);
                if(enemy.isToBeDestroyed()) {
                    enemies.remove(enemy);
                }
            }

            if  (enemies.size() == 0) {
                for (int i = 0; i < bossParts.size(); i++) {
                    Boss bossPart = bossParts.get(i);
                    bossPart.update(player);
                    if (bossPart.isToBeDestroyed()) {
                        bossParts.remove(bossPart);
                    }
                }
            }

            player.update(enemies, bossParts);
            camera.update();
        }
    }

    @Override
    public void input(MouseHandler mouse, KeyHandler key) {
        player.input(mouse, key);
        camera.input(mouse, key);
        key.esc.tick();
        if (key.esc.clicked) {
            gsm.add(GameStateManager.PAUSE);
            gsm.paused = true;
            }
        }

    @Override
    public void render(Graphics2D g) {
        tileManager.render(g);
        Sprite.drawArray(g,GamePanel.oldFrameCount + " FPS",
                        new Vector2f(GamePanel.width - 192, 10), 32,32,20,0);
        Sprite.drawArray(g,"LIVES " + player.getLives(),
                new Vector2f(GamePanel.width / 2 - 600, 10), 32,32,20,0);
        Sprite.drawArray(g,"HP " + player.getHealth(),
                new Vector2f(GamePanel.width / 2 - 600, 50), 32,32,20,0);

        if (enemies.size() != 0) {
            Integer numberOfEnemies = enemies.size();
            Sprite.drawArray(g, numberOfEnemies.toString(),
                    new Vector2f(GamePanel.width / 2, 50), 32,32,20,0);
        }

        player.render(g);
        for (Enemy enemy : enemies) {
            enemy.render(g);
        }
        if (enemies.size() == 0) {
            for (Boss bossPart : bossParts) {
                bossPart.render(g);
            }
        }
        camera.render(g);
    }

}
