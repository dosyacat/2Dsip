package object;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;

public class OBJ_DOOR extends SuperObject{
    GamePanel gp;
    public OBJ_DOOR(GamePanel gp){
        this.gp =gp;
        name = "Door";
        try{
            image = ImageIO.read(getClass().getResourceAsStream("/objects/doors.png"));
            uTool.scaleImage(image,gp.tileSize,gp.tileSize);

        }catch(IOException e){
            e.printStackTrace();
        }
        collision = true;

    }
}
