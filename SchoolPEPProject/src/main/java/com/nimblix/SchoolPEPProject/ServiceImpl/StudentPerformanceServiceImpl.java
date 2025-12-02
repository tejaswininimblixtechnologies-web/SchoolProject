package com.nimblix.SchoolPEPProject.ServiceImpl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nimblix.SchoolPEPProject.Dto.ImprovementTrendDTO;
import com.nimblix.SchoolPEPProject.Dto.StudentPerformanceResponse;
import com.nimblix.SchoolPEPProject.Dto.SubjectScoreDTO;
import com.nimblix.SchoolPEPProject.Model.Score;
import com.nimblix.SchoolPEPProject.Model.Student;
import com.nimblix.SchoolPEPProject.Repository.ScoreRepository;
import com.nimblix.SchoolPEPProject.Repository.StudentRepository;
import com.nimblix.SchoolPEPProject.Service.StudentPerformanceService;

import jakarta.persistence.EntityNotFoundException;

@Service
public class StudentPerformanceServiceImpl implements StudentPerformanceService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private ScoreRepository scoreRepository;

    private SimpleDateFormat isoDate = new SimpleDateFormat("yyyy-MM-dd");

    @Override
    public StudentPerformanceResponse getStudentPerformance(Integer studentId) {
        Student student = studentRepository.findById(studentId).orElse(null);
        if (student == null) {
            return null;
        }

        List<Score> scores = scoreRepository.findByStudentId(studentId);

        // Build subject-wise list
        List<SubjectScoreDTO> subjectScores = new ArrayList<SubjectScoreDTO>();
        int totalMarks = 0;
        int totalMax = 0;
        for (Score sc : scores) {
            SubjectScoreDTO dto = new SubjectScoreDTO();
            dto.setSubject(sc.getSubject());
            dto.setMarks(sc.getMarks());
            dto.setMaxMarks(sc.getMaxMarks() == 0 ? 100 : sc.getMaxMarks());
            dto.setExamName(sc.getExamName());
            if (sc.getExamDate() != null) {
                dto.setExamDate(isoDate.format(sc.getExamDate()));
            }
            subjectScores.add(dto);
            totalMarks += sc.getMarks();
            totalMax += (sc.getMaxMarks() == 0 ? 100 : sc.getMaxMarks());
        }

        double overallPercent = 0.0;
        if (totalMax > 0) {
            overallPercent = ((double) totalMarks / (double) totalMax) * 100.0;
        }

        // Compute grade
        String grade = computeGrade(overallPercent);

        // Compute ranking (basic): compare average across all students
        int ranking = computeRanking(studentId);

        // Improvement trends: group by examDate (average percent per exam)
        Map<String, List<Double>> examDateToPercents = new TreeMap<String, List<Double>>();
        for (Score sc : scores) {
            if (sc.getExamDate() == null) continue;
            String d = isoDate.format(sc.getExamDate());
            double perc = (sc.getMaxMarks() == 0 ? 100.0 : sc.getMaxMarks()) > 0 ?
                    ((double) sc.getMarks() / (double) (sc.getMaxMarks() == 0 ? 100 : sc.getMaxMarks())) * 100.0 : 0.0;
            List<Double> arr = examDateToPercents.get(d);
            if (arr == null) {
                arr = new ArrayList<Double>();
                examDateToPercents.put(d, arr);
            }
            arr.add(perc);
        }
        List<ImprovementTrendDTO> trends = new ArrayList<ImprovementTrendDTO>();
        for (String d : examDateToPercents.keySet()) {
            List<Double> arr = examDateToPercents.get(d);
            double sum = 0.0;
            for (Double v : arr) { sum += v; }
            double avg = arr.size() > 0 ? sum / arr.size() : 0.0;
            ImprovementTrendDTO t = new ImprovementTrendDTO();
            t.setExamDate(d);
            t.setAveragePercent(round(avg));
            trends.add(t);
        }

        StudentPerformanceResponse response = new StudentPerformanceResponse();
        response.setStudentId(student.getId());
        response.setStudentName(student.getFullName());
        response.setSubjectScores(subjectScores);
        response.setOverallPercentage(round(overallPercent));
        response.setGrade(grade);
        response.setRanking(ranking);
        response.setImprovementTrends(trends);

        return response;
    }

    private String computeGrade(double percent) {
        if (percent >= 90.0) return "A+";
        if (percent >= 80.0) return "A";
        if (percent >= 70.0) return "B";
        if (percent >= 60.0) return "C";
        if (percent >= 50.0) return "D";
        return "F";
    }

    private int computeRanking(Integer studentId) {
        // compute average percent for every student based on scores table, then sort descending
        List<Score> allScores = scoreRepository.findAll();
        Map<Integer, List<Double>> map = new HashMap<Integer, List<Double>>();
        Map<Integer, Integer> maxSum = new HashMap<Integer, Integer>();
        for (Score s : allScores) {
            Integer sid = s.getStudentId();
            List<Double> arr = map.get(sid);
            if (arr == null) {
                arr = new ArrayList<Double>();
                map.put(sid, arr);
                maxSum.put(sid, 0);
            }
            double perc = (s.getMaxMarks() == 0 ? 100.0 : s.getMaxMarks()) > 0 ?
                    ((double) s.getMarks() / (double) (s.getMaxMarks() == 0 ? 100 : s.getMaxMarks())) * 100.0 : 0.0;
            arr.add(perc);
            maxSum.put(sid, maxSum.get(sid) + (s.getMaxMarks() == 0 ? 100 : s.getMaxMarks()));
        }

        // compute averages
        Map<Integer, Double> avgMap = new HashMap<Integer, Double>();
        for (Integer sid : map.keySet()) {
            List<Double> arr = map.get(sid);
            double sum = 0.0;
            for (Double d : arr) sum += d;
            double avg = arr.size() > 0 ? sum / arr.size() : 0.0;
            avgMap.put(sid, avg);
        }

        // count how many have higher average than given student
        Double target = avgMap.get(studentId);
        if (target == null) {
            return avgMap.size() + 1; // no scores => last
        }
        int rank = 1;
        for (Integer sid : avgMap.keySet()) {
            if (sid.equals(studentId)) continue;
            Double v = avgMap.get(sid);
            if (v != null && v > target) {
                rank++;
            }
        }
        return rank;
    }

    private double round(double v) {
        return Math.round(v * 100.0) / 100.0;
    }
}

