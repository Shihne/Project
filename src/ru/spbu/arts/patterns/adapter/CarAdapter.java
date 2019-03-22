package ru.spbu.arts.patterns.adapter;

import ru.spbu.arts.patterns.factory.Animal;

public class CarAdapter extends Animal {
    //Адаптер позволяет создать объект-наследник какого-то класса (у нас : животных Animal) на основе объекта, который
    //к этому классу не относится.

    //адаптер создаётся на основе машины, чтобы превратить её в животное
    public CarAdapter(Car car) {
        super(car.getName());
    }
}
