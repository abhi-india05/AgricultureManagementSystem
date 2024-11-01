package classes;

import java.util.ArrayList;
import java.util.List;

public class Scheme {
    private String name;
    private String description;
    private String schemeId;
    private List<Farmer> applicants = new ArrayList<>();

    public Scheme(String schemeId, String name, String description) {
        this.schemeId = schemeId;
        this.name = name;
        this.description = description;
    }

    public Scheme(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public void apply(Farmer farmer) {
        applicants.add(farmer);
    }

    public List<Farmer> getApplicants() {
        return applicants;
    }

    public String getSchemeId() {
        return schemeId;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}
