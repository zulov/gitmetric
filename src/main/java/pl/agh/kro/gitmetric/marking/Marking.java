/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.agh.kro.gitmetric.marking;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author Tomek
 */
public abstract class Marking {

    Map<String, Integer> users;

    public Marking() {
        users = new HashMap<>();
    }

    public abstract void incMap(String person, Integer value, String line);

    public Map<String, Integer> getUsers() {
        return users;
    }

    public Set<String> getNames() {
        return users.keySet();
    }
}
