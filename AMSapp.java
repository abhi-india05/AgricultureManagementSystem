import java.util.List;

//class files
import classes.Controller;
import classes.Farmer;
import classes.Grievance;
import classes.Repository;
import classes.Scheme;
import classes.Service;
//javafx files
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class AMSapp extends Application {
    private Repository repository = new Repository();
    private Service service = new Service(repository);
    private Controller controller = new Controller(service);
    private Farmer loggedInFarmer = null;

    @Override
    public void start(Stage primaryStage) {
        showHomePage(primaryStage);
    }

    private void showHomePage(Stage primaryStage) {
        Button signupButton = new Button("Sign Up");
        Button farmerLoginButton = new Button("Farmer Login");
        Button govtLoginButton = new Button("Government Login");
        Label label=new Label("Welcome to AMS!!!");
        label.setTextFill(Color.RED);
        signupButton.setOnAction(e -> showSignupPage(primaryStage));
        farmerLoginButton.setOnAction(e -> showFarmerLoginPage(primaryStage));
        govtLoginButton.setOnAction(e -> showGovtLoginPage(primaryStage));

        VBox vbox = new VBox(label,signupButton, farmerLoginButton, govtLoginButton);                                                                                                                                                                                              
        vbox.setAlignment(Pos.CENTER);
        Scene scene = new Scene(vbox, 300, 200);
        primaryStage.setScene(scene);
        primaryStage.setTitle("AMS - Home");
        primaryStage.show();
    }

    private void showSignupPage(Stage primaryStage) {
        TextField usernameField = new TextField();
        TextField nameField = new TextField();
        PasswordField passwordField = new PasswordField();
        Button registerButton = new Button("Register");
        Button backButton = new Button("Back");

        registerButton.setOnAction(e -> {
            String username = usernameField.getText();
            String name = nameField.getText();
            String password = passwordField.getText();
            if (controller.registerFarmer(username, name, password)) {
                showAlert(Alert.AlertType.INFORMATION, "Registration Successful", "You can now log in.");
                showHomePage(primaryStage);
            } else {
                showAlert(Alert.AlertType.ERROR, "Registration Failed", "Username already exists.");
            }
        });

        backButton.setOnAction(e -> showHomePage(primaryStage));

        VBox vbox = new VBox(new Label("Username:"), usernameField, new Label("Name:"), nameField,
                new Label("Password:"), passwordField, registerButton, backButton);
        Scene scene = new Scene(vbox, 300, 250);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Sign Up");
    }


    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showFarmerLoginPage(Stage primaryStage) {
        TextField usernameField = new TextField();
        PasswordField passwordField = new PasswordField();
        Button loginButton = new Button("Login");
        Button backButton = new Button("Back");

        loginButton.setOnAction(e -> {
            String username = usernameField.getText();
            String password = passwordField.getText();
            if (controller.loginFarmer(username, password)) {
                loggedInFarmer = repository.findFarmerByUsername(username);
                showFarmerHomePage(primaryStage);
            } else {
                showAlert(Alert.AlertType.ERROR, "Login Failed", "Invalid username or password.");
            }
        });

        backButton.setOnAction(e -> showHomePage(primaryStage));

        VBox vbox = new VBox(new Label("Username:"), usernameField, new Label("Password:"), passwordField, loginButton,
                backButton);
        Scene scene = new Scene(vbox, 300, 200);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Farmer Login");
    }

    private void showFileGrievancePage(Stage primaryStage) {
        Label usernameLabel = new Label("Username: " + loggedInFarmer.getUsername());
        TextArea grievanceField = new TextArea();
        grievanceField.setPromptText("Describe your issue");
        Button submitButton = new Button("Submit");
        Button backButton = new Button("Back");
    
        submitButton.setOnAction(e -> {
            String issue = grievanceField.getText();
            controller.fileGrievance(loggedInFarmer.getUsername(), issue);
            showAlert(Alert.AlertType.INFORMATION, "Grievance Filed", "Your grievance has been filed successfully.");
            showFarmerHomePage(primaryStage);
        });
    
        backButton.setOnAction(e -> showFarmerHomePage(primaryStage));
    
        VBox vbox = new VBox(usernameLabel, grievanceField, submitButton, backButton);
        Scene scene = new Scene(vbox, 400, 300);
        primaryStage.setScene(scene);
        primaryStage.setTitle("File Grievance");
    }

    private void showGrievancesPage(Stage primaryStage) {
        VBox vbox = new VBox();
        Button backButton = new Button("Back");
    
        for (Grievance grievance : controller.getGrievances()) {
            Label usernameLabel = new Label("Username: " + grievance.getUsername());
            Label issueLabel = new Label("Issue: " + grievance.getIssue());
            Label responseLabel = new Label("Response: " + (grievance.getResponse() != null ? grievance.getResponse() : "No response yet"));
            TextArea responseField = new TextArea();
            responseField.setPromptText("Respond to grievance");
            Button respondButton = new Button("Submit Response");
    
            respondButton.setOnAction(e -> {
                String response = responseField.getText();
                controller.respondToGrievance(grievance.getUsername(), response);
                showAlert(Alert.AlertType.INFORMATION, "Response Submitted", "Response has been submitted successfully.");
                showGrievancesPage(primaryStage);
            });
    
            vbox.getChildren().addAll(usernameLabel, issueLabel, responseLabel, responseField, respondButton);
        }
    
        backButton.setOnAction(e -> showGovtHomePage(primaryStage));
        vbox.getChildren().add(backButton);
    
        Scene scene = new Scene(vbox, 400, 300);
        primaryStage.setScene(scene);
        primaryStage.setTitle("View Grievances");
    }
    

    private void showGovtLoginPage(Stage primaryStage) {
        TextField usernameField = new TextField();
        PasswordField passwordField = new PasswordField();
        Button loginButton = new Button("Login");
        Button backButton = new Button("Back");

        loginButton.setOnAction(e -> {
            String username = usernameField.getText();
            String password = passwordField.getText();
            if (controller.loginGovt(username, password)) {
                showGovtHomePage(primaryStage);
            } else {
                showAlert(Alert.AlertType.ERROR, "Login Failed", "Invalid username or password.");
            }
        });

        backButton.setOnAction(e -> showHomePage(primaryStage));

        VBox vbox = new VBox(new Label("Username:"), usernameField, new Label("Password:"), passwordField, loginButton,
                backButton);
        Scene scene = new Scene(vbox, 300, 200);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Government Login");
    }

    private void showFarmerGrievancesPage(Stage primaryStage) {
        VBox vbox = new VBox();
        Label headerLabel = new Label("Your Grievances");
        vbox.getChildren().add(headerLabel);
    
        List<Grievance> farmerGrievances = controller.getFarmerGrievances(loggedInFarmer);
        for (Grievance grievance : farmerGrievances) {
            Label grievanceLabel = new Label("Issue: " + grievance.getIssue());
            Label responseLabel = new Label("Response: " + (grievance.getResponse() != null ? grievance.getResponse() : "No response yet"));
            vbox.getChildren().addAll(grievanceLabel, responseLabel);
        }
    
        Button backButton = new Button("Back");
        backButton.setOnAction(e -> showFarmerHomePage(primaryStage));
        vbox.getChildren().add(backButton);
    
        Scene scene = new Scene(vbox, 400, 300);
        primaryStage.setScene(scene);
        primaryStage.setTitle("View Grievances");}
    private void showFarmerHomePage(Stage primaryStage) {
        Label welcomeLabel = new Label("Hello, " + loggedInFarmer.getName());
        Button logoutButton = new Button("Logout");
        Button fileGrievanceButton = new Button("File Grievance");
        VBox schemeButtons = new VBox();
        Button viewGrievancesButton = new Button("View Grievances");
        fileGrievanceButton.setOnAction(e -> showFileGrievancePage(primaryStage));
        viewGrievancesButton.setOnAction(e -> showFarmerGrievancesPage(primaryStage));
        for (Scheme scheme : controller.getSchemes()) {
            Button schemeButton = new Button(scheme.getName());
            schemeButton.setOnAction(e -> showSchemePage(primaryStage, scheme));
            schemeButtons.getChildren().add(schemeButton);
        }

        List<Scheme> appliedSchemes = repository.getAppliedSchemesForFarmer(loggedInFarmer);
        Label appliedLabel = new Label("Schemes you've applied for:");
        VBox appliedSchemesBox = new VBox();
        for (Scheme scheme : appliedSchemes) {
            appliedSchemesBox.getChildren().add(new Label(scheme.getName()));
        }



        logoutButton.setOnAction(e -> {
            loggedInFarmer = null;
            showHomePage(primaryStage);
        });

        VBox vbox = new VBox(welcomeLabel, schemeButtons, appliedLabel, appliedSchemesBox, fileGrievanceButton,viewGrievancesButton,logoutButton);
        Scene scene = new Scene(vbox, 400, 300);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Farmer Home");
    }

    private void showGovtHomePage(Stage primaryStage) {
        Label welcomeLabel = new Label("Welcome, Admin");
        Button addSchemeButton = new Button("Add New Scheme");
        Button viewApplicantsButton = new Button("View Applicants");
        Button logoutButton = new Button("Logout");

        Button viewGrievancesButton = new Button("View Grievances");



        addSchemeButton.setOnAction(e -> showAddSchemePage(primaryStage));
        viewApplicantsButton.setOnAction(e -> showViewApplicantsPage(primaryStage));
        viewGrievancesButton.setOnAction(e -> showGrievancesPage(primaryStage));
        logoutButton.setOnAction(e -> showHomePage(primaryStage));

        VBox vbox = new VBox(welcomeLabel, addSchemeButton, viewApplicantsButton, logoutButton,viewGrievancesButton);
        Scene scene = new Scene(vbox, 300, 200);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Government Home");
    }

    private void showSchemePage(Stage primaryStage, Scheme scheme) {
        Label schemeNameLabel = new Label("Scheme: " + scheme.getName());
        Label schemeDescriptionLabel = new Label("Description: " + scheme.getDescription());
        Button applyButton = new Button("Apply");
        Button backButton = new Button("Back");

        applyButton.setOnAction(e -> {
            scheme.apply(loggedInFarmer);
            showAlert(Alert.AlertType.INFORMATION, "Application Successful",
                    "You have applied for " + scheme.getName());
            showFarmerHomePage(primaryStage);
        });

        backButton.setOnAction(e -> showFarmerHomePage(primaryStage));

        VBox vbox = new VBox(schemeNameLabel, schemeDescriptionLabel, applyButton, backButton);
        Scene scene = new Scene(vbox, 400, 300);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Scheme Details");
    }

    private void showAddSchemePage(Stage primaryStage) {
        TextField schemeNameField = new TextField();
        TextArea schemeDescriptionField = new TextArea();
        Button submitButton = new Button("Submit");
        Button backButton = new Button("Back");

        submitButton.setOnAction(e -> {
            String schemeName = schemeNameField.getText();
            String schemeDescription = schemeDescriptionField.getText();
            controller.addNewScheme(schemeName, schemeDescription);
            showAlert(Alert.AlertType.INFORMATION, "Scheme Added", "New scheme added successfully.");
            showGovtHomePage(primaryStage);
        });

        backButton.setOnAction(e -> showGovtHomePage(primaryStage));

        VBox vbox = new VBox(new Label("Scheme Name:"), schemeNameField, new Label("Description:"),
                schemeDescriptionField, submitButton, backButton);
        Scene scene = new Scene(vbox, 300, 300);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Add New Scheme");
    }

    private void showViewApplicantsPage(Stage primaryStage) {
        VBox vbox = new VBox();
        Button backButton = new Button("Back");

        for (Scheme scheme : controller.getSchemes()) {
            Label schemeLabel = new Label("Scheme: " + scheme.getName());
            vbox.getChildren().add(schemeLabel);
            for (Farmer applicant : scheme.getApplicants()) {
                Label applicantLabel = new Label("  - " + applicant.getName());
                vbox.getChildren().add(applicantLabel);
            }
        }

        backButton.setOnAction(e -> showGovtHomePage(primaryStage));
        vbox.getChildren().add(backButton);

        Scene scene = new Scene(vbox, 400, 300);
        primaryStage.setScene(scene);
        primaryStage.setTitle("View Applicants");
    }

    private void addNewScheme(String schemeName, String schemeDescription) {
        // Add the new scheme to the repository using the controller
        controller.addNewScheme(schemeName, schemeDescription);
        // Display a confirmation message
        showAlert(Alert.AlertType.INFORMATION, "Scheme Added", "The new scheme has been added successfully.");
    }

    public static void main(String[] args) {
        launch(args);
    }
}