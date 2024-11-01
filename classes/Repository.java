package classes;

import java.util.ArrayList;
import java.util.List;

public class Repository {
    private List<Farmer> farmers = new ArrayList<>();
    private List<Scheme> schemes = new ArrayList<>();
    private List<Grievance> grievances = new ArrayList<>();

    public void addGrievance(Grievance grievance) {
        grievances.add(grievance);
    }

    public List<Grievance> getGrievances() {
        return grievances;
    }

    public Grievance findGrievanceByUsername(String username) {
        return grievances.stream().filter(g -> g.getUsername().equals(username)).findFirst().orElse(null);
    }
    public void addFarmer(Farmer farmer) {
        farmers.add(farmer);
    }

    public List<Grievance> getGrievancesForFarmer(Farmer farmer) {
        List<Grievance> farmerGrievances = new ArrayList<>();
        for (Grievance grievance : grievances) {
            if (grievance.getUsername().equals(farmer.getUsername())) {
                farmerGrievances.add(grievance);
            }
        }
        return farmerGrievances;
    }

    public boolean usernameExists(String username) {
        return farmers.stream().anyMatch(farmer -> farmer.getUsername().equals(username));
    }

    public Farmer findFarmerByUsername(String username) {
        return farmers.stream().filter(farmer -> farmer.getUsername().equals(username)).findFirst().orElse(null);
    }

    public void addScheme(Scheme scheme) {
        schemes.add(scheme);
    }

    public List<Scheme> getSchemes() {
        return schemes;
    }

    public Scheme findSchemeById(String schemeId) {
        return schemes.stream().filter(scheme -> scheme.getSchemeId().equals(schemeId)).findFirst().orElse(null);
    }

    public List<Scheme> getAppliedSchemesForFarmer(Farmer farmer) {
        List<Scheme> appliedSchemes = new ArrayList<>();
        for (Scheme scheme : schemes) {
            if (scheme.getApplicants().contains(farmer)) {
                appliedSchemes.add(scheme);
            }
        }
        return appliedSchemes;
    }
}
