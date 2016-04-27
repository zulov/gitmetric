/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.agh.kro.gitmetric.marking;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import org.jfree.data.time.TimeSeries;

/**
 * @author Tomek
 */
public abstract class Marking {

    Map<String, Integer> users;
    Map<String, TimeSeries> userToHistory;

    public Marking() {
        users = new HashMap<>();
        userToHistory = new HashMap<>();
    }
    /**
     * 
     * @param person
     * @param value
     * @param line
     * @param date
     * @return true if map was modified
     */
    public abstract boolean incMap(String person, Integer value, String line, Date date);

    public Map<String, Integer> getUsers() {
        return users;
    }

    public Set<String> getNames() {
        return users.keySet();
    }
    
    public Map<String, TimeSeries> getUserToHistory() {
        return userToHistory;
    }
}
