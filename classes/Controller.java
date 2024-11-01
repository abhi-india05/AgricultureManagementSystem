package classes;

import java.util.List;

public class Controller {
    private Service service;

    public Controller(Service service) {
        this.service = service;
    }

    public boolean loginFarmer(String username, String password) {
        return service.authenticateFarmer(username, password);
    }

    public boolean loginGovt(String username, String password) {
        return service.authenticateGovt(username, password);
    }

    public boolean registerFarmer(String username, String name, String password) {
        return service.registerFarmer(username, name, password);
    }

    public void addNewScheme(String schemeId, String name, String description) {
        service.addNewScheme(schemeId, name, description);
    }

    public void addNewScheme(String name, String description) {
        service.addNewScheme(name, description);
    }

    public List<Scheme> getSchemes() {
        return service.viewSchemes();
    }

    public void applyForScheme(String schemeId, Farmer farmer) {
        service.applyForScheme(schemeId, farmer);
    }

    public List<Scheme> getAppliedSchemes(Farmer farmer) {
        return service.getAppliedSchemes(farmer);
    }

    public void fileGrievance(String username, String issue) {
        service.fileGrievance(username, issue);
    }

    public List<Grievance> getGrievances() {
        return service.viewGrievances();
    }
    public List<Grievance> getFarmerGrievances(Farmer farmer) {
        return service.getFarmerGrievances(farmer);
    }

    public void respondToGrievance(String username, String response) {
        service.respondToGrievance(username, response);
    }
}
