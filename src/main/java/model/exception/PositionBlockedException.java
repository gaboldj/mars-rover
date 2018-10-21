package model.exception;

public class PositionBlockedException extends Exception {

    private static final long serialVersionUID = 806679988884005473L;

    public PositionBlockedException(String failureMessage) {
        super(failureMessage + " Movement abborted!");
    }

}