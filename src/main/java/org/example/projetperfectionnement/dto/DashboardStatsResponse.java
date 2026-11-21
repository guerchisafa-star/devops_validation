package org.example.projetperfectionnement.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DashboardStatsResponse {
    private Long totalInterviews;
    private Double averageScore;
    private Double passRate;
    private Long todayInterviews;
    private Long totalResults;
    private Long acceptedResults;
    private Long rejectedResults;
    //dto --> data transfer object 
    //permet de transfere des donnees
    //sans toucher entiity pour -->securité/simplicité/performance
}