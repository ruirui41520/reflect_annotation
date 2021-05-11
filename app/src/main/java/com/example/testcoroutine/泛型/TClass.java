package com.example.testcoroutine.泛型;

public class TClass<T> implements TInterface<T> {
    T value;

    @Override
    public void update(T value) {
        this.value = value;
    }

    public T getValue(){
        return value;
    }

    public static <E> void updateNewVariable(E e){
        System.out.println("e:" + e);
    }

    public <E> void updateMethodVariable(E value){
        System.out.println("value:" + value);
    }
}
