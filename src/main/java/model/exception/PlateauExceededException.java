package model.exception;

public class PlateauExceededException extends Exception {

    private static final long serialVersionUID = 8981356657698723321L;

    public PlateauExceededException(String failureMessage) {
        super(failureMessage + " Movement abborted!");
    }

}