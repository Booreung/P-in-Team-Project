import java.util.Random;

public class Main {
    public static void main(String[] args) {
        System.out.println("시작");
        final int FRAMES = 10;
        final int PINS = 10;
        int totalScore = 0;
        Random random = new Random();
        //안녕

        for (int frame = 1; frame <= FRAMES; frame++) {
            int firstRoll = roll(random, PINS);
            int secondRoll = 0;

            // 스트라이크
            if (firstRoll == PINS) {
                System.out.println("Frame " + frame + ": STRIKE!");
                totalScore += PINS + roll(random, PINS);
            } else {
                secondRoll = roll(random, PINS - firstRoll);
                System.out.println("Frame " + frame + ": " + firstRoll + " / " + secondRoll);

                // 스페어
                if (firstRoll + secondRoll == PINS) {
                    totalScore += PINS + roll(random, PINS);
                } else {
                    totalScore += firstRoll + secondRoll;
                }
            }
        }
        System.out.println("Total Score: " + totalScore);
    }

    // 주어진 핀 수 범위 내에서 랜덤하게 핀을 넘어뜨림
    public static int roll(Random random, int pins) {
        return random.nextInt(pins + 1);
    }
}
