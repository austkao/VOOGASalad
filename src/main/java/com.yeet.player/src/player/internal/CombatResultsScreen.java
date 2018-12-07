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
import player.internal.Elements.CharacterChooseDisplay;
import renderer.external.Renderer;

import java.util.List;


/** Displays the winner of the combat as well as other related statistics and information
 *  @author bpx
 */
public class CombatResultsScreen extends Screen {

    public static final int RANK_Y_DROP = 40;
    private Text winnerBannerText;
    private List<String> playerList;

    private HBox playerBoxContainer;

    public CombatResultsScreen(Group root, Renderer renderer, SceneSwitch nextScene) {
        super(root, renderer);
        ImageView background = new ImageView(new Image(this.getClass().getClassLoader().getResourceAsStream("characterselect_bg.jpg")));
        background.setFitWidth(1280.0);
        background.setFitHeight(800.0);
        background.setOpacity(0.52);
        VBox mainContainer = new VBox();
        Rectangle topSpacer = new Rectangle(1280,20, Color.TRANSPARENT);
        StackPane winnerBanner = new StackPane();
        winnerBanner.setPrefSize(1280.0,200.0);
        winnerBanner.setAlignment(Pos.CENTER);
        ImageView winnerBannerImage = new ImageView(new Image(this.getClass().getClassLoader().getResourceAsStream("winner_banner.png")));
        winnerBannerImage.setFitWidth(1280.0);
        winnerBannerImage.setFitHeight(200.0);
        winnerBannerText = this.getMyRenderer().makeText("",true,120,Color.BLACK,0.0,0.0);
        playerBoxContainer = new HBox(5.0);
        playerBoxContainer.setPrefSize(1280.0,480.0);
        playerBoxContainer.setAlignment(Pos.CENTER);
        Rectangle midSpacer = new Rectangle(1280.0,100.0,Color.TRANSPARENT);
        winnerBanner.getChildren().addAll(winnerBannerImage,winnerBannerText);
        mainContainer.getChildren().addAll(topSpacer,winnerBanner,midSpacer,playerBoxContainer);
        this.getMyRoot().getChildren().addAll(background,mainContainer);
        this.setOnKeyPressed(event -> nextScene.switchScene());
    }

    /** Sets the name of the winner
     *  @param allPlayers The character names of the players
     *  @param playerRank The corresponding ranks of each character for the combat
     *  @param characterChooseList The list of {@code CharacterChooseDisplay} objects to display
     */
    public void setWinner(List<String> allPlayers, List<Integer> playerRank, List<CharacterChooseDisplay> characterChooseList){
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
                VBox displayContainer = new VBox();
                CharacterChooseDisplay display = characterChooseList.get(i);
                display.setDisable(true);
                VBox awardContainer = new VBox();
                awardContainer.setPrefSize(display.getPrefWidth(),display.getPrefHeight());
                awardContainer.setAlignment(Pos.CENTER);
                ImageView awardImage;
                if(playerRank.get(i)==1){
                    awardImage = new ImageView(new Image(this.getClass().getClassLoader().getResourceAsStream("rank1_icon.png")));
                }
                else if(playerRank.get(i)==2){
                    awardImage = new ImageView(new Image(this.getClass().getClassLoader().getResourceAsStream("rank2_icon.png")));
                    displayContainer.getChildren().add(new Rectangle(display.getPrefWidth(), RANK_Y_DROP,Color.TRANSPARENT));
                }
                else if(playerRank.get(i)==3){
                    awardImage = new ImageView(new Image(this.getClass().getClassLoader().getResourceAsStream("rank3_icon.png")));
                    displayContainer.getChildren().add(new Rectangle(display.getPrefWidth(), RANK_Y_DROP *2, Color.TRANSPARENT));
                }
                else if(playerRank.get(i)==4){
                    awardImage = new ImageView(new Image(this.getClass().getClassLoader().getResourceAsStream("rank4_icon.png")));
                    displayContainer.getChildren().add(new Rectangle(display.getPrefWidth(), RANK_Y_DROP *3,Color.TRANSPARENT));
                }
                else{
                    awardImage = new ImageView(new Image(this.getClass().getClassLoader().getResourceAsStream("empty.png")));
                    displayContainer.getChildren().add(new Rectangle(display.getPrefWidth(), RANK_Y_DROP *4,Color.TRANSPARENT));
                }
                awardImage.setFitHeight(awardContainer.getPrefWidth()/2);
                awardImage.setFitWidth(awardContainer.getPrefWidth()/2);
                awardContainer.getChildren().addAll(awardImage);
                display.getChildren().addAll(awardContainer);
                displayContainer.getChildren().addAll(display);
                playerBoxContainer.getChildren().add(displayContainer);
                if(i!=allPlayers.size()-1){
                    playerBoxContainer.getChildren().add(new Rectangle(90.0,480.0,Color.TRANSPARENT));
                }
            }
        }

    }
}
