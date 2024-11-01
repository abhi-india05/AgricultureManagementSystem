package classes;

import java.util.List;

public class Service {
    private Repository repository;

    public Service(Repository repository) {                                                                                                                                                                             
        this.repository = repository;
    }

    public boolean authenticateFarmer(String username, String password) {
        Farmer farmer = repository.findFarmerByUsername(username);
        return farmer != null && farmer.getPassword().equals(password);
    }

    public boolean authenticateGovt(String username, String password) {
        return "admin".equals(username) && "admin@123".equals(password);
    }

    public boolean registerFarmer(String username, String name, String password) {
        if (!repository.usernameExists(username)) {
            repository.addFarmer(new Farmer(username, name, password));
            return true;
        }
        return false;
    }

    public void addNewScheme(String schemeId, String name, String description) {
        repository.addScheme(new Scheme(schemeId, name, description));
    }

    public void addNewScheme(String name, String description) {
        repository.addScheme(new Scheme(name, description));
    }

    public List<Scheme> viewSchemes() {
        return repository.getSchemes();
    }

    public void applyForScheme(String schemeId, Farmer farmer) {
        Scheme scheme = repository.findSchemeById(schemeId);
        if (scheme != null) {
            scheme.apply(farmer);
        }
    }
    public List<Grievance> getFarmerGrievances(Farmer farmer) {
        return repository.getGrievancesForFarmer(farmer);
    }
    public List<Scheme> getAppliedSchemes(Farmer farmer) {
        return repository.getAppliedSchemesForFarmer(farmer);
    }
    public void fileGrievance(String username, String issue) {
        repository.addGrievance(new Grievance(username, issue));
    }

    public List<Grievance> viewGrievances() {
        return repository.getGrievances();
    }

    public void respondToGrievance(String username, String response) {
        Grievance grievance = repository.findGrievanceByUsername(username);
        if (grievance != null) {
            grievance.setResponse(response);
        }
    }
}

