package puzzles.clock;
import solver.Configuration;

import java.util.ArrayList;

/**
 * Configuration clock class for the solver algorithm
 *
 * @author Maija Philip
 * @author Caitlin Patton
 */
public class ClockConfiguration implements Configuration<Integer> {

    private int hours;
    private int start;
    private int end;

    public ClockConfiguration(int hours, int start, int end) {
        this.hours = hours;
        this.start = start;
        this.end = end;
    }

    @Override
    public boolean isSolution(Integer integer) {
        return integer == end;
    }

    @Override
    public ArrayList<Integer> getNeighbors(Integer integer) {
        int less = integer - 1;
        int more = integer + 1;

        if (less < 1) {
            less = hours;
        }
        if (more > hours) {
            more = 1;
        }

        ArrayList<Integer> result = new ArrayList<>();
        result.add(less);
        result.add(more);

        return result;
    }

    @Override
    public boolean equals(Object other) {
        boolean result = false;
        if (other instanceof ClockConfiguration) {
            ClockConfiguration otherC = (ClockConfiguration) other;
            result = this.hours == otherC.hours && this.start == otherC.start && this.end == otherC.end;

        }
        return result;
    }

    @Override
    public int hashCode() {
        return hours + start + end;
    }
}
