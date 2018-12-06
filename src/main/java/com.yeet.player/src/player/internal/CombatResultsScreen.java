package player.internal;

import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import renderer.external.Renderer;

import java.util.List;


/** Displays the winner of the combat as well as other related statistics and information
 *  @author bpx
 */
public class CombatResultsScreen extends Screen {

    private Text winnerBannerText;
    private List<String> playerList;

    private HBox playerBoxContainer;

    public CombatResultsScreen(Group root, Renderer renderer) {
        super(root, renderer);
        ImageView background = new ImageView(new Image(this.getClass().getClassLoader().getResourceAsStream("characterselect_bg.jpg")));
        background.setFitWidth(1280.0);
        background.setFitHeight(800.0);
        background.setOpacity(50);
        VBox mainContainer = new VBox();
        Rectangle topSpacer = new Rectangle(1280,20, Color.TRANSPARENT);
        StackPane winnerBanner = new StackPane();
        winnerBanner.setPrefSize(1280.0,200.0);
        winnerBanner.setAlignment(Pos.CENTER);
        ImageView winnerBannerImage = new ImageView(new Image(this.getClass().getClassLoader().getResourceAsStream("winner_banner.png")));
        winnerBannerImage.setFitWidth(1280.0);
        winnerBannerImage.setFitHeight(200.0);
        winnerBannerText = this.getMyRenderer().makeText("",true,50,Color.WHITE,0.0,0.0);
        playerBoxContainer = new HBox(5.0);
        playerBoxContainer.setPrefSize(1280.0,480.0);
        playerBoxContainer.setAlignment(Pos.CENTER);
        winnerBanner.getChildren().addAll(winnerBannerImage,winnerBannerText);
        mainContainer.getChildren().addAll(topSpacer,winnerBanner,playerBoxContainer);
        this.getMyRoot().getChildren().addAll(background,mainContainer);
    }

    /** Sets the name of the winner
     *  @param allPlayers The character names of the players
     *  @param playerRank The corresponding ranks of each character for the combat
     */
    public void setWinner(List<String> allPlayers, List<Integer> playerRank){
        //search for winner
        int winnerID = -1;
        for(int i=0;i<playerRank.size();i++){
            if(playerRank.get(i)==1){
                winnerID = i;
            }
        }
        if(winnerID!=-1){
            winnerBannerText.setText(allPlayers.get(winnerID));
            this.playerList = allPlayers;
            for(int i=0;i<allPlayers.size();i++){
                //TODO: CREATE PLAYER DISPLAYS
            }
        }

    }
}
