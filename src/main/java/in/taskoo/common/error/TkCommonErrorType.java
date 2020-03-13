package in.taskoo.common.error;

import lombok.AllArgsConstructor;
import lombok.Getter;
/**
 * This enum defines error message types with commonly used errors.
 * Please add a new error in the end by increasing the error code by one.
 * Error code must start with TK-1xxx.
 */
@Getter
@AllArgsConstructor
public enum TkCommonErrorType implements TkBaseErrorType {
    NOT_FOUND(              "TK-1001", "{source} is not found with id {identifier}"),
    NOT_ACCEPTABLE(         "TK-1002", "This action is not acceptable."),
    BAD_REQUEST(            "TK-1003", "This request contains bad data/format"),
    INTERNAL_SERVER_ERROR(  "TK-1004", "There is an internal server error"), 
    EXISTS(                 "TK-1005", "{source} already exists, with {identifier}"),
    DUPLICATE(              "TK-1006", "{source} can not have duplicates. {duplicate} is duplicated"),
    INVALID_ENUM(           "TK-1007", "The given value {value} for enum {enum} is not valid"),
    METHOD_NOT_ALLOWED(     "TK-1008", "This method is not allowed");

    private String errorCode;
    private String message;
}