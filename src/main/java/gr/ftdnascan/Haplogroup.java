package gr.ftdnascan;

import com.diffplug.common.base.TreeNode;
import com.diffplug.common.base.TreeNodeable;

import java.util.*;

public class Haplogroup extends Element<Haplogroup> {

    private String name; // YP1129*
    private String color; // ADFF2F
    private int section; // 2
    private String recommendation; // Big Y needed
    private String original; // 1. M420>YP4141>YP5018* (more STRs needed)

    private final Set<Kit> associatedKits = new HashSet<>();

    public Haplogroup() {

        this.name = "(root)";
        this.color = null;
        this.section = 0;
        this.recommendation = null;
        this.original = null;
    }

    public Haplogroup( String thisName ) {
        this();
        this.name = thisName;
    }

    public Haplogroup( String name, String color, int section, String recommendation, String original ) {
        this();
        this.name = name;
        this.color = color;
        this.section = section;
        this.recommendation = recommendation;
        this.original = original;
    }

    @Override
    public boolean equals(Object anObject) {
        return this.name.equals(anObject.toString());
    }

    @Override
    public int hashCode() { return this.name.hashCode(); }

    @Override
    public String toString() { return this.name; }

    void addKit( Kit kit ) {
        associatedKits.add(kit);
    }

    public String longName() {
        TreeNodeable<Haplogroup> tn = this;
        int depth = tn.depth();
        String spaces = String.join("", Collections.nCopies(depth, " "));
        String longName = spaces + this.section + "." + this.name + "(" + associatedKits.size() + ")";
        return longName;
    }
}
