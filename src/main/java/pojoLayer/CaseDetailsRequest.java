package pojoLayer;

import java.time.Instant;
import java.time.LocalDate;

public class CaseDetailsRequest {

    private Integer litigationCaseNumber;
    private String jocReferenceId;
    private String caseOwnerId;
    private String caseOwnerName;
    private String caseOwnerManager;
    private LocalDate dateOfInitiation;
    private String partyIdentifier;
    private String litigationType;
    private Double outstandingBalance;
    private String createdBy;
    private Instant createdOn;
    private String updatedBy;
    private Instant updatedOn;

    public Integer getLitigationCaseNumber() {
        return litigationCaseNumber;
    }

    public void setLitigationCaseNumber(Integer litigationCaseNumber) {
        this.litigationCaseNumber = litigationCaseNumber;
    }

    public String getJocReferenceId() {
        return jocReferenceId;
    }

    public void setJocReferenceId(String jocReferenceId) {
        this.jocReferenceId = jocReferenceId;
    }

    public String getCaseOwnerId() {
        return caseOwnerId;
    }

    public void setCaseOwnerId(String caseOwnerId) {
        this.caseOwnerId = caseOwnerId;
    }

    public String getCaseOwnerName() {
        return caseOwnerName;
    }

    public void setCaseOwnerName(String caseOwnerName) {
        this.caseOwnerName = caseOwnerName;
    }

    public String getCaseOwnerManager() {
        return caseOwnerManager;
    }

    public void setCaseOwnerManager(String caseOwnerManager) {
        this.caseOwnerManager = caseOwnerManager;
    }

    public LocalDate getDateOfInitiation() {
        return dateOfInitiation;
    }

    public void setDateOfInitiation(LocalDate dateOfInitiation) {
        this.dateOfInitiation = dateOfInitiation;
    }

    public String getPartyIdentifier() {
        return partyIdentifier;
    }

    public void setPartyIdentifier(String partyIdentifier) {
        this.partyIdentifier = partyIdentifier;
    }

    public String getLitigationType() {
        return litigationType;
    }

    public void setLitigationType(String litigationType) {
        this.litigationType = litigationType;
    }

    public Double getOutstandingBalance() {
        return outstandingBalance;
    }

    public void setOutstandingBalance(Double outstandingBalance) {
        this.outstandingBalance = outstandingBalance;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Instant createdOn) {
        this.createdOn = createdOn;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Instant getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(Instant updatedOn) {
        this.updatedOn = updatedOn;
    }
}
