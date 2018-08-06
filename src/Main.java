import MVC.IslandController;
import MVC.IslandModel;
import MVC.IslandView;

public class Main {
    public static void main(String[] args) {
        IslandView view = new IslandView();
        IslandModel model = new IslandModel(view);
        IslandController controller = new IslandController(model,view);
    }
}
