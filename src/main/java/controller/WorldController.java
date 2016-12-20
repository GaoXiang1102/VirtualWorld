package controller;

import exception.MyException;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import model.Delta;
import model.Direction;
import model.Location;
import model.World;
import util.JsonUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static model.Direction.*;

public class WorldController {

    // the AnchorPane to place photos and direction control buttons
    @FXML
    public AnchorPane photo_panel;

    // the ImageView to display photos
    @FXML
    private ImageView photo;

    // the ImageView to display a little guidance map
    @FXML
    private ImageView map;

    // the ImageView point out your current location in the little guidance map
    @FXML
    private ImageView position_label;

    // the button to turn left
    @FXML
    private ImageView turn_left;

    // the button to turn right
    @FXML
    private ImageView turn_right;

    // the button to move forward
    @FXML
    private ImageView go_button;

    // the first item that can be dragged into the virtual world
    @FXML
    private ImageView item1;

    // the second item that can be dragged into the virtual world
    @FXML
    public ImageView item2;

    // the third item that can be dragged into the virtual world
    @FXML
    public ImageView item3;

    // the forth item that can be dragged into the virtual world
    @FXML
    public ImageView item4;

    // the picker to pick the items back from the virtual world
    @FXML
    public ImageView picker;

    // the logo of the pokemon theme
    @FXML
    public ImageView pokemon_logo;

    // the logo of the one piece theme
    @FXML
    public ImageView one_piece_logo;

    // the logo of the dragon ball theme
    @FXML
    public ImageView dragon_ball_logo;

    // a HashMap to record all items and corresponding filenames
    private Map<ImageView, String> item_list = new HashMap<ImageView, String>();

    // a HashMap to record the location and direction information of all items
    private Map<ImageView, String> item_info = new HashMap<ImageView, String>();

    // a HashMap to record the coordinates of those items which are dragged into the virtual world
    private Map<ImageView, String> item_coordinates = new HashMap<ImageView, String>();

    // a World model to record all the location information of the virtual world
    private World world = new World();

    // a Location model indicating where you are
    private Location location = new Location("32");

    // a Direction model indicating your current direction
    private Direction direction = N;

    // a number indicating which theme you want to use
    private int skin_num = 0;

    // get and set function
    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    /**
     * initialise the item_list from the json file
     *
     * @throws MyException
     */
    public void init_item_list() throws MyException {
        if (item_list.size() == 0) {
            List<String> items = JsonUtil.getInstance().get_all_items(skin_num);
            if (items.size() < 4) {
                throw new MyException("the number of items must >= 4");
            }
            item_list.put(item1, items.get(0));
            item_list.put(item2, items.get(1));
            item_list.put(item3, items.get(2));
            item_list.put(item4, items.get(3));
        }
    }

    /**
     * display a photo as a part of your virtual world
     */
    public void show_photo() {
        Image image = JsonUtil.getInstance().get_one_picture(location.getLocation_number() + "-" + direction.getDirection_name() + ".jpg");
        photo.setImage(image);
    }

    /**
     * display the little guidance map
     */
    public void show_map() {
        Image image = JsonUtil.getInstance().get_one_picture("map.png");
        map.setImage(image);
    }

    /**
     * display the position label on the little guidance map to show where you are
     */
    public void show_position_label() {
        Image image = JsonUtil.getInstance().get_one_picture("position_label.png");
        position_label.setImage(image);
        switch (this.location.getLocation_number()) {
            case "32":
                position_label.setLayoutX(132);
                position_label.setLayoutY(210);
                break;
            case "42":
                position_label.setLayoutX(132);
                position_label.setLayoutY(50);
                break;
            case "31":
                position_label.setLayoutX(10);
                position_label.setLayoutY(210);
                break;
            case "33":
                position_label.setLayoutX(240);
                position_label.setLayoutY(190);
                break;
            case "22":
                position_label.setLayoutX(135);
                position_label.setLayoutY(320);
                break;
            case "12":
                position_label.setLayoutX(120);
                position_label.setLayoutY(440);
                break;
        }

    }

    /**
     * show turn left and turn right button
     */
    public void show_arrow() {
        Image left = JsonUtil.getInstance().get_one_picture("turn_left.png");
        turn_left.setImage(left);
        Image right = JsonUtil.getInstance().get_one_picture("turn_right.png");
        turn_right.setImage(right);
    }

    /**
     * show all the items, either in the bottom panel of in the virtual world
     */
    public void show_items() {
        for (ImageView item : item_list.keySet()) {
            if (item.getImage() == null) {
                Image image = JsonUtil.getInstance().get_one_picture(item_list.get(item));
                item.setImage(image);
            } else {
                if (item_info.containsKey(item) &&
                        !item_info.get(item).equals(
                                location.getLocation_number() + "-" + direction.getDirection_name())) {
                    item.setVisible(false);
                } else {
                    item.setVisible(true);
                }
            }
        }
    }

    /**
     * update all items of a new theme
     */
    public void update_items() {
        for (ImageView item : item_list.keySet()) {
            Image image = JsonUtil.getInstance().get_one_picture(item_list.get(item));
            item.setImage(image);
            item.setVisible(true);
        }
    }

    /**
     * display the picker according to the current skin_num
     */
    public void show_picker() {
        if (picker.getImage() == null) {
            String file = JsonUtil.getInstance().get_picker(skin_num);
            Image image = JsonUtil.getInstance().get_one_picture(file);
            picker.setImage(image);
        }
    }

    /**
     * update the picker according a new skin_num read from the json file
     */
    public void update_picker() {
        String file = JsonUtil.getInstance().get_picker(skin_num);
        Image image = JsonUtil.getInstance().get_one_picture(file);
        picker.setImage(image);
    }

    /**
     * show logos of all themes
     */
    public void show_logo() {
        if (pokemon_logo.getImage() == null) {
            Image image = JsonUtil.getInstance().get_one_picture("pokemon_logo.gif");
            pokemon_logo.setImage(image);
        }
        if (one_piece_logo.getImage() == null) {
            Image image = JsonUtil.getInstance().get_one_picture("one_piece_logo.png");
            one_piece_logo.setImage(image);
        }
        if (dragon_ball_logo.getImage() == null) {
            Image image = JsonUtil.getInstance().get_one_picture("dragon_ball_logo.png");
            dragon_ball_logo.setImage(image);
        }
    }

    /**
     * show forward button if you can move forward, or the lock button prohibiting you from moving forward
     */
    public void show_button() {
        Image go = null;
        if (next_location_in_this_direction() != null && !hit_the_wall()) {
            go = JsonUtil.getInstance().get_one_picture("go.png");
        } else {
            go = JsonUtil.getInstance().get_one_picture("lock.png");
        }
        go_button.setImage(go);
    }

    /**
     * judge whether you hit the wall, if you do, you can't move forward
     *
     * @return
     */
    public boolean hit_the_wall() {
        return (this.direction.equals(WN) ||
                this.direction.equals(NE) ||
                this.direction.equals(WS) ||
                this.direction.equals(SE));
    }

    /**
     * compute the next location if you move forward in your current direction
     *
     * @return
     */
    public Location next_location_in_this_direction() {
        String location_num = this.location.getLocation_number();
        Location next_location = null;
        switch (getDirection()) {
            case N:
                next_location = new Location((Integer.parseInt(location_num) + 10) + "");
                break;
            case WN:
                next_location = new Location((Integer.parseInt(location_num) + 9) + "");
                break;
            case W:
                next_location = new Location((Integer.parseInt(location_num) - 1) + "");
                break;
            case WS:
                next_location = new Location((Integer.parseInt(location_num) - 11) + "");
                break;
            case S:
                next_location = new Location((Integer.parseInt(location_num) - 10) + "");
                break;
            case SE:
                next_location = new Location((Integer.parseInt(location_num) - 9) + "");
                break;
            case E:
                next_location = new Location((Integer.parseInt(location_num) + 1) + "");
                break;
            case NE:
                next_location = new Location((Integer.parseInt(location_num) + 11) + "");
        }
        if (this.world.has_a_location(next_location)) {
            return next_location;
        }
        return null;
    }

    /**
     * turn left (45 degrees)
     *
     * @param event
     */
    public void turn_left(MouseEvent event) {
        switch (getDirection()) {
            case N:
                setDirection(WN);
                break;
            case WN:
                setDirection(W);
                break;
            case W:
                setDirection(WS);
                break;
            case WS:
                setDirection(S);
                break;
            case S:
                setDirection(SE);
                break;
            case SE:
                setDirection(E);
                break;
            case E:
                setDirection(NE);
                break;
            case NE:
                setDirection(N);
        }
        show_photo();
        show_button();
        show_items();
        show_picker();
    }

    /**
     * turn right (45 degrees)
     *
     * @param event
     */
    public void turn_right(MouseEvent event) {
        switch (getDirection()) {
            case N:
                setDirection(NE);
                break;
            case NE:
                setDirection(E);
                break;
            case E:
                setDirection(SE);
                break;
            case SE:
                setDirection(S);
                break;
            case S:
                setDirection(WS);
                break;
            case WS:
                setDirection(W);
                break;
            case W:
                setDirection(WN);
                break;
            case WN:
                setDirection(N);
        }
        show_photo();
        show_button();
        show_items();
        show_picker();
    }

    /**
     * move forward (move to the next location)
     *
     * @param event
     */
    public void move_forword(MouseEvent event) {
        Location next_location = next_location_in_this_direction();
        if (next_location == null) {
            return;
        } else {
            if (hit_the_wall()) {
                return;
            }
            setLocation(next_location);
            show_photo();
            show_button();
            show_items();
            show_picker();
            show_position_label();
        }
    }

    /**
     * initialise all the actions of items
     */
    public void initialise_items_action() {

        final Delta dragDelta = new Delta();
        final Glow glow = new Glow(1.0);

        for (final ImageView item : item_list.keySet()) {

            item.setOnMousePressed(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    // record a delta distance for the drag and drop operation.
                    dragDelta.x = item.getLayoutX() - mouseEvent.getSceneX();
                    dragDelta.y = item.getLayoutY() - mouseEvent.getSceneY();
                    item.setCursor(Cursor.MOVE);
                    item.setEffect(glow);
                }
            });
            item.setOnMouseReleased(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    item.setCursor(Cursor.HAND);
                    item.setEffect(null);
                    if (item.getLayoutX() >= 0 && item.getLayoutX() <= 920 && item.getLayoutY() >= 0 && item.getLayoutY() <= 470) {
                        item.setFitHeight(100);
                        item.setFitWidth(100);
                        item_info.put(item, getLocation().getLocation_number() + "-" + getDirection().getDirection_name());
                        item_coordinates.put(item, item.getLayoutX() + "-" + item.getLayoutY());
                    } else {
                        if (item_coordinates.containsKey(item)) {
                            String[] coordinates = item_coordinates.get(item).split("-");
                            item.setLayoutX(Double.parseDouble(coordinates[0]));
                            item.setLayoutY(Double.parseDouble(coordinates[1]));
                        } else {
                            item_info.remove(item);
                            item.setFitHeight(200);
                            item.setFitWidth(200);
                            if (item == item1) {
                                item.setLayoutX(500);
                                item.setLayoutY(600);
                            } else if (item == item2) {
                                item.setLayoutX(700);
                                item.setLayoutY(600);
                            } else if (item == item3) {
                                item.setLayoutX(900);
                                item.setLayoutY(600);
                            } else if (item == item4) {
                                item.setLayoutX(1100);
                                item.setLayoutY(600);
                            }
                        }
                    }
                }
            });
            item.setOnMouseDragged(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    item.setLayoutX(mouseEvent.getSceneX() + dragDelta.x);
                    item.setLayoutY(mouseEvent.getSceneY() + dragDelta.y);
                }
            });
            item.setOnMouseEntered(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    item.setCursor(Cursor.HAND);
                    item.setEffect(glow);
                }
            });
            item.setOnMouseExited(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    item.setEffect(null);
                }
            });
        }
    }

    /**
     * initialise all the actions of the picker
     */
    public void initialise_picker_action() {
        final Delta dragDelta = new Delta();
        final Glow glow = new Glow(1.0);

        picker.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                // record a delta distance for the drag and drop operation.
                dragDelta.x = picker.getLayoutX() - mouseEvent.getSceneX();
                dragDelta.y = picker.getLayoutY() - mouseEvent.getSceneY();
                picker.setCursor(Cursor.MOVE);
                picker.setEffect(glow);
            }
        });
        picker.setOnMouseReleased(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                picker.setCursor(Cursor.HAND);
                picker.setEffect(null);
                for (ImageView item : item_list.keySet()) {
                    if (item.getLayoutX() >= 0 && item.getLayoutX() <= 920 && item.getLayoutY() >= 0 && item.getLayoutY() <= 470 && catch_it(item)) {
                        item.setFitWidth(200);
                        item.setFitHeight(200);
                        if (item.equals(item1)) {
                            item.setLayoutX(500);
                            item.setLayoutY(600);
                        } else if (item.equals(item2)) {
                            item.setLayoutX(700);
                            item.setLayoutY(600);
                        } else if (item.equals(item3)) {
                            item.setLayoutX(900);
                            item.setLayoutY(600);
                        } else if (item.equals(item4)) {
                            item.setLayoutX(1100);
                            item.setLayoutY(600);
                        }
                        item_info.remove(item);
                        item_coordinates.remove(item);
                    }
                }
                picker.setLayoutX(250);
                picker.setLayoutY(600);
            }
        });
        picker.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                picker.setLayoutX(mouseEvent.getSceneX() + dragDelta.x);
                picker.setLayoutY(mouseEvent.getSceneY() + dragDelta.y);
            }
        });
        picker.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                picker.setCursor(Cursor.HAND);
                picker.setEffect(glow);
            }
        });
        picker.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                picker.setEffect(null);
            }
        });
    }

    /**
     * judge whether you catch the item through your picker
     *
     * @param item
     * @return
     */
    public boolean catch_it(ImageView item) {
        double picker_center_x = picker.getLayoutX() + picker.getFitWidth() / 2;
        double picker_center_Y = picker.getLayoutY() + picker.getFitHeight() / 2;
        if (picker_center_x > item.getLayoutX() && picker_center_x < item.getLayoutX() + item.getFitWidth()
                && picker_center_Y > item.getLayoutY() && picker_center_Y < item.getLayoutY() + item.getFitHeight()) {
            return true;
        }
        return false;
    }

    /**
     * reset all items, get all the items in the virtual world out, put them in the bottom pane
     *
     * @throws MyException
     */
    public void reset_items() throws MyException {
        this.item_info.clear();
        this.item_coordinates.clear();
        this.item_list.clear();
        this.init_item_list();
        item1.setLayoutX(500);
        item1.setLayoutY(600);
        item1.setFitWidth(200);
        item1.setFitHeight(200);
        item2.setLayoutX(700);
        item2.setLayoutY(600);
        item2.setFitWidth(200);
        item2.setFitHeight(200);
        item3.setLayoutX(900);
        item3.setLayoutY(600);
        item3.setFitWidth(200);
        item3.setFitHeight(200);
        item4.setLayoutX(1100);
        item4.setLayoutY(600);
        item4.setFitWidth(200);
        item4.setFitHeight(200);
    }

    /**
     * initialise the location and direction from the json file
     */
    public void init_location() {
        String loc_and_direc = JsonUtil.getInstance().get_loc_and_direc();
        String location = loc_and_direc.split("-")[0];
        String direction = loc_and_direc.split("-")[1];
        this.location.setLocation_number(location);
        switch (direction) {
            case "N":
                this.direction = N;
                break;
            case "S":
                this.direction = S;
                break;
            case "W":
                this.direction = W;
                break;
            case "E":
                this.direction = E;
                break;
            case "WN":
                this.direction = WN;
                break;
            case "WS":
                this.direction = WS;
                break;
            case "SE":
                this.direction = SE;
                break;
            case "NE":
                this.direction = NE;
                break;
        }
    }

    /**
     * restart the application, reset all the elements to the initial state
     *
     * @param actionEvent
     * @throws MyException
     */
    public void restart(ActionEvent actionEvent) throws MyException {
        init_location();
        reset_items();
        show_photo();
        show_button();
        show_items();
        show_picker();
        show_position_label();
    }

    /**
     * change the current theme to pokemon theme
     *
     * @param mouseEvent
     * @throws MyException
     */
    public void change_pokemon(MouseEvent mouseEvent) throws MyException {
        this.skin_num = 0;
        init_location();
        reset_items();
        show_photo();
        show_button();
        update_items();
        update_picker();
        show_position_label();
    }

    /**
     * change the current theme to one piece theme
     *
     * @param mouseEvent
     * @throws MyException
     */
    public void change_one_piece(MouseEvent mouseEvent) throws MyException {
        this.skin_num = 1;
        init_location();
        reset_items();
        show_photo();
        show_button();
        update_items();
        update_picker();
        show_position_label();
    }

    /**
     * change the current theme to dragon ball theme
     *
     * @param mouseEvent
     * @throws MyException
     */
    public void change_dragon_ball(MouseEvent mouseEvent) throws MyException {
        this.skin_num = 2;
        init_location();
        reset_items();
        show_photo();
        show_button();
        update_items();
        update_picker();
        show_position_label();
    }
}
