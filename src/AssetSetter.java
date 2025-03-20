public class AssetSetter {
    //GamePanel object.
    GamePanel gp;

    /**
     * AssetSetter constructor that initializes the game panel.
     * @param gp - GamePanel being used.
     */
    public AssetSetter(GamePanel gp) {
        this.gp = gp;
    }

    /**
     * Method that sets locations of objects such as keys, doors, and chests.
     */
    public void setObject() {
        //Sets position of first key.
        gp.obj[0] = new OBJKey(gp);
        gp.obj[0].worldX = 23 * gp.tileSize;
        gp.obj[0].worldY = 7 * gp.tileSize;

        //Sets position of second key.
        gp.obj[1] = new OBJKey(gp);
        gp.obj[1].worldX = 23 * gp.tileSize;
        gp.obj[1].worldY = 40 * gp.tileSize;

        //Sets position of third key.
        gp.obj[2] = new OBJKey(gp);
        gp.obj[2].worldX = 38 * gp.tileSize;
        gp.obj[2].worldY = 8 * gp.tileSize;

        //Sets position of the first door.
        gp.obj[3] = new OBJDoor(gp);
        gp.obj[3].worldX = 10 * gp.tileSize;
        gp.obj[3].worldY = 11 * gp.tileSize;

        //Sets position of the second door.
        gp.obj[4] = new OBJDoor(gp);
        gp.obj[4].worldX = 8 * gp.tileSize;
        gp.obj[4].worldY = 28 * gp.tileSize;

        //Sets position of the third door.
        gp.obj[5] = new OBJDoor(gp);
        gp.obj[5].worldX = 12 * gp.tileSize;
        gp.obj[5].worldY = 22 * gp.tileSize;

        //Sets position of the chest.
        gp.obj[6] = new OBJChest(gp);
        gp.obj[6].worldX = 10 * gp.tileSize;
        gp.obj[6].worldY = 7 * gp.tileSize;

        //Sets position of boots.
        gp.obj[7] = new OBJBoots(gp);
        gp.obj[7].worldX = 37 * gp.tileSize;
        gp.obj[7].worldY = 42 * gp.tileSize;
    }
}
