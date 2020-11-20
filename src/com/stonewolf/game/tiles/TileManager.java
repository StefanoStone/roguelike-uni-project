package com.stonewolf.game.tiles;

import com.stonewolf.game.graphics.Sprite;
import com.stonewolf.game.utils.Camera;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.awt.*;
import java.util.ArrayList;

public class TileManager {

    public static ArrayList<TileMap> tileMap;
    private Camera camera;

    public TileManager() {
        tileMap = new ArrayList<>();
    }

    public TileManager(String path, Camera camera) {
        tileMap = new ArrayList<>();

        addTileMap(path, 64, 64, camera);
    }

    private void addTileMap(String path, int blockWidth, int blockHeight, Camera camera) {
        this.camera = camera;
        String imagePath;

        int width = 0;
        int height = 0;
        int tileWidth;
        int tileHeight;
        int tileCount;
        int tileColumns;
        int layers = 0;
        Sprite sprite;

        String[] data = new String[10];

        try {
            System.out.println("Loading: " + path + "...");
            DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = builderFactory.newDocumentBuilder();
            Document doc = builder.parse(this.getClass().getResourceAsStream(path));
            doc.getDocumentElement().normalize();

            NodeList list = doc.getElementsByTagName("tileset");
            Node node = list.item(0);
            Element element = (Element) node;

            imagePath = element.getAttribute("name");
            tileWidth = Integer.parseInt(element.getAttribute("tilewidth"));
            tileHeight = Integer.parseInt(element.getAttribute("tileheight"));
            tileColumns = Integer.parseInt(element.getAttribute("columns"));
            tileCount = Integer.parseInt(element.getAttribute("tilecount"));
            sprite = new Sprite("tile/" + imagePath + ".png", tileWidth, tileHeight);

            list = doc.getElementsByTagName("layer");
            layers = list.getLength();

            for (int i = 0; i < layers; i++) {
                node = list.item(i);
                element = (Element) node;
                if (i <= 0) {
                    width = Integer.parseInt(element.getAttribute("width"));
                    height = Integer.parseInt(element.getAttribute("height"));
                }

                data[i] = element.getElementsByTagName("data").item(0).getTextContent();

                if (i >= 1) {
                    tileMap.add(new TileMapNorm(data[i], sprite, width, height, blockWidth, blockHeight, tileColumns));
                } else {
                    tileMap.add(new TileMapObj(data[i], sprite, width, height, blockWidth, blockHeight, tileColumns));
                }

                camera.setLimit(width * blockWidth, height * blockHeight);
            }

        } catch (Exception e) {
            System.out.println("ERROR - TILEMANAGER: cannot read tilemap");
        }

    }

    public void render(Graphics2D g) {
        if (camera == null)
            return;

        for (int i = 0; i < tileMap.size(); i++) {
            tileMap.get(i).render(g, camera.getBounds());
        }
    }


}
