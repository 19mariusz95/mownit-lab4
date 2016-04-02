package algorithm.speed;

/**
 * Created by Mariusz on 02.04.2016.
 */
public enum SpeedImpl implements Speed {
    FAST {
        @Override
        public void slowDown() {

        }
    }, SLOW {
        @Override
        public void slowDown() throws InterruptedException {
            Thread.sleep(5);
        }
    };
}
