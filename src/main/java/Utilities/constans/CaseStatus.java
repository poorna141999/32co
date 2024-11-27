package Utilities.constans;

public enum CaseStatus {
	
	All("All"),
	Archived("Archived"),
	Draft("Draft"),
	AwaitingFiles("Awaiting Files"),
	NewCases("New Cases"),
	Assign("Assign"),
	UploadSTLs("Upload STLs"),
	AwaitingDesignerAcceptance("Awaiting Designer Acceptance"),
	AwaitingSTLVerification("Awaiting STL Verification"),
	AwaitingSpecialistAcceptance("Awaiting Specialist Acceptance"),
	AwaitingInstruction("Awaiting Instruction"),
	OnHold("On Hold"),
	NotSuitablePending("Not Suitable Pending"),
	AwaitingDesign("Awaiting Design"),
	AwaitingAdvice("Awaiting Advice"),
	ReviewDesign("Review Design"),
	AwaitingDentistApproval("Awaiting Dentist Approval"),
	Approved("Approved"),
	ReviewSTLFiles("Review STL Files"),
	WithManufacturer("With Manufacturer"),
	With32Co("With 32Co"),
	Shipped("Shipped"),
	InTreatment("In Treatment"),
	Completed("Completed"),
	NotSuitable("Not Suitable"),
	Expired("Expired"),
	ReAssign("RE-ASSIGN");
	
	private String status;
	
	CaseStatus(String caseStatus){
		this.status = caseStatus;
	}
	
	public String getStatus() {
		return status;
	}

}
