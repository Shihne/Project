package ru.spbu.arts.javafx;
/*
import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class PropertiesListenersAndBindings extends Application {

    //поля класса. Они живут и доступны все время, пока жив класс, т.е.
    //окно нашей программы
    private TextField textField1;
    private Button button1;
    private Label label2 = new Label();
    private TextField textField2 = new TextField("Введите текст");
    private Label label3 = new Label();
    private TextField textField3 = new TextField("Введите текст");
    private Label label4 = new Label();
    private TextField textField4 = new TextField("Введите текст");
    private Label label5 = new Label();
    private TextField textField5 = new TextField("Введите текст");
    private Label label6 = new Label();
    private TextField textField6 = new TextField("Введите текст");

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Properties, Listeners, and Bindings");

        Parent root = initInterface();
        primaryStage.setScene(new Scene(root));

        initInteraction(); //наш новый метод, в нем будет описываться поведение элементов интерфейса

        primaryStage.show();
    }

    private Parent initInterface() {
        FlowPane root = new FlowPane();

        //----------------------------------------------------
        VBox example1 = new VBox();
        //Старайтесь давать переменным осмысленные названия. Здесь button1 означает
        //только, что это кнопка для первого примера.
        textField1 = new TextField();
        button1 = new Button("Пример 1");
        example1.getChildren().addAll(textField1, button1);

        //давайте раскрасим панель example1
        example1.setBackground( //фон
                new Background(
                        new BackgroundFill(
                                Color.web("#FF0000"), //Цвет может выступать запонителем фона
                                new CornerRadii(0), //радиус закругления углов
                                Insets.EMPTY //пустой отступ для закраски
                        ) //заполнитель для фона
                )
        );
        example1.setStyle("-fx-background-color: #00FF00"); //другой способ, чуть короче
        //пользуемся CSS со стилями из javafx
        //----------------------------------------------------

        //Ctrl + D дублировать строку
        VBox example2 = new VBox(new Label("Пример 2"), label2, textField2);
        VBox example3 = new VBox(new Label("Пример 3"), label3, textField3);
        VBox example4 = new VBox(new Label("Пример 4"), label4, textField4);
        VBox example5 = new VBox(new Label("Пример 5"), label5, textField5);
        VBox example6 = new VBox(new Label("Пример 6"), label6, textField6);

        //------------------------------

        root.setHgap(8); // добавим вертикальные и горизонтальные отступы между
        root.setVgap(8); // панельками с примерами
        root.getChildren().addAll(example1, example2, example3, example4, example5, example6);

        return root;
    }

    private void initInteraction() {
        //-------------Пример 1-------------
        //как заставить кнопку что-то делать

        //1) универсальный, но сложный способ
        //В JavaFX реализована работа с событями. Разные объекты генерируют события
        //можно добавлять слушателей (обработчиков), которые реагируют на интерующие
        //их события.
        //Кнопка генерирует событие типа ActionEvent.ACTION, это происходит
        //в момент нажатия на кнопку. Нажатие может быть разным, мышкой, кнопкой Enter,
        //пробелом, касанием экрана и т.п.
        //В принципе, все Node, включая кнопку, генерируют еще и события мыши, т.е.
        //можно реагировать на событие, что на кнопку наведена/нажата/отпущена мышь.
        //Но в этой программе нам это не инетресно
        button1.addEventHandler(
                ActionEvent.ACTION, //какое именно событие кнопки нас интересует
                a -> System.out.println("pressed: " + a)
                //второе - это действие, в виде лямбда выражения.
                //a - информация о событии. В этой информации всегда можно найти
                //источник события. Если бы у нас было событие от мыши, мы бы
                //дополнительно могли получить информацию о положении курсора.
        );

        //2) другой способ обработки события нажатия на кнопку
        button1.setOnAction(a -> System.out.println("pressed (onAction): " + a));
        //этот способ проще, чаще всего его достаточно
        //этим способом можно связать с кнопкой только одно действие.
        //А addEventHandler - можно добавить любое количество обработчиков

        //другие варианты события
        //MouseEvent.MOUSE_CLICKED - мышь нажали
        //MouseEvent.MOUSE_ENTERED - мышь навели на компонент ...

        //Свойства. В JavaFX объекты имеют свойства. Это данные, которые
        //связаны с объектами. Например, у кнопки - это текст, который на ней
        //написан (это свойство называется text). Или действие, которое выполняется
        //при нажатии (это свойство называется onAction)
        //У текстового поля тоже есть свойство text - это тот текст, который в него введен.
        //Свойства хранятся в полях объекта. Но данные оформлены в виде
        //свойства, то у вас появляется возможность следить за изменениями этого свойства
        //И ещё управлять изменением (см. связывание - bindings)
        //
        //К примеру, у текствого поля есть свойство text, тогда с ним можно работать так:

        textField1.textProperty() //обращение ко свойству text
                .set(""); //метод set устанавливает значение для свойства
        System.out.println(
                textField1.textProperty().get() //узнаит значение свойства
        );
        textField1.textProperty().addListener( //можно добавлять слушателей об изменении
                prop -> System.out.println(
                        "Значение изменилось на " + textField1.textProperty().get()
                )
                //Это InvalidationListener
                //prop - ссылка на тот property, который изменился
                //в нашем случае это тот же самый textField1.textProperty
                //но бывает так, что один и тот же слушатель добавляется на разные
                //свойства, в этом случае бывает важно знать, какое именно свойство изменилось
        );
        textField1.textProperty().addListener(
                (prop, oldValue, newValue) -> System.out.println(
                        "Значение изменилось с " + oldValue + "на " + newValue
                )
                //Это ChangeListener, в нём можно дополнительно узнать какое значение было
        );
        //вместо textField1.textProperty().get() можно писать
        textField1.getText(); //т.е. get[Имя свойства]
        //аналогично
        textField1.setText("42"); //т.е. set[Имя свойства]

        //итого, если у объекта о есть свойство abc, то есть методы:
        //o.abcProperty(), o.getAbc(), o.setAbc() - последнего нет для read only свойств

        //-------------Пример 2-------------
        //Связывание свойств. Сделаем так, что текст на метке label2
        //всегда будет совпадать с текстом, введенным в поле textFiled2

        textField2.textProperty().addListener(
                prop -> label2.setText(textField2.getText())
        );
        //чтобы вначале значения тоже совпадали
        label2.setText(textField2.getText());

        //-----------------Пример 3-------------------
        //Свяжем свойства напрямую. Скажем, чтобы значение свойства
        //text в метке всегда было равно значению свойства text в текстовом поле
        label3.textProperty().bind(textField3.textProperty());
        //свойство теперь привязано, его нельзя менять напрямую:
        //label3.setText() - запрещено

        //-----------------Пример 4-------------------
        //Хотим, чтобы в метке дополнительно рисовались скобочки в начале и конце
        label4.textProperty().bind( //javafx.beans.bindings
                Bindings.concat( //аналог операции + для строк, но здесь не строки, а property
                        "[",
                        textField4.textProperty(),
                        "]"
                ) //метод concat создает значение, за изменением которого можно следить
        );
        //-----------------Пример 5-------------------
        //Хотим, чтобы на метке текст всегда был в верхнем регистре
        label5.textProperty().bind(
                //этот метод позволяет произвольным образом менять значение
                Bindings.createStringBinding(
                        () -> textField5.getText().toUpperCase(),
                        textField5.textProperty()
                )
        );

        //Не используйте bindings там, где они не нужны. Есть универсальный механизм слушателей,
        //а bindings подходят только для вполне конкретной задачи: следить, чтобы значения
        //разных свойств соответсвовали друг другу.
    }

}*/