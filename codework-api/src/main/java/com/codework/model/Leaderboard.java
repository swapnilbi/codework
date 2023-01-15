package com.codework.model;

import com.codework.entity.ChallengeInstance;
import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
public class Leaderboard {

	private List<ChallengeInstance> challengeInstanceList;
	private List<UserSubmissions> userSubmissionsList;

	@Data
	public static class UserSubmissions implements Comparable<UserSubmissions> {
		private Long id;
		private UserProfile userDetails;
		private Double totalPoints = 0d;
		private Long totalTimeTaken = 0l;
		private Set<ChallengeInstancePoints> challengeInstancePoints;

		@Override
		public int compareTo(UserSubmissions o) {
			if(totalPoints!=null && o.getTotalPoints()!=null){
				if(totalPoints.equals(o.getTotalPoints())){
					if(totalTimeTaken.equals(o.getTotalTimeTaken())){
						return 0;
					}
					return totalTimeTaken.compareTo(o.getTotalTimeTaken());
				}
				return o.getTotalPoints().compareTo(totalPoints);
			}
			if(totalPoints!=null && o.getTotalPoints()==null){
				return 1;
			}
			if(totalPoints==null && o.getTotalPoints()!=null){
				return -1;
			}
			return 0;
		}
	}

	@Data
	public static class ChallengeInstancePoints {
		private Long challengeInstanceId;
		private Long userId;
		private Double points;
		private Long timeTaken;
	}

}
