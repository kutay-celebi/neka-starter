package tr.com.nekasoft.core.common.exception;

public interface NekaBaseException<M extends BaseExceptionMessage> {
    M getExceptionDetail();
}
