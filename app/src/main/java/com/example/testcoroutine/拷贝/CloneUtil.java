package com.example.testcoroutine.拷贝;

import android.util.Log;

public class CloneUtil {
    public static void testShallowClone(){
        People lPeople = new People();
        lPeople.age = 10;
        lPeople.holder = new People.Holder();
        lPeople.holder.value = 20;
        People rPeople = (People)lPeople.clone();
        rPeople.age = 100;
        rPeople.holder.value = 200;
        Log.e("test","lPeople.age: " + lPeople.age +  "  rPeople.age: " + rPeople.age);
        Log.e("test","lPeople.holder.value: " + lPeople.holder.value + " rPeople.holder.value : " + lPeople.holder.value);
    }

    public static void testDeepClone(){
        Animal lAnimal = new Animal();
        lAnimal.holder = new Animal.Holder();
        lAnimal.holder.value = 10;
        lAnimal.age = 20;
        Animal rAnimal = (Animal) lAnimal.clone();
        rAnimal.age = 200;
        rAnimal.holder.value = 100;
        Log.e("test","lAnimal.age: " + lAnimal.age +  "  lAnimal.age: " + rAnimal.age);
        Log.e("test","lAnimal.holder.value: " + lAnimal.holder.value + " lAnimal.holder.value: " + rAnimal.holder.value);
    }
}
