package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.OverrunStyle;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import seedu.address.model.opportunity.Opportunity;

/**
 * An UI component that displays information of a {@code Opportunity}.
 */
public class OpportunityCard extends UiPart<Region> {

    private static final String FXML = "OpportunityListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    @FXML
    private HBox cardPane;
    @FXML
    private Label id;
    @FXML
    private Label nameCompany;
    @FXML
    private Label contactRoleAndRole;
    @FXML
    private Label email;
    @FXML
    private Label phone;
    @FXML
    private Label status;
    @FXML
    private Label cycle;

    /**
     * Creates a {@code OpportunityCard} with the given {@code Opportunity} and index to display.
     */
    public OpportunityCard(Opportunity opportunity, int displayedIndex) {
        super(FXML);
        configureFlexibleHBoxLabel(nameCompany);
        configureFlexibleHBoxLabel(contactRoleAndRole);
        configureWrappedLabel(email);
        configureFlexibleHBoxLabel(phone);
        configureWrappedLabel(cycle);

        id.setText(displayedIndex + ". ");
        nameCompany.setText(opportunity.getName().getFullName() + " @ "
                + opportunity.getCompany().getCompanyName());
        contactRoleAndRole.setText(opportunity.getContactRole().getContactRoleName() + " | "
                + opportunity.getRole().getRoleName());
        email.setText(opportunity.getEmail().getValue());
        status.setText(opportunity.getStatus().getStatusName());
        status.getStyleClass().setAll("status-badge", getStatusStyleClass(opportunity.getStatus().getStatusName()));
        cycle.setText(opportunity.getCycle().getValue());
        opportunity.getPhone().ifPresentOrElse(
                p -> {
                    phone.setText(p.getValue());
                    phone.setVisible(true);
                    phone.setManaged(true);
                }, () -> {
                    phone.setVisible(false);
                    phone.setManaged(false);
                }
        );
    }

    private void configureFlexibleHBoxLabel(Label label) {
        configureWrappedLabel(label);
        HBox.setHgrow(label, Priority.ALWAYS);
    }

    private void configureWrappedLabel(Label label) {
        label.setWrapText(true);
        label.setTextOverrun(OverrunStyle.CLIP);
        label.setMinWidth(0);
        label.setMaxWidth(Double.MAX_VALUE);
    }

    /**
     * Returns the CSS style class for the given status name.
     * The class follows the pattern {@code status-<lowercase>},
     * e.g. {@code "status-applied"} for {@code "APPLIED"}.
     * Each returned class has a corresponding rule in {@code DarkTheme.css}.
     */
    static String getStatusStyleClass(String statusName) {
        return "status-" + statusName.toLowerCase();
    }
}
