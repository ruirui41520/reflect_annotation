package com.example.testcoroutine.拷贝;

import androidx.annotation.NonNull;

public class Animal implements Cloneable{
    int age;
    Holder holder;

    @Override
    protected Object clone() {
        try {
            Animal animal = (Animal)super.clone();
            animal.holder = (Animal.Holder)this.holder.clone();
            return animal;
        }catch (CloneNotSupportedException e){
            e.printStackTrace();
        }
        return null;
    }

    public static class Holder implements Cloneable{
        int value;

        @Override
        protected Object clone() {
            try {
                return super.clone();
            }catch (CloneNotSupportedException e){
                e.printStackTrace();
            }
            return null;
        }
    }
}
