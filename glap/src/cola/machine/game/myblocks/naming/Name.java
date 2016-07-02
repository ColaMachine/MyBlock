package cola.machine.game.myblocks.naming;

import com.google.common.base.Preconditions;

import java.util.Locale;

/**
 * Created by luying on 14-10-15.
 */
public class Name implements  Comparable<Name>{
    public static final Name EMPTY=new Name("");

    private final String originalName;

    private final String normalisedName;

    public Name(String name){
        Preconditions.checkNotNull(name);
        this.originalName=name;
        this.normalisedName=name.toLowerCase(Locale.ENGLISH);
    }
    public boolean isEmpty(){
        return normalisedName.isEmpty();
    }
    public String toLowerCase(){
        return normalisedName;
    }
    public String toUperCase(){
        return normalisedName.toUpperCase();
    }
    public int compareTo(Name o){
        return normalisedName.compareTo(o.normalisedName);
    }
    public boolean equals(Object obj){
        if(obj==this){
            return true;
        }
        if(obj instanceof Name){
            Name other=(Name) obj;
            return normalisedName.equals(other.normalisedName);
        }
        return false;
    }
    public int hashCode(){
        return normalisedName.hashCode();
    }
    public String toString(){
        return originalName;
    }
}
