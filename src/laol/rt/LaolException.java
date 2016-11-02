package laol.rt;

/**
 *
 * @author gburdell
 */
public class LaolException extends RuntimeException {

    public LaolException(final String detail) {
        super(detail);
    }

    public static class Immutable extends LaolException {

        public Immutable(final String detail) {
            super(detail);
        }

        public Immutable() {
            this("cannot change immutable object");
        }
    }

    public static class InvalidType extends LaolException {

        public InvalidType(final String detail) {
            super(detail);
        }

        public InvalidType() {
            this("invalid type");
        }
    }
}
