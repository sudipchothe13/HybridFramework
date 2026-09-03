package JavaDevelopment;

import java.util.ArrayList;
import java.util.List;

class Workout {

	private int id;
	private int startTime;
	private int endTime;

	public Workout(int id, int startTime, int endTime) {
		this.id = id;
		this.startTime = startTime;
		this.endTime = endTime;
	}

	public int getId() {
		return id;
	}

	public int getStartTime() {
		return startTime;
	}

	public int getEndTime() {
		return endTime;
	}

	public int getDuration() {
		return endTime - startTime;
	}

}

enum MembershipStatus {
	BRONZE, SILVER, GOLD
}

class Member {
	public int memberId;
	public String name;
	public MembershipStatus membershipStatus;
	public List<Workout> workout;

	public Member(int memberId, String name, MembershipStatus membershipStatus, List<Workout> workout) {
		this.memberId = memberId;
		this.name = name;
		this.membershipStatus = membershipStatus;
		this.workout = workout;
	}

	@Override
	public String toString() {
		// return "Member [memberId=" + memberId + ", name=" + name + ",
		// membershitStatus=" + membershitStatus + "]";
		return "Member ID: " + memberId + ", Name: " + name + ", Memebership Status: " + membershipStatus;
	}

}

class Memebership {

	public List<Member> members;

	// constructor & member---s
//	public Membership() {
//		member = new ArrayList<>();
//		
//	}
	public Memebership() {
		members = new ArrayList<>();

	}

	public void addMember(Member member) {
		members.add(member);
	}

	public void updateMembership(int memberId, MembershipStatus membershipStatus) {
		for (Member member : members) {
			if (member.memberId == memberId) {
				member.membershipStatus = membershipStatus;
				break;
			}
		}
	}

	public MembershipStatistics getMembershipStatistics() {
		int totalMembers = members.size();
		int totalPaidMembers = 0;

		for (Member member : members) {
			if (member.membershipStatus == MembershipStatus.GOLD) {
				totalPaidMembers++;
			}
		}

		double conversionRate = (totalPaidMembers / (double) totalMembers) * 100.0;
		return new MembershipStatistics(totalMembers, totalPaidMembers, conversionRate);
	}
}

class MembershipStatistics {

	public int totalMembers;
	public int totalPaidMembers;
	public double conversionRate;

	public MembershipStatistics(int totalMembers, int totalPaidMembers, double conversionRate) {
		this.totalMembers = totalMembers;
		this.totalPaidMembers = totalPaidMembers;
		this.conversionRate = conversionRate;
	}

}

public class GymApplication {

	public void testMember() {
		
		System.out.println("Running testMember ");
		Member testMember = new Member(1, "John", MembershipStatus.BRONZE, new ArrayList<>());
		
		assert testMember.name.equals("John") : "member name should be \" John\", was\"" + testMember.name;
		
	}

	public void testGetAverageWorkoutDuration() {
		System.out.println("testGetAverageWorkoutDuration");
	}

	public void testMembership() {
		System.out.println("testMembership");
	}

	public static void main(String[] args) {

		GymApplication g = new GymApplication();
		g.testMember();
		g.testMembership();
		g.testGetAverageWorkoutDuration();
	}

}
