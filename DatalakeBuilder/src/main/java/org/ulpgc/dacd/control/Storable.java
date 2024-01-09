package org.ulpgc.dacd.control;

public interface Storable {
    void save();
    void toFile(String dateEvent, String text);
}
