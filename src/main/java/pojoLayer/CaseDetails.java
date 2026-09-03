package pojoLayer;

import java.time.LocalDate;
import java.time.OffsetDateTime;

public class CaseDetails {

	private String caseOwnerId;
	private String caseOwnerManager;
	private String caseOwnerName;
	private String createdBy;
	private OffsetDateTime createdOn;
	private String dateOfInitiation;
	private String jocReferenceId;
	private Integer litigationCaseNumber;
	private String litigationType;
	private Double outstandingBalance;
	private String partyIdentifier;
	private String updatedBy;
	private OffsetDateTime updatedOn;

	public String getCaseOwnerId() {
		return caseOwnerId;
	}

	public void setCaseOwnerId(String caseOwnerId) {
		this.caseOwnerId = caseOwnerId;
	}

	public String getCaseOwnerManager() {
		return caseOwnerManager;
	}

	public void setCaseOwnerManager(String caseOwnerManager) {
		this.caseOwnerManager = caseOwnerManager;
	}

	public String getCaseOwnerName() {
		return caseOwnerName;
	}

	public void setCaseOwnerName(String caseOwnerName) {
		this.caseOwnerName = caseOwnerName;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public OffsetDateTime getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(OffsetDateTime createdOn) {
		this.createdOn = createdOn;
	}

	public String getDateOfInitiation() {
		return dateOfInitiation;
	}

	public void setDateOfInitiation(String string) {
		this.dateOfInitiation = string;
	}

	public String getJocReferenceId() {
		return jocReferenceId;
	}

	public void setJocReferenceId(String jocReferenceId) {
		this.jocReferenceId = jocReferenceId;
	}

	public Integer getLitigationCaseNumber() {
		return litigationCaseNumber;
	}

	public void setLitigationCaseNumber(Integer litigationCaseNumber) {
		this.litigationCaseNumber = litigationCaseNumber;
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

	public String getPartyIdentifier() {
		return partyIdentifier;
	}

	public void setPartyIdentifier(String partyIdentifier) {
		this.partyIdentifier = partyIdentifier;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public OffsetDateTime getUpdatedOn() {
		return updatedOn;
	}

	public void setUpdatedOn(OffsetDateTime updatedOn) {
		this.updatedOn = updatedOn;
	}
}
