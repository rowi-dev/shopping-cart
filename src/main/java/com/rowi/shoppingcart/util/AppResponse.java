package com.rowi.shoppingcart.util;

import lombok.*;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AppResponse<T> {

    private T data;
    private MetaDataResponseDto meta;
    private ErrorResponseDto error;
    private static final Integer STATUS_CODE_SUCCESS = 1;
    private static final String STATUS_SUCCESS = "Success";
    private static final Integer STATUS_CODE_ERROR = 0;
    private static final String STATUS_ERROR = "Error";

    public static <T> AppResponse<T> accepted() {
        MetaDataResponseDto metaDto = MetaDataResponseDto.builder().code(HttpStatus.ACCEPTED.value()).message(HttpStatus.ACCEPTED.name())
                .build();
        return AppResponse.<T>builder().meta(metaDto).build();
    }

    public static <T> AppResponse<T> ok(T data) {
        MetaDataResponseDto metaDto = MetaDataResponseDto.builder().code(STATUS_CODE_SUCCESS).message(STATUS_SUCCESS)
                .build();
        return AppResponse.<T>builder().data(data).meta(metaDto).build();
    }

    public static <T> AppResponse<T> okList(T dataList) {
        MetaDataResponseDto metaDto = MetaDataResponseDto.builder().code(STATUS_CODE_SUCCESS).message(STATUS_SUCCESS)
                .build();

        return AppResponse.<T>builder().data(dataList).meta(metaDto).build();
    }

    public static <T> AppResponse<T> error(int status, String detail) {
        MetaDataResponseDto metaDto = MetaDataResponseDto.builder().code(STATUS_CODE_ERROR).message(STATUS_ERROR)
                .build();

        ErrorResponseDto errorDto = ErrorResponseDto.builder().status(status)/*.source(source).title(title)*/.detail(detail)
                .build();

        return AppResponse.<T>builder().data(null).meta(metaDto).error(errorDto).build();
    }

}
