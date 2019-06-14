package ru.spbu.arts.multithreading;
/*
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.concurrent.Task;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class JavaFXTaskExample extends Application {
    private ListView<Integer> ints = new ListView<>();

    @Override
    public void start(Stage primaryStage) {
        Button start = new Button("start");
        primaryStage.setScene(new Scene(new VBox(ints, start)));
        primaryStage.show();

        start.setOnAction((e) -> {
            //считает простые числа
            Task<List<Integer>> task = new Task<List<Integer>>() {
                @Override
                protected List<Integer> call() {
                    //в методе call выполняются те действия, которые должны
                    //быть в новом потоке. Т.е. именно здесь выполняются
                    //вычисления.
                    List<Integer> primes = new ArrayList<>();

                    for (int i = 2; i < 100_000_000; i++) {
                        if (isPrime(i))
                            primes.add(i);
                        updateValue(
                                new ArrayList<>(primes)
                        ); //создаем новый список, потому что иначе
                           //метод updateValue считает, что значение
                           //не меняется.
                    }

                    //после завершения метода изменится значение свойства
                    //value
                    return primes;
                }
            };
            new Thread(task).start(); //запускается как поток

            //task.setOnSucceeded(); - можно добавить слушатель на успешное
            //завершение вычислений.
            task.valueProperty().addListener(e2 ->
                //превратить в ObservableList
                ints.setItems(FXCollections.observableList(
                        task.getValue() //возвращает обычный список
                ))
            ); //свойство value - результат вычислений
        });
    }

    private boolean isPrime(int n) {
        int n2 = (int)Math.floor(Math.sqrt(n));
        for (int d = 2; d <= n2; d++)
            if (n % d == 0)
                return false;
        return true;
    }
}

/*
В JavaFX приложении всегда есть поток пользовательского интерфейса (UI),
в этом потоке отрисовываются элементы интерфейса и выполняются слушатели.
Пока кнопка производит вычисления внутри слушателя, не выполняется рисование.
Соответственно, в хорошей программе с интерфейсом в потоке UI (в слушателях) не
будет делаться долгих вычислений и других действий.

Поэтому для вычислений нужно создавать отдельные потоки. Можно было бы создать
Thread для поиска простых чисел, но возникнут трудности с синхронизациями,
получением результата вычислений из потока и др.

Есть вспомогательный класс Task<?>, он запускается как поток, чтобы выполнить
вычисление. Тип результата вычисления указывается в параметре типа, т.е. если
результат вычисления это число, то Task<Integer>, если список чисел, то
Task<List<Integer>>
 */
