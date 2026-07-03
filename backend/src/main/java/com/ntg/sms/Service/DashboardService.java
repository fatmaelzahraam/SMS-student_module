import com.ntg.sms.Entities.Dtos.Response.CourseResponse;
import com.ntg.sms.Entities.Dtos.Response.DashboardResponse;
import com.ntg.sms.Repositories.AssignmentRepository;
import com.ntg.sms.Repositories.AttendanceRepository;
import com.ntg.sms.Repositories.MarkRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DashboardService {

    private final AttendanceRepository attendanceRepository;
    private final AssignmentRepository assignmentRepository;
    private final MarkRepository markRepository;

    @Transactional(readOnly = true)
    public DashboardResponse getDashboard(Long studentId) {

      //  AttendanceSummary attendance = attendanceRepository.getAttendanceSummary(studentId);

        CourseResponse.AssignmentSummary assignments =
                assignmentRepository.getAssignmentSummary(studentId);

        Double performance =
                markRepository.calculatePerformance(studentId);

        Integer rank =
                markRepository.getStudentRank(studentId);

        Long totalStudents =
                markRepository.countStudents();

        return DashboardResponse.builder()
                .attendance(attendance)
                .assignments(assignments)
                .performance(performance)
                .academicRank(rank)
                .totalStudents(totalStudents)
                .build();
    }
}

