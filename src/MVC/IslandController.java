package MVC;

public class IslandController {
        private IslandView view;
        private IslandModel model;


        //сязываем модель и вид
        public IslandController(IslandModel model, IslandView view){
            this.model=model;
            this.view=view;
            init();
        }

        //добавляем слушателей
        private void init(){

        }


}
