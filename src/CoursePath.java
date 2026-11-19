import java.util.ArrayList;
import java.util.List;

public class CoursePath {
    public static class Result {
        private final boolean P;
        private final boolean S;
        private final boolean H;
        private final boolean D;
        private final boolean eligible;
        private final List<String> messages;

        public Result(boolean P, boolean S, boolean H, boolean D, boolean eligible, List<String> messages) {
            this.P = P;
            this.S = S;
            this.H = H;
            this.D = D;
            this.eligible = eligible;
            this.messages = messages;
        }

        public boolean isPrerequisite() {
            return P;
        }

        public boolean isSeatAvailable() {
            return S;
        }

        public boolean hasRegistrationHold() {
            return H;
        }

        public boolean isRegistrationOpen() {
            return D;
        }

        public boolean isEligible() {
            return eligible;
        }

        public List<String> getMessages() {
            return messages;
        }
    }

    public static Result evaluate(String answer1, String answer2, String answer3, String answer4) {
        boolean P = answer1.equalsIgnoreCase("yes");
        boolean S = answer2.equalsIgnoreCase("yes");
        boolean H = answer3.equalsIgnoreCase("yes");
        boolean D = answer4.equalsIgnoreCase("yes");

        boolean eligible = P && S && !H && D;
        ArrayList<String> messages = new ArrayList<>();

        if (eligible) {
            messages.add("READY TO REGISTER: You meet all four registration conditions.");
        } else if (!P) {
            messages.add("CHECK PREREQUISITE: You must complete the prerequisite first.");
        }
        if (!S) {
            messages.add("CHOOSE ANOTHER SECTION: There are no available seats.");
        }
        if (H) {
            messages.add("REMOVE THE HOLD: You have a registration hold that needs to be resolved.");
        }
        if (!D) {
            messages.add("REGISTRATION IS CLOSED: The registration deadline passed.");
        }

        return new Result(P, S, H, D, eligible, messages);
    }
}
